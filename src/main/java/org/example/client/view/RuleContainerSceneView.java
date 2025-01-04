package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Data;
import org.example.client.controllers.RuleContainerController;

@Data
public class RuleContainerSceneView {
    private Parent view;
    private RuleContainerController controller;

    public RuleContainerSceneView() {
        this.controller = new RuleContainerController();
        this.view = loadView();
    }

    public RuleContainerSceneView(RuleContainerController controller) {
        this.controller = controller;
        this.view = loadView();
    }

    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RuleContainer.fxml"));
            loader.setController(controller);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }
}

