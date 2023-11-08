package edu.gonzaga.events;

import java.util.ArrayList;
import java.util.Collection;

public class EventHandlers {
    private final ArrayList<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public ArrayList<Listener> getListeners() {
        return this.listeners;
    }

    public boolean removeListener(Listener listener) {
        return listeners.remove(listener);
    }

    public void registerAll(Collection<Listener> listeners) {
        for (Listener listener : listeners) {
            this.addListener(listener);
        }
    }

}
