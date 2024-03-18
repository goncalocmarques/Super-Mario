package uni.ldts.mario.events;

import uni.ldts.events.Event;
import uni.ldts.mario.elements.Mario;

public class MarioDeathEvent implements Event {

    private final Mario mario;
    private final Reason reason;

    // reason for the death
    public enum Reason {
        VOID, WATER, ENEMY;
    }

    public MarioDeathEvent(Mario mario, Reason reason) {
        this.mario = mario;
        this.reason = reason;
    }

    public Mario getMario() { return this.mario; }
    public Reason getReason() { return this.reason; }
}
