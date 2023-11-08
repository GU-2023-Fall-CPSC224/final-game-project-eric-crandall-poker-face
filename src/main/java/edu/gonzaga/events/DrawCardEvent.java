package edu.gonzaga.events;

public class DrawCardEvent extends Event implements Cancellable {

    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean var) {
        this.cancelled = var;
    }
}
