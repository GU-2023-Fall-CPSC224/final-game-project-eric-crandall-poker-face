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
                int numUnfolded = 0;
                for (Player pl : frame.getPlayers()) {
                    if (!pl.isFolded()) numUnfolded++;
                }
                event.setCancelled(numUnfolded == 1);
                break;
            case RAISE_BUTTON:
                boolean success = handleRaiseButton(frame, frame.getRaiseAmount());
                if (!success) event.setCancelled(true);
                break;
        }
    }

    private boolean handleCallButton(GameFrame gameFrame, int amt) {
        Player p = gameFrame.getPlayers().get(gameFrame.getCurrentPlayerWatched());
        if (gameFrame.getCurrentBet() == 0) {
            System.out.println(p.getName() + " Checked");
        } else {
            System.out.println(p.getName() + " Called");
        }
        return p.incrementEscrowChips(amt);
    }

    private void handleFoldButton(GameFrame gameFrame) {
        Player p = gameFrame.getPlayers().get(gameFrame.getCurrentPlayerWatched());
        p.setFolded(true);
        System.out.println(p.getName() + " Folded");
    }

    private boolean handleRaiseButton(GameFrame gameFrame, Integer amount) {
        Player p = gameFrame.getPlayers().get(gameFrame.getCurrentPlayerWatched());
        int amt = amount + (gameFrame.getCurrentBet() - p.getEscrowChips());
        if (p.incrementEscrowChips(amt)) {
            gameFrame.raiseBetAmount(amount);
            System.out.println(p.getName() + " Raised " + amount + " Chips");
            return true;
        } else return false;
    }
}
