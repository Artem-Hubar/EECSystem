package org.example.client.view.expression.object;

import javafx.scene.Parent;
import lombok.Data;
import lombok.Getter;
import org.example.client.controllers.expression.object.ExpressionObjectController;

@Data
public abstract class ObjectView {
    private Parent view;
    private ExpressionObjectController expressionObjectController;

    public abstract Parent loadView();
}
