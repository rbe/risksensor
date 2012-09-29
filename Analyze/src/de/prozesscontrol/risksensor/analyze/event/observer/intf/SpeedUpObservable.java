package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface SpeedUpObservable {

    void registerSpeedUpObserver(SpeedUpObserver speedUpObserver);

    void unregisterSpeedUpObserver(SpeedUpObserver speedUpObserver);

    void fireSpeedUpEvent(TRsTourData tourData);

}
