package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.example.client.view.ToolBarItemView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToolBarController {
    @FXML
    HBox elementsContainer;

    @FXML
    ToolBar toolBar;
    List<Object> objects;
    List<ToolBarItemController> toolBarItemControllers = new ArrayList<>();

    public ToolBarController( List<Object> objects) {
        this.objects = objects;
    }

    @FXML
    public void initialize(){
        for (Object o : objects){
            ToolBarItemView toolBarItemView = new ToolBarItemView(o);
            toolBarItemControllers.add(toolBarItemView.getToolBarController());
            toolBar.getItems().add(toolBarItemView.getView());
        }
    }

    public Parent getToolbarItem(Object o){
        HBox toolbarItem = new HBox();
        Text text = new Text(o.toString());
        toolbarItem.getChildren().add(text);
        return toolbarItem;
    }
}
