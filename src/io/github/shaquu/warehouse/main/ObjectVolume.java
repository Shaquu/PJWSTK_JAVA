package io.github.shaquu.warehouse.main;

public class ObjectVolume {

    private long volume;

    public ObjectVolume(long volume) {
        this.volume = volume;
    }

    public ObjectVolume(long x, long y, long z) {
        this.volume = x * y * z;
    }


    public long get() {
        return volume;
    }

    public void set(long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return String.valueOf(volume);
    }
}