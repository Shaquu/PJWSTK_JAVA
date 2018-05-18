/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.gui.element;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.item.Item;
import io.github.shaquu.warehouse.room.Room;
import io.github.shaquu.warehouse.room.TooManyThingsException;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ItemCanvas extends TableView<Item> {

    private boolean initialized = false;

    private void initialize() {
        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, String> volumeColumn = new TableColumn<>("Volume (in m3)");
        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));

        this.getColumns().add(nameColumn);
        this.getColumns().add(volumeColumn);

        ContextMenu cm = new ContextMenu();
        MenuItem removeItemMenu = new MenuItem("Remove Item");
        removeItemMenu.setOnAction((ActionEvent event) -> {
            Object item = ItemCanvas.this.getSelectionModel().getSelectedItem();
            remove(Main.selectedRoomId, ((Item) item).id());
        });
        cm.getItems().add(removeItemMenu);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!Main.mainWindow.getController().adminMode)
                if (Main.mainWindow.getController().warehouseManager.getRoomOwner(Main.selectedRoomId) == Main.selectedPersonid)
                    if (ItemCanvas.this.getItems().size() > 0)
                        if (e.getButton() == MouseButton.SECONDARY || e.getButton() == MouseButton.PRIMARY) {
                            cm.show(ItemCanvas.this, e.getScreenX(), e.getScreenY());
                        }
        });

        initialized = true;
    }

    public void init() {
        getItems().removeAll(getItems());
    }

    public void init(Room room) {
        init();
        for (Item item : room.itemList.values()) {
            getItems().add(item);
        }
    }

    public boolean add(Room room, Item item) {
        try {
            room.addItem(item);
            if (!initialized) {
                initialize();
            }
            getItems().add(item);
            return true;
        } catch (TooManyThingsException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void remove(int roomId, int itemId) {
        Room room = Main.mainWindow.getController().warehouseManager.getRoom(roomId);
        room.removeItem(itemId);
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i).id() == itemId) {
                getItems().remove(i);
            }
        }
    }

    public void clear() {
        getItems().removeAll(getItems());
    }
}
