package edu.gonzaga;

import edu.gonzaga.events.Event;
import edu.gonzaga.events.annotations.EventMethod;
import edu.gonzaga.events.backend.EventExecutor;
import edu.gonzaga.events.backend.EventHandlers;
import edu.gonzaga.events.backend.EventListener;
import edu.gonzaga.events.backend.EventManager;
import edu.gonzaga.events.util.Cancellable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EventTest extends Event implements Cancellable {
    private static final EventHandlers handlers = new EventHandlers();

    private final int testNum;
    private boolean cancelled = false;

    public EventTest(int numToTest) {
        this.testNum = numToTest;
    }

    public static EventHandlers getHandlersList() {
        return handlers;
    }

    @Override
    public EventHandlers getHandlers() {
        return handlers;
    }


    public int getTestNum() {
        return this.testNum;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean var) {
        this.cancelled = var;
    }
}

class TestListener implements EventListener {

    public TestListener() {

    }

    public void dummyCancelMethod() {
    }

    @EventMethod
    void onTestEvent(EventTest event) {
        event.setCancelled(true);
    }
}


class InnerTests {

    @Test
    void fireEventTest() {
        EventExecutor executor = new EventExecutor();
        EventManager manager = new EventManager(executor);

        TestListener mock = Mockito.mock(TestListener.class);
        manager.registerEvent(mock);
        EventTest event = new EventTest(5);
        EventManager.callEvent(event);

        Mockito.verify(mock, Mockito.times(1)).onTestEvent(Mockito.any());
    }

}
