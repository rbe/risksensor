package de.prozesscontrol.risksensor.analyze.event.observer.impl;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.observer.AbstractObserver;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PowerOffObserver;
import de.prozesscontrol.risksensor.analyze.tourstate.TourState;

import java.util.logging.Logger;

public class PowerOffObserverImpl extends AbstractObserver implements PowerOffObserver {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(PowerOffObserverImpl.class.getName());
    }

    public PowerOffObserverImpl(TourState tourState) {
        super(tourState);
    }

    public void processPowerOffEvent(TRsTourData tourData) {
        if (tourState.isOnRoad()) {
            logger.info(tourData.getDeviceid() + "/" + tourData.getCreatedon().getTime() + "/" +
                        tourData.getSdcardid() + "/" + tourData.getRecordnum() + "/" + tourData.getEventid());
        } else {
            logger.warning(tourData.getDeviceid() + "/" + tourData.getCreatedon().getTime() + "/" +
                           tourData.getSdcardid() + "/" + tourData.getRecordnum() + "/" + tourData.getEventid() +
                           ": power-off makes no sense; not on-road");
        }
        // We're not on-road anymore
        tourState.setOnRoad(false);
    }

}
