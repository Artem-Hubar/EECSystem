package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Data;
import org.example.client.controllers.ActionController;

import java.util.List;

@Data
public class ActionView {
    private final Parent view;
    private final ActionController controller;

    public ActionView(List<Object> objects) {
        controller = new ActionController(objects);
        this.view = loadView();
    }

    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/actionView.fxml"));
            loader.setController(controller);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }
}
