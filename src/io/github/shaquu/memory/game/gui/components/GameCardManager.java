/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.gui.components;

import io.github.shaquu.memory.game.gui.screens.GameScreen;
import io.github.shaquu.memory.game.utils.RandomImageName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameCardManager extends VBox {

    private final int rows;
    private final int cols;
    public static double cardSpacing = 25;
    private int leftToMatch;

    private GameScreen gameScreen;
    private GameCardFX firstSelected;
    private GameCardFX secondSelected;

    public GameCardManager(int rows, int cols, GameScreen gameScreen){
        this.rows = rows;
        this.cols = cols;

        this.gameScreen = gameScreen;

        this.leftToMatch = rows * cols / 2;

        this.setSpacing(cardSpacing);
        VBox.setMargin(this, new Insets(cardSpacing, 0,cardSpacing, 0));

        build();
    }

    private void build(){
        for(int i = 0; i < this.rows; i++){
            HBox cardsRow = new HBox(cardSpacing);
            cardsRow.setAlignment(Pos.CENTER);
            VBox.setMargin(cardsRow, new Insets(0, cardSpacing,0 , cardSpacing));

            for(int j = 0; j < this.cols; j++){
                GameCardFX gameCard = new GameCardFX(RandomImageName.get());
                cardsRow.getChildren().add(gameCard);
            }
            cardsRow.setMinSize(cardsRow.getWidth(), cardsRow.getHeight());
            this.getChildren().add(cardsRow);
        }
        this.setMinSize(this.getWidth(), this.getHeight());
    }

    private long sleepTimeBeforeMatch = 1000;
    private long sleepTimeAfterMatch = 100;
    private boolean matching = false;

    void cardSelected(GameCardFX gameCardFX){
        if(gameCardFX.getState() == GameCardFX.GameCardState.DONE){
            return;
        }
        if(matching){
            return;
        }

        if(firstSelected == null){
            firstSelected = gameCardFX;
            gameCardFX.selected();
        } else {
            if(firstSelected.getState() == GameCardFX.GameCardState.REVERS){
                return;
            }
            matching = true;

            secondSelected = gameCardFX;
            gameCardFX.selected();

            if(firstSelected.name.equalsIgnoreCase(secondSelected.name)){
                new Thread(() -> {
                    leftToMatch--;
                    try {
                        Thread.sleep(sleepTimeBeforeMatch);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    firstSelected.matched();
                    secondSelected.matched();
                    if(leftToMatch < 1){
                        gameScreen.stop();
                    }
                    firstSelected = null;
                    try {
                        Thread.sleep(sleepTimeAfterMatch);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    matching = false;
                }).start();
            } else {
                new Thread(() -> {
                    try {
                        Thread.sleep(sleepTimeBeforeMatch);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    firstSelected.unselected();
                    secondSelected.unselected();
                    firstSelected = null;
                    try {
                        Thread.sleep(sleepTimeAfterMatch);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    matching = false;
                }).start();
            }
        }
    }

}
