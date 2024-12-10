package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.entity.Device;
import org.example.service.InflexDBService;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
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
