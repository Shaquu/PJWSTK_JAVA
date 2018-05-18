package io.github.shaquu.warehouse.gui.controller;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.gui.animation.ShakeTransition;
import io.github.shaquu.warehouse.main.ObjectVolume;
import io.github.shaquu.warehouse.room.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRoomController extends WController {

    @FXML
    public TextField xLbl;
    @FXML
    public TextField yLbl;
    @FXML
    public TextField zLbl;
    @FXML
    public TextField volumeLbl;

    @FXML
    public Tab xyzTab;
    @FXML
    public Tab volumeTab;

    @FXML
    public Button saveBtn;
    @FXML
    public Button closeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.addRoomWindow.setController(this);
    }

    @FXML
    public void saveBtnAction(ActionEvent actionEvent) {
        Room room = null;
        if (xyzTab.isSelected()) {
            try {
                long x = Long.parseLong(xLbl.getText());
                long y = Long.parseLong(yLbl.getText());
                long z = Long.parseLong(zLbl.getText());
                room = new Room(new ObjectVolume(x, y, z), -1);
            } catch (NumberFormatException e) {
                new ShakeTransition(saveBtn).playFromStart();
                actionEvent.consume();
                return;
            }
        } else if (volumeTab.isSelected()) {
            try {
                long volume = Long.parseLong(volumeLbl.getText());
                room = new Room(new ObjectVolume(volume), -1);
            } catch (NumberFormatException e) {
                new ShakeTransition(saveBtn).playFromStart();
                actionEvent.consume();
                return;
            }
        }
        if (room == null) {
            new ShakeTransition(saveBtn).playFromStart();
        } else {
            Main.mainWindow.getController().warehouseManager.addRoom(room);
            Main.addRoomWindow.hide();
            clearFields();
        }
        actionEvent.consume();
    }

    @FXML
    public void closeBtnAction(ActionEvent actionEvent) {
        Main.addRoomWindow.hide();
        clearFields();
        actionEvent.consume();
    }

    @Override
    public void clearFields() {
        xLbl.clear();
        yLbl.clear();
        zLbl.clear();
        volumeLbl.clear();
    }

    @Override
    public void init(int id) {

    }
}
