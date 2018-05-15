package io.github.shaquu.warehouse.room;


import io.github.shaquu.warehouse.item.Item;

public class TooManyThingsException extends Exception {
    private Item item;
    private Room room;

    TooManyThingsException(Item item, Room room) {
        this.item = item;
        this.room = room;
    }

    @Override
    public void printStackTrace() {
        System.out.printf("Cannot add item %s to room %s. Item too big.\n", item.toString(), room.toString());
    }
}