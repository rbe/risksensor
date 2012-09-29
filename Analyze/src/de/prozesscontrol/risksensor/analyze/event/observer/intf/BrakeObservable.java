package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface BrakeObservable {

    void registerBrakeObserver(BrakeObserver brakeObserver);

    void unregisterBrakeObserver(BrakeObserver brakeObserver);

    void fireBrakeEvent(TRsTourData tourData);

}
