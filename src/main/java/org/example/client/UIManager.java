package org.example.client;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.App;
import org.example.client.view.MapView;
import org.example.client.view.RuleSceneView;
import org.example.client.view.RulesSceneView;


import java.util.List;


public class UIManager {
    private final Stage primaryStage;
    private VBox mainLayout;
    SceneManager sceneManager;

    public UIManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        sceneManager = new SceneManager();
    }

    public void init() {
        mainLayout = new VBox();
        MenuBar menuBar = createMenuBar();
        mainLayout.getChildren().add(menuBar);

        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("EECSystem");
        primaryStage.show();
    }

    public void showMainScene() {
        Parent mainContent = sceneManager.loadFXML("/mainScene.fxml");
        updateContent(mainContent);
        primaryStage.setTitle("Main window");
    }

    public void showRuleBuilder(List<Object> objects) {
        Parent secondContent = sceneManager.getRuleBuilder(objects);
        updateContent(secondContent);
    }

    public void  showRules(){
        RulesSceneView rulesSceneView= new RulesSceneView();
        Parent rulesScene = rulesSceneView.getView();
        updateContent(rulesScene);
    }

    public void showMap(List<Object> objects){
        MapView MapView = new MapView(objects);
        Parent MapContent = MapView.getView();
        updateContent(MapContent);
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
        ruleConstructorItem.setOnAction(e -> showRuleBuilder(App.getObjects()));

        MenuItem mapItem = new MenuItem("Device Map");
        mapItem.setOnAction(e -> showMap(App.getObjects()));

        MenuItem rulesItem = new MenuItem("Rules");
        rulesItem.setOnAction(e->showRules());

        menuWindows.getItems().addAll(mapItem, rulesItem, ruleConstructorItem);
        menuBar.getMenus().add(menuWindows);

        return menuBar;
    }
}
