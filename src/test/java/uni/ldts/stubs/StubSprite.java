package uni.ldts.stubs;

import uni.ldts.view.Sprite;

public class StubSprite extends Sprite {

    public StubSprite() {
        super(null);
    }

    @Override
    public int getSelectedFrame() { return -1; }
}
