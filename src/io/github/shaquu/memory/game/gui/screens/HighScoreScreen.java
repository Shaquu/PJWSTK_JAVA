package io.github.shaquu.memory.game.gui.screens;

import io.github.shaquu.memory.game.Main;
import io.github.shaquu.memory.game.gui.components.GameCardFX;
import io.github.shaquu.memory.game.gui.components.GameCardManager;
import io.github.shaquu.memory.game.gui.utils.Screen;
import io.github.shaquu.memory.game.score.Score;
import io.github.shaquu.memory.game.utils.GameLogger;
import io.github.shaquu.memory.game.utils.RandomImageName;
import io.github.shaquu.memory.game.utils.ShakeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.event.MouseEvent;

public class HighScoreScreen extends Screen {

    public HighScoreScreen(){
    }

    @Override
    public void init() {
        this.build();
    }

    @Override
    protected void build(){
        GameLogger.log("Building HighScoreScreen");

        VBox root = new VBox(5);
        root.setMinSize(400, 300);
        root.setMaxSize(400, 300);
        root.setPrefSize(400, 300);
        root.setAlignment(Pos.CENTER);

        Label gameTitleLbl = new Label("high score");
        gameTitleLbl.setFont(Font.font("Monospaced", 30));

        ScrollPane scrollPane = new ScrollPane();

        VBox scoresList = new VBox(5);
        scoresList.setAlignment(Pos.CENTER);

        for(Score score : Main.score.getOrderd()){
            Label scoreLbl = new Label("");
            scoreLbl.setFont(Font.font("Monospaced", 15));
            scoreLbl.setAlignment(Pos.CENTER_RIGHT);

            String scoreText = score.playerName + " " +
                    score.rows +
                    "X" +
                    score.cols +
                    " T=" +
                    GameScreen.formatMillis(score.time);
            scoreLbl.setText(scoreText);

            scoresList.getChildren().add(scoreLbl);
        }

        scrollPane.setContent(scoresList);
        root.getChildren().addAll(gameTitleLbl, new Label(""), scrollPane);
        root.setMinSize(root.getWidth(), root.getHeight());

        Scene scene = new Scene(root);
        this.getStage().setScene(scene);
    }
}
