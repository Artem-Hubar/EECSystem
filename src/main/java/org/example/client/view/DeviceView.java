package org.example.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.example.client.controllers.DeviceViewController;
import org.example.entity.Device;

@Getter
public class DeviceView {
    private final Device deviceModel; // Модель устройства
    private final Parent view; // Графический интерфейс устройства
    private DeviceViewController deviceViewController;

    public DeviceView(Device deviceModel) {
        this.deviceModel = deviceModel;
        deviceViewController = new DeviceViewController(deviceModel);
        this.view = loadView();
    }

    private Parent loadView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/device.fxml"));
            loader.setController(deviceViewController);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device view", e);
        }
    }

}
