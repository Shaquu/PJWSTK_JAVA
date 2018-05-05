package two;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings("ALL")
public class Two extends Application {

    public static Button[] buttons = new Button[20];

    private static String valueOne = "";
    private static String valueTwo = "";
    private static String action = "";
    private static String sumText = "";

    static TextField sumField;

    public Two() {
    }
    public Two(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setTitle("Kalkulator");

        sumField = new TextField ("0");
        sumField.setAlignment(Pos.CENTER_RIGHT);
        sumField.setPrefWidth(300);

        Label systemLbl = new Label("system liczbowy:");
        systemLbl.setAlignment(Pos.CENTER);
        systemLbl.setPrefWidth(300);

        RadioButton hexRb = new RadioButton("szesnastkowy");
        hexRb.setPrefWidth(100);
        RadioButton decRb = new RadioButton("dziesietny");
        decRb.setPrefWidth(100);
        RadioButton binRb = new RadioButton("binarny");
        binRb.setPrefWidth(100);

        Button zeroBtn = new CalcButton ("0", CalcButtonType.BINDEC);
        Button oneBtn = new CalcButton ("1", CalcButtonType.BINDEC);
        Button twoBtn = new CalcButton ("2", CalcButtonType.DEC);
        Button threeBtn = new CalcButton ("3", CalcButtonType.DEC);
        Button fourBtn = new CalcButton ("4", CalcButtonType.DEC);
        Button fiveBtn = new CalcButton ("5", CalcButtonType.DEC);
        Button sixBtn = new CalcButton ("6", CalcButtonType.DEC);
        Button sevenBtn = new CalcButton ("7", CalcButtonType.DEC);
        Button eightBtn = new CalcButton ("8", CalcButtonType.DEC);
        Button nineBtn = new CalcButton ("9", CalcButtonType.DEC);
        Button aBtn = new CalcButton ("A", CalcButtonType.HEX);
        Button bBtn = new CalcButton ("B", CalcButtonType.HEX);
        Button cBtn = new CalcButton ("C", CalcButtonType.HEX);
        Button dBtn = new CalcButton ("D", CalcButtonType.HEX);
        Button eBtn = new CalcButton ("E", CalcButtonType.HEX);
        Button fBtn = new CalcButton ("F", CalcButtonType.HEX);
        Button ceBtn = new CalcButton ("CE", CalcButtonType.ACTION);
        Button minusBtn = new CalcButton ("-", CalcButtonType.ACTION);
        Button plusBtn = new CalcButton ("+", CalcButtonType.ACTION);
        Button equalBtn = new CalcButton ("=", CalcButtonType.ACTION);

        int index = 0;
        buttons[index++] = zeroBtn;
        buttons[index++] = oneBtn;
        buttons[index++] = twoBtn;
        buttons[index++] = threeBtn;
        buttons[index++] = fourBtn;
        buttons[index++] = fiveBtn;
        buttons[index++] = sixBtn;
        buttons[index++] = sevenBtn;
        buttons[index++] = eightBtn;
        buttons[index++] = nineBtn;
        buttons[index++] = aBtn;
        buttons[index++] = bBtn;
        buttons[index++] = cBtn;
        buttons[index++] = dBtn;
        buttons[index++] = eBtn;
        buttons[index++] = fBtn;
        buttons[index++] = ceBtn;
        buttons[index++] = minusBtn;
        buttons[index++] = plusBtn;
        buttons[index++] = equalBtn;

        GridPane gridMain = new GridPane();

        GridPane gridOne = new GridPane();
        gridOne.addRow(0, sumField);
        gridOne.addRow(1, systemLbl);
        gridOne.addRow(2, new Label(""));

        GridPane gridTwo = new GridPane();
        gridTwo.addRow(0, hexRb, decRb, binRb);
        gridTwo.addRow(1, new Label(""));

        GridPane gridThree = new GridPane();
        gridThree.addRow(0, zeroBtn, oneBtn, twoBtn, threeBtn);
        gridThree.addRow(1, fourBtn, fiveBtn, sixBtn, sevenBtn);
        gridThree.addRow(2, eightBtn, nineBtn, aBtn, bBtn);
        gridThree.addRow(3, cBtn, dBtn, eBtn, fBtn);
        gridThree.addRow(4, ceBtn, minusBtn, plusBtn, equalBtn);
        gridThree.addRow(5, new Label(""));

        gridMain.addRow(0, gridOne );
        gridMain.addRow(1, gridTwo );
        gridMain.addRow(2, gridThree );

        Group root = (Group) scene.getRoot();
        root.getChildren().add(gridMain);

        stage.setScene(scene);
        stage.show();
    }

    public static void addValue(String value){
        if(action == null || action.length() < 1){
            valueOne += value;
        } else {
            valueTwo += value;
        }
        sumText += value;
        sumField.setText(sumText);
    }

    public static void addAction(String action){
        switch (action.toUpperCase()){
            case "CE":
                valueOne = "";
                valueTwo = "";
                action = "";
                sumText = "";
                sumField.setText("0");
                break;
            case "+":
            case "-":
                if (valueTwo.length() < 1) {
                    if(Two.action.length() > 0){
                        sumText = sumText.substring(0, sumText.length() - 1);
                    }
                    Two.action = action;
                    sumText += action;
                    sumField.setText(sumText);
                }
                break;
            case "=":
                int result = 0;
                if(action.equalsIgnoreCase("-")){
                    result = Integer.parseInt(valueOne) - Integer.parseInt(valueTwo);
                }
                if(action.equalsIgnoreCase("+")){
                    result = Integer.parseInt(valueOne) + Integer.parseInt(valueTwo);
                }
                sumField.setText(String.valueOf(result));

                valueOne = "";
                valueTwo = "";
                action = "";
                sumText = "";
                break;
        }
    }

}
