package io.tools.modules.templaterenderer;

public class TemplateRendererFactory {

    public static TemplateRenderer create() {
        return new TemplateRendererMustacheImplementation();
    }
}
