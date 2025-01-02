package org.example.client.controllers.parser;

import javafx.scene.control.TextField;
import org.example.client.controllers.ExpressionController;
import org.example.client.controllers.expression.object.ExpressionObjectController;
import org.example.entity.Device;

public class ExpressionObjectListParser {
    public Object[] getExpression(Object targetObject, ExpressionController expressionController){
        Object[] expression = new Object[3];
        String operand = expressionController.getChoiceBox().getValue();
        if (isDevice(targetObject)){
            String method = getMethod(expressionController);
            expression = new Object[]{targetObject, method, operand};
        }else if (isTextField(targetObject)){
            TextField textField = (TextField) targetObject;
            targetObject = Double.parseDouble(textField.getText());
            expression = new Object[]{targetObject, null, operand};
        }
        return expression;
    }

    private boolean isTextField(Object targetObject) {
        return targetObject instanceof TextField;
    }

    private boolean isDevice(Object targetObject) {
        return targetObject instanceof Device;
    }

    private String getMethod(ExpressionController expressionController) {
        ExpressionObjectController expressionObjectController = expressionController.getExpressionObjectController();
        return expressionObjectController.getMethodString();
    }

}
