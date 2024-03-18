package uni.ldts.events;

import uni.ldts.events.collisions.CollisionFilter;
import uni.ldts.events.collisions.CollisionEvent;
import uni.ldts.model.GameManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {

    private final GameManager manager;
    private final ArrayList<ListenerClass> instances;
    private final HashMap<Class<?>, ArrayList<ListenerMethod>> listeners;

    public EventManager(GameManager manager) {
        this.instances = new ArrayList<>();
        this.listeners = new HashMap<>();
        this.manager = manager;
    }

    /**
     * ListenerClass contains an instance, along with @EventHandler
     * properties, such as associated game state/ master mode
     */
    public static class ListenerClass {
        public IEventListener instance;
        public boolean master;
        public int state;
        public ListenerClass(IEventListener i, int s, boolean m) {
            this.instance = i;
            this.state = s;
            this.master = m;
        }
    }

    /**
     * ListenerMethod maps a method to an index in the 'ArrayList instances' so
     * that we're able to invoke the listener method when an event is called
     */
    private static class ListenerMethod {
        public Method method;
        public int index;
        public ListenerMethod(Method m, int i) {
            method = m;
            index = i;
        }
    }

    /**
     * Register a new event listener
     * @param listener event listener
     */
    public void registerListener(IEventListener listener) {

        EventHandler handler = listener.getClass().getAnnotation(EventHandler.class);
        if (handler == null) return; // <- game dev forgot to add @EventHandler

        // if a listener class is empty, aka has no listener
        // methods, then it's not listening to anything and has no use
        boolean isEmpty = true;

        for (Method method : listener.getClass().getMethods()) {

            /* a method counts as a listener only if it
            contains a single parameter, of type Event */
            if (method.getParameters().length != 1) continue;
            Class<?> clazz = method.getParameters()[0].getType();
            if (!Event.class.isAssignableFrom(clazz)) continue;

            ArrayList<ListenerMethod> list = this.listeners.get(clazz);
            if (list == null) {
                list = new ArrayList<>();
                listeners.put(clazz, list);
            }

            int k = instances.size();
            list.add(new ListenerMethod(method, k));
            isEmpty = false;
        }

        if (!isEmpty) {
            ListenerClass c = new ListenerClass(listener,handler.state(),handler.master());
            instances.add(c);
        }
    }

    /**
     * By calling an event, you'll be notifying
     * every listener that is associated with it
     * @param event event to call
     */
    public void callEvent(Event event) {

        Class<?> clazz = event.getClass();
        if (!listeners.containsKey(clazz)) return;
        // no listeners for this event ^

        for (ListenerMethod m : listeners.get(clazz)) {

            // check if listener state matches current game state
            ListenerClass instancePkg = instances.get(m.index);
            if (instancePkg.state != manager.getState().toInt() && !instancePkg.master) continue;
            // custom check for collision event filtering
            if (event.getClass() == CollisionEvent.class) {
                CollisionEvent e = (CollisionEvent)event;
                CollisionFilter c = m.method.getAnnotation(CollisionFilter.class);
                if (c != null) {
                    if (c.typeA() != Integer.MIN_VALUE && c.typeA() != e.getA().getType().id()) continue;
                    if (c.typeB() != Integer.MIN_VALUE && c.typeB() != e.getB().getType().id()) continue;
                }
            }
            try {
                m.method.invoke(instancePkg.instance, event);
            } catch (ReflectiveOperationException e) {
                System.err.println("Error occurred notifying a listener of an event");
            }
        }
    }
}
