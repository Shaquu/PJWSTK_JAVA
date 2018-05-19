package io.github.shaquu.memory.game.gui.utils;

import javafx.stage.Stage;

public abstract class Screen {

    public abstract void init();
    protected abstract void build();

    protected Screen(){
        this.stage = new Stage();
    }
    private Stage stage;


    protected Stage getStage() {
        return stage;
    }

    public void show(){
        this.stage.show();
    }

    public void hide(){
        this.stage.hide();
    }
}
