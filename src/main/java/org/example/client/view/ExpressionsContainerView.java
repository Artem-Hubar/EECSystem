package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import org.example.client.controllers.ExpressionsContainerController;

import java.util.List;

@Getter
public class ExpressionsContainerView {
    private final List<Object> objects;
    private final Parent view; // Графический интерфейс устройства
    private final ExpressionsContainerController expressionsContainerController;

    public ExpressionsContainerView(List<Object> objects) {
        this.objects = objects;
        expressionsContainerController = new ExpressionsContainerController(objects);
        this.view = loadView();
    }

    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/expressionsContainer.fxml"));
            loader.setController(expressionsContainerController);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }

}
