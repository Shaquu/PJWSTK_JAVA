/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.gui.controller;

import io.github.shaquu.warehouse.gui.element.ItemCanvas;
import io.github.shaquu.warehouse.gui.element.PersonListView;
import io.github.shaquu.warehouse.gui.element.WarehouseTreeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.Serializable;

public abstract class WController implements Serializable, Initializable {

    public boolean adminMode = false;
    //List with people
    @FXML
    public PersonListView personManager;
    //Tree of warehouse and rooms
    @FXML
    public WarehouseTreeView warehouseManager;
    //Graphical representation of room (items)
    @FXML
    public ItemCanvas itemCanvas;
    boolean active = false;

    public abstract void init(int id);

    public abstract void clearFields();
}
