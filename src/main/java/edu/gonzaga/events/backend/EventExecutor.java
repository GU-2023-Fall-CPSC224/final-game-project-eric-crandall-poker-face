package edu.gonzaga.events.backend;

import edu.gonzaga.events.Event;
import edu.gonzaga.events.annotations.EventMethod;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventExecutor {


    public Map<Class<? extends Event>, Set<Listener>> createListener(EventListener listener) {
        Map<Class<? extends Event>, Set<Listener>> ret = new HashMap<>();

        HashSet<Method> methods;
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<>();
            Method[] listenerMethods = publicMethods;
            int methodsLength = publicMethods.length;
            int i = 0;
            label:
            while (true) {
                Method method;
                if (i >= methodsLength) {
                    listenerMethods = privateMethods;
                    methodsLength = privateMethods.length;
                    i = 0;

                    while (true) {
                        if (i >= methodsLength) {
                            break label;
                        }
                        method = listenerMethods[i];
                        methods.add(method);
                        ++i;
                    }


                }
                method = listenerMethods[i];
                methods.add(method);
                ++i;
            }
        } catch (NoClassDefFoundError ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Could not register events for " + listener.getClass() + " because " + ex.getMessage() + " does not exist!");
            return ret;
        }

        Iterator<Method> iter = methods.iterator();

        while (true) {
            while (true) {
                Method method;
                EventMethod em;
                do {
                    do {
                        do {
                            if (!iter.hasNext()) {
                                return ret;
                            }

                            method = iter.next();
                            em = method.getAnnotation(EventMethod.class);
                        } while (em == null);
                    } while (method.isBridge());
                } while (method.isSynthetic());

                Class<?> checkClass;
                if (method.getParameterTypes().length == 1 && Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
                    final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
                    method.setAccessible(true);
                    Set<Listener> eventSet = ret.computeIfAbsent(eventClass, k -> new HashSet<>());

                    Method finalMethod = method;
                    ExecuteEvents executor = (listener1, event) -> {
                        try {
                            if (eventClass.isAssignableFrom(event.getClass())) {
                                finalMethod.invoke(listener1, event);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    };
                    eventSet.add(new Listener(listener, executor, em.ignoreCancelled()));
                }

            }
        }
    }
}
