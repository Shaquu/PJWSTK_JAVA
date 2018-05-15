package io.github.shaquu.warehouse.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WWindow {
    private Stage stage;
    private WController controller;

    public WWindow(Stage stage) {
        this.stage = new Stage();
    }

    public Stage getStage() {
        return stage;
    }

    public void setup(String fxmlPath, String title, boolean show) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/" + fxmlPath + ".fxml"));
        Parent content = loader.load();

        stage.setTitle(title);

        Scene scene = new Scene(content);

        stage.setTitle(title);
        stage.setScene(scene);

        if (show)
            show();
    }

    public WController getController() {
        return controller;
    }

    public void setController(WController controller) {
        this.controller = controller;
    }

    public void show() {
        getController().active = true;
        stage.show();
    }

    public void hide() {
        getController().active = false;
        stage.hide();
    }

}
