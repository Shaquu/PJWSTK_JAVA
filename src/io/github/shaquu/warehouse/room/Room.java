/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.room;


import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.item.Item;
import io.github.shaquu.warehouse.main.ObjectVolume;
import io.github.shaquu.warehouse.main.Warehouse;
import io.github.shaquu.warehouse.main.WarehouseObject;
import javafx.scene.control.TreeItem;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Room extends WarehouseObject<Item> {
    private static int roomCount = 1;
    public HashMap<Integer, Item> itemList;
    private LocalDate rentDate;
    private int rentForDays;
    private int personId = -1;
    private int lastItemId = 0;
    private ObjectVolume objectVolume;
    private long freeVolume;

    private RoomStatus status = RoomStatus.OPEN;

    public Room(ObjectVolume objectVolume, int warehouseId) {
        super(false, false, true, warehouseId, -1);
        this.objectVolume = objectVolume;
        setup();
    }

    private void setup() {
        this.id = roomCount++;
        this.itemList = new HashMap<>();
        this.freeVolume = objectVolume.get();
    }

    private void unrent() {
        this.rentDate = null;
        this.status = RoomStatus.OPEN;
        this.personId = -1;
    }

    public void rent(int personId, int forDays) throws AlreadyRentedException {
        if (this.rentDate != null) {
            throw new AlreadyRentedException(this);
        }

        if (status == RoomStatus.CLOSED) {
            throw new AlreadyRentedException(this);
        }

        this.personId = personId;
        this.rentForDays = forDays;
        this.rentDate = LocalDate.now();
        Main.mainWindow.getController().personManager.updateRentDate(personId, this.rentDate);

        status = RoomStatus.RENTED;

        System.out.printf("Room %s\n\trented for person %s\n", this.toString(),
                Main.mainWindow.getController().personManager.get(personId));
    }

    public void addItem(Item item) throws TooManyThingsException {
        if (this.freeVolume >= item.getVolume() && this.freeVolume > 0) {
            itemList.put(lastItemId++, item);
            freeVolume -= item.getVolume();
            System.out.printf("Added item %s to room %s\n", item, this);
        } else throw new TooManyThingsException(item, this);
    }

    public Item removeItem(String itemName) {
        int index = -1;

        long volume = 0;

        Item item = null;

        Iterator it = itemList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (((Item) pair.getValue()).getName().equals(itemName)) {
                volume = ((Item) pair.getValue()).getVolume();
                index = (int) pair.getKey();
                item = new Item((Item) pair.getValue());
                break;
            }
            it.remove();
        }
        if (index >= 0) {
            itemList.remove(index);
            freeVolume += volume;
            return item;
        }
        return item;
    }

    public void removeItem(int itemId) {
        int index = -1;

        long volume = 0;

        Iterator it = itemList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (((Item) pair.getValue()).id() == itemId) {
                volume = ((Item) pair.getValue()).getVolume();
                index = (int) pair.getKey();
                break;
            }
            it.remove();
        }
        if (index >= 0) {
            itemList.remove(index);
            freeVolume += volume;
        }
    }

    private void clearItems() {
        this.freeVolume = objectVolume.get();
        this.itemList.clear();
    }

    private LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getRentedTo() {
        return getRentDate().plusDays(getRentForDays());
    }

    private int getRentForDays() {
        return rentForDays;
    }

    public int getPersonId() {
        return personId;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public long getFreeVolume() {
        return freeVolume;
    }

    @Override
    public String toString() {
        if (rentDate == null)
            return
                    "{ " +
                            "Id:" + id +
                            ", Volume:" + objectVolume.get() + "m3" +
                            ", FreeVolume:" + getFreeVolume() + "m3" +
                            " }";
        else return
                "{ " +
                        "Id:" + id +
                        ", PersonId:" + getPersonId() +
                        ", Rent from:" + getRentDate() +
                        " to:" + getRentedTo() +
                        ", Volume:" + objectVolume.get() + "m3" +
                        ", FreeVolume:" + getFreeVolume() + "m3" +
                        " }";
    }

    public boolean isRented() {
        return rentDate != null;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public void dismantle() {
        for (TreeItem<WarehouseObject> warehouseTreeItem : Main.mainWindow.getController().warehouseManager.rootProperty().get().getChildren()) {
            if (warehouseTreeItem.getValue() instanceof Warehouse) {
                Warehouse warehouse = (Warehouse) warehouseTreeItem.getValue();
                if (warehouse != null) {
                    for (TreeItem<WarehouseObject> roomTreeItem : warehouseTreeItem.getChildren()) {
                        if (roomTreeItem.getValue() instanceof Room) {
                            Room room = (Room) roomTreeItem.getValue();
                            if (room.id() != this.id()) {
                                if (room.getPersonId() == this.getPersonId()) {
                                    if (!room.getRentedTo().isEqual(this.getRentedTo())) {
                                        HashMap<Integer, Item> tempItems = new HashMap<>(this.itemList);
                                        Iterator it = tempItems.entrySet().iterator();
                                        while (it.hasNext()) {
                                            Map.Entry pair = (Map.Entry) it.next();
                                            try {
                                                if (pair.getValue() != null) {
                                                    room.addItem(new Item((Item) pair.getValue()));
                                                    this.itemList.remove(pair.getKey());
                                                }
                                            } catch (TooManyThingsException e) {
                                                e.printStackTrace();
                                            }
                                            it.remove();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.clearItems();
        this.unrent();
    }
}
