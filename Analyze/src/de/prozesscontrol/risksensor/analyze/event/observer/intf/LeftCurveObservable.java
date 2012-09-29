package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface LeftCurveObservable {

    void registerLeftCurveObserver(LeftCurveObserver leftCurveObserver);

    void unregisterLeftCurveObserver(LeftCurveObserver leftCurveObserver);

    void fireLeftCurveEvent(TRsTourData tourData);

}

