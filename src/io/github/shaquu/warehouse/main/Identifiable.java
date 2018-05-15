package io.github.shaquu.warehouse.main;

import io.github.shaquu.warehouse.gui.element.NewElement;

public abstract class Identifiable extends NewElement {
    private static final long serialVersionUID = 1L;
    protected int id;

    public int id() {
        return id;
    }
}
