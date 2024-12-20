package org.example.client.controllers.parser;

import javafx.scene.control.TextField;
import org.example.client.controllers.ExpressionController;
import org.example.client.controllers.expression.object.ExpressionObjectController;
import org.example.entity.Device;

public class ExpressionObjectListParser {
    public Object[] getExpression(Object targetObject, ExpressionController expressionController){
        Object[] expression = new Object[3];
        if (isDevice(targetObject)){
            String method = getMethod(expressionController);
            String operand = expressionController.getChoiceBox().getValue();
            expression = new Object[]{targetObject, method, operand};
        }else if (isTextField(targetObject)){
            TextField textField = (TextField) targetObject;
            targetObject = Double.parseDouble(textField.getText());
            expression = new Object[]{targetObject, null, null};
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
