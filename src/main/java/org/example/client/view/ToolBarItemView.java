package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import lombok.Getter;
import org.example.client.controllers.ToolBarItemController;

@Getter
public class ToolBarItemView {
    private final Object objectModel;
    private final Parent view;
    private final ToolBarItemController toolBarController;


    public ToolBarItemView(Object objectModel) {
        this.toolBarController = new ToolBarItemController(objectModel);
        this.objectModel = objectModel;
        this.view = loadView();
    }


    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/toolBarItem.fxml"));
            loader.setController(toolBarController);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }
}
