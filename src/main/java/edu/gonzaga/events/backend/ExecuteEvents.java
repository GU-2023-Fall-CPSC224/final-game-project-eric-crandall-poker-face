package edu.gonzaga.events.backend;

import edu.gonzaga.events.Event;

public interface ExecuteEvents {

    void executeEvent(EventListener listener, Event event);
}
