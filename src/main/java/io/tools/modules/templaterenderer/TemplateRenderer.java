package io.tools.modules.templaterenderer;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateRenderer implements TemplateRendererPort {

    private static final String attributesRegexInMustache = "\\{\\{(\\w+)(?:\\s*\\(\\s*width=(\\d+)\\s*(?:,\\s*pad='([^']*)'|,\\s*(--numeric))?\\s*\\))?\\s*}}";
    private static final Pattern attributesPattern = Pattern.compile(attributesRegexInMustache);

    @Override
    public String renderFromTemplateWithAttributes(String template, Map<String, String> values) throws IOException {
        Map<String, String> formattedValues = matchPatternAndFormatValues(template, values);
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        String templateWithoutAttributes = removeAttributesFromTemplate(template);
        Mustache mustache = mustacheFactory.compile(new StringReader(templateWithoutAttributes), "template");
        Writer writer = new StringWriter();
        mustache.execute(writer, formattedValues).flush();
        return writer.toString();
    }

    private Map<String, String> matchPatternAndFormatValues(String template, Map<String, String> values) {
        Matcher matcher = attributesPattern.matcher(template);
        return formatValues(values, matcher);
    }

    private Map<String, String> formatValues(Map<String, String> values, Matcher matcher) {
        Map<String, String> formattedValues = new HashMap<>();
        while (matcher.find()) {
            String key = matcher.group(1);
            String widthAttribute = matcher.group(2);
            String padCharacterAttribute = matcher.group(3);
            String numericFlag = matcher.group(4) ;

            boolean isNumericFlagEnabled = Objects.nonNull(numericFlag);

            String value = setDefaultValueIfNumericFlagIsEnabled(values, isNumericFlagEnabled, key);

            formattedValues.put(key,
                    StringFormatterFactory.createFormatter(value,widthAttribute,padCharacterAttribute,isNumericFlagEnabled)
                            .format());
        }
        return formattedValues;
    }

    private  String setDefaultValueIfNumericFlagIsEnabled(Map<String, String> values, boolean isNumericField, String key) {
        return isNumericField ? values.getOrDefault(key, "0") : values.get(key);
    }

    private String removeAttributesFromTemplate(String template) {
        String regex = "\\{\\{\\s*([^\\s(]+)(?:\\s*\\(.*?\\))?\\s*\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);
        return matcher.replaceAll("{{ $1 }}");
    }
}
