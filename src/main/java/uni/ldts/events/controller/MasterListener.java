package uni.ldts.events.controller;

import uni.ldts.events.EventHandler;
import uni.ldts.model.GameManager;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MasterListener implements java.awt.event.KeyListener {

    protected final GameManager manager;
    private final ArrayList<IKeyListener> listeners;

    public MasterListener(GameManager manager) {
        this.manager = manager;
        this.listeners = new ArrayList<>();
    }

    // for external sources to inject more listeners
    public void newListener(IKeyListener l) { this.listeners.add(l); }

    /**
     * When a key is pressed, the MasterListener will notify only the children whose
     * specified state (through the @EventHandler) matches the current game state
     */
    private boolean canNotify(IKeyListener l) {
        EventHandler handler = l.getClass().getAnnotation(EventHandler.class);
        if (handler == null) return false; // <- forgot to add @EventHandler
        return handler.master() || handler.state() == manager.getState().toInt();
    }

    @Override
    public void keyTyped(KeyEvent e) { /* null - we don't need it */ }

    @Override
    public void keyPressed(KeyEvent e) {
        for (IKeyListener l : this.listeners) {
            if (this.canNotify(l)) l.onKeyPress(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (IKeyListener l : this.listeners) {
            if (this.canNotify(l)) l.onKeyRelease(e);
        }
    }
}
