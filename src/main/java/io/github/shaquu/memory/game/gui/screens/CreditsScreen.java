/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.gui.screens;

import io.github.shaquu.memory.game.gui.utils.Screen;
import io.github.shaquu.memory.game.utils.GameLogger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CreditsScreen extends Screen {

    CreditsScreen() {
    }

    @Override
    public void init() {
        this.build();
    }

    @Override
    protected void build(){
        GameLogger.log("Building HighScoreScreen");

        VBox root = new VBox(5);
        root.setMinSize(450, 150);
        root.setMaxSize(450, 150);
        root.setPrefSize(450, 150);
        root.setAlignment(Pos.CENTER);

        Label gameTitleLbl = new Label("memory the game");
        gameTitleLbl.setFont(Font.font("Monospaced", 30));

        Label gameAuthor = new Label("by Tadeusz Wyrzykowski");
        gameAuthor.setFont(Font.font("Monospaced", 10));

        Label iconAuthor = new Label("Cards icons designed by Freepik from Flaticon");
        iconAuthor.setFont(Font.font("Monospaced", 15));


        root.getChildren().addAll(gameTitleLbl, gameAuthor, new Label(""), iconAuthor);
        root.setMinSize(root.getWidth(), root.getHeight());

        Scene scene = new Scene(root);
        this.getStage().setScene(scene);
    }
}
