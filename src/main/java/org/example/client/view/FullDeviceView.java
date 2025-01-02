package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Data;
import org.example.client.controllers.FullDeviceController;

@Data
public class FullDeviceView {
    private Parent view;
    private FullDeviceController controller;

    public FullDeviceView(Object object) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fullDevice.fxml"));
            this.view = loader.load();
            this.controller = loader.getController();
            controller.setDevice(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load view", e);
        }

    }
}

