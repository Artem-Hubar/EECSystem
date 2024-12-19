package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import org.example.client.controllers.ToolBarController;


import java.util.List;
@Getter
public class ToolBarView {
    private final List<Object> objectModel;
    private final Parent view;
    private final ToolBarController toolBarController;


    public ToolBarView( List<Object> objectModel) {
        this.toolBarController = new ToolBarController(objectModel);
        this.objectModel = objectModel;
        this.view = loadView();
    }


    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/toolBar.fxml"));
            loader.setController(toolBarController);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }
}
