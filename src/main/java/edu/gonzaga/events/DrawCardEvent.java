package edu.gonzaga.events;

public class DrawCardEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private final EventHandlers handlers = new EventHandlers();

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean var) {
        this.cancelled = var;
    }

    @Override
    public EventHandlers getHandlers() {
        return handlers;
    }
}
