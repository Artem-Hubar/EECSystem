package org.example.client.controllers.expression.object;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class ExpressionObjectController {
    protected Runnable actionOnDelete;
    public Object object;

    @FXML
    protected void OnDelete(){
        if (actionOnDelete != null) {
            actionOnDelete.run();
        }
    }
    public abstract String getMethodString();
}
