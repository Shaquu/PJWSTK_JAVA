/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.gui.components;

import io.github.shaquu.memory.game.Main;
import io.github.shaquu.memory.game.utils.GameLogger;
import io.github.shaquu.memory.game.utils.transitions.PulseTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

@SuppressWarnings("ALL")
public class GameCardFX extends VBox {

    private static Image cardReverse = Main.resourceManager.getImage("cardReverse");
    private static Image cardFinished = Main.resourceManager.getImage("cardFinished");
    private Image cardAverse;
    private ImageView imageView;
    private Label label;
    public GameCardState state = GameCardState.REVERS;

    protected String name;

    public static float width = 128f;
    public static float height = 103f;

    public GameCardFX(String name){
        this.name = name;
        this.cardAverse = Main.cardImageManager.get(name);

        this.setPrefSize(width, height);
        this.setMinSize(width, height);
        this.setMaxSize(width, height);
        this.setSpacing(2);
        this.setAlignment(Pos.CENTER);

        this.imageView = new ImageView(cardReverse);
        this.imageView.setFitHeight(84f);
        this.imageView.setFitWidth(width);

        this.imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Main.gameCardManager.cardSelected(this);
        });

        this.label = new Label(name);
        this.label.setVisible(false);

        configureFlip();

        this.getChildren().add(this.imageView);
        this.getChildren().add(this.label);
    }

    private double flipSpeed = 300;
    private PulseTransition matchedPulse;
    private volatile boolean flipping = false;
    private ScaleTransition scaleTransitionHide;
    private ScaleTransition scaleTransitionShow;

    void selected() {
        flip();
    }

    void matched() {
        flipMatched();
    }

    void unselected() {
        flip();
    }

    private void configureFlip() {
        this.scaleTransitionHide = new ScaleTransition(Duration.millis(flipSpeed), this.imageView);
        this.scaleTransitionHide.setByY(-1f);
        this.scaleTransitionHide.setCycleCount(1);
        this.scaleTransitionHide.setAutoReverse(false);

        this.scaleTransitionShow = new ScaleTransition(Duration.millis(flipSpeed), this.imageView);
        this.scaleTransitionShow.setByY(1f);
        this.scaleTransitionShow.setCycleCount(1);
        this.scaleTransitionShow.setAutoReverse(false);


        this.scaleTransitionHide.setOnFinished(event -> {
            switch (state) {
                case MATCHED:
                    GameLogger.log("Card matched " + GameCardFX.this.name);
                    //GameCardFX.this.imageView.setImage(cardFinished);
                    GameCardFX.this.state = GameCardState.DONE;
                    //GameCardFX.this.label.setVisible(false);

                    break;
                case AVERS:
                    GameCardFX.this.imageView.setImage(cardReverse);
                    GameCardFX.this.state = GameCardState.REVERS;
                    GameCardFX.this.label.setVisible(false);
                    break;
                case REVERS:
                    GameCardFX.this.imageView.setImage(cardAverse);
                    GameCardFX.this.state = GameCardState.AVERS;
                    GameCardFX.this.label.setVisible(true);
            }

            scaleTransitionShow.play();
        });

        this.scaleTransitionShow.setOnFinished(event -> {
            flipping = false;
        });

        Paint defaultPaint = label.getTextFill();
        matchedPulse = new PulseTransition(label);
    }

    private void flip(){
        new Thread(() -> {
            while(flipping){}
            flipping = true;
            if (GameCardFX.this.state != GameCardState.MATCHED) {
                scaleTransitionHide.play();
            } else {
                matchedPulse.playFromStart();
            }
        }).start();
    }

    public GameCardState getState() {
        return state;
    }

    public enum GameCardState {
        AVERS, REVERS, MATCHED, DONE
    }

    private void flipMatched(){
        new Thread(() -> {
            while(flipping){}
            GameCardFX.this.state = GameCardState.MATCHED;
            //flipping = true;
            //scaleTransitionHide.play();
        }).start();
    }
}
