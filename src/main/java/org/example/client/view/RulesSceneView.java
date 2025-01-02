package org.example.client.view;

import javafx.scene.Parent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.client.controllers.RulesSceneController;

@EqualsAndHashCode(callSuper = true)
@Data
public class RulesSceneView extends View {
    private Parent view;
    private RulesSceneController controller;

    public RulesSceneView() {
        controller = new RulesSceneController();
        view = loadView(controller, "/RulesScene.fxml");
    }
}
