package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.Setter;
import org.example.client.service.ObjectService;
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

    ExpressionsContainerController expressionsContainerControllers;
    @Setter
    Object selectedObject;
    @Setter
    String selectedField;
    @Setter
    ExpressionsContainerView expressionsContainerView;
    private Runnable onDelete;

    public ActionController() {
        ObjectService objectService = ObjectService.getInstance();
        this.objects = objectService.getObjects();
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
        if (selectedObject != null) actionObjectSelector.setValue(selectedObject);
        if (selectedField != null) actionFieldSelector.setValue(selectedField);
        if (expressionsContainerView != null) {
            addExpressionsContainerView(expressionsContainerView);
        }else {
            ExpressionsContainerView expressionsContainerView = new ExpressionsContainerView(objects);
            addExpressionsContainerView(expressionsContainerView);
        }
    }

    public void addExpressionsContainerView(ExpressionsContainerView expressionsContainerView) {
        Parent expressionScene = expressionsContainerView.getView();
        ExpressionsContainerController expressionsContainerController = expressionsContainerView.getExpressionsContainerController();
        expressionsContainer.getChildren().add(expressionScene);
        expressionsContainerControllers = expressionsContainerController;
    }


    @FXML
    private void handleDeleteButtonAction() {
        onDelete.run();
    }
}
