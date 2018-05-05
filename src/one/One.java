package one;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings("ALL")
public class One extends Application {

    public One() {
    }
    public One(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setTitle("Sumator");

        Label aLbl = new Label("a:");
        TextField aField = new TextField ("start value");

        Label bLbl = new Label("b:");
        TextField bField = new TextField ("start value");

        Button sumBtn = new Button ("sum");
        sumBtn.setMinWidth(150);

        sumBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float a = 0;
                float b = 0;

                try {
                    a = Float.parseFloat(aField.getText());
                } catch (NumberFormatException e){
                    aField.setText("must be number");
                    sumBtn.setText("sum");
                    return;
                }

                try {
                    b = Float.parseFloat(bField.getText());
                } catch (NumberFormatException e){
                    bField.setText("must be number");
                    sumBtn.setText("sum");
                    return;
                }

                float sum = a + b;

                sumBtn.setText(String.valueOf(sum));
                event.consume();
            }
        });

        GridPane grid = new GridPane();
        grid.addRow(0, aLbl, aField, bLbl, bField, sumBtn);

        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);

        stage.setScene(scene);
        stage.show();
    }

}
