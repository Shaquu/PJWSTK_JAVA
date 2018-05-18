/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.person;

public class IncorrectPeselException extends Exception {
    private String pesel;
    private String reason;

    IncorrectPeselException(String pesel, String reason) {
        this.pesel = pesel;
        this.reason = reason;
    }

    @Override
    public void printStackTrace() {
        System.out.printf("Pesel %s is incorrect. Reason: %s\n", pesel, reason);
    }
}
