/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.gui.element;

import javafx.scene.image.Image;

public class NewElement {
    static Image plusImage;
    public boolean isNewElement = false;

    public NewElement() {
        plusImage = new Image("/plus.png");
    }
}
