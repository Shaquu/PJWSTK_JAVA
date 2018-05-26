/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.gui.screens;

import io.github.shaquu.memory.game.Main;
import io.github.shaquu.memory.game.gui.utils.Screen;
import io.github.shaquu.memory.game.score.Score;
import io.github.shaquu.memory.game.utils.GameLogger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HighScoreScreen extends Screen {

    HighScoreScreen() {
    }

    @Override
    public void init() {
        this.build();
    }

    @Override
    protected void build(){
        GameLogger.log("Building HighScoreScreen");

        VBox root = new VBox(5);
        root.setMinSize(300, 300);
        root.setMaxSize(300, 300);
        root.setPrefSize(300, 300);
        root.setAlignment(Pos.CENTER);

        Label gameTitleLbl = new Label("high score");
        gameTitleLbl.setFont(Font.font("Monospaced", 30));

        ScrollPane scrollPane = new ScrollPane();

        VBox scoresList = new VBox(15);
        scoresList.setAlignment(Pos.CENTER);

        for(Score score : Main.score.getOrderd()){
            Label scoreLbl = new Label("");

            String scoreText = score.playerName + " " +
                    score.rows +
                    "X" +
                    score.cols +
                    " T=" +
                    GameScreen.formatMillis(score.time);
            scoreLbl.setText(scoreText);

            scoreLbl.setFont(Font.font("Monospaced", 15));
            scoreLbl.setAlignment(Pos.CENTER);

            scoresList.getChildren().add(scoreLbl);
        }

        scrollPane.setContent(scoresList);
        root.getChildren().addAll(gameTitleLbl, new Label(""), scrollPane);
        root.setMinSize(root.getWidth(), root.getHeight());

        Scene scene = new Scene(root);
        this.getStage().setScene(scene);
    }
}
