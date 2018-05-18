/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.person;

import io.github.shaquu.warehouse.main.Identifiable;

import java.time.LocalDate;

public class Person extends Identifiable {

    private static int personCount = 1;

    private String name;
    private String surname;
    private Pesel pesel;
    private String address;
    private LocalDate firstRentDate;

    public Person(String name, String surname, String peselStr, String address) throws IncorrectPeselException {
        this.name = name;
        this.surname = surname;
        this.pesel = new Pesel(peselStr);
        this.address = address;

        this.id = personCount++;
    }

    public Person(boolean isNewElement) {
        this.isNewElement = isNewElement;
    }

    private LocalDate getFirstRentDate() throws NeverRentException {
        if (this.firstRentDate == null)
            throw new NeverRentException();
        return firstRentDate;
    }

    public void setFirstRentDate(LocalDate firstRentDate) {
        if (this.firstRentDate != null)
            this.firstRentDate = firstRentDate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPesel() {
        return pesel.get();
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return pesel.getBirthDate();
    }

    @Override
    public String toString() {
        try {
            return
                    "{ " +
                            "Id:" + id +
                            ", Name:" + getName() + " " + getSurname() +
                            ", Pesel:" + getPesel() +
                            ", Address:" + getAddress() +
                            ", BirthDate:" + getBirthDate() +
                            ", FirstRentDate: " + getFirstRentDate() +
                            " }";
        } catch (NeverRentException e) {
            return
                    "{ " +
                            "Id:" + id +
                            ", Name:" + getName() + " " + getSurname() +
                            ", Pesel:" + getPesel() +
                            ", Address:" + getAddress() +
                            ", BirthDate:" + getBirthDate() +
                            " }";
        }
    }
}