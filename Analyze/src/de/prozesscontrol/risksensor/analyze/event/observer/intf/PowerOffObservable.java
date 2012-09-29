package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface PowerOffObservable {

    void registerPowerOffObserver(PowerOffObserver powerOffObserver);

    void unregisterPowerOffObserver(PowerOffObserver powerOffObserver);

    void firePowerOffEvent(TRsTourData tourData);

}
