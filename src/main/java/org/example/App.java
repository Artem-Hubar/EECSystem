package org.example;

import javafx.application.Application;
import javafx.application.Platform;
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
        ObjectService objectService = ObjectService.getInstance();
        objects = objectService.getObjects();
        UIManager uiManager = new UIManager(primaryStage);
        uiManager.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Closing all windows");
        Platform.exit(); // Закрывает все окна
        System.exit(0);  // Полностью завершает JVM
    }



    public static void main(String[] args) {
        launch(args);
    }

}
