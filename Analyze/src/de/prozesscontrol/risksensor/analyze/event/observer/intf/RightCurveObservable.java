package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface RightCurveObservable {

    void registerRightCurveObserver(RightCurveObserver rightCurveObserver);

    void unregisterRightCurveObserver(RightCurveObserver rightCurveObserver);

    void fireRightCurveEvent(TRsTourData tourData);

}

