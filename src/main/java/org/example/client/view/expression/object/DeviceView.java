package org.example.client.view.expression.object;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import org.example.client.controllers.expression.object.DeviceController;

import org.example.entity.Device;

@Getter
public class DeviceView extends ObjectView{
    private final Device deviceModel;

    public DeviceView(Device deviceModel) {
        this.deviceModel = deviceModel;
        this.setExpressionObjectController( new DeviceController(deviceModel));
        this.setView(loadView());
    }

    public Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/device.fxml"));
            loader.setController(getExpressionObjectController());
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }

}
