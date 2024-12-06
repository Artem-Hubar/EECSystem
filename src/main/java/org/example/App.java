package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App extends Application
{
//    private Parent createContent() {
//        return new StackPane(new Text("Hello world"));
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setScene(new Scene(createContent(), 300, 300));
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
@Override
public void start(Stage primaryStage) throws IOException {
    // Создание и настройка менеджера сцен
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
}

    public static void main(String[] args) {
        launch(args);
    }
}
