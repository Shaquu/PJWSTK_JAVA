package io.github.shaquu.warehouse.gui.controller;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.gui.animation.ShakeTransition;
import io.github.shaquu.warehouse.item.Item;
import io.github.shaquu.warehouse.main.ObjectVolume;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController extends WController {

    @FXML
    public TextField xLbl;
    @FXML
    public TextField yLbl;
    @FXML
    public TextField zLbl;
    @FXML
    public TextField volumeLbl;
    @FXML
    public TextField nameLbl;
    @FXML
    public TextField maxLbl;

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
        Main.addItemWindow.setController(this);
    }

    @Override
    public void init(int id) {
        maxLbl.setText(String.valueOf(Main.mainWindow.getController().warehouseManager.getRoom(id).getFreeVolume()));
    }

    @FXML
    public void saveBtnAction(ActionEvent actionEvent) {
        Item item = null;
        if (nameLbl.getText().length() < 1) {
            new ShakeTransition(saveBtn).playFromStart();
            actionEvent.consume();
            return;
        }
        if (xyzTab.isSelected()) {
            try {
                long x = Long.parseLong(xLbl.getText());
                long y = Long.parseLong(yLbl.getText());
                long z = Long.parseLong(zLbl.getText());
                item = new Item(nameLbl.getText(), new ObjectVolume(x, y, z));
            } catch (NumberFormatException e) {
                new ShakeTransition(saveBtn).playFromStart();
                actionEvent.consume();
                return;
            }
        } else if (volumeTab.isSelected()) {
            try {
                long volume = Long.parseLong(volumeLbl.getText());
                item = new Item(nameLbl.getText(), new ObjectVolume(volume));
            } catch (NumberFormatException e) {
                new ShakeTransition(saveBtn).playFromStart();
                actionEvent.consume();
                return;
            }
        }
        if (item == null) {
            new ShakeTransition(saveBtn).playFromStart();
        } else {
            if (!Main.mainWindow.getController().itemCanvas.add
                    (
                            Main.mainWindow.getController().warehouseManager.getRoom(Main.selectedRoomId)
                            , item
                    )
                    ) {
                new ShakeTransition(saveBtn).playFromStart();
                actionEvent.consume();
                return;
            }
            Main.addItemWindow.hide();
            clearFields();
        }
        actionEvent.consume();
    }

    @FXML
    public void closeBtnAction(ActionEvent actionEvent) {
        Main.addItemWindow.hide();
        clearFields();
        actionEvent.consume();
    }

    private void clearFields() {
        xLbl.clear();
        yLbl.clear();
        zLbl.clear();
        volumeLbl.clear();
        nameLbl.clear();
        maxLbl.clear();
    }
}
