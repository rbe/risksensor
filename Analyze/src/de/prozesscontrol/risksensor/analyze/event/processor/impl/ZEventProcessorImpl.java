package de.prozesscontrol.risksensor.analyze.event.processor.impl;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.BumpObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.BumpObserver;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PotHoleObservable;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PotHoleObserver;
import de.prozesscontrol.risksensor.analyze.event.processor.intf.EventProcessor;

import java.util.LinkedList;
import java.util.List;

public class ZEventProcessorImpl implements EventProcessor, BumpObservable, PotHoleObservable {

    private List<BumpObserver> bumpObserver;

    private List<PotHoleObserver> potHoleObserver;

    public ZEventProcessorImpl() {
        bumpObserver = new LinkedList<BumpObserver>();
        potHoleObserver = new LinkedList<PotHoleObserver>();
    }

    public void registerBumpObserver(BumpObserver bumpObserver) {
        this.bumpObserver.add(bumpObserver);
    }

    public void unregisterBumpObserver(BumpObserver bumpObserver) {
        this.bumpObserver.remove(bumpObserver);
    }

    public void fireBumpEvent(TRsTourData tourData) {
        for (BumpObserver b : bumpObserver) {
            b.processBumpEvent(tourData);
        }
    }

    public void registerPotHoleObserver(PotHoleObserver potHoleObserver) {
        this.potHoleObserver.add(potHoleObserver);
    }

    public void unregisterPotHoleObserver(PotHoleObserver potHoleObserver) {
        this.potHoleObserver.remove(potHoleObserver);
    }

    public void firePotHoleEvent(TRsTourData tourData) {
        for (PotHoleObserver p : potHoleObserver) {
            p.processPotHoleEvent(tourData);
        }
    }

}
