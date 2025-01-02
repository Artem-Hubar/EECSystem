package org.example.client.view.expression.object;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.example.client.controllers.expression.object.TextFieldController;

public class TextFieldView extends ObjectView {

    public TextFieldView(TextFieldController textFieldController){
        this.setExpressionObjectController(textFieldController);
        this.setView(loadView());
    }

    public TextFieldView() {
        this.setExpressionObjectController(new TextFieldController());
        this.setView(loadView());
    }

    public Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/value.fxml"));
            loader.setController(getExpressionObjectController());
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }
}
