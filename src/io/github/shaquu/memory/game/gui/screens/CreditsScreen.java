package io.github.shaquu.memory.game.gui.screens;

import io.github.shaquu.memory.game.Main;
import io.github.shaquu.memory.game.gui.components.GameCardManager;
import io.github.shaquu.memory.game.gui.utils.Screen;
import io.github.shaquu.memory.game.score.Score;
import io.github.shaquu.memory.game.utils.GameLogger;
import io.github.shaquu.memory.game.utils.RandomImageName;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class CreditsScreen extends Screen {

    public CreditsScreen(){
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
