/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.main;

import io.github.shaquu.warehouse.gui.element.NewElement;

public abstract class Identifiable extends NewElement {
    protected int id;

    public int id() {
        return id;
    }
}
