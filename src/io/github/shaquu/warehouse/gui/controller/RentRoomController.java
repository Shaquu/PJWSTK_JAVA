/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.gui.controller;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.gui.animation.ShakeTransition;
import io.github.shaquu.warehouse.room.AlreadyRentedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RentRoomController extends WController {

    @FXML
    public TextField forDaysLbl;

    @FXML
    public Button rentBtn;
    @FXML
    public Button closeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.rentRoomindow.setController(this);
    }


    public void rentBtnAction(ActionEvent actionEvent) {
        if (forDaysLbl.getText().length() < 1) {
            new ShakeTransition(rentBtn).playFromStart();
            actionEvent.consume();
            return;
        }

        try {
            int rentForDays = Integer.parseInt(forDaysLbl.getText());
            Main.mainWindow.getController().warehouseManager.getRoom(Main.selectedRoomId).rent(Main.selectedPersonid, rentForDays);
        } catch (NumberFormatException | AlreadyRentedException e) {
            new ShakeTransition(rentBtn).playFromStart();
            actionEvent.consume();
            return;
        }

        Main.mainWindow.getController().warehouseManager.forceRefresh();
        Main.rentRoomindow.hide();
        clearFields();
        actionEvent.consume();
    }

    @FXML
    public void closeBtnAction(ActionEvent actionEvent) {
        Main.rentRoomindow.hide();
        clearFields();
        actionEvent.consume();
    }

    @Override
    public void clearFields() {
        forDaysLbl.clear();
    }

    @Override
    public void init(int id) {

    }
}
