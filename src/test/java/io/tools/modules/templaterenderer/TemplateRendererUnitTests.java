package io.tools.modules.templaterenderer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemplateRendererUnitTests {

    @Test
    public void testRenderFromTemplateWithAttributesBasicTemplate() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Hello, {{name}}!";
        Map<String, String> values = new HashMap<>();
        values.put("name", "Sofiene");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Hello, Sofiene!", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesShouldNotRenderWhenOnlyPaddingIsPresent() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Item: {{item(pad='0')}}";
        Map<String, String> values = new HashMap<>();
        values.put("item", "5");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Item: ", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithWidthOnly() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Total: {{total(width=10)}}";
        Map<String, String> values = new HashMap<>();
        values.put("total", "50");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Total:         50", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithWidthAndPaddingCharacter() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Code: {{code(width=5, pad='0')}}";
        Map<String, String> values = new HashMap<>();
        values.put("code", "7");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Code: 00007", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithLongValueThatShouldBeTruncated() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Item: {{item(width=5)}}";
        Map<String, String> values = new HashMap<>();
        values.put("item", "long_value");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Item: long_", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesBasicTemplateWithMissingKey() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Hello, {{name}}!";
        Map<String, String> values = new HashMap<>();

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Hello, !", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesBasicTemplateAndNullValue() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Hello, {{name}}!";
        Map<String, String> values = new HashMap<>();
        values.put("name", null);

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Hello, !", result);  // Null value
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithWidthAttributeAndMissingKey() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Hello, {{name (width=10)}}!";
        Map<String, String> values = new HashMap<>();

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Hello,           !", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithWidthAttributeAndNullValue() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Hello, {{name (width=10)}}!";
        Map<String, String> values = new HashMap<>();
        values.put("name", null);

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Hello,           !", result);  // Null value
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithWidthAttributePadAndNullValue() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Hello, {{name (width=10, pad='*')}}!";
        Map<String, String> values = new HashMap<>();
        values.put("name", null);

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Hello, **********!", result);  // Null value
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithMultipleVariables() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Hello, {{firstName}} {{lastName}}!";
        Map<String, String> values = new HashMap<>();
        values.put("firstName", "John");
        values.put("lastName", "Doe");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Hello, John Doe!", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithDefaultPadding() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Welcome: {{username(width=10)}}";
        Map<String, String> values = new HashMap<>();
        values.put("username", "Alice");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Welcome:      Alice", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithCustomPadding() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Hello: {{name(width=10, pad='*')}}";
        Map<String, String> values = new HashMap<>();
        values.put("name", "Bob");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Hello: *******Bob", result);
    }
    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithMultipleValuesAndAttributes() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Product: {{productName(width=15, pad='-')}} | Price: {{price(width=10)}} | Code: {{code(width=5, pad='0')}}";
        Map<String, String> values = new HashMap<>();
        values.put("productName", "Super Widget");
        values.put("price", "99.99");
        values.put("code", "123");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Product: ---Super Widget | Price:      99.99 | Code: 00123", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithNumericFlagAndValidDecimal() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Total: {{total (width=10, --numeric)}}";
        Map<String, String> values = new HashMap<>();
        values.put("total", "1234.56");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        assertEquals("Total: 0000123456", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithNumericFlagAndZeroValue() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Total: {{total(width=6, --numeric)}}";
        Map<String, String> values = new HashMap<>();
        values.put("total", "0");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Total: 000000", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithNumericFlagAndMissingKey() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Total: {{total(width=6, --numeric)}}";
        Map<String, String> values = new HashMap<>();

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Total: 000000", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithNumericFlagAndNonNumericString() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Total: {{total(width=6, --numeric)}}";
        Map<String, String> values = new HashMap<>();
        values.put("total", "abc");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Total:    abc", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithoutNumericFlagAndValidDecimal() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Total: {{total(width=10, pad='0')}}";
        Map<String, String> values = new HashMap<>();
        values.put("total", "1234.56");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Total: 0001234.56", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesTemplateWithNumericFlagAndLargeNumber() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Total: {{total(width=10, --numeric)}}";
        Map<String, String> values = new HashMap<>();
        values.put("total", "1234567890.12");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Total: 1234567890", result);
    }

    @Test
    public void testRenderFromTemplateWithAttributesShouldRenderNothingWhenNumericFlagAndPadAttributeExist() throws IOException {
        // Given
        TemplateRenderer renderer = new TemplateRenderer();
        String template = "Total: {{total(width=10, pad='0', --numeric)}}";
        Map<String, String> values = new HashMap<>();
        values.put("total", "890.12");

        // When
        String result = renderer.renderFromTemplateWithAttributes(template, values);

        // Then
        assertEquals("Total: ", result);
    }
}
