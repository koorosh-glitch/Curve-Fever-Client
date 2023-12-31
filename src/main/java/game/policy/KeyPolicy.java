package game.policy;

import common.game.logic.AbstractPolicy;

public abstract class KeyPolicy extends AbstractPolicy {
    protected final static int UP = 38;
    protected final static int DOWN = 40;
    protected final static int RIGHT = 39;
    protected final static int LEFT = 37;
    protected final static int ESCAPE = 27;
    protected final static int X = 88;
    protected final static int SPACE = 32;
    public boolean enforce(int keyCode, boolean pressed) {
        if (pressed) {
            press();
        }
        else {
            release();
        }
        return false;
    }
    public abstract boolean isEnforceable(int keyCode);

    protected abstract void press();
    protected abstract void release();
}
