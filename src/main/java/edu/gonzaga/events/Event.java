package edu.gonzaga.events;

import edu.gonzaga.events.backend.EventHandlers;
import edu.gonzaga.events.backend.EventManager;
import edu.gonzaga.events.util.Cancellable;

public abstract class Event {


    public boolean callEvent() {
        // TODO: 11/8/2023 Event caller method
        EventManager.callEvent(this);
        if (this instanceof Cancellable) {
            return !((Cancellable) this).isCancelled();
        } else {
            return true;
        }
    }

    public abstract EventHandlers getHandlers();


}
