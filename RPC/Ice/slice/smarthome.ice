#ifndef CALC_ICE
#define CALC_ICE

module SmartHome
{
    interface Sensor {
        idempotent int getBatteryLevel();
    }

    interface HumiditySensor extends Sensor {
        idempotent float getHumidity();
    }

    interface TemperatureSensor extends Sensor {
        idempotent float getTemperature();
    }

    enum Intensity {
        Off = 0,
        Low = 1,
        Medium = 2,
        High = 3
    }

    exception IntensityRangeError {
        string reason = "Intensity value out of range.";
    }

    exception WorkTimeRangeError {
        string reason = "Work time value out of range.";
    }

    struct HumidifierSettings {
        Intensity intensity;
        float workTime;
    }

    interface Humidifier {
        idempotent Intensity getIntensity();
        idempotent float getWorkTime();
        idempotent HumidifierSettings getSettings();
        idempotent void setSettings(HumidifierSettings humidifierSettings) throws IntensityRangeError, WorkTimeRangeError;
    }

    interface LightBulb {
        void toggleState();
        bool getState();
    }
};

#endif
