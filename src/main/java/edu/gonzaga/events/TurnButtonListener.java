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
        Player p = frame.getPlayers().get(frame.getCurrentPlayerWatched());
        switch (event.getButtonType()) {
            case CALL_BUTTON:
                event.setCancelled(!handleCallButton(frame, frame.getCurrentBet() - p.getEscrowChips()));
                break;
            case FOLD_BUTTON:
                handleFoldButton(frame);
                break;
            case RAISE_BUTTON:
                boolean success = handleRaiseButton(frame, frame.getRaiseAmount());
                if (!success) event.setCancelled(true);
                break;
        }
    }

    private boolean handleCallButton(GameFrame gameFrame, int amt) {
        Player p = gameFrame.getPlayers().get(gameFrame.getCurrentPlayerWatched());
        return p.incrementEscrowChips(amt);
    }

    private void handleFoldButton(GameFrame gameFrame) {
        gameFrame.getPlayers().get(gameFrame.getCurrentPlayerWatched()).setFolded(true);
    }

    private boolean handleRaiseButton(GameFrame gameFrame, Integer amount) {
        Player p = gameFrame.getPlayers().get(gameFrame.getCurrentPlayerWatched());
        if (p.incrementEscrowChips(amount)) {
            gameFrame.raiseBetAmount(amount);
            return true;
        } else return false;
    }
}
