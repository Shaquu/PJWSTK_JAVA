/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

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