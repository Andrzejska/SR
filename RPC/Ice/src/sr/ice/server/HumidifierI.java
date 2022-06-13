package sr.ice.server;

import SmartHome.*;
import com.zeroc.Ice.Current;

public class HumidifierI implements Humidifier {
    private static final long serialVersionUID = -2448962912780867773L;

    private Intensity intensity = Intensity.Off;
    private float workTime = 36.5f;

    @Override
    public Intensity getIntensity(Current current) {
        return this.intensity;
    }

    @Override
    public float getWorkTime(Current current) {
        return this.workTime;
    }

    @Override
    public HumidifierSettings getSettings(Current current) {
        return new HumidifierSettings(this.intensity, this.workTime);
    }

    @Override
    public void setSettings(HumidifierSettings humidifierSettings, Current current) throws IntensityRangeError, WorkTimeRangeError {
        if (humidifierSettings.intensity.value() < 0 || humidifierSettings.intensity.value() > 3)
            throw new IntensityRangeError();
        this.workTime = humidifierSettings.workTime;


        if (humidifierSettings.workTime < 0)
            throw new WorkTimeRangeError();
        this.intensity = humidifierSettings.intensity;

    }
}
