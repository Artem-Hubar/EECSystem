package org.example.client.controllers.expression.object;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Getter;
import org.example.client.factory.DeviceUIFactory;
import org.example.entity.Device;

import javafx.scene.control.Button;

@Getter
public class DeviceController extends ExpressionObjectController {
    private final Device device;
    @FXML
    private Label labelClass;
    @FXML
    private Label labelName;

    @FXML
    private Text deviceId;

    @FXML
    private Button deleteButton;
    @FXML
    private ComboBox<String> methodBox;

    public DeviceController(Object device) {
        this.device = (Device) device;
    }


    @Override
    public Object getObject() {
        return device;
    }

    @FXML
    public void initialize() {
        initLabelName();
        initLabelClass();
        deviceId.setText(device.getSensorId());
        DeviceUIFactory deviceUIFactory = new DeviceUIFactory();
        Device deviceUi = deviceUIFactory.getDeviceUi(device);
        for (var method : deviceUi.getClass().getDeclaredMethods()) {
            methodBox.getItems().add(method.getName());
        }
    }

    private void initLabelClass() {
        labelClass.setText(getDeviceClassName());
    }

    private void initLabelName() {
        if (isDeviceTitleNotEmpty()){
            labelName.setText(device.getTitle());
        }
    }

    private boolean isDeviceTitleNotEmpty() {
        return device.getTitle() != null && !device.getTitle().isEmpty();
    }

    private String getDeviceClassName() {
        String[] devicePath = device.getClass().getName().split("\\.");
        return devicePath[devicePath.length-1];
    }

    public void setMethod(String methodString) {
        this.methodBox.setValue(methodString);
    }

    @Override
    public String getMethodString() {
        return methodBox.getValue();
    }
}

