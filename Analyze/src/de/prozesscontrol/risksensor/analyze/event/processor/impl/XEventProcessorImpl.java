package de.prozesscontrol.risksensor.analyze.event.processor.impl;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.BrakeObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.BrakeObserver;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.SpeedUpObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.SpeedUpObserver;
import de.prozesscontrol.risksensor.analyze.event.processor.intf.EventProcessor;

import java.util.LinkedList;
import java.util.List;

public class XEventProcessorImpl implements EventProcessor, SpeedUpObservable, BrakeObservable {

    private List<SpeedUpObserver> speedUpObserver;

    private List<BrakeObserver> brakeObserver;

    public XEventProcessorImpl() {
        brakeObserver = new LinkedList<BrakeObserver>();
        speedUpObserver = new LinkedList<SpeedUpObserver>();
    }

    public void registerSpeedUpObserver(SpeedUpObserver speedUpObserver) {
        this.speedUpObserver.add(speedUpObserver);
    }

    public void unregisterSpeedUpObserver(SpeedUpObserver speedUpObserver) {
        this.speedUpObserver.remove(speedUpObserver);
    }

    public void fireSpeedUpEvent(TRsTourData tourData) {
        for (SpeedUpObserver s : speedUpObserver) {
            s.processSpeedUpEvent(tourData);
        }
    }

    public void registerBrakeObserver(BrakeObserver brakeObserver) {
        this.brakeObserver.add(brakeObserver);
    }

    public void unregisterBrakeObserver(BrakeObserver brakeObserver) {
        this.brakeObserver.remove(brakeObserver);
    }

    public void fireBrakeEvent(TRsTourData tourData) {
        for (BrakeObserver b : brakeObserver) {
            b.processBrakeEvent(tourData);
        }
    }

}
