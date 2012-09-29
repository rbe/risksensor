package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface PotHoleObservable {

    void registerPotHoleObserver(PotHoleObserver potHoleObserver);

    void unregisterPotHoleObserver(PotHoleObserver potHoleObserver);

    void firePotHoleEvent(TRsTourData tourData);

}
