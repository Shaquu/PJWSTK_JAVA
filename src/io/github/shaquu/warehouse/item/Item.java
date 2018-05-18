/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.item;


import io.github.shaquu.warehouse.main.Identifiable;
import io.github.shaquu.warehouse.main.ObjectVolume;

public class Item extends Identifiable {

    private static int itemCount = 1;
    private String name;
    private String volume;
    private ObjectVolume objectVolume;

    public Item(Item item) {
        this.id = itemCount++;
        this.name = item.name;
        this.objectVolume = item.objectVolume;
        this.volume = objectVolume.toString();
    }

    public Item(String name, ObjectVolume objectVolume) {
        this.id = itemCount++;
        this.name = name;
        this.objectVolume = objectVolume;
        this.volume = objectVolume.toString();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return
                "{ " +
                        "Id:" + id() +
                        ", Name:" + getName() +
                        ", Volume:" + objectVolume.get() + "m3" +
                        " }";
    }

    public long getVolume() {
        return objectVolume.get();
    }
}
