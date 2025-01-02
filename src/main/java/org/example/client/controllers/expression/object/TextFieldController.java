package org.example.client.controllers.expression.object;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Setter;

public class TextFieldController extends ExpressionObjectController {
    @FXML
    private TextField textField;

    public void setInitValue(Double initValue) {
        this.initValue = initValue;
        System.out.println(6 + initValue);
    }

    private Double initValue = 0.0;

    @Override
    public Object getObject() {
        return textField;
    }
    @FXML
    public void initialize() {
        System.out.println(initValue);
        if (initValue!=0){
            System.out.println(4);
            textField.setText(initValue.toString());
        }
    }

    public double getValue(){
        return Double.parseDouble(textField.getText());
    }

    public String getMethodString() {
        return "getText";
    }
}
