package edu.gonzaga.events;

import edu.gonzaga.events.backend.EventHandlers;
import edu.gonzaga.events.util.Cancellable;
import edu.gonzaga.items.GameFrame;

import javax.swing.*;

public class TurnButtonEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private static final EventHandlers handlers = new EventHandlers();

    public JButton getButton() {
        return button;
    }

    private final JButton button;

    public GameFrame getGameFrame() {
        return frame;
    }

    private final GameFrame frame;

    public ButtonType getButtonType() {
        return type;
    }

    private final ButtonType type;

    public TurnButtonEvent(JButton button, GameFrame frame, ButtonType type) {
        this.button = button;
        this.frame = frame;
        this.type = type;
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


    public enum ButtonType {
        CALL_BUTTON,
        FOLD_BUTTON,
        RAISE_BUTTON;
    }

}
