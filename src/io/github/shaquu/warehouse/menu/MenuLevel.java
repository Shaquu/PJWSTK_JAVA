/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.warehouse.menu;

import java.util.Scanner;

public abstract class MenuLevel {

    private static Scanner sc = new Scanner(System.in);
    private String header;

    MenuLevel(String header) {
        this.header = header;
    }

    private void printHeader() {
        System.out.println(header);
    }

    protected abstract void preparedShow();

    void show() {
        printHeader();
        preparedShow();
    }

    String getInput() {
        return sc.nextLine();
    }

}
