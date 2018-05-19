/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.gui.screens;

import io.github.shaquu.memory.game.Main;
import io.github.shaquu.memory.game.gui.components.GameCardFX;
import io.github.shaquu.memory.game.gui.components.GameCardManager;
import io.github.shaquu.memory.game.gui.utils.Screen;
import io.github.shaquu.memory.game.utils.GameLogger;
import io.github.shaquu.memory.game.utils.ShakeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MenuScreen extends Screen {

    public MenuScreen(){
    }

    @Override
    public void init() {
        this.build();
    }

    @Override
    protected void build(){
        GameLogger.log("Building MenuScreen");

        float maxCols = (float) ((Main.screenBounds.getWidth() - 100) / (GameCardFX.width + GameCardManager.cardSpacing));
        GameLogger.log("MaxCols " + maxCols);
        float maxRows = (float) ((Main.screenBounds.getHeight() - 100) / (GameCardFX.height + GameCardManager.cardSpacing));
        GameLogger.log("MaxRows " + maxRows);

        VBox root = new VBox(25);
        root.setMinSize(700, 600);
        root.setMaxSize(700, 600);
        root.setPrefSize(700, 600);
        root.setAlignment(Pos.CENTER);

        Label gameTitleLbl = new Label("memory the game");
        gameTitleLbl.setFont(Font.font("Monospaced", 60));

        VBox inputs = new VBox(5);
        inputs.setAlignment(Pos.CENTER);
        HBox inputsRow = new HBox(5);
        inputsRow.setAlignment(Pos.CENTER);

        VBox nameV = new VBox(1);
        nameV.setAlignment(Pos.CENTER);

        TextField nameFld = new TextField();
        nameFld.setPromptText("...");
        nameFld.setAlignment(Pos.CENTER);
        nameFld.setMaxWidth(400);
        nameFld.setMinWidth(400);
        nameFld.setPrefWidth(400);
        nameFld.setBackground(Background.EMPTY);
        nameFld.setFont(Font.font("Monospaced", 40));

        Label nameLbl = new Label("is your name");
        nameLbl.setAlignment(Pos.CENTER);
        nameLbl.setMaxWidth(200);
        nameLbl.setMinWidth(200);
        nameLbl.setPrefWidth(200);
        nameLbl.setBackground(Background.EMPTY);
        nameLbl.setFont(Font.font("Monospaced", 20));

        nameV.getChildren().addAll(nameFld, nameLbl);

        VBox rovV = new VBox(1);
        rovV.setAlignment(Pos.CENTER);

        TextField rowFld = new TextField();
        rowFld.setPromptText("...");
        rowFld.setAlignment(Pos.CENTER);
        rowFld.setMaxWidth(150);
        rowFld.setMinWidth(150);
        rowFld.setPrefWidth(150);
        rowFld.setBackground(Background.EMPTY);
        rowFld.setFont(Font.font("Monospaced", 30));
        rowFld.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                rowFld.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        Label rowLbl = new Label("rows will be");
        rowLbl.setAlignment(Pos.CENTER);
        rowLbl.setMaxWidth(150);
        rowLbl.setMinWidth(150);
        rowLbl.setPrefWidth(150);
        rowLbl.setBackground(Background.EMPTY);
        rowLbl.setFont(Font.font("Monospaced", 10));


        rovV.getChildren().addAll(rowFld, rowLbl);

        TextField xLbl = new TextField("X");
        xLbl.setDisable(true);
        xLbl.setAlignment(Pos.CENTER);
        xLbl.setMaxWidth(50);
        xLbl.setMinWidth(50);
        xLbl.setPrefWidth(50);
        xLbl.setBackground(Background.EMPTY);
        xLbl.setFont(Font.font("Monospaced", 25));

        VBox colsV = new VBox(1);
        colsV.setAlignment(Pos.CENTER);

        TextField colFld = new TextField();
        colFld.setPromptText("...");
        colFld.setAlignment(Pos.CENTER);
        colFld.setMaxWidth(150);
        colFld.setMinWidth(150);
        colFld.setPrefWidth(150);
        colFld.setBackground(Background.EMPTY);
        colFld.setFont(Font.font("Monospaced", 30));
        colFld.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                colFld.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        Label colLbl = new Label("colls will be");
        colLbl.setAlignment(Pos.CENTER);
        colLbl.setMaxWidth(150);
        colLbl.setMinWidth(150);
        colLbl.setPrefWidth(150);
        colLbl.setBackground(Background.EMPTY);
        colLbl.setFont(Font.font("Monospaced", 10));

        colsV.getChildren().addAll(colFld, colLbl);

        inputsRow.getChildren().addAll(rovV, xLbl, colsV);

        inputs.getChildren().addAll(nameV, inputsRow);

        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction(event -> {
            try {
                int cols = Integer.parseInt(colFld.getText());
                int rows = Integer.parseInt(rowFld.getText());
                if(nameFld.getText().length() < 1){
                    GameLogger.log("To start game enter player name");
                    new ShakeTransition(root).playFromStart();
                    return;
                }
                if(cols * rows == 1){
                    GameLogger.log("To start game enter proper numbers in cols and rows - cannot be 1 and 1");
                    new ShakeTransition(root).playFromStart();
                    return;
                }
                if(cols * rows % 2 != 0){
                    GameLogger.log("To start game enter proper numbers in cols and rows - must make a even cards number");
                    new ShakeTransition(root).playFromStart();
                    return;
                }
                if(cols > maxCols){
                    GameLogger.log("To start game enter lower number for cols");
                    new ShakeTransition(root).playFromStart();
                    return;
                }
                if(rows > maxRows){
                    GameLogger.log("To start game enter lower number for rows");
                    new ShakeTransition(root).playFromStart();
                    return;
                }
                GameScreen gameScreen = new GameScreen(rows, cols, nameFld.getText());
                gameScreen.init();
                gameScreen.show();
            } catch (NumberFormatException e){
                GameLogger.log("To start game enter proper numbers in cols and rows - must be numbers");
                new ShakeTransition(root).playFromStart();
            }
        });
        newGameBtn.setBackground(Background.EMPTY);
        newGameBtn.setFont(Font.font("Monospaced", 22));

        Button highScoresBtn = new Button("High Scores");
        highScoresBtn.setOnAction(event -> {
            HighScoreScreen highScoreScreen = new HighScoreScreen();
            highScoreScreen.init();
            highScoreScreen.show();
        });
        highScoresBtn.setBackground(Background.EMPTY);
        highScoresBtn.setFont(Font.font("Monospaced", 22));


        Button creditsBtn = new Button("Credits");
        creditsBtn.setOnAction(event -> {
            CreditsScreen creditsScreen = new CreditsScreen();
            creditsScreen.init();
            creditsScreen.show();
        });
        creditsBtn.setBackground(Background.EMPTY);
        creditsBtn.setFont(Font.font("Monospaced", 22));


        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
        exitBtn.setBackground(Background.EMPTY);
        exitBtn.setFont(Font.font("Monospaced", 22));


        root.getChildren().addAll(gameTitleLbl, inputs, newGameBtn, highScoresBtn, creditsBtn, exitBtn);
        root.setMinSize(root.getWidth(), root.getHeight());

        Platform.runLater(root::requestFocus);

        Scene scene = new Scene(root);
        this.getStage().setScene(scene);
    }
}
