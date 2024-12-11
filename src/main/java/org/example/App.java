package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.client.service.ObjectService;
import org.example.client.UIManager;

import java.util.List;

public class App extends Application {
    @Getter
    private static List<Object> objects;

    @Override
    public void start(Stage primaryStage) {
        objects = ObjectService.loadObjects();
        UIManager uiManager = new UIManager(primaryStage);
        uiManager.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
