package org.example.client.ruleparser;

import org.example.client.controllers.ExpressionController;
import org.example.entity.Device;

public class ObjectExpressionControllerParser {
    public ExpressionController getExpressionController(Object[] expressionData) {
        Object targetObject = expressionData[0];
        String method = (String) expressionData[1];
        String operand = (String) expressionData[2];

        if (targetObject instanceof Device device) {
            ExpressionController deviceController = new ExpressionController(device);
            deviceController.setSelectedOperator(operand);
            deviceController.setDeviceMethod(method);
            return deviceController;
        } else if (targetObject instanceof Number value) {

            ExpressionController deviceController = new ExpressionController(value);
            deviceController.setSelectedOperator(operand);
            return deviceController;
        } else {
            throw new IllegalArgumentException("Unsupported object type: " + targetObject.getClass());
        }
    }
}
