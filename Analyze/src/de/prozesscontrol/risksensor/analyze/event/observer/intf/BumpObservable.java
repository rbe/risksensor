package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface BumpObservable {

    void registerBumpObserver(BumpObserver bumpObserver);

    void unregisterBumpObserver(BumpObserver bumpObserver);

    void fireBumpEvent(TRsTourData tourData);

}
