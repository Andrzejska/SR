package sr.ice.server;

import SmartHome.LightBulb;
import com.zeroc.Ice.Current;

public class LightBulbI implements LightBulb {

    private static final long serialVersionUID = -2448962912780867770L;

    boolean state = true;

    @Override
    public void toggleState(Current current) {
        this.state = !this.state;
    }

    @Override
    public boolean getState(Current current) {
        return this.state;
    }
}
