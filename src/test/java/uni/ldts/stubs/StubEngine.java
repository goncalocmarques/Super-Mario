package uni.ldts.stubs;

import uni.ldts.events.controller.IKeyListener;
import uni.ldts.events.controller.MasterListener;
import uni.ldts.model.GameManager;

public class StubEngine implements Runnable {

    private final GameManager manager;
    private final MasterListener listener;
    private final Thread thread;
    private final int tps;

    public StubEngine(GameManager manager) {
        this.tps = 120;
        this.manager = manager;
        this.listener = new MasterListener(manager);
        this.thread = new Thread(this, "test");
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {

        double delta1 = 1000.0/tps;
        long lastTick = System.currentTimeMillis();

        while (!thread.isInterrupted()) {
            long now = System.currentTimeMillis();
            if (now-lastTick >= delta1) {
                this.manager.tick();
                lastTick = System.currentTimeMillis();
            }
        }
    }

    public GameManager getManager() { return this.manager; }
    public void registerInputListener(IKeyListener l) { this.listener.newListener(l); }
    public MasterListener getListener() {return this.listener;}
}
