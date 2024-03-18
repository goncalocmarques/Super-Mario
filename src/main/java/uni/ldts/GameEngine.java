package uni.ldts;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import uni.ldts.events.controller.IKeyListener;
import uni.ldts.events.controller.MasterListener;
import uni.ldts.model.GameManager;
import uni.ldts.view.Renderer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GameEngine implements Runnable {

    private final GameManager manager;
    private final MasterListener listener;
    private SwingTerminalFrame terminal;

    private final int tps, fps;
    private boolean isRunning;

    /**
     * Create a game engine
     * @param title game title
     * @param manager game manager
     * @param renderer game renderer
     * @param tps tick rate
     * @param fps frame rate
     */
    public GameEngine(String title, GameManager manager, Renderer renderer, int tps, int fps) {
        this.tps = tps;
        this.fps = fps;
        this.manager = manager;
        this.listener = new MasterListener(manager);
        this.thread = new Thread(this, title+" helper");
        this.init(title, renderer);
    }

    /**
     * Initialize the game engine by loading core components.
     * After initialization, call the start method to start the game
     */
    private void init(String title, Renderer renderer) {

        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(1,1));
        factory.setTerminalEmulatorTitle(title);

        this.terminal = factory.createSwingTerminal();
        this.makeWindowVisible(terminal);

        Screen screen;
        try {
            screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException e) {
            // system not supported (very unlikely)
            throw new UnsupportedOperationException();
        }
        terminal.close();

        /*
        from here on out, we'll use lanterna's SwingTerminal
        underlying architecture, Swing, for better performance
         */
        terminal.setContentPane(renderer);
        terminal.setResizable(false);
        terminal.pack();
        terminal.setLocationRelativeTo(null);
        terminal.setVisible(true);

        // master coordinates input listener events
        terminal.addKeyListener(this.listener);
    }

    @Override
    public void run() {

        double delta1 = 1000.0/tps;
        double delta2 = 1000.0/fps;

        long lastTick = System.currentTimeMillis();
        long lastFrame = lastTick;

        while (isRunning && !thread.isInterrupted() && terminal.isVisible()) {
            long now = System.currentTimeMillis();
            if (now-lastTick >= delta1) {
                this.manager.tick();
                lastTick = System.currentTimeMillis();
            }
            if (now-lastFrame >= delta2) {
                // redraw the screen
                this.terminal.repaint();
                lastFrame = System.currentTimeMillis();
            }
        }
    }

    // game loop runs async
    private final Thread thread;

    /** Start the game */
    public void start() {
        if (isRunning) return;
        this.isRunning = true;
        this.thread.start();
    }

    /**
     * Forcefully stop the game
     * beware: the engine is not reusable, and thus
     * calling start() after stop() is not supported
     */
    public void stop() {
        if (!isRunning) return;
        this.isRunning = false;
        this.thread.interrupt();
        terminal.close();
    }

    /*
    used to avoid a bug in lanterna where, if you ask for a specific type of terminal,
    like a SwingTerminal, it forgets to call this method, causing the screen not to start
    */
    private void makeWindowVisible(Terminal terminal) {
        try {
            Class<?> cls = Class.forName("java.awt.Window");
            Method method = cls.getDeclaredMethod("setVisible", boolean.class);
            method.invoke(terminal, true);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to make terminal emulator window visible.", ex);
        }
    }

    public GameManager getManager() { return this.manager; }
    public void registerInputListener(IKeyListener l) { this.listener.newListener(l); }
}
