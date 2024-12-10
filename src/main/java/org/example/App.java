package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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
//@Override
//public void start(Stage primaryStage) throws IOException {
//    // Создание и настройка менеджера сцен
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
//    Parent root = loader.load();
//
//    Scene scene = new Scene(root);
//    primaryStage.setScene(scene);
//    primaryStage.show();
//}

    private final List<Object> objects = new ArrayList<>(); // Список объектов
    private Stage primaryStage;
    private VBox mainLayout;
//    @Override
//    public void start(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//        initObjects();
//
//        // Показ первого окна
//        showWithMenu();
//    }


    @Override
    public void start(Stage stage) throws Exception {
        SensorMonitor sensorMonitor = new SensorMonitor();
        sensorMonitor.start(stage);
    }

    private void showWithMenu() {
        // Создание корневого контейнера
        this.primaryStage = primaryStage;

        // Инициализация общего корневого контейнера
        mainLayout = new VBox();

        // Добавление меню
        MenuBar menuBar = createMenuBar();
        mainLayout.getChildren().add(menuBar);

        // Установка стартового окна
        showMainScene();

        // Настройка и отображение сцены
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Приложение с меню");
        primaryStage.show();
    }

    // Метод для отображения первого окна
    private void showMainScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
            Parent mainContent = loader.load();

            // Обновление содержимого основного контейнера
            updateContent(mainContent);

            primaryStage.setTitle("Главное окно");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSecondScene() {
        // Main container
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Title
        Label title = new Label("Конструктор правил");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Object selector for the condition
        ComboBox<Object> objectSelector = new ComboBox<>();
        objectSelector.getItems().addAll(objects);
        objectSelector.setPromptText("Выберите объект");

        // Field selector for the condition
        ComboBox<String> fieldSelector = new ComboBox<>();
        fieldSelector.setPromptText("Выберите поле");

        // Update fields when an object is selected
        objectSelector.setOnAction(e -> {
            Object selectedObject = objectSelector.getValue();
            if (selectedObject != null) {
                fieldSelector.getItems().clear();
                for (var field : selectedObject.getClass().getDeclaredFields()) {
                    fieldSelector.getItems().add(field.getName());
                }
            }
        });

        // Operator selector
        ComboBox<String> operatorSelector = new ComboBox<>();
        operatorSelector.getItems().addAll("==", "!=", ">", "<", ">=", "<=");
        operatorSelector.setPromptText("Выберите оператор");

        // Comparison value input
        TextField comparisonValue = new TextField();
        comparisonValue.setPromptText("Введите значение для сравнения");

        // Action selectors
        ComboBox<Object> actionObjectSelector = new ComboBox<>();
        actionObjectSelector.getItems().addAll(objects);
        actionObjectSelector.setPromptText("Выберите объект для действия");

        ComboBox<String> actionFieldSelector = new ComboBox<>();
        actionFieldSelector.setPromptText("Выберите поле для действия");

        actionObjectSelector.setOnAction(e -> {
            Object selectedObject = actionObjectSelector.getValue();
            if (selectedObject != null) {
                actionFieldSelector.getItems().clear();
                for (var field : selectedObject.getClass().getDeclaredFields()) {
                    actionFieldSelector.getItems().add(field.getName());
                }
            }
        });

        TextField actionValue = new TextField();
        actionValue.setPromptText("Введите новое значение");

        // Add rule button
        Button addRuleButton = new Button("Добавить правило");
        addRuleButton.setOnAction(e -> {
            Object selectedObject = objectSelector.getValue();
            String selectedField = fieldSelector.getValue();
            String selectedOperator = operatorSelector.getValue();
            String value = comparisonValue.getText();

            Object actionObject = actionObjectSelector.getValue();
            String actionField = actionFieldSelector.getValue();
            String newValue = actionValue.getText();

            if (selectedObject != null && selectedField != null && selectedOperator != null && value != null &&
                    actionObject != null && actionField != null && newValue != null) {
                System.out.println("Добавлено правило:");
                System.out.println("ЕСЛИ " + selectedObject + "." + selectedField + " " + selectedOperator + " " + value);
                System.out.println("ТО " + actionObject + "." + actionField + " = " + newValue);
            } else {
                System.out.println("Заполните все поля для создания правила.");
            }
        });

        // Condition container
        VBox conditionBox = new VBox(5, new Label("Условие:"), objectSelector, fieldSelector, operatorSelector, comparisonValue);
        conditionBox.setPadding(new Insets(5));
        conditionBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-padding: 10px;");

        // Action container
        VBox actionBox = new VBox(5, new Label("Действие:"), actionObjectSelector, actionFieldSelector, actionValue);
        actionBox.setPadding(new Insets(5));
        actionBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-padding: 10px;");

        // Add components to the root container
        root.getChildren().addAll(title, conditionBox, actionBox, addRuleButton);

        // Update the content of the main layout
        updateContent(root);
    }

    private void updateContent(Parent newContent) {
        // Удаляем старое содержимое, оставляя меню
        if (mainLayout.getChildren().size() > 1) {
            mainLayout.getChildren().remove(1);
        }

        // Добавляем новое содержимое
        mainLayout.getChildren().add(newContent);
    }
    // Пример добавления объектов для тестирования
    private void initObjects() {
        InflexDBService inflexDBService = new InflexDBService();
        List<Device> devices = inflexDBService.getAllDevices();
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("testClient");
        this.objects.add(mqttPublisher);
        this.objects.addAll(devices);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Меню "Окна"
        Menu menuWindows = new Menu("Окна");

        // Пункт меню для главного окна
        MenuItem mainSceneItem = new MenuItem("Главное окно");
        mainSceneItem.setOnAction(e -> showMainScene());

        // Пункт меню для конструктора правил
        MenuItem ruleConstructorItem = new MenuItem("Конструктор правил");
        ruleConstructorItem.setOnAction(e -> showSecondScene());

        menuWindows.getItems().addAll(mainSceneItem, ruleConstructorItem);
        menuBar.getMenus().add(menuWindows);

        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
