package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public abstract class View {



    protected Parent loadView(Object controller, String uriVIew) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(uriVIew));
            loader.setController(controller);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }
}
