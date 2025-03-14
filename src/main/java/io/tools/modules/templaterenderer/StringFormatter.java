package io.tools.modules.templaterenderer;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StringFormatter {

    public static final char DECIMAL_DELIMITER = ',';
    public static final char DEFAULT_PAD_CHARACTER = ' ';
    public static final char NUMERIC_PAD_CHARACTER = '0';
    public static final String DEFAULT_VALUE = "";

    private String value;
    private String widthAttribute;
    private String padCharacterAttribute;
    private boolean isNumericValueFlag;

    public StringFormatter(String value, String widthAttribute, String padCharacterAttribute, boolean isNumericValueFlag) {
        this.value = value;
        this.widthAttribute = widthAttribute;
        this.padCharacterAttribute = padCharacterAttribute;
        this.isNumericValueFlag = isNumericValueFlag;
    }

    public String format() {
        if(Objects.isNull(value)) {
            value = DEFAULT_VALUE;
        }
        int width =  getWidth();
        char padCharacter =  getPadCharacter();
        if (isNumericValueFlag && isNumeric()) {
            value = replaceDecimalPoint();
            padCharacter = NUMERIC_PAD_CHARACTER;
        }
        return formatString(width, padCharacter);
    }

    private char getPadCharacter() {
        if (Objects.nonNull(widthAttribute)  && Objects.nonNull(padCharacterAttribute)) {
            return padCharacterAttribute.charAt(0);
        }
        return DEFAULT_PAD_CHARACTER;
    }

    private int getWidth() {
        if (Objects.nonNull(widthAttribute))
            return Integer.parseInt(widthAttribute);
        return value.length();
    }

    private String formatString(int width, char padCharacter) {
        if (isStringTooLong(width)) {
            return value.substring(0, width);
        }
        return padString(width, padCharacter);
    }

    private boolean isStringTooLong(int width) {
        return value.length() > width;
    }

    private String padString(int width, char padCharacter) {
        int paddingNeeded = width - value.length();
        String padding = Stream.generate(() -> String.valueOf(padCharacter))
                .limit(paddingNeeded)
                .collect(Collectors.joining());
        if (isNumericValueFlag)
            return addPaddingLeft(padding);
        return addPaddingRight(padding);
    }

    private String replaceDecimalPoint() {
        return value.replace('.', DECIMAL_DELIMITER);
    }

    private boolean isNumeric() {
        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String addPaddingRight(String padding) {
        return value + padding;
    }

    private String addPaddingLeft(String padding) {
        return padding + value;
    }
}
