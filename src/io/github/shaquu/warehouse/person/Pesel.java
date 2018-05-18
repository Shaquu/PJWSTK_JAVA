/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.person;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Pesel {

    private String pesel;
    private LocalDate birthDate;

    public Pesel(String pesel) throws IncorrectPeselException {
        if (pesel.length() < 6) {
            throw new IncorrectPeselException(this.pesel, "incorrect length");
        }
        this.pesel = pesel;

        calculateBirthDate();
    }

    private void calculateBirthDate() throws IncorrectPeselException {
        String day = this.pesel.substring(4, 6);
        if (day.length() == 1)
            day = "0" + day;

        String month = normalizeMonth(this.pesel.substring(2, 4));
        if (month.length() == 1)
            month = "0" + month;

        String year = normalizeYear(this.pesel.substring(2, 4), this.pesel.substring(0, 2));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            birthDate = LocalDate.parse(year + "-" + month + "-" + day, formatter);
        } catch (DateTimeParseException e) {
            throw new IncorrectPeselException(this.pesel, "incorrect date");
        }
    }

    private String normalizeMonth(String rawMonth) throws IncorrectPeselException {
        String month;
        switch (rawMonth.substring(0, 1)) {
            case "8":
            case "9":
                month = String.valueOf((Integer.parseInt(rawMonth) - 80));
                break;
            case "0":
            case "1":
                month = String.valueOf((Integer.parseInt(rawMonth)));
                break;
            case "2":
            case "3":
                month = String.valueOf((Integer.parseInt(rawMonth) - 20));
                break;
            case "4":
            case "5":
                month = String.valueOf((Integer.parseInt(rawMonth) - 40));
                break;
            case "6":
            case "7":
                month = String.valueOf((Integer.parseInt(rawMonth) - 60));
                break;
            default:
                throw new IncorrectPeselException(this.pesel, "incorrect month");
        }
        if (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12)
            throw new IncorrectPeselException(this.pesel, "incorrect month " + Integer.parseInt(month));
        else return month;
    }

    private String normalizeYear(String rawMonth, String rawYear) throws IncorrectPeselException {
        switch (rawMonth.substring(0, 1)) {
            case "8":
            case "9":
                return "18" + rawYear;
            case "0":
            case "1":
                return "19" + rawYear;
            case "2":
            case "3":
                return "20" + rawYear;
            case "4":
            case "5":
                return "21" + rawYear;
            case "6":
            case "7":
                return "22" + rawYear;
            default:
                throw new IncorrectPeselException(this.pesel, "incorrect year");
        }
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String get() {
        return pesel;
    }

    @Override
    public String toString() {
        return
                "{ " +
                        "Pesel:" + get() +
                        ", BirthDate:" + getBirthDate() +
                        " }";
    }
}