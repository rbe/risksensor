package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface PowerOnObservable {

    void registerPowerOnObserver(PowerOnObserver powerOnObserver);

    void unregisterPowerOnObserver(PowerOnObserver powerOnObserver);

    void firePowerOnEvent(TRsTourData tourData);

}
