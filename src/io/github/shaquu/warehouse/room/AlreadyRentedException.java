/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.room;

public class AlreadyRentedException extends Exception {
    private Room room;

    AlreadyRentedException(Room room) {
        this.room = room;
    }

    @Override
    public void printStackTrace() {
        System.out.printf("Cannot rent room %s. Already rented to another person.\n", room);
    }
}