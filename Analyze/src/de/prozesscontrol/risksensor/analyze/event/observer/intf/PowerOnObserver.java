package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface PowerOnObserver {

    void processPowerOnEvent(TRsTourData tourData);

}
