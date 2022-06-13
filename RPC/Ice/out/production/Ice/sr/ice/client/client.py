import sys

import Ice

Ice.loadSlice("C:/Paladin/Nauka/SR/Ice/slice/smarthome.ice")
import SmartHome

if __name__ == '__main__':
    with Ice.initialize(['--Ice.Config=config.client'] + sys.argv) as communicator:

        while True:
            line = input(f'> ')

            args = line.lower().split(' ')

            if args[0] == 'humidifier':
                base = communicator.propertyToProxy(f'Humidifier1.Proxy')
                humidifier = SmartHome.HumidifierPrx.checkedCast(base)
                if args[1] == 'intensity':
                    intensity = humidifier.getIntensity()
                    print('Humidifier intensity: ' + str(intensity))

                if args[1] == 'time':
                    time = humidifier.getWorkTime()
                    print('Humidifier work time: ' + str(time) + 's')

                if args[1] == 'settings':
                    if args[2] == 'get':
                        settings = humidifier.getSettings()
                        print('Humidifier settings: ' + str(settings))
                    if args[2] == 'set':
                        settings = humidifier.setSettings(SmartHome.HumidifierSettings(SmartHome.Intensity.High, 32.5))
                        print('Humidifier settings was successfully changed.')

            if args[0] == 'humidity':
                num = args[1]
                base = communicator.propertyToProxy(f'HumSensor{num}.Proxy')
                humiditySensor = SmartHome.HumiditySensorPrx.checkedCast(base)
                humidity = humiditySensor.getHumidity()
                print(f'Current humidity on sensor {num} is {humidity}%')

            if args[0] == 'lightbulb':
                base = communicator.propertyToProxy(f'LightBulb1.Proxy')
                lightBulb = SmartHome.LightBulbPrx.checkedCast(base)
                if args[1] == 'toggle':
                    lightBulb.toggleState()
                    print("State was successfully toggled")
                else:
                    state = lightBulb.getState()
                    print(f"State of bulb is {state}")

            if args[0] == 'temperature':
                base = communicator.propertyToProxy("TempSensor1")
                temperatureSensor = SmartHome.TemperatureSensorPrx.checkedCast(base)
                if args[1] == 'battery':
                    battery = temperatureSensor.getBatteryLevel()
                    print(f'Current battery level of temperature sensor is {battery}%')
                else:
                    temperature = temperatureSensor.getTemperature()
                    print(f'Current temperature on sensor is {temperature}')
