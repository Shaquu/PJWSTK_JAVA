package io.github.shaquu.warehouse.utils;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.gui.controller.WController;
import io.github.shaquu.warehouse.item.Item;
import io.github.shaquu.warehouse.main.Warehouse;
import io.github.shaquu.warehouse.main.WarehouseObject;
import io.github.shaquu.warehouse.person.Person;
import io.github.shaquu.warehouse.room.Room;
import javafx.scene.control.TreeItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static void save() {
        try {

            FileOutputStream fos = new FileOutputStream("data.serializable");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Main.mainWindow.getController());
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printable() {

        try {

            FileOutputStream fos = new FileOutputStream("warehouseManager.txt");

            for (Person person : Main.mainWindow.getController().personManager.getItems()) {
                fos.write(("Person " + person.toString() + "\n").getBytes());
            }
            fos.write(("\n").getBytes());

            for (TreeItem<WarehouseObject> warehouseTree : Main.mainWindow.getController().warehouseManager.get()) {
                if (warehouseTree.getValue() instanceof Warehouse) {
                    fos.write(("Warehouse " + warehouseTree.getValue().toString() + "\n").getBytes());
                    for (TreeItem<WarehouseObject> roomTree : warehouseTree.getChildren()) {
                        if (roomTree.getValue() instanceof Room) {
                            Room room = (Room) roomTree.getValue();
                            fos.write(("\t Room " + room.toString() + "\n").getBytes());
                            if (room.isRented()) {
                                List<Item> itemList = new ArrayList<>(room.itemList.values());
                                order(itemList);

                                for (Item item : itemList) {
                                    fos.write(("\t\t Item " + item.toString() + "\n").getBytes());
                                }
                            }
                        }
                    }
                }
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            FileInputStream fis = new FileInputStream("data.serializable");
            ObjectInputStream ois = new ObjectInputStream(fis);
            WController result = (WController) ois.readObject();
            Main.mainWindow.getController().personManager = result.personManager;
            Main.mainWindow.getController().warehouseManager = result.warehouseManager;
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void order(List<Item> itemList) {

        itemList.sort((o1, o2) -> {

            long x1 = o1.getVolume();
            long x2 = o2.getVolume();

            if (x1 > x2) {
                return -1;
            } else if (x1 < x2)
                return 1;

            String y1 = o1.getName();
            String y2 = o2.getName();
            return y1.compareTo(y2);
        });
    }
}
