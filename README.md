# Template Renderer

## Purpose

The Template Renderer is a Java library designed to facilitate the rendering of text templates with dynamic values and formatting options.
It allows users to create templates that can include variables with various attributes such as width and a specific padding character.
The library is useful for generating formatted strings in applications such as reporting, logging, and user notifications.

## Features

- Render templates with variable substitution.
- Support for specific padding characters and width specifications.
- Truncation of long values to fit within specified widths.
- Handling of missing or null variables gracefully.
- Numeric formatting options for decimal values.

## Constraints on Attributes

### Variable Attributes

- **Width (`width`)**:
  - Must be a positive integer.
  - If the value's length exceeds the specified width, it will be truncated from the right.

- **Padding Character (`pad`)**:
  - Must be a single character.
  - should be specified with `width` `(width=10, pad='*')`
  - the padding character will be used to fill the space before the value.
  - If specified, it cannot be used in conjunction with the numeric flag.

- **Numeric Flag (`--numeric`)**:
  - If specified, the renderer will format the value as a numeric value by replacing the decimal point to comma, and the value will be padded by zeros.
  - should be specified with `width` `(width=10, --numeric)`
  - If specified, it cannot be used in conjunction with a padding character.
