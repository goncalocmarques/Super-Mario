package uni.ldts.mario;

import uni.ldts.model.IGameState;

public enum GameState implements IGameState {

    START_SCREEN(0),
    LEVEL(1),
    PAUSED(2),
    GAME_OVER(3);

    private final int val;
    GameState(int val) { this.val = val; }
    @Override public int toInt() { return val; }
}