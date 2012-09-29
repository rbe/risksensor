package de.prozesscontrol.risksensor.analyze.event.observer.impl;

import de.prozesscontrol.risksensor.analyze.entity.EntityServiceFacade;
import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.SensorEvent;
import de.prozesscontrol.risksensor.analyze.event.observer.AbstractObserver;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.EmptySecondObserver;
import de.prozesscontrol.risksensor.analyze.tourstate.TourState;

import java.util.Calendar;
import java.util.logging.Logger;

public class EmptySecondObserverImpl extends AbstractObserver implements EmptySecondObserver {

    private static final Logger logger;

    private static final EntityServiceFacade esf;

    static {
        logger = Logger.getLogger(EmptySecondObserverImpl.class.getName());
        esf = new EntityServiceFacade();
    }

    public EmptySecondObserverImpl(TourState tourState) {
        super(tourState);
    }

    public void processEmptySecondEvent(TRsTourData tourData) {
        // Check state: we must be on-road
        if (!tourState.isOnRoad()) {
            // TODO Save 'standing' time?
            logger.warning("Won't calculate and create missing second entries when not on-road!");
            return;
        }
        //
        long missingSeconds = tourState.getMissingSeconds(tourData.getCreatedon());
        // Last event + 1 second = first missing second
        Calendar last = Calendar.getInstance();
        last.setTimeInMillis(tourState.getActualEventOccurredOn().getTimeInMillis());
        last.add(Calendar.SECOND, 1);
        // Last missing second
        Calendar tmp = Calendar.getInstance();
        tmp.setTimeInMillis(tourState.getActualEventOccurredOn().getTimeInMillis());
        tmp.add(Calendar.SECOND, (int) missingSeconds);
        //
        logger.info(tourData.getDeviceid() + "/" + tourData.getCreatedon().getTime() + "/" + tourData.getSdcardid() +
                    ": actual event is " + SensorEvent.getById(tourData.getEventid().intValue()) + " on " +
                    tourData.getCreatedon().getTime() + ", last event was " + tourState.getActualSensorEvent() + " " +
                    (missingSeconds + 1) + " seconds ago; generating " + missingSeconds +
                    " empty-second-events from " + last.getTime() + " til " + tmp.getTime());
        // Generate missing seconds
        while (last.before(tmp) || last.compareTo(tmp) == 0) {
            // Set data in TourState
            tourState.setActualEventOccurredOn(last);
            tourState.setActualSensorEvent(SensorEvent.NOTHING);
            // Subtract points
            tourState.getPointsAccount().subtract(0.1d);
            // Create TRsTourLog
            if (!tourState.isEventSameSecond()) {
                esf.persistEntity(tourState.getTRsTourLog());
            }
            // Add 1 second
            last.add(Calendar.SECOND, 1);
        }
    }

}
