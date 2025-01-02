package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Data;
import org.example.client.controllers.FullDeviceController;
import org.example.client.controllers.RuleBuilderSceneController;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Data
public class RuleSceneView {
    private Parent view;
    private RuleBuilderSceneController controller;

    public RuleSceneView(RuleBuilderSceneController controller){
        this.controller = controller;
        this.view = loadView();
    }

    public RuleSceneView(){
        this.controller = new RuleBuilderSceneController();
        this.view = loadView();

    }

    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RuleBuilderScene.fxml"));
            loader.setController(controller);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }
}

