package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface BrakeObserver {

    void processBrakeEvent(TRsTourData tourData);

}
