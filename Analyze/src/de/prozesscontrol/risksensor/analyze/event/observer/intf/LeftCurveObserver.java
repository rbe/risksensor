package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface LeftCurveObserver {

    void processLeftCurveEvent(TRsTourData tourData);

}
