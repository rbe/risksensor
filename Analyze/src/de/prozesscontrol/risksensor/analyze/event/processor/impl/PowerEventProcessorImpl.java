package de.prozesscontrol.risksensor.analyze.event.processor.impl;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PowerOffObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PowerOffObserver;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PowerOnObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PowerOnObserver;
import de.prozesscontrol.risksensor.analyze.event.processor.intf.EventProcessor;

import java.util.LinkedList;
import java.util.List;

public class PowerEventProcessorImpl implements EventProcessor, PowerOnObservable, PowerOffObservable {

    private List<PowerOnObserver> powerOnObserver;

    private List<PowerOffObserver> powerOffObserver;

    public PowerEventProcessorImpl() {
        powerOnObserver = new LinkedList<PowerOnObserver>();
        powerOffObserver = new LinkedList<PowerOffObserver>();
    }

    public void registerPowerOnObserver(PowerOnObserver powerOnObserver) {
        this.powerOnObserver.add(powerOnObserver);
    }

    public void unregisterPowerOnObserver(PowerOnObserver powerOnObserver) {
        this.powerOnObserver.remove(powerOnObserver);
    }

    public void firePowerOnEvent(TRsTourData tourData) {
        for (PowerOnObserver p : powerOnObserver) {
            p.processPowerOnEvent(tourData);
        }
    }

    public void registerPowerOffObserver(PowerOffObserver powerOffObserver) {
        this.powerOffObserver.add(powerOffObserver);
    }

    public void unregisterPowerOffObserver(PowerOffObserver powerOffObserver) {
        this.powerOffObserver.remove(powerOffObserver);
    }

    public void firePowerOffEvent(TRsTourData tourData) {
        for (PowerOffObserver p : powerOffObserver) {
            p.processPowerOffEvent(tourData);
        }
    }

}
