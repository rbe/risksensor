package de.prozesscontrol.risksensor.analyze.event.observer;

import de.prozesscontrol.risksensor.analyze.tourstate.TourState;

public abstract class AbstractObserver {

    protected TourState tourState;

    protected AbstractObserver(TourState tourState) {
        this.tourState = tourState;
    }

}
