package edu.gonzaga.events.util;

public interface Cancellable {

    boolean isCancelled();
    void setCancelled(boolean var);
}
