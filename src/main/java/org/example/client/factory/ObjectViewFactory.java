package org.example.client.factory;

import javafx.scene.control.TextField;
import org.example.client.view.expression.object.DeviceView;
import org.example.client.view.expression.object.ObjectView;
import org.example.client.view.expression.object.TextFieldView;
import org.example.entity.Device;

public class ObjectViewFactory {
    public ObjectView getObjectView(Object object){
        if (object instanceof Device device){
            return new DeviceView(device);
        } else if (object instanceof String string) {
            if (string.equals("textField")){
                return new TextFieldView();
            }else {
                return null;
            }
        }else {
            return null;
        }

    }
}
