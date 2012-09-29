package de.prozesscontrol.risksensor.analyze.event.processor.impl;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.EmptySecondObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.EmptySecondObserver;
import de.prozesscontrol.risksensor.analyze.event.processor.intf.EventProcessor;

import java.util.LinkedList;
import java.util.List;

public class EmptySecondProcessorImpl implements EventProcessor, EmptySecondObservable {

    private List<EmptySecondObserver> emptySecondObserver;

    public EmptySecondProcessorImpl() {
        emptySecondObserver = new LinkedList<EmptySecondObserver>();
    }

    public void registerEmptySecondObserver(EmptySecondObserver emptySecondObserver) {
        this.emptySecondObserver.add(emptySecondObserver);
    }

    public void unregisterEmptySecondObserver(EmptySecondObserver emptySecondObserver) {
        this.emptySecondObserver.remove(emptySecondObserver);
    }

    public void fireEmptySecondEvent(TRsTourData tourData) {
        for (EmptySecondObserver e : emptySecondObserver) {
            e.processEmptySecondEvent(tourData);
        }
    }

}
