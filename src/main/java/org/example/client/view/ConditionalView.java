package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import org.example.client.controllers.ConditionalController;

import java.util.List;
@Getter
public class ConditionalView {

    private final Parent view; // Графический интерфейс устройства
    private final ConditionalController conditionalView;

    public ConditionalView() {

        conditionalView = new ConditionalController();
        this.view = loadView();
    }

    public ConditionalView(ConditionalController controller) {
        conditionalView = controller;
        this.view = loadView();
    }

    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConditionalScene.fxml"));
            loader.setController(conditionalView);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }
}
