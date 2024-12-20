package org.example.client.controllers.expression.object;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TextFieldController extends ExpressionObjectController {
    @FXML
    TextField textField;

    @Override
    public Object getObject() {
        return textField;
    }

    public double getValue(){
        return Double.parseDouble(textField.getText());
    }

    public String getMethodString() {
        return "getText";
    }
}
