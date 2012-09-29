package de.prozesscontrol.risksensor.analyze.event.observer.impl;

import de.prozesscontrol.risksensor.analyze.AnalyzeTour;
import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.SensorEvent;
import de.prozesscontrol.risksensor.analyze.event.observer.AbstractObserver;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PowerOnObserver;
import de.prozesscontrol.risksensor.analyze.tourstate.TourState;

import java.util.Calendar;
import java.util.logging.Logger;

public class PowerOnObserverImpl extends AbstractObserver implements PowerOnObserver {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(PowerOnObserverImpl.class.getName());
    }

    public PowerOnObserverImpl(TourState tourState) {
        super(tourState);
    }

    public void processPowerOnEvent(TRsTourData tourData) {
        if (!tourState.isOnRoad()) {
            logger.info(tourData.getDeviceid() + "/" + tourData.getCreatedon().getTime() + "/" +
                        tourData.getSdcardid() + "/" + tourData.getRecordnum() + "/" + tourData.getEventid());
        } else {
            logger.warning(tourData.getDeviceid() + "/" + tourData.getCreatedon().getTime() + "/" +
                           tourData.getSdcardid() + "/" + tourData.getRecordnum() + "/" + tourData.getEventid() +
                           ": power-on makes no sense; already on-road -- simulating power-off event");
            // Simulate power-off event: last event + 1 second = power-off
            Calendar lastEventOccurredOn = tourState.getTRsTourState().getLastEventOccurredOn();
            lastEventOccurredOn.add(Calendar.SECOND, 1);
            // Create TRsTourData
            TRsTourData tRsTourData = new TRsTourData();
            tRsTourData.setCreatedon(lastEventOccurredOn);
            tRsTourData.setEventid(Long.valueOf(SensorEvent.PWROFF.getEventId()));
            tRsTourData.setRecordnum(0L);
            // Fire event
            AnalyzeTour.getInstance().fireEvent(tourState, tRsTourData);
        }
        // We are on-road
        tourState.setOnRoad(true);
        // Increase tour count
        tourState.increaseTourCount();
    }

}
