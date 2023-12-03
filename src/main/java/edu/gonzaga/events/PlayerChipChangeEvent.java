package edu.gonzaga.events;

import edu.gonzaga.events.backend.EventHandlers;
import edu.gonzaga.items.Player;

public class PlayerChipChangeEvent extends Event {

    private static final EventHandlers handlers = new EventHandlers();

    private final Player player;
    private final int prevChips;
    private final int newChips;

    public PlayerChipChangeEvent(Player player, int prevChips, int newChips) {
        this.player = player;
        this.prevChips = prevChips;
        this.newChips = newChips;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getPrevChips() {
        return prevChips;
    }

    public int getNewChips() {
        return newChips;
    }

    public static EventHandlers getHandlersList() {
        return handlers;
    }

    @Override
    public EventHandlers getHandlers() {
        return handlers;
    }
}
