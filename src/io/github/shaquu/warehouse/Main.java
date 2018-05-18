package io.github.shaquu.warehouse;

import io.github.shaquu.warehouse.gui.controller.MainWindowController;
import io.github.shaquu.warehouse.gui.controller.WController;
import io.github.shaquu.warehouse.gui.controller.WWindow;
import io.github.shaquu.warehouse.gui.element.NewElement;
import io.github.shaquu.warehouse.menu.Menu;
import io.github.shaquu.warehouse.person.IncorrectPeselException;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    public static int selectedPersonid = -2;
    public static int selectedWarehouseId;
    public static int selectedRoomId;
    public static WWindow mainWindow;
    public static WWindow addPersonWindow;
    public static WWindow viewPersonWindow;
    public static WWindow addRoomWindow;
    public static WWindow addItemWindow;
    public static WWindow rentRoomindow;

    public static void main(String[] args) {
        if(args.length > 0){
            mainWindow = new WWindow();
            mainWindow.setController(new WController() {
                @Override
                public void init(int id) {

                }

                @Override
                public void initialize(URL location, ResourceBundle resources) {

                }
            });
            try {
                new Menu().run();
            } catch (IncorrectPeselException e) {
                e.printStackTrace();
            }
        } else {
            new NewElement();

            launch(args);
        }
    }

    private static void init(Stage primaryStage){
        mainWindow = new WWindow(primaryStage);
        addPersonWindow = new WWindow(primaryStage);
        viewPersonWindow = new WWindow(primaryStage);
        addRoomWindow = new WWindow(primaryStage);
        addItemWindow = new WWindow(primaryStage);
        rentRoomindow = new WWindow(primaryStage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);

        mainWindow.setup("mainWindow", "Warehouse Manager", true);
        addPersonWindow.setup("addPersonWindow", "", false);
        viewPersonWindow.setup("viewPersonWindow", "", false);
        addRoomWindow.setup("addRoomWindow", "", false);
        addItemWindow.setup("addItemWindow", "", false);
        rentRoomindow.setup("rentRoomWindow", "", false);
    }
}
