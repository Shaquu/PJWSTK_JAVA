/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.main;

public class ObjectVolume {

    private long volume;

    public ObjectVolume(long volume) {
        this.volume = volume;
    }

    public ObjectVolume(long x, long y, long z) {
        this.volume = x * y * z;
    }


    public long get() {
        return volume;
    }

    @SuppressWarnings("unused")
    public void set(long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return String.valueOf(volume);
    }
}