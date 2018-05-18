package io.github.shaquu.warehouse.menu;

import java.util.Scanner;

public abstract class MenuLevel {

    private String header;
    private String footer;

    public MenuLevel(String header){
        this.header = header;
    }

    protected void printHeader() {
        System.out.println(header);
    }

    private static Scanner sc = new Scanner(System.in);

    protected abstract void preparedShow();

    protected void show(){
        printHeader();
        preparedShow();
    }

    protected String getInput(){
        return sc.nextLine();
    }

    protected void printSpacing(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

}
