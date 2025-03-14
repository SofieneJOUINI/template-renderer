package io.tools.modules.templaterenderer;

import java.io.IOException;
import java.util.Map;

public interface TemplateRenderer {
    String renderFromTemplateWithAttributes(String template, Map<String, String> values) throws IOException;
}
