package de.prozesscontrol.risksensor.analyze.event.observer.intf;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;

public interface EmptySecondObservable {

    void registerEmptySecondObserver(EmptySecondObserver emptySecondObserver);

    void unregisterEmptySecondObserver(EmptySecondObserver emptySecondObserver);

    void fireEmptySecondEvent(TRsTourData tourData);

}
