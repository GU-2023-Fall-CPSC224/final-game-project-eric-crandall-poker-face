package edu.gonzaga.events;

import edu.gonzaga.events.backend.EventHandlers;
import edu.gonzaga.events.util.Cancellable;
import edu.gonzaga.items.Card;

public class DrawCardEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private static final EventHandlers handlers = new EventHandlers();

    private final Card card;

    public DrawCardEvent(Card cardDrawn) {
        this.card = cardDrawn;
    }

    public Card getDrawnCard() {
        return this.card;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean var) {
        this.cancelled = var;
    }

    public static EventHandlers getHandlersList() {
        return handlers;
    }

    @Override
    public EventHandlers getHandlers() {
        return handlers;
    }
}
