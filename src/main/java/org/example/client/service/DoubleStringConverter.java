package org.example.client.service;

import javafx.util.StringConverter;

public class DoubleStringConverter extends StringConverter<Number> {

    @Override
    public String toString(Number object) {
        return object == null ? "" : object.toString();
    }

    @Override
    public Number fromString(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
