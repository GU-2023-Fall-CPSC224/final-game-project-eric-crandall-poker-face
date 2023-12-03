package edu.gonzaga.events;

import edu.gonzaga.events.backend.EventHandlers;
import edu.gonzaga.items.Card;
import edu.gonzaga.items.Player;

public class PlayerChipChangeEvent extends Event {

    private static final EventHandlers handlers = new EventHandlers();

    private final Player player;

    public PlayerChipChangeEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }


    public static EventHandlers getHandlersList() {
        return handlers;
    }

    @Override
    public EventHandlers getHandlers() {
        return handlers;
    }
}
