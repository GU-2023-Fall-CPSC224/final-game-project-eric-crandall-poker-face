package edu.gonzaga.events;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.annotations.EventMethod;
import edu.gonzaga.events.backend.EventListener;
import edu.gonzaga.events.backend.Listener;
import edu.gonzaga.items.GameFrame;
import edu.gonzaga.items.Player;
import edu.gonzaga.items.PlayerPanel;

public class TurnButtonListener implements EventListener {

    public TurnButtonListener() {
        MainGame.getManager().registerEvent(this);
    }


    @EventMethod
    public void onTurnButtonPressed(TurnButtonEvent event) {
        System.out.println("Event! ButtonType: " + event.getButtonType().name());
        GameFrame frame = event.getGameFrame();
        switch (event.getButtonType()) {
            case CALL_BUTTON:
                handleCallButton(frame, 0);
            case FOLD_BUTTON:
                handleFoldButton(frame);
            case RAISE_BUTTON:
                boolean success = handleRaiseButton(frame, 50);
                if (!success) event.setCancelled(true);
        }
    }

    private void handleCallButton(GameFrame gameFrame, int amt) {

    }

    private void handleFoldButton(GameFrame gameFrame) {
        gameFrame.getPlayers().get(gameFrame.getCurrentPlayerWatched()).setFolded(true);
    }

    private boolean handleRaiseButton(GameFrame gameFrame, int amt) {
        Player p = gameFrame.getPlayers().get(gameFrame.getCurrentPlayerWatched());
        if (p.incrementEscrowChips(amt)) {
            return true;
        } else return false;
    }
}