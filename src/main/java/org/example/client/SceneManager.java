package org.example.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.client.controllers.ConditionalController;
import org.example.client.controllers.RuleBuilderSceneController;
import org.example.client.controllers.ExpressionsContainerController;

import java.io.IOException;
import java.util.List;

public class SceneManager {
    public Parent getThirdScene(List<Object> objects) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/expressionsContainer.fxml"));
        ExpressionsContainerController controller = new ExpressionsContainerController();
        loader.setController(controller);
        return getLoad(loader);
    }

    public Parent getConditionalScene(List<Object> objects){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConditionalScene.fxml"));
        ConditionalController controller = new ConditionalController();
        loader.setController(controller);
        return getLoad(loader);
    }

    Parent loadFXML(String resource) {
        return getLoad(new FXMLLoader(getClass().getResource(resource)));
    }


    private Parent getLoad(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Parent getRuleBuilder(List<Object> objects) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RuleBuilderScene.fxml"));
        RuleBuilderSceneController controller = new RuleBuilderSceneController();
        loader.setController(controller);
        return getLoad(loader);
    }
}
