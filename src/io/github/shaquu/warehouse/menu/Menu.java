package io.github.shaquu.warehouse.menu;


import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.person.Person;

@SuppressWarnings("RedundantStringFormatCall")
public class Menu extends MenuScreen {

    private final MenuLevel selectingPersonLevel;
    private final MenuLevel mainLevel;
    private final MenuLevel personListLevel;
    private final MenuLevel personManageLevel;

    private int warehouseId;
    private int personId;
    private int roomId;

    public Menu(){
        mainLevel = new MenuLevel("-----------------\n--- Options") {
            @Override
            protected void preparedShow() {
                System.out.printf("-- Person List [1]\n");
                System.out.printf("-- Select Person [2]\n");
                System.out.printf("-- Save Warehouse [3] - NOT WORKING\n");
                System.out.printf("-- Quit [Q]\n");
                System.out.printf("Command: ");

                String command = getInput().toUpperCase();

                switch(command){
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
                }
            }
        };

        personListLevel = new MenuLevel("-----------------\n--- Person List") {
            @Override
            protected void preparedShow() {
                for(Person person : Main.mainWindow.getController().personManager.getItems()){
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

                if(!Main.personManager.exists(tempPersonId)){
                    System.out.printf("- Wrong PersonId\n");
                } else {
                    System.out.printf("- Selected Person %s\n", Main.personManager.get(tempPersonId));
                    Menu.this.personId = tempPersonId;
                    personManageLevel.show();
                }
            }
        };

        personManageLevel = new MenuLevel("-----------------\n--- Person Manage") {
            @Override
            protected void preparedShow() {
                int tempWarehouseId;
                int tempRoomId;
                Room tempRoom;
                Warehouse tempWarehouse;

                System.out.printf("-- Print My Data [1]\n");
                System.out.printf("-- Switch Person [2]\n");
                System.out.printf("-- Print Non Occupied Rooms List [3]\n");
                System.out.printf("-- Print Occupied Rooms List [4]\n");
                System.out.printf("-- Print Room Data [5]\n");
                System.out.printf("-- Rent Room [6]\n");
                System.out.printf("-- Add Item To Room [7]\n");
                System.out.printf("-- Remove Item From Room [7]\n");
                System.out.printf("-- Back To Main Menu [Q]\n");
                System.out.printf("Command: ");

                String command = getInput().toUpperCase();

                switch(command){
                    default:
                        System.out.printf("- Wrong command\n");
                        break;
                    case "Q":
                        mainLevel.show();
                        break;
                    case "1":
                        System.out.println("Person: " + Main.personManager.get(personId));
                        for(Warehouse wh : Main.warehouseManager){
                            boolean once = false;
                            for(Room r : wh){
                                if(r.getPersonId() == personId){
                                    if(!once){
                                        System.out.println("Warehouse: " + wh);
                                        once = true;
                                    }
                                    System.out.println("\tRoom: " + r);
                                }
                            }
                        }
                        personManageLevel.show();
                        break;
                    case "2":
                        selectingPersonLevel.show();
                        break;
                    case "3":
                        for(Warehouse wh : Main.warehouseManager){
                            boolean once = false;
                            for(Room r : wh){
                                if(r.getPersonId() < 0){
                                    if(!once){
                                        System.out.println("Warehouse: " + wh);
                                        once = true;
                                    }
                                    System.out.println("\tRoom: " + r);
                                }
                            }
                        }
                        personManageLevel.show();
                        break;
                    case "4":
                        for(Warehouse wh : Main.warehouseManager){
                            boolean once = false;
                            for(Room r : wh){
                                if(r.getPersonId() > 0){
                                    if(!once){
                                        System.out.println("Warehouse: " + wh);
                                        once = true;
                                    }
                                    System.out.println("\tRoom: " + r);
                                }
                            }
                        }
                        personManageLevel.show();
                        break;
                    case "5":
                        System.out.println("WarehouseId: ");
                        tempWarehouseId = Integer.parseInt(getInput());
                        if(!Main.warehouseManager.exists(tempWarehouseId)) {
                            System.out.printf("- Wrong WarehouseId\n");
                            personManageLevel.show();
                            return;
                        }

                        tempWarehouse = Main.warehouseManager.get(tempWarehouseId);

                        System.out.println("RoomId: ");
                        tempRoomId = Integer.parseInt(getInput());
                        if(!tempWarehouse.exists(tempRoomId)){
                            System.out.printf("- Wrong RoomId\n");
                            personManageLevel.show();
                            return;
                        }

                        tempRoom = tempWarehouse.get(tempRoomId);

                        System.out.println("Warehouse: " + tempWarehouse);
                        System.out.println("\tRoom: " + tempRoom);

                        for(Item i : tempRoom){
                            System.out.println("\t\tItems: " + i);
                        }

                        personManageLevel.show();
                        break;
                    case "6":
                        System.out.println("WarehouseId: ");
                        tempWarehouseId = Integer.parseInt(getInput());
                        if(!Main.warehouseManager.exists(tempWarehouseId)) {
                            System.out.printf("- Wrong WarehouseId\n");
                            personManageLevel.show();
                            return;
                        }

                        tempWarehouse = Main.warehouseManager.get(tempWarehouseId);

                        System.out.println("RoomId: ");
                        tempRoomId = Integer.parseInt(getInput());
                        if(!tempWarehouse.exists(tempRoomId)){
                            System.out.printf("- Wrong RoomId\n");
                            personManageLevel.show();
                            return;
                        }

                        tempRoom = tempWarehouse.get(tempRoomId);

                        System.out.println("Rent For Days: ");
                        int rentForDays = Integer.parseInt(getInput());

                        try {
                            tempRoom.rent(Menu.this.personId, rentForDays);
                        } catch (Room.AlreadyRentedException e) {
                            e.printStackTrace();
                        }

                        personManageLevel.show();
                        break;
                    case "7":
                        System.out.println("WarehouseId: ");
                        tempWarehouseId = Integer.parseInt(getInput());
                        if(!Main.warehouseManager.exists(tempWarehouseId)) {
                            System.out.printf("- Wrong WarehouseId\n");
                            personManageLevel.show();
                            return;
                        }

                        tempWarehouse = Main.warehouseManager.get(tempWarehouseId);

                        System.out.println("RoomId: ");
                        tempRoomId = Integer.parseInt(getInput());
                        if(!tempWarehouse.exists(tempRoomId)){
                            System.out.printf("- Wrong RoomId\n");
                            personManageLevel.show();
                            return;
                        }
                        tempRoom = tempWarehouse.get(tempRoomId);


                        if(tempRoom.getStatus() == RoomStatus.RENTED){
                            if(tempRoom.getPersonId() == Menu.this.personId){
                                System.out.println("ItemName: ");
                                String itemName = getInput();
                                System.out.println("ItemVolume(m3): ");
                                long itemVolume = Long.parseLong(getInput());
                                Item item = new Item(itemName, itemVolume);
                                try {
                                    tempRoom.addItem(item);
                                } catch (Room.TooManyThingsException e) {
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
                        System.out.println("WarehouseId: ");
                        tempWarehouseId = Integer.parseInt(getInput());
                        if(!Main.warehouseManager.exists(tempWarehouseId)) {
                            System.out.printf("- Wrong WarehouseId\n");
                            personManageLevel.show();
                            return;
                        }

                        tempWarehouse = Main.warehouseManager.get(tempWarehouseId);

                        System.out.println("RoomId: ");
                        tempRoomId = Integer.parseInt(getInput());
                        if(!tempWarehouse.exists(tempRoomId)){
                            System.out.printf("- Wrong RoomId\n");
                            personManageLevel.show();
                            return;
                        }
                        tempRoom = tempWarehouse.get(tempRoomId);

                        System.out.println("ItemName: ");
                        String itemName = getInput();
                        if(!tempRoom.hasItem(itemName)){
                            System.out.printf("- Wrong ItemName\n");
                        } else {
                            System.out.printf("- Removed item %s from room %s\n", tempRoom.getAndRemoveItem(itemName), tempRoom);
                        }

                        personManageLevel.show();
                        break;
                }
            }
        };
    }

    public void run(){

        System.out.printf("---- Welcome into interactive Warehouse Manager ----\n");
        while (active){
            mainLevel.show();
        }

    }
}
