/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.gui.utils;

import javafx.stage.Stage;

public abstract class Screen {

    public abstract void init();

    protected abstract void build();

    protected Screen(){
        this.stage = new Stage();
    }

    private Stage stage;


    public Stage getStage() {
        return stage;
    }

    public void show(){
        this.stage.show();
    }

    public void hide() {
        this.stage.hide();
    }
}
