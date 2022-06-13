package sr.ice.server;

import SmartHome.HumiditySensor;
import com.zeroc.Ice.Current;

public class HumidifierSensorI extends SensorI implements HumiditySensor {
    private static final long serialVersionUID = -2448962912780867773L;

    private float humidity = 45f;

    @Override
    public float getHumidity(Current current) {
        return this.humidity;
    }
}
