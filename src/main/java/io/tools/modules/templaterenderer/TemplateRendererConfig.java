package io.tools.modules.templaterenderer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateRendererConfig {
    @Bean
    public TemplateRenderer templateRenderer() {
        return new TemplateRendererMustacheImplementation();
    }
}