package io.github.shaquu.warehouse.menu;


import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.gui.element.PersonListView;
import io.github.shaquu.warehouse.gui.element.WarehouseTreeView;
import io.github.shaquu.warehouse.item.Item;
import io.github.shaquu.warehouse.main.ObjectVolume;
import io.github.shaquu.warehouse.main.Warehouse;
import io.github.shaquu.warehouse.main.WarehouseObject;
import io.github.shaquu.warehouse.person.IncorrectPeselException;
import io.github.shaquu.warehouse.person.Person;
import io.github.shaquu.warehouse.room.AlreadyRentedException;
import io.github.shaquu.warehouse.room.Room;
import io.github.shaquu.warehouse.room.RoomStatus;
import io.github.shaquu.warehouse.room.TooManyThingsException;
import io.github.shaquu.warehouse.utils.FileManager;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("RedundantStringFormatCall")
public class Menu extends MenuScreen {

    private final MenuLevel selectingPersonLevel;
    private final MenuLevel mainLevel;
    private final MenuLevel personListLevel;
    private final MenuLevel personManageLevel;

    private int warehouseId;
    private int personId;
    private int roomId;

    public Menu() throws IncorrectPeselException {

        if(Main.mainWindow.getController().personManager == null){
            Main.mainWindow.getController().personManager = new PersonListView();
            Main.mainWindow.getController().personManager.addNewElement();
            Main.mainWindow.getController().personManager.removeNewElement();
        }

        if(Main.mainWindow.getController().warehouseManager == null){
            Main.mainWindow.getController().warehouseManager = new WarehouseTreeView();
            Main.mainWindow.getController().warehouseManager.addNewElement();
            Main.mainWindow.getController().warehouseManager.removeNewElement();
        }

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

        mainLevel = new MenuLevel("-----------------\n--- Options") {
            @Override
            protected void preparedShow() {
                System.out.printf("-- Person List [1]\n");
                System.out.printf("-- Select Person [2]\n");
                System.out.printf("-- Save Warehouse [3]\n");
                System.out.printf("-- Quit [Q]\n");
                System.out.printf("Command: ");

                String command = getInput().toUpperCase();

                switch (command) {
                    default:
                        System.out.printf("- Wrong command\n");
                        break;
                    case "Q":
                        System.out.printf("- Quiting\n");
                        active = false;
                        break;
                    case "1":
                        personListLevel.show();
                        break;
                    case "2":
                        selectingPersonLevel.show();
                        break;
                    case "3":
                        FileManager.printable();
                        System.out.printf("- Saved\n");
                        break;
                }
            }
        };

        personListLevel = new MenuLevel("-----------------\n--- Person List") {
            @Override
            protected void preparedShow() {
                for (Person person : Main.mainWindow.getController().personManager.getItems()) {
                    System.out.println(person.toString());
                }
                mainLevel.show();
            }
        };

        selectingPersonLevel = new MenuLevel("-----------------\n--- Selecting Person") {
            @Override
            protected void preparedShow() {
                System.out.printf("PersonId: ");
                String command = getInput().toUpperCase();

                int tempPersonId = Integer.parseInt(command);

                Person person = Main.mainWindow.getController().personManager.get(tempPersonId);

                if (person == null) {
                    System.out.printf("- Wrong PersonId\n");
                } else {
                    System.out.printf("- Selected Person %s\n", person);
                    Menu.this.personId = tempPersonId;
                    personManageLevel.show();
                }
            }
        };

        personManageLevel = new MenuLevel("-----------------\n--- Person Manage") {
            @Override
            protected void preparedShow() {
                int tempRoomId;
                Room room;

                System.out.printf("-- Print My Data [1]\n");
                System.out.printf("-- Switch Person [2]\n");
                System.out.printf("-- Print Non Occupied Rooms List [3]\n");
                System.out.printf("-- Print Occupied Rooms List [4]\n");
                System.out.printf("-- Print Room Data [5]\n");
                System.out.printf("-- Rent Room [6]\n");
                System.out.printf("-- Add Item To Room [7]\n");
                System.out.printf("-- Remove Item From Room [8]\n");
                System.out.printf("-- Back To Main Menu [Q]\n");
                System.out.printf("Command: ");

                String command = getInput().toUpperCase();

                switch (command) {
                    default:
                        System.out.printf("- Wrong command\n");
                        break;
                    case "Q":
                        mainLevel.show();
                        break;
                    case "1":
                        Person person = Main.mainWindow.getController().personManager.get(personId);
                        System.out.println("Person: " + person);

                        for (TreeItem<WarehouseObject> warehouseTree : Main.mainWindow.getController().warehouseManager.get()) {
                            boolean once = false;
                            if (warehouseTree.getValue() instanceof Warehouse) {
                                for (TreeItem<WarehouseObject> roomTree : warehouseTree.getChildren()) {
                                    if (roomTree.getValue() instanceof Room) {
                                        room = (Room) roomTree.getValue();
                                        if (room.getPersonId() == personId) {
                                            if (!once) {
                                                System.out.println("Warehouse: " + warehouseTree.getValue());
                                                once = true;
                                            }
                                            System.out.println("\tRoom: " + room);
                                        }
                                    }
                                }
                            }
                        }
                        personManageLevel.show();
                        break;
                    case "2":
                        selectingPersonLevel.show();
                        break;
                    case "3":
                        for (TreeItem<WarehouseObject> warehouseTree : Main.mainWindow.getController().warehouseManager.get()) {
                            boolean once = false;
                            if (warehouseTree.getValue() instanceof Warehouse) {
                                for (TreeItem<WarehouseObject> roomTree : warehouseTree.getChildren()) {
                                    if (roomTree.getValue() instanceof Room) {
                                        room = (Room) roomTree.getValue();
                                        if (!room.isRented()) {
                                            if (!once) {
                                                System.out.println("Warehouse: " + warehouseTree.getValue());
                                                once = true;
                                            }
                                            System.out.println("\tRoom: " + room);
                                        }
                                    }
                                }
                            }
                        }
                        personManageLevel.show();
                        break;
                    case "4":

                        for (TreeItem<WarehouseObject> warehouseTree : Main.mainWindow.getController().warehouseManager.get()) {
                            boolean once = false;
                            if (warehouseTree.getValue() instanceof Warehouse) {
                                for (TreeItem<WarehouseObject> roomTree : warehouseTree.getChildren()) {
                                    if (roomTree.getValue() instanceof Room) {
                                        room = (Room) roomTree.getValue();
                                        if (room.isRented()) {
                                            if (!once) {
                                                System.out.println("Warehouse: " + warehouseTree.getValue());
                                                once = true;
                                            }
                                            System.out.println("\tRoom: " + room);
                                        }
                                    }
                                }
                            }
                        }
                        personManageLevel.show();
                        break;
                    case "5":
                        System.out.println("RoomId: ");
                        tempRoomId = Integer.parseInt(getInput());
                        room = Main.mainWindow.getController().warehouseManager.getRoom(tempRoomId);
                        if (room == null) {
                            System.out.printf("- Wrong RoomId\n");
                            personManageLevel.show();
                            return;
                        }

                        System.out.println("Room: " + room);


                        List<Item> itemList = new ArrayList<>(room.itemList.values());
                        for (Item item : itemList) {
                            System.out.println("\tItems: " + item);
                        }

                        personManageLevel.show();
                        break;
                    case "6":
                        System.out.println("RoomId: ");
                        tempRoomId = Integer.parseInt(getInput());
                        room = Main.mainWindow.getController().warehouseManager.getRoom(tempRoomId);
                        if (room == null) {
                            System.out.printf("- Wrong RoomId\n");
                            personManageLevel.show();
                            return;
                        }

                        System.out.println("Rent For Days: ");
                        int rentForDays = Integer.parseInt(getInput());

                        try {
                            room.rent(Menu.this.personId, rentForDays);
                        } catch (AlreadyRentedException e) {
                            e.printStackTrace();
                        }

                        personManageLevel.show();
                        break;
                    case "7":
                        System.out.println("RoomId: ");
                        tempRoomId = Integer.parseInt(getInput());
                        room = Main.mainWindow.getController().warehouseManager.getRoom(tempRoomId);
                        if (room == null) {
                            System.out.printf("- Wrong RoomId\n");
                            personManageLevel.show();
                            return;
                        }


                        if (room.getStatus() == RoomStatus.RENTED) {
                            if (room.getPersonId() == Menu.this.personId) {
                                System.out.println("ItemName: ");
                                String itemName = getInput();
                                System.out.println("ItemVolume(m3): ");
                                long itemVolume = Long.parseLong(getInput());
                                Item item = new Item(itemName, new ObjectVolume(itemVolume));
                                try {
                                    room.addItem(item);
                                } catch (TooManyThingsException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.printf("- Cannot add item to not owned room\n");
                            }
                        } else {
                            System.out.printf("- Cannot add item to not rented room\n");
                        }

                        personManageLevel.show();
                        break;
                    case "8":
                        System.out.println("RoomId: ");
                        tempRoomId = Integer.parseInt(getInput());
                        room = Main.mainWindow.getController().warehouseManager.getRoom(tempRoomId);
                        if (room == null) {
                            System.out.printf("- Wrong RoomId\n");
                            personManageLevel.show();
                            return;
                        }

                        System.out.println("ItemName: ");
                        String itemName = getInput();
                        System.out.printf("- If exists then removed item %s from room %s\n", room.removeItem(itemName), room);
                        personManageLevel.show();
                        break;
                }
            }
        };
    }

    public void run() {

        System.out.printf("---- Welcome into interactive Warehouse Manager ----\n");
        while (active) {
            mainLevel.show();
        }

    }
}
