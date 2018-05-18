package io.github.shaquu.warehouse.gui.element;

import io.github.shaquu.warehouse.Main;
import io.github.shaquu.warehouse.person.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import java.time.LocalDate;

public class PersonListView extends ListView<Person> {

    private ObservableList<Person> personList;
    private Person newElementPerson;

    public PersonListView() {
        personList = FXCollections.observableArrayList();
        newElementPerson = new Person(true);

        this.setItems(personList);

        this.setCellFactory(param -> new ListCell<Person>() {
            @Override
            protected void updateItem(Person item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (item.isNewElement) {

                        setOnMousePressed(event -> {
                            if (Main.mainWindow.getController().adminMode)
                                Main.addPersonWindow.show();
                        });

                        setGraphic(new ImageView(NewElement.plusImage));
                        setText("Add Person");
                    } else {
                        setGraphic(null);
                        setOnMousePressed(event -> {
                            if (!Main.mainWindow.getController().adminMode) {
                                Main.selectedPersonid = item.id();
                                Main.mainWindow.getController().warehouseManager.forceRefresh();
                                if(event.getButton() == MouseButton.SECONDARY){
                                    Main.viewPersonWindow.getController().init(Main.selectedPersonid);
                                    Main.viewPersonWindow.show();
                                }
                            }
                        });
                        setText(item.getName() + " " + item.getSurname() + " #" + item.id());
                    }
                }
            }
        });

    }

    public void updateRentDate(int personId, LocalDate date) {
        for (Person aPersonList : personList) {
            if (aPersonList.id() == personId) {
                aPersonList.setFirstRentDate(date);
                return;
            }
        }
    }

    public void add(Person person) {
        personList.add(person);
    }

    public Person get(int personId) {
        for (Person aPersonList : personList) {
            if (aPersonList.id() == personId) {
                return aPersonList;
            }
        }
        return null;
    }

    public void removeNewElement() {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).isNewElement) {
                personList.remove(i);
            }
        }
    }

    public void addNewElement() {
        personList.add(newElementPerson);
    }

    public void clear() {
        personList.removeAll(personList);
        Main.selectedPersonid = -2;
    }
}
