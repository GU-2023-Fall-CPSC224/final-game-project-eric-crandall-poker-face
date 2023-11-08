package edu.gonzaga.events;

import edu.gonzaga.events.annotations.EventMethod;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventExecutor {

    public void registerEvent(EventListener listener) {
        Iterator iter = createListener(listener).entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Class<? extends Event>, Set<Listener>> entry = (Map.Entry)iter.next();
            //this.getEventListeners(this.getRegistrationClass((Class)entry.getKey())).registerAll((Collection)entry.getValue());
        }
    }

    private EventHandlers getEventListeners(Class<? extends Event> clazz) {
        try {
            Method method = this.getRegistrationClass(clazz).getDeclaredMethod("getHandlers");
            method.setAccessible(true);
            return (EventHandlers) method.invoke((Object)null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlers");
            return clazz;
        } catch (NoSuchMethodException ex) {
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class) && Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return this.getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            } else {
                throw new IllegalCallerException("Could not find getHandlers method for event class! ");
            }
        }
    }


    public Map<Class<? extends Event>, Set<Listener>> createListener(EventListener listener) {
        Map<Class<? extends Event>, Set<Listener>> ret = new HashMap();

        HashSet methods;
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet();
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

        Iterator iter = methods.iterator();

        while (true) {
            while (true) {
                Method method;
                EventMethod em;
                do {
                    do {
                        do {
                            if (iter.hasNext()) {
                                return ret;
                            }

                            method = (Method) iter.next();
                            em = (EventMethod) method.getAnnotation(EventMethod.class);
                        } while (em == null);
                    } while (method.isBridge());
                } while (method.isSynthetic());

                Class checkClass;
                if (method.getParameterTypes().length == 1 && Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
                    final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
                    method.setAccessible(true);
                    Set<Listener> eventSet = (Set) ret.get(eventClass);
                    if (eventSet == null) {
                        eventSet = new HashSet<>();
                        ret.put(eventClass, eventSet);
                    }

                    Method finalMethod = method;
                    ExecuteEvents executor = new ExecuteEvents() {
                        public void executeEvent(EventListener listener, Event event) {
                            try {
                                if (eventClass.isAssignableFrom(event.getClass())) {
                                    finalMethod.invoke(listener, event);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    };
                    eventSet.add(new Listener(listener, executor, em.ignoreCancelled()));
                }

            }
        }
    }
}
