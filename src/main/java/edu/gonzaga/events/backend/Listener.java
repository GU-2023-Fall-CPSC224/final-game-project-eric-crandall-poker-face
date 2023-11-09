package edu.gonzaga.events.backend;

import edu.gonzaga.events.util.Cancellable;
import edu.gonzaga.events.Event;

public class Listener {
    private final EventListener eventListener;
    private final boolean ignoreCancelled;
    private final ExecuteEvents executor;

    public Listener(EventListener listener, ExecuteEvents executor, boolean ignoreCancelled) {
        this.eventListener = listener;
        this.executor = executor;
        this.ignoreCancelled = ignoreCancelled;
    }

    public void callEvent(Event event) {
        if (!(event instanceof Cancellable) || !((Cancellable)event).isCancelled() || !this.isIgnoringCancelled()) {
            this.executor.executeEvent(this.eventListener, event);
        }
    }

    public EventListener getListener() {
        return this.eventListener;
    }

    public boolean isIgnoringCancelled() {
        return this.ignoreCancelled;
    }
}
