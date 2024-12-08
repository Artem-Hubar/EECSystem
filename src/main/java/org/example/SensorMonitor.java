package org.example;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class SensorMonitor{

    public static class Sensor {
        private final StringProperty data = new SimpleStringProperty("0.0");

        public String getData() {
            return data.get();
        }

        public void setData(String value) {
            data.set(value);
        }

        public StringProperty dataProperty() {
            return data;
        }
    }


    public void start(Stage primaryStage) {
        Sensor sensor = new Sensor();
        startSensorDataUpdate(sensor);

        VBox root = createSensorScene(sensor);

        Scene scene = new Scene(root, 400, 250);
        primaryStage.setTitle("Sensor Monitor with CheckBox");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSensorScene(Sensor sensor) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Заголовок
        Label titleLabel = new Label("Sensor Data Monitor");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Мітка для показу даних сенсора
        Label dataLabel = new Label();
        dataLabel.textProperty().bind(sensor.dataProperty());
        dataLabel.setStyle("-fx-font-size: 16px;");

        // CheckBox для показу стану
        CheckBox statusCheckBox = new CheckBox("Sensor Active");
        statusCheckBox.setStyle("-fx-font-size: 14px;");
        statusCheckBox.setDisable(true); // Робимо недоступним для користувача

        // Логіка оновлення CheckBox залежно від значення даних
        sensor.dataProperty().addListener((obs, oldValue, newValue) -> {
            try {
                double value = Double.parseDouble(newValue);
                statusCheckBox.setSelected(value > 50.0); // CheckBox активний, якщо значення > 50
            } catch (NumberFormatException e) {
                statusCheckBox.setSelected(false); // Якщо не число, CheckBox неактивний
            }
        });

        root.getChildren().addAll(titleLabel, new Label("Current Data:"), dataLabel, statusCheckBox);

        return root;
    }

    private void startSensorDataUpdate(Sensor sensor) {
        Runnable task = () -> {
            Random random = new Random();
            while (true) {
                try {
                    // Імітуємо оновлення даних сенсора
                    Thread.sleep(1000); // Оновлення кожну секунду
                    double newValue = random.nextDouble() * 100; // Генеруємо випадкове значення від 0 до 100
                    Platform.runLater(() -> sensor.setData(String.format("%.2f", newValue))); // Оновлюємо UI
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true); // Потік завершується разом із додатком
        thread.start();
    }

}
