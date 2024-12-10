package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class UIManager {
    private final Stage primaryStage;
    private VBox mainLayout;

    public UIManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init() {
        mainLayout = new VBox();
        MenuBar menuBar = createMenuBar();
        mainLayout.getChildren().add(menuBar);

        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Приложение с меню");
        primaryStage.show();
    }

    public void showMainScene() {
        Parent mainContent = loadFXML("/mainScene.fxml");
        updateContent(mainContent);
        primaryStage.setTitle("Главное окно");
    }

    public void showSecondScene(List<Object> objects) {
        SecondSceneConstructor constructor = new SecondSceneConstructor(objects);
        Parent secondContent = constructor.createScene();
        updateContent(secondContent);
    }

    private Parent loadFXML(String resource) {
        try {
            return new FXMLLoader(getClass().getResource(resource)).load();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить FXML: " + resource, e);
        }
    }

    private void updateContent(Parent newContent) {
        if (mainLayout.getChildren().size() > 1) {
            mainLayout.getChildren().remove(1);
        }
        mainLayout.getChildren().add(newContent);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuWindows = new Menu("Окна");

        MenuItem mainSceneItem = new MenuItem("Главное окно");
        mainSceneItem.setOnAction(e -> showMainScene());

        MenuItem ruleConstructorItem = new MenuItem("Конструктор правил");
        ruleConstructorItem.setOnAction(e -> showSecondScene(App.getObjects()));

        menuWindows.getItems().addAll(mainSceneItem, ruleConstructorItem);
        menuBar.getMenus().add(menuWindows);

        return menuBar;
    }
}
