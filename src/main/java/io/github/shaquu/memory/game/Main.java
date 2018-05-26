/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game;

import io.github.shaquu.memory.game.gui.components.GameCardManager;
import io.github.shaquu.memory.game.gui.screens.MenuScreen;
import io.github.shaquu.memory.game.score.ScoreManager;
import io.github.shaquu.memory.game.utils.CardImageManager;
import io.github.shaquu.memory.game.utils.GameLogger;
import io.github.shaquu.memory.game.utils.ResourceManager;
import io.github.shaquu.memory.game.utils.transitions.PulseTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main extends Application {

    public static ResourceManager resourceManager;
    public static CardImageManager cardImageManager;
    public static GameCardManager gameCardManager;
    public static ScoreManager score;
    public static Rectangle2D screenBounds;

    public static void main(String[] args) throws IOException, URISyntaxException {
        screenBounds = Screen.getPrimary().getVisualBounds();

        new GameLogger();
        resourceManager = new ResourceManager();
        cardImageManager = new CardImageManager();
        score = new ScoreManager();

        launch(args);

        score.save();

    }

    @Override
    public void start(Stage primaryStage) {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.init();
        menuScreen.show();

        new PulseTransition(menuScreen.getStage().getScene().getRoot()).playFromStart();
    }
}
