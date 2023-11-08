package edu.gonzaga.events;

public interface Cancellable {

    boolean isCancelled();
    void setCancelled(boolean var);
}
