package io.github.shaquu.warehouse.room;

public class AlreadyRentedException extends Exception {
    private Room room;

    AlreadyRentedException(Room room) {
        this.room = room;
    }

    @Override
    public void printStackTrace() {
        System.out.printf("Cannot rent room %s. Already rented to another person.\n", room);
    }
}