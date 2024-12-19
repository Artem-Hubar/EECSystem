package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.example.client.view.DeviceView;
import org.example.entity.Device;

@Getter
public class ExpressionController {
    private final Device device;
    private final String selectedOperator;
    @FXML
    private HBox expressionsBox;
    private DeviceView deviceView;
    ChoiceBox<String> choiceBox = new ChoiceBox<>();;
    private DeviceViewController deviceViewController;



    public ExpressionController(Device device, String selectedOperator) {
        this.device = device;
        this.selectedOperator = selectedOperator;
        deviceView = new DeviceView(device);
        deviceViewController = deviceView.getDeviceViewController();
    }
    @FXML
    public void initialize() {
        expressionsBox.getChildren().add(deviceView.getView());
        if (isNotEnd()) {
            choiceBox.getItems().addAll("+", "-", "/", "*", "==", ">", "<", ">=", "<=");
            expressionsBox.getChildren().add(choiceBox);
        }

    }

    private boolean isNotEnd() {
        if (selectedOperator == null) return true;
        if (
                selectedOperator.equals("==") ||
                selectedOperator.equals("<") ||
                selectedOperator.equals(">") ||
                selectedOperator.equals(">=") ||
                selectedOperator.equals("<=")) {
            return false;
        }
        return true;
    }
}
