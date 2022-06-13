package sr.ice.server;

import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;

public class SmartHomeServantLocator implements ServantLocator {

    @Override
    public LocateResult locate(Current current) throws UserException {
        String name = current.id.name;
        System.out.println("locate: " + name + ";");

        ObjectAdapter adapter = current.adapter;

        switch (name) {
            case "lightBulb1":
                LightBulbI lightBulbIServant = new LightBulbI();
                adapter.add(lightBulbIServant, new Identity(name, "lightBulb"));
                return new ServantLocator.LocateResult(lightBulbIServant, null);

            case "tempSensor1":
                TemperatureSensorI temperatureSensorServant = new TemperatureSensorI();
                adapter.add(temperatureSensorServant, new Identity(name, "sensor"));
                return new ServantLocator.LocateResult(temperatureSensorServant, null);


            case "humSensor1":

            case "humSensor2":
                HumidifierSensorI humidifierSensorIServant = new HumidifierSensorI();
                adapter.add(humidifierSensorIServant, new Identity(name, "sensor"));
                return new ServantLocator.LocateResult(humidifierSensorIServant, null);


            case "humidifier1":
                HumidifierI humidifierIServant = new HumidifierI();
                adapter.add(humidifierIServant, new Identity(name, "humidifier"));
                return new ServantLocator.LocateResult(humidifierIServant, null);

            default:
                throw new Error("Invalid name provided.");
        }
    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {

    }

    @Override
    public void deactivate(String s) {

    }

    public void printServantsList() {
        System.out.println("Available devices: lightBulb1, tempSensor1, humSensor1, humSensor2, humidifier1");
    }
}
