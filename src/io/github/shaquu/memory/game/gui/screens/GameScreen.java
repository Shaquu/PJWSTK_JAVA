/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.gui.screens;

import io.github.shaquu.memory.game.Main;
import io.github.shaquu.memory.game.gui.components.GameCardManager;
import io.github.shaquu.memory.game.gui.utils.Screen;
import io.github.shaquu.memory.game.score.Score;
import io.github.shaquu.memory.game.utils.GameLogger;
import io.github.shaquu.memory.game.utils.RandomImageName;
import io.github.shaquu.memory.game.utils.transitions.ShakeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class GameScreen extends Screen {

    private final int rows;
    private final int cols;
    private Score score;

    GameScreen(int rows, int cols, String playerName) {
        this.rows = rows;
        this.cols = cols;

        this.score = new Score(rows, cols);
        this.score.update(playerName);
    }

    @Override
    public void init() {
        new RandomImageName(rows, cols);
        Main.gameCardManager = new GameCardManager(rows, cols, this);
        this.build();
    }

    @Override
    protected void build(){
        GameLogger.log("Building GameScreen");

        VBox root = new VBox(40);
        root.setAlignment(Pos.CENTER);
        root.setMinWidth(350);

        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);

        pane.getChildren().add(Main.gameCardManager);
        pane.setMinSize(pane.getWidth(), pane.getHeight());

        timeFld = new Label("");
        timeFld.setAlignment(Pos.CENTER);
        timeFld.setMaxWidth(350);
        timeFld.setMinWidth(350);
        timeFld.setPrefWidth(350);
        timeFld.setBackground(Background.EMPTY);
        timeFld.setFont(Font.font("Monospaced", 20));

        root.getChildren().addAll(pane, timeFld);

        root.setMinSize(root.getWidth(), root.getHeight());

        Scene scene = new Scene(root);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (new KeyCodeCombination(KeyCode.Q, KeyCombination.SHIFT_ANY, KeyCombination.CONTROL_ANY).match(e)) {
                GameLogger.log("Quting game");
                this.exit();
            }
            e.consume();
        });

        this.getStage().setScene(scene);

        start();
    }

    private Label timeFld;
    private long startTime;
    private Timeline timeline;
    private long newTime;

    private void start(){
        startTime = System.currentTimeMillis();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1),
                        event -> {
                            newTime = System.currentTimeMillis() - startTime;
                            timeFld.setText(formatMillis(newTime));
                        }));
        timeline.playFromStart();
    }

    public void stop(){
        timeline.stop();
        score.update(newTime);
        score.finish();
        Main.score.add(score);
        Platform.runLater(() -> timeFld.setText("you won with time " + formatMillis(newTime)));

        new ShakeTransition(timeFld).playFromStart();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exit();
    }

    static String formatMillis(long timeInMillis) {
        String sign = "";
        if (timeInMillis < 0) {
            sign = "-";
            timeInMillis = Math.abs(timeInMillis);
        }

        long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
        long millis = timeInMillis % TimeUnit.SECONDS.toMillis(1);

        return sign +
                String.format("%02d", minutes) +
                String.format(":%02d", seconds) +
                String.format(".%03d", millis);
    }

    private void exit() {
        Platform.runLater(this::hide);
    }
}
