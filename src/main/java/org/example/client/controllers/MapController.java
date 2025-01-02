package org.example.client.controllers;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.shape.Rectangle;
import org.example.client.view.FullDeviceView;
import org.example.entity.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MapController {

    private static final Logger logger = Logger.getLogger(MapController.class.getName());

    @FXML
    private VBox listBox;

    @FXML
    private AnchorPane dropPane;  // Используем AnchorPane для гибкости
    @FXML
    private ScrollPane scrollPane; // ScrollPane, который оборачивает Pane

    private final Map<VBox, Device> vBoxDeviceMap = new HashMap<>();
    private VBox draggedVBox = null;

    private final List<FullDeviceController> fullDeviceControllers = new ArrayList<>();

    public MapController() {}

    @FXML
    public void initialize() {
        // Инициализация UI с событиями
    }

    public void setDevices(List<Object> devices) {
        if (devices == null) {
            throw new IllegalArgumentException("Devices list cannot be null");
        }
        initialize(devices);
    }

    private String getDeviceString(Device device) {
        return String.format("%s %s", device.getDeviceType(), device.getSensorId());
    }

    public void initialize(List<Object> objects) {
        if (listBox == null || dropPane == null) {
            throw new IllegalStateException("FXML elements are not initialized");
        }

        // Получаем список устройств
        List<Device> devices = getDevicesFromObjects(objects);

        // Инициализация UI с текстовыми элементами для устройств
        initializeDeviceList(devices);

        // Устанавливаем обработчики для перетаскивания в dropPane
        setupDragAndDropParsers(devices);
    }

    private List<Device> getDevicesFromObjects(List<Object> objects) {
        List<Device> devices = new ArrayList<>();
        for (Object object : objects) {
            if (object instanceof Device device) {
                devices.add(device);
            }
        }
        return devices;
    }

    private void initializeDeviceList(List<Device> devices) {
        for (Device device : devices) {
            String deviceInfo = getDeviceString(device);
            logger.info("Device info: " + deviceInfo);
            Text text = new Text(deviceInfo);

            // Обработчик начала перетаскивания
            text.setOnDragDetected(event -> handleDragDetected(event, deviceInfo));

            listBox.getChildren().add(text);
        }
    }

    private void handleDragDetected(MouseEvent event, String deviceInfo) {
        Text sourceText = (Text) event.getSource();
        Dragboard db = sourceText.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(deviceInfo);
        db.setContent(content);
        event.consume();
    }

    private void setupDragAndDropParsers(List<Device> devices) {
        dropPane.setOnDragOver(event -> handleDragOver(event));
        dropPane.setOnDragDropped(event -> handleDragDropped(event, devices));
        dropPane.setOnKeyPressed(e->{
            if (e.getCode() == KeyCode.DELETE && draggedVBox != null) {
                dropPane.getChildren().remove(draggedVBox);
            }
        });
    }

    private void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != dropPane && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    private void handleDragDropped(DragEvent event, List<Device> devices) {
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            String deviceInfo = db.getString();
            logger.info("Drag Dropped: " + deviceInfo);

            // Удаление старого элемента, если был перетащен
            if (event.getGestureSource() instanceof Parent) {
                if (draggedVBox != null) {
                    dropPane.getChildren().remove(draggedVBox);
                    vBoxDeviceMap.remove(draggedVBox);
                }
            }

            // Поиск и добавление нового устройства
            for (Device device : devices) {
                if (getDeviceString(device).equals(deviceInfo)) {
                    addDeviceToDropPane(device, event);
                    event.setDropCompleted(true);
                    break;
                }
            }
        } else {
            event.setDropCompleted(false);
        }
        event.consume();
    }

    private void addDeviceToDropPane(Device device, DragEvent event) {
        FullDeviceView fullDeviceView = new FullDeviceView(device);
        FullDeviceController fullDeviceController = fullDeviceView.getController();
        Parent deviceView = fullDeviceView.getView();

        VBox vBox = new VBox();
        vBox.getChildren().add(deviceView);
        dropPane.getChildren().add(vBox);

        vBoxDeviceMap.put(vBox, device);
        draggedVBox = vBox;

        double x = event.getX();
        double y = event.getY();
        vBox.setLayoutX(x - vBox.getWidth() / 2);
        vBox.setLayoutY(y - vBox.getHeight() / 2);

        setupDragAndDropForVBox(vBox, device);
    }

    private void setupDragAndDropForVBox(VBox vBox, Device device) {
        // Для перетаскивания уже добавленных элементов
        vBox.setOnMousePressed(event -> {
            draggedVBox = vBox;
            mouseDragX = event.getSceneX();
            mouseDragY = event.getSceneY();
            startDragX = vBox.getLayoutX();
            startDragY = vBox.getLayoutY();
            vBox.requestFocus();
        });

        vBox.setOnMouseDragged(event -> {
            if (draggedVBox != null) {
                // Вычисляем смещение мыши относительно начальной позиции
                double deltaX = event.getSceneX() - mouseDragX;
                double deltaY = event.getSceneY() - mouseDragY;

                // Обновляем позицию элемента на основе смещения
                double newX = startDragX + deltaX;
                double newY = startDragY + deltaY;

                // Устанавливаем новые координаты для элемента
                draggedVBox.setLayoutX(newX);
                draggedVBox.setLayoutY(newY);
            }
        });

        vBox.setOnMouseReleased(event -> {
            draggedVBox = null;
        });

        // Обработка нажатия клавиши Delete
        vBox.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.DELETE) { // Проверяем, что нажата клавиша Delete
                dropPane.getChildren().remove(vBox); // Удаляем VBox из внутреннего контейнера

            }
        });

        // Убедимся, что VBox может получать фокус для обработки событий клавиатуры
        vBox.setFocusTraversable(true);
    }


    private double mouseDragX, mouseDragY, startDragX, startDragY;



}
