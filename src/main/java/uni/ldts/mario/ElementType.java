package uni.ldts.mario;

import uni.ldts.model.IElementType;

public enum ElementType implements IElementType {

    MARIO(0),
    GOOMBA(1),
    GOOMBA_FLY(2),
    KOOPA(3),
    MYSTERY(4),

    COIN(-1),
    CLOUD(-2),
    MYSTERY_BLOCK(-3),
    BREAKABLE_BLOCK(-4),
    UNBREAKABLE_BLOCK(-5),
    GOAL_POST(-6),
    WAVES(-7);

    private final int id;
    ElementType(int id) { this.id = id; }
    @Override public int id() { return this.id; }
}