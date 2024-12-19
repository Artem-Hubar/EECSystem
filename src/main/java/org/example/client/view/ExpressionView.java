package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import org.example.client.controllers.ExpressionController;
import org.example.entity.Device;

@Getter
public class ExpressionView {
    private final Object objectModel;
    private final Parent view;
    private final ExpressionController expressionModelView;

    public ExpressionView(Object objectModel, String selectedOperator) {
        this.objectModel = objectModel;
        expressionModelView = new ExpressionController((Device) objectModel, selectedOperator);
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

