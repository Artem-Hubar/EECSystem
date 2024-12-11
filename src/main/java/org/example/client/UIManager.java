package org.example.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.App;
import org.example.client.controllers.RuleBuilderSceneController;


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

        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("EECSystem");
        primaryStage.show();
    }

    public void showMainScene() {
        Parent mainContent = loadFXML("/mainScene.fxml");
        updateContent(mainContent);
        primaryStage.setTitle("Main window");
    }

    public void showSecondScene(List<Object> objects) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SecondScene.fxml"));
            RuleBuilderSceneController controller = new RuleBuilderSceneController(objects);
            loader.setController(controller);
            Parent secondContent = loader.load();
            updateContent(secondContent);
        } catch (IOException e) {
            System.err.println("Loading error SecondScene.fxml: " + e.getMessage());
            e.printStackTrace();
        }
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
        Menu menuWindows = new Menu("Windows");

        MenuItem mainSceneItem = new MenuItem("Main window");
        mainSceneItem.setOnAction(e -> showMainScene());

        MenuItem ruleConstructorItem = new MenuItem("Rule builder");
        ruleConstructorItem.setOnAction(e -> showSecondScene(App.getObjects()));

        menuWindows.getItems().addAll(mainSceneItem, ruleConstructorItem);
        menuBar.getMenus().add(menuWindows);

        return menuBar;
    }
}
