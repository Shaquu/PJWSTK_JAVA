package io.github.shaquu.warehouse.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

public abstract class Manageable<T extends Identifiable> extends Observable implements Iterable<T> {

    protected HashMap<Integer, T> list = new HashMap<>();

    public int add(T t) {
        System.out.printf("New %s %s\n", t.getClass().getSimpleName(), t.toString());
        list.put(t.id, t);
        setChanged();
        notifyObservers(t);
        return t.id;
    }

    public T get(int id) {
        return list.get(id);
    }

    public boolean exists(int id) {
        return list.containsKey(id);
    }

    public Iterator<T> iterator() {
        return new ManageableIterator();
    }

    private class ManageableIterator implements Iterator<T> {

        private Iterator<Map.Entry<Integer, T>> iterator;

        ManageableIterator() {
            iterator = Manageable.this.list.entrySet().iterator();
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public T next() {
            if (this.hasNext()) {
                Map.Entry<Integer, T> pair = iterator.next();
                return pair.getValue();
            }
            return null;
        }
    }
}