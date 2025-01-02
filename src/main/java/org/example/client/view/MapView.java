package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Data;
import org.example.client.controllers.MapController;

import java.io.IOException;
import java.util.List;

@Data
public class MapView {

    private Parent view;
    private MapController controller;

    public MapView(List<Object> objects) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/map.fxml"));
        try {
            this.view = loader.load();
            this.controller = loader.getController();
            controller.setDevices(objects);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load view", e);
        }
    }
}
