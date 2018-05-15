package io.github.shaquu.warehouse.main;


import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.room.Room;

import java.util.ArrayList;
import java.util.List;

public class Warehouse extends WarehouseObject<Room> {

    private static int warehouseCount = 1;

    public Warehouse() {
        super(false, true, false, -1, -1);
        this.id = warehouseCount++;
        this.objectList = new ArrayList<>();
    }

    public Warehouse(List<Room> rooms) {
        super(false, true, false, -1, -1);
        this.objectList = rooms;
        this.id = warehouseCount++;
    }

    public boolean exists(int roomId) {
        for (Room r : objectList) {
            if (r.id == roomId)
                return true;
        }
        return false;
    }

    public Room get(int roomId) {
        for (Room r : objectList) {
            if (r.id == roomId)
                return r;
        }
        return null;
    }

    @Override
    public String toString() {
        return
                "{ " +
                        "Id:" + id +
                        ", Rooms:" + Main.mainWindow.getController().warehouseManager.roomCount(id) +
                        " }";
    }
}
