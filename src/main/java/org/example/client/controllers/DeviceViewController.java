package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.Device;

import javafx.scene.control.Button;

@Getter
public class DeviceViewController {
    private final Device device;

    @FXML
    private Text deviceId;

    @FXML
    private ComboBox<String> methodBox;

    @FXML
    private Button deleteButton;
    @Setter
    private Runnable actionOnDelete;

    public DeviceViewController(Device device) {
        this.device = device;
    }

    @FXML
    public void initialize() {
        deviceId.setText(device.getSensorId());
        for (var method : device.getClass().getDeclaredMethods()) {
            methodBox.getItems().add(method.getName());
        }

    }

    private void writeTree(Parent pane, String s){
        if (pane == null){
            return;
        }

        writeTree(pane.getParent(), s + "-".repeat(pane.toString().length()));
        System.out.println(s+pane);
    }

    @FXML
    private void OnDelete(){
        if (actionOnDelete != null) {
            actionOnDelete.run();
        }
    }

//    @FXML
//    private void OnDelete() {
//        writeTree((Pane) deviceId.getParent(), "-");
//        Node deviceNode = deviceId.getParent();
//        Node expressionNode = deviceNode.getParent();
//        if (expressionNode.getParent() instanceof Pane parentPane) {
//            parentPane.getChildren().remove(expressionNode);
//        }
//    }


}

