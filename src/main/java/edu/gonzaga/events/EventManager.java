package edu.gonzaga.events;

import java.util.ArrayList;

public class EventManager {

    public static void callEvent(Event event) {
        EventHandlers handlers = event.getHandlers();
        ArrayList<Listener> listeners = handlers.getListeners();
        for (Listener listener : listeners) {
            listener.callEvent(event);
        }
    }
}
