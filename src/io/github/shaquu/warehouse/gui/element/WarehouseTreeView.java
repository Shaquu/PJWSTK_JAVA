/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.gui.element;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.main.Warehouse;
import io.github.shaquu.warehouse.main.WarehouseObject;
import io.github.shaquu.warehouse.room.Room;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.util.List;

public class WarehouseTreeView extends TreeView<WarehouseObject> {

    public WarehouseTreeView() {

        this.setCellFactory(param -> new TreeCell<WarehouseObject>() {
            @Override
            protected void updateItem(WarehouseObject item, boolean empty) {
                super.updateItem(item, empty);

                //System.out.println("update " + item);
                setTooltip(null);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (item.roomId == -100) {
                        setText("Warehouses & Rooms");
                    } else if (item.isNewElement) {
                        setGraphic(new ImageView(NewElement.plusImage));
                        if (item.isWarehouse) {
                            setOnMousePressed(event -> {
                                if (Main.mainWindow.getController().adminMode)
                                    addWarehouse(new Warehouse());
                            });
                            setText("Add Warehouse");
                        } else if (item.isRoom) {
                            setOnMousePressed(event -> {
                                if (Main.mainWindow.getController().adminMode) {
                                    Main.selectedWarehouseId = item.warehouseId;
                                    //System.out.println("Selected warehouse with id " + Main.selectedWarehouseId);
                                    Main.addRoomWindow.getStage().setTitle("Warehouse #" + item.warehouseId);
                                    Main.addRoomWindow.show();
                                }
                            });
                            setText("Add Room");
                        } else {
                            int ownerId = getRoomOwner(item.roomId);
                            if (ownerId == Main.selectedPersonid && Main.selectedPersonid > 0) {
                                setOnMousePressed(event -> {
                                    if (!Main.mainWindow.getController().adminMode) {
                                        if (ownerId == Main.selectedPersonid) {
                                            Main.selectedWarehouseId = item.warehouseId;
                                            Main.selectedRoomId = item.roomId;
                                            Main.addItemWindow.getController().init(item.roomId);
                                            Main.mainWindow.getController().itemCanvas.init(getRoom(item.roomId));
                                            //System.out.println("Selected warehouse with id " + Main.selectedWarehouseId);
                                            Main.addItemWindow.getStage().setTitle("Room #" + item.roomId);
                                            Main.addItemWindow.show();
                                        }
                                    }
                                });
                                setText("Add Item");
                            } else if (ownerId < 0 && Main.selectedPersonid > 0) {
                                setOnMousePressed(event -> {
                                    if (!Main.mainWindow.getController().adminMode) {
                                        if (Main.selectedPersonid > 0) {
                                            Main.selectedWarehouseId = item.warehouseId;
                                            Main.selectedRoomId = item.roomId;
                                            Main.mainWindow.getController().itemCanvas.init();
                                            //System.out.println("Selected warehouse with id " + Main.selectedWarehouseId);;
                                            Main.rentRoomindow.getStage().setTitle("Room #" + item.roomId);
                                            Main.rentRoomindow.show();
                                        }
                                    }
                                });
                                setText("Rent Room");
                                setTooltip(new Tooltip("Room volume " + getRoom(item.roomId).getFreeVolume()));
                            } else {
                                setOnMousePressed(event -> {
                                    if (!Main.mainWindow.getController().adminMode) {
                                        Main.selectedWarehouseId = item.warehouseId;
                                        Main.selectedRoomId = item.roomId;
                                        Main.mainWindow.getController().itemCanvas.init(getRoom(item.roomId));
                                        //System.out.println("Selected warehouse with id " + Main.selectedWarehouseId);;
                                    }
                                });
                                setText("View Room");
                            }
                        }
                    } else if (!(item instanceof Warehouse) && !(item instanceof Room)) {
                        setGraphic(null);
                        setText(null);
                        //System.out.println(item);
                    } else {
                        setGraphic(null);
                        setOnMousePressed(event -> {
                            if (item instanceof Warehouse) {
                                Main.selectedWarehouseId = item.id();
                                //System.out.println("Selected warehouse with id " + Main.selectedWarehouseId);
                                Main.mainWindow.getController().itemCanvas.init();
                            } else {
                                Main.selectedRoomId = item.id();
                                Main.mainWindow.getController().itemCanvas.init(getRoom(item.id()));
                                //System.out.println("Selected room with id " + Main.selectedRoomId);
                            }
                        });

                        if (item instanceof Warehouse) {
                            setText("Warehouse #" + item.id());
                        } else {
                            setText("Room #" + item.id());
                            Room room = getRoom(item.id());
                            if (room.getPersonId() == Main.selectedPersonid) {
                                if (room.isRented()) {
                                    setTooltip(new Tooltip("Rented to " + room.getRentedTo()));
                                }
                            }
                        }

                    }
                }
            }
        });

    }

    public void addWarehouse(Warehouse warehouse) {
        TreeItem<WarehouseObject> warehouseItem = new TreeItem<>(warehouse);
        if (Main.mainWindow.getController().adminMode) {
            TreeItem<WarehouseObject> roomItem
                    = new TreeItem<>(new WarehouseObject(true, false, true, warehouse.id(), -1));
            warehouseItem.getChildren().add(roomItem);
        }
        rootProperty().get().getChildren().add(warehouseItem);
    }

    public void removeNewElement() {
        checkAndRemoveNewElement();
        forceRefresh();
    }

    private void checkAndRemoveNewElement() {
        for (int i = 0; i < rootProperty().get().getChildren().size(); i++) {
            TreeItem<WarehouseObject> warehouseObjectTreeItem = rootProperty().get().getChildren().get(i);

            if (warehouseObjectTreeItem.getValue().isNewElement
                    || (
                    !(warehouseObjectTreeItem.getValue() instanceof Room)
                            && !(warehouseObjectTreeItem.getValue() instanceof Warehouse)
            )
                    ) {
                warehouseObjectTreeItem.getParent().getChildren().remove(warehouseObjectTreeItem);
            } else {
                for (int j = 0; j < warehouseObjectTreeItem.getChildren().size(); j++) {
                    TreeItem<WarehouseObject> roomItem = warehouseObjectTreeItem.getChildren().get(j);

                    if (roomItem.getValue().isNewElement
                            || (
                            !(roomItem.getValue() instanceof Room)
                                    && !(roomItem.getValue() instanceof Warehouse)
                    )
                            ) {
                        roomItem.getParent().getChildren().remove(roomItem);
                    }
                }
            }
        }


        /*for(int i = 0; i < rootProperty().get().getChildren().size(); i++){
            System.out.printf("i%d size%d %s\n", i, rootProperty().get().getChildren().get(i).getChildren().size(), rootProperty().get().getChildren().get(i));

            for(int j = 0; j < rootProperty().get().getChildren().get(i).getChildren().size(); j++){
                System.out.printf("i%dj%d %s\n", i, j, rootProperty().get().getChildren().get(i).getChildren().get(j));
                if(rootProperty().get().getChildren().get(i).getChildren().get(j).getValue().isNewElement){
                    rootProperty().get().getChildren().get(i).getChildren().remove(j);
                }
            }

            if(rootProperty().get().getChildren().get(i).getValue().isNewElement){
                rootProperty().get().getChildren().remove(i);
            }

                /*TreeItem<WarehouseObject> treeItem = rootProperty().get().getChildren().get(i);
                WarehouseObject warehouseObject = treeItem.getValue();
                if(warehouseObject.isNewElement)
                    rootProperty().get().getChildren().remove(i);
                else {
                    for(int j = 0; i < treeItem.getChildren().size(); j++){
                        WarehouseObject warehouseObjectRoom = treeItem.getChildren().get(j).getValue();
                        if(warehouseObjectRoom.isNewElement)
                            treeItem.getChildren().remove(j);
                    }
                }
        }*/
    }

    public Room getRoom(int roomId) {
        for (TreeItem<WarehouseObject> warehouseItem : rootProperty().get().getChildren()) {
            for (TreeItem<WarehouseObject> roomItem : warehouseItem.getChildren()) {
                if (roomItem.getValue().id() == roomId)
                    return (Room) roomItem.getValue();
            }
        }
        return null;
    }

    public void addNewElement() {
        if (rootProperty().get() == null) {
            TreeItem<WarehouseObject> root
                    = new TreeItem<>(new WarehouseObject(false, false, true, -1, -100));
            root.setExpanded(true);
            root.getChildren().add(
                    new TreeItem<>(new WarehouseObject(true, true, false, -1, -1))
            );
            this.setRoot(root);
        } else if (rootProperty().get().isLeaf()) {
            rootProperty().get().setExpanded(true);
            rootProperty().get().getChildren().add(
                    new TreeItem<>(new WarehouseObject(true, true, false, -1, -1))
            );
        } else {
            for (TreeItem<WarehouseObject> treeItem : rootProperty().get().getChildren()) {
                if (!treeItem.getValue().isNewElement) {
                    boolean isNewElementHere = false;
                    for (TreeItem<WarehouseObject> treeItemCheck : treeItem.getChildren()) {
                        if (treeItemCheck.getValue().isNewElement) {
                            isNewElementHere = true;
                            break;
                        }
                    }
                    if (!isNewElementHere) {
                        treeItem.getChildren().add(
                                new TreeItem<>(new WarehouseObject(true, false, true, treeItem.getValue().id(), -1))
                        );
                    }
                }
            }
            rootProperty().get().getChildren().add(
                    new TreeItem<>(new WarehouseObject(true, true, false, -1, -1))
            );
        }
    }

    public void addRoom(Room room) {
        for (TreeItem<WarehouseObject> treeItem : rootProperty().get().getChildren()) {
            if (treeItem.getValue() instanceof Warehouse) {
                if (!treeItem.getValue().isNewElement) {
                    if (treeItem.getValue().id() == Main.selectedWarehouseId) {
                        TreeItem<WarehouseObject> roomItem = new TreeItem<>(room);

                        TreeItem<WarehouseObject> itemItem
                                = new TreeItem<>(new WarehouseObject(true, false, false, -1, room.id()));
                        roomItem.getChildren().add(itemItem);

                        treeItem.getChildren().add(roomItem);
                        return;
                    }
                }
            }
        }
    }

    int getRoomOwner(int roomId) {
        for (TreeItem<WarehouseObject> warehouseTreeItem : rootProperty().get().getChildren()) {
            for (TreeItem<WarehouseObject> roomTreeItem : warehouseTreeItem.getChildren()) {
                if (roomTreeItem.getValue() instanceof Room) {
                    Room room = (Room) roomTreeItem.getValue();
                    if (room.id() == roomId)
                        return room.getPersonId();
                }
            }
        }
        return -1;
    }

    public void forceRefresh() {
        addNewElement();
        checkAndRemoveNewElement();
    }

    public void clear() {
        rootProperty().get().getChildren().removeAll(rootProperty().get().getChildren());
    }

    public List<TreeItem<WarehouseObject>> get() {
        return rootProperty().get().getChildren();
    }

    public int roomCount(int warehouseId) {
        int count = 0;
        for (TreeItem<WarehouseObject> warehouseTreeItem : rootProperty().get().getChildren()) {
            if (warehouseTreeItem.getValue() instanceof Warehouse) {
                Warehouse warehouse = (Warehouse) warehouseTreeItem.getValue();
                if (warehouse.id() == warehouseId) {
                    for (TreeItem<WarehouseObject> roomTreeItem : warehouseTreeItem.getChildren()) {
                        if (roomTreeItem.getValue() instanceof Room) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public void simulate(LocalDate todayDate) {
        if (rootProperty() != null)
            if (rootProperty().get() != null)
                if (rootProperty().get().getChildren() != null)
                    for (TreeItem<WarehouseObject> warehouseTreeItem : rootProperty().get().getChildren()) {
                        if (warehouseTreeItem.getValue() instanceof Warehouse) {
                            Warehouse warehouse = (Warehouse) warehouseTreeItem.getValue();
                            if (warehouse != null) {
                                for (TreeItem<WarehouseObject> roomTreeItem : warehouseTreeItem.getChildren()) {
                                    if (roomTreeItem.getValue() instanceof Room) {
                                        Room room = (Room) roomTreeItem.getValue();
                                        if (room.isRented()) {
                                            if (room.getRentedTo().isBefore(todayDate.plusDays(1))) {
                                                room.dismantle();
                                                if (Main.selectedRoomId == room.id()) {
                                                    Main.mainWindow.getController().itemCanvas.clear();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
        forceRefresh();
    }
}
