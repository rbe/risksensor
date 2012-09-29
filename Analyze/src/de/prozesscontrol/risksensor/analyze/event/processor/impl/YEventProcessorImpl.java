package de.prozesscontrol.risksensor.analyze.event.processor.impl;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.LeftCurveObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.LeftCurveObserver;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.RightCurveObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.RightCurveObserver;
import de.prozesscontrol.risksensor.analyze.event.processor.intf.EventProcessor;

import java.util.LinkedList;
import java.util.List;

public class YEventProcessorImpl implements EventProcessor, LeftCurveObservable, RightCurveObservable {

    private List<LeftCurveObserver> leftCurveObserver;

    private List<RightCurveObserver> rightCurveObserver;

    public YEventProcessorImpl() {
        leftCurveObserver = new LinkedList<LeftCurveObserver>();
        rightCurveObserver = new LinkedList<RightCurveObserver>();
    }

    public void registerLeftCurveObserver(LeftCurveObserver leftCurveObserver) {
        this.leftCurveObserver.add(leftCurveObserver);
    }

    public void unregisterLeftCurveObserver(LeftCurveObserver leftCurveObserver) {
        this.leftCurveObserver.remove(leftCurveObserver);
    }

    public void fireLeftCurveEvent(TRsTourData tourData) {
        for (LeftCurveObserver l : leftCurveObserver) {
            l.processLeftCurveEvent(tourData);
        }
    }

    public void registerRightCurveObserver(RightCurveObserver rightCurveObserver) {
        this.rightCurveObserver.add(rightCurveObserver);
    }

    public void unregisterRightCurveObserver(RightCurveObserver rightCurveObserver) {
        this.rightCurveObserver.remove(rightCurveObserver);
    }

    public void fireRightCurveEvent(TRsTourData tourData) {
        for (RightCurveObserver r : rightCurveObserver) {
            r.processRightCurveEvent(tourData);
        }
    }

}
