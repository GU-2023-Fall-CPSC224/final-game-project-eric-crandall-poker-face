package edu.gonzaga;

import edu.gonzaga.events.Event;
import edu.gonzaga.events.annotations.EventMethod;
import edu.gonzaga.events.backend.EventExecutor;
import edu.gonzaga.events.backend.EventHandlers;
import edu.gonzaga.events.backend.EventListener;
import edu.gonzaga.events.backend.EventManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EventTest extends Event {
    private static final EventHandlers handlers = new EventHandlers();

    private final int testNum;

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
}


class InnerTests implements EventListener {

    private final EventManager manager;

    public InnerTests() {
        EventExecutor executor = new EventExecutor();
        manager = new EventManager(executor);
        manager.registerEvent(this);
    }

    @Test
    void fireEventTest() {
        InnerTests mock = Mockito.mock(InnerTests.class);
        EventTest event = new EventTest(5);
        EventManager.callEvent(event);

        Mockito.verify(mock, Mockito.times(1)).dummyMethod();
    }


    private void dummyMethod() {}

    @EventMethod
    void onTestEvent(EventTest event) {
        dummyMethod();
    }
}
