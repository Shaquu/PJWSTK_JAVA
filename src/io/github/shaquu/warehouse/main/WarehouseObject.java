package io.github.shaquu.warehouse.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WarehouseObject<T> extends Identifiable implements Iterable<T> {

    public boolean isWarehouse;
    public boolean isRoom;
    public int warehouseId;
    public int roomId;
    List<T> objectList = new ArrayList<>();

    public WarehouseObject(boolean isNewElement, boolean isWarehouse, boolean isRoom, int warehouseId, int roomId) {
        this.isNewElement = isNewElement;
        this.isWarehouse = isWarehouse;
        this.warehouseId = warehouseId;
        this.isRoom = isRoom;
        this.roomId = roomId;
    }

    public Iterator<T> iterator() {
        return new WarehouseObjectIterator();
    }

    @Override
    public String toString() {
        return
                "{ " +
                        "Id:" + id +
                        ", isNewElement:" + isNewElement +
                        ", isWarehouse:" + isWarehouse +
                        " }";
    }

    private class WarehouseObjectIterator implements Iterator<T> {

        int id = 0;

        public boolean hasNext() {
            return id < objectList.size();
        }

        public T next() {
            if (this.hasNext()) {
                return objectList.get(id++);
            }
            return null;
        }
    }
}
