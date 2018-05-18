package io.github.shaquu.warehouse.gui.controller;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.gui.animation.ShakeTransition;
import io.github.shaquu.warehouse.person.IncorrectPeselException;
import io.github.shaquu.warehouse.person.Person;
import io.github.shaquu.warehouse.person.Pesel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddPersonController extends WController {

    @FXML
    public TextField nameLbl;
    @FXML
    public TextField surnameLbl;
    @FXML
    public TextField peselLbl;
    @FXML
    public TextField birthdateLbl;
    @FXML
    public TextField addressLbl;

    @FXML
    public Button saveBtn;
    @FXML
    public Button closeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.addPersonWindow.setController(this);

        peselLbl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (active) {
                Pesel pesel;
                try {
                    pesel = new Pesel(peselLbl.getText());
                } catch (IncorrectPeselException e) {
                    birthdateLbl.clear();
                    return;
                }
                birthdateLbl.setText(pesel.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });


    }

    @FXML
    public void saveBtnAction(ActionEvent actionEvent) {
        if (!nameLbl.getText().matches("^.{1,}$")
                || !surnameLbl.getText().matches("^.{1,}$")
                || !peselLbl.getText().matches("^[0-9]{11}$")
                || !addressLbl.getText().matches("^.{1,}$")
                ) {
            new ShakeTransition(peselLbl).playFromStart();
            actionEvent.consume();
            return;
        }
        try {
            Person person = new Person(nameLbl.getText(), surnameLbl.getText(), peselLbl.getText(), addressLbl.getText());
            Main.mainWindow.getController().personManager.add(person);
            Main.addPersonWindow.hide();
            clearFields();
            actionEvent.consume();
        } catch (IncorrectPeselException e) {
            peselLbl.setText("Incorrect Pesel");
            new ShakeTransition(peselLbl).playFromStart();
        }
    }

    @FXML
    public void closeBtnAction(ActionEvent actionEvent) {
        Main.addPersonWindow.hide();
        clearFields();
        actionEvent.consume();
    }

    @Override
    public void clearFields() {
        nameLbl.clear();
        surnameLbl.clear();
        peselLbl.clear();
        birthdateLbl.clear();
        addressLbl.clear();
    }

    @Override
    public void init(int id) {

    }
}
