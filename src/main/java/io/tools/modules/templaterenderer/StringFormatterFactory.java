package io.tools.modules.templaterenderer;

public class StringFormatterFactory {

    public static StringFormatter createFormatter(String value, String widthAttribute, String padCharacterAttribute, boolean isNumericValueFlag) {
        return new StringFormatter(value,widthAttribute,padCharacterAttribute,isNumericValueFlag);
    }

}
