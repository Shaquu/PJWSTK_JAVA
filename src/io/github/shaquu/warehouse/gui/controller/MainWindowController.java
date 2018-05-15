package io.github.shaquu.warehouse.gui.controller;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.main.ObjectVolume;
import io.github.shaquu.warehouse.main.Warehouse;
import io.github.shaquu.warehouse.person.IncorrectPeselException;
import io.github.shaquu.warehouse.person.Person;
import io.github.shaquu.warehouse.room.Room;
import io.github.shaquu.warehouse.utils.FileManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainWindowController extends WController {

    private static boolean simulateActive = false;
    private static Task<Void> task;
    private static Thread th;
    //  MenuBar
    //  File
    @FXML
    public MenuItem menuFile;
    @FXML
    public MenuItem menuSave;
    @FXML
    public MenuItem menuQuit;
    //  MenuBar END
    //  Warehouse
    @FXML
    public MenuItem menuTest;
    @FXML
    public MenuItem menuAdmin;
    @FXML
    public MenuItem menuSimulate;
    //  Simulate END
    //  Simulate
    //  Label with "actual" date
    @FXML
    public Label todayLbl;
    //  Progress bar for day
    @FXML
    public ProgressBar todayProgress;
    private LocalDate todayDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.mainWindow.setController(this);

        todayProgress.setVisible(false);
        todayDate = LocalDate.now();

        updateTodayLbl();
        //FileManager.load();
    }

    private void updateTodayLbl() {
        todayLbl.setText("Today is " + todayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Main.mainWindow.getController().warehouseManager.simulate(todayDate);
    }

    @FXML
    public void menuAdminAction(ActionEvent actionEvent) {
        if (adminMode) {
            personManager.removeNewElement();
            warehouseManager.removeNewElement();
        } else {
            personManager.addNewElement();
            warehouseManager.addNewElement();
            Main.selectedPersonid = -2;
        }
        adminMode = !adminMode;
        actionEvent.consume();
    }

    @Override
    public void init(int id) {

    }

    @FXML
    public void menuNewBtnAction(ActionEvent actionEvent) {
        Main.mainWindow.getController().warehouseManager.clear();
        Main.mainWindow.getController().personManager.clear();
        Main.mainWindow.getController().itemCanvas.clear();
    }

    @FXML
    public void menuSaveBtnAction(ActionEvent actionEvent) {
        FileManager.printable();
    }

    @FXML
    public void menuQuitBtnAction(ActionEvent actionEvent) {
        Main.mainWindow.hide();
        //FileManager.save();
        System.exit(0);
    }

    @FXML
    public void menuTestBtnAction(ActionEvent actionEvent) throws IncorrectPeselException {
        Person person1 = new Person("Jan", "Kowalski", "91121204331", "Aleja Jana Pawla 3");
        Main.mainWindow.getController().personManager.add(person1);
        Person person2 = new Person("Jakub", "Babacki", "92021204331", "Testowa 1/2");
        Main.mainWindow.getController().personManager.add(person2);
        Person person3 = new Person("Anna", "Kowalska", "91111404331", "Aleja Jana Pawla 32");
        Main.mainWindow.getController().personManager.add(person3);
        Person person4 = new Person("Piotr", "Kabacki", "85121204331", "Kwitnąca 1/6");
        Main.mainWindow.getController().personManager.add(person4);
        Person person5 = new Person("Stefan", "Jakiśtam", "90121204331", "Ulica Jakaśtamowa 12/1");
        Main.mainWindow.getController().personManager.add(person5);

        Warehouse warehouse1 = new Warehouse();
        Main.mainWindow.getController().warehouseManager.addWarehouse(warehouse1);
        Main.selectedWarehouseId = warehouse1.id();

        Room room1 = new Room(new ObjectVolume(15), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room1);
        Room room2 = new Room(new ObjectVolume(25), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room2);
        Room room3 = new Room(new ObjectVolume(35), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room3);
        Room room4 = new Room(new ObjectVolume(45), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room4);
        Room room5 = new Room(new ObjectVolume(55), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room5);
        Room room6 = new Room(new ObjectVolume(65), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room6);
        Room room7 = new Room(new ObjectVolume(75), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room7);
        Room room8 = new Room(new ObjectVolume(85), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room8);
        Room room9 = new Room(new ObjectVolume(95), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room9);
        Room room10 = new Room(new ObjectVolume(115), warehouse1.id());
        Main.mainWindow.getController().warehouseManager.addRoom(room10);
    }

    @FXML
    public void menuSimulateTimeBtnAction(ActionEvent actionEvent) {
        System.out.println("Called simulate time");
        if (simulateActive) {
            todayProgress.setVisible(false);
            System.out.println("Trying to stop simulate");
            task.cancel(true);
            th.interrupt();
        } else {
            todayProgress.setVisible(true);

            System.out.println("Trying to start simulate");
            task = new Task<Void>() {
                @Override
                public Void call() {
                    updateProgress(0, 1000);
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            updateProgress(0, 1000);
                            for (int i = 0; i < 1000; i++) {
                                Thread.sleep(10);
                                updateProgress(i + 1, 1000);
                            }

                            todayDate = todayDate.plusDays(1);
                            Platform.runLater(() -> updateTodayLbl());
                            updateProgress(1000, 1000);
                            Thread.sleep(10);
                        }
                    } catch (InterruptedException e) {
                        return null;
                    }
                    return null;
                }
            };

            todayProgress.progressProperty().bind(task.progressProperty());

            th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }
        simulateActive = !simulateActive;
    }
}
