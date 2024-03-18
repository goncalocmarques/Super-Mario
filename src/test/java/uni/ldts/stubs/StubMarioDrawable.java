package uni.ldts.stubs;

import uni.ldts.mario.elements.view.MarioDrawable;

import java.io.IOException;

public class StubMarioDrawable extends MarioDrawable {

    public StubMarioDrawable() throws IOException {
        super(null);
    }

    @Override
    public int getSelectedFrame() { return -1; }
}
