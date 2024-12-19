package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Getter;
import org.example.entity.Device;

@Getter
public class ToolBarItemController {
    private final Object object;
    @FXML
    Label toolBarText;

    public ToolBarItemController(Object object) {
        this.object = object;
    }

    @FXML
    public void initialize() {
        toolBarText.setText(toStringObject(object));
    }

    public String toStringObject(Object o) {
        if (o instanceof Device device) {
            String[] className = device.getClass().getName().split("\\.");
            return className[className.length - 1] + " " + device.getSensorId();
        } else {
            return o.getClass().getName();
        }
    }
}
