package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface PowerOffObserver {

    void processPowerOffEvent(TRsTourData tourData);

}
