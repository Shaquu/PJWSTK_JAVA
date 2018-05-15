package io.github.shaquu.warehouse;

import io.github.shaquu.warehouse.gui.controller.WWindow;
import io.github.shaquu.warehouse.gui.element.NewElement;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static int selectedPersonid = -2;
    public static int selectedWarehouseId;
    public static int selectedRoomId;
    public static WWindow mainWindow;
    public static WWindow addPersonWindow;
    public static WWindow addRoomWindow;
    public static WWindow addItemWindow;
    public static WWindow rentRoomindow;

    public static void main(String[] args) {
        new NewElement();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = new WWindow(primaryStage);
        addPersonWindow = new WWindow(primaryStage);
        addRoomWindow = new WWindow(primaryStage);
        addItemWindow = new WWindow(primaryStage);
        rentRoomindow = new WWindow(primaryStage);

        mainWindow.setup("mainWindow", "Warehouse Manager", true);
        addPersonWindow.setup("addPersonWindow", "", false);
        addRoomWindow.setup("addRoomWindow", "", false);
        addItemWindow.setup("addItemWindow", "", false);
        rentRoomindow.setup("rentRoomWindow", "", false);
    }
}
