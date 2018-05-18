package io.github.shaquu.warehouse.gui.controller;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.person.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ViewPersonController extends WController {

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
    public Button closeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.viewPersonWindow.setController(this);
    }

    @FXML
    public void closeBtnAction(ActionEvent actionEvent) {
        Main.viewPersonWindow.hide();
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
        Person person = Main.mainWindow.getController().personManager.get(id);

        if(person == null){
            clearFields();
            Main.viewPersonWindow.hide();
            return;
        }

        nameLbl.setText(person.getName());
        surnameLbl.setText(person.getSurname());
        peselLbl.setText(person.getPesel());
        birthdateLbl.setText(person.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        addressLbl.setText(person.getAddress());
    }
}
