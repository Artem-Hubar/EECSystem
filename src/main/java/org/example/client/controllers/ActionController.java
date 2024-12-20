package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.example.client.view.ExpressionsContainerView;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActionController {
    private final List<Object> objects;
    @FXML
    private ComboBox<Object> actionObjectSelector;
    @FXML
    private ComboBox<String> actionFieldSelector;
    @FXML
    private VBox expressionsContainer;
    List<ExpressionsContainerController> expressionsContainerControllers = new ArrayList<>();

    private Runnable onDelete;


    public ActionController(List<Object> objects) {
        this.objects = objects;
    }


    @FXML
    public void initialize() {
        actionObjectSelector.getItems().addAll(objects);
        actionObjectSelector.setOnAction(e -> {
            Object selectedObject = actionObjectSelector.getValue();
            if (selectedObject != null) {
                actionFieldSelector.getItems().clear();
                for (var method : selectedObject.getClass().getDeclaredMethods()) {
                    actionFieldSelector.getItems().add(method.getName());
                }
            }
        });
        ExpressionsContainerView expressionsContainerView = new ExpressionsContainerView(objects);
        Parent expressionScene = expressionsContainerView.getView();
        ExpressionsContainerController expressionsContainerController = expressionsContainerView.getExpressionsContainerController();
        expressionsContainer.getChildren().add(expressionScene);
        expressionsContainerControllers.add(expressionsContainerController);
    }




    @FXML
    private void handleDeleteButtonAction() {
        onDelete.run();
    }
}
