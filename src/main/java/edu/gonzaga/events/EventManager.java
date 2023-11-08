package edu.gonzaga.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class EventManager {

    private final EventExecutor executor;


    public EventManager(EventExecutor executor) {
        this.executor = executor;
    }

    public static void callEvent(Event event) {
        EventHandlers handlers = event.getHandlers();
        ArrayList<Listener> listeners = handlers.getListeners();
        for (Listener listener : listeners) {
            listener.callEvent(event);
        }
    }

    public void registerEvent(EventListener listener) {
        for (Map.Entry<Class<? extends Event>, Set<Listener>> entry : executor.createListener(listener).entrySet()) {
            try {
                this.getEventListeners(this.getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private EventHandlers getEventListeners(Class<? extends Event> clazz) throws NoSuchMethodException {
        try {
            Method method = this.getRegistrationClass(clazz).getDeclaredMethod("getHandlersList");
            method.setAccessible(true);
            return (EventHandlers) method.invoke(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
            throw new NoSuchMethodException(ex.getMessage());
        }
    }


    private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlersList");
            return clazz;
        } catch (NoSuchMethodException ex) {
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class) && Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return this.getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            } else {
                throw new IllegalCallerException("Could not find getHandlers method for event class! ");
            }
        }
    }
}
