package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import org.example.client.controllers.ExpressionController;

@Getter
public class ExpressionView {

    private Object objectModel;
    private final Parent view;
    private final ExpressionController expressionModelView;

    public ExpressionView(Object objectModel, String lastOperator) {
        this.objectModel = objectModel;
        expressionModelView = new ExpressionController(objectModel, lastOperator);
        this.view = loadView();
    }

    public ExpressionView(ExpressionController expressionModelView) {
        this.expressionModelView = expressionModelView;
        this.view = loadView();
    }

    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Expressions.fxml"));
            loader.setController(expressionModelView);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }

}

