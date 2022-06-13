package sr.ice.server;

import SmartHome.TemperatureSensor;
import com.zeroc.Ice.Current;

public class TemperatureSensorI extends SensorI implements TemperatureSensor {
    private static final long serialVersionUID = -2448962912780867772L;

    private float currentTemperature = 29.5f;

    @Override
    public float getTemperature(Current current) {
        return this.currentTemperature;
    }
}
