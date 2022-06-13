package sr.ice.server;

import SmartHome.Sensor;
import com.zeroc.Ice.Current;

public class SensorI implements Sensor {
    private static final long serialVersionUID = -2448962912780867771L;

    private int batteryLevel = 70;

    @Override
    public int getBatteryLevel(Current current) {
        return this.batteryLevel;
    }
}
