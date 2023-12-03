package edu.gonzaga.events;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.annotations.EventMethod;
import edu.gonzaga.events.backend.EventListener;

public class ChipChangeListener implements EventListener {

    public ChipChangeListener() {
        MainGame.getManager().registerEvent(this);
    }


    @EventMethod
    public void onChange(ChipChangeListener event) {

    }
}
