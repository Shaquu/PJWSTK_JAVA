package two;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CalcButton extends Button {

    CalcButtonType type;

    public CalcButton(String text, CalcButtonType type){
        super(text);
        setPrefWidth(75);

        this.type = type;

        setOnAction(event -> {
            switch(type){
                case BINDEC:
                case DEC:
                case HEX:
                    Two.addValue(getText());
                    break;
                case ACTION:
                    Two.addAction(getText());
                    break;
            }
        });
    }
}
