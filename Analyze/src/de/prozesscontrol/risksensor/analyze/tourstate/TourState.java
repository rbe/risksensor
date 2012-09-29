package de.prozesscontrol.risksensor.analyze.tourstate;

import de.prozesscontrol.risksensor.analyze.entity.PointsAccount;
import de.prozesscontrol.risksensor.analyze.entity.SdcardInfo;
import de.prozesscontrol.risksensor.analyze.entity.TRsTourLog;
import de.prozesscontrol.risksensor.analyze.entity.TRsTourState;
import de.prozesscontrol.risksensor.analyze.event.SensorEvent;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class TourState {

    private static final Logger logger;

    private TRsTourState tRsTourState;

    private SdcardInfo sdcardInfo;

    /**
     * Do we accept state changes? Used to prevent processing of historical data.
     */
    private boolean stateChangeAccepted;

    /**
     * Does the actual event occur in the same second as the last one?
     */
    private boolean eventSameSecond;

    static {
        logger = Logger.getLogger(TourState.class.getName());
    }

    public TourState(SdcardInfo sdcardInfo) {
        this.sdcardInfo = sdcardInfo;
        //
        tRsTourState = new TRsTourState();
        tRsTourState.setSdcardInfo(sdcardInfo);
        //
        tRsTourState.setPointsAccount(new PointsAccount());
        // State change enabled
        stateChangeAccepted = true;
    }

    public TourState(TRsTourState tRsTourState) {
        if (null == tRsTourState) {
            throw new IllegalArgumentException("Null argument");
        }
        this.tRsTourState = tRsTourState;
        sdcardInfo = tRsTourState.getSdcardInfo();
        // State change enabled
        stateChangeAccepted = true;
    }

    public SdcardInfo getSdcardInfo() {
        return sdcardInfo;
    }

    public void setOnRoad(boolean onRoad) {
        if (stateChangeAccepted) {
            tRsTourState.setOnRoad(onRoad);
        } else {
            logger.warning(sdcardInfo + ": No state change possible");
        }
    }

    public boolean isOnRoad() {
        return tRsTourState.isOnRoad();
    }

    public Calendar getLastEventOccurredOn() {
        return tRsTourState.getLastEventOccurredOn();
    }

    public boolean wouldStateChangeBeAccepted(Calendar cal) {
        Calendar actualSaved = tRsTourState.getActualEventOccurredOn();
        if (null == actualSaved || cal.compareTo(actualSaved) == 0 || cal.after(actualSaved)) {
            logger.finest(sdcardInfo + ": Would accept state change (date is earlier than actual saved one)");
            return true;
        } else {
            logger.finest(sdcardInfo +
                        ": Would not accept state change (date is equal to or later than actual saved one)");
            return false;
        }
    }

    public boolean isStateChangeAccepted() {
        return stateChangeAccepted;
    }

    public boolean isEventSameSecond() {
        return eventSameSecond;
    }

    public void setActualEventOccurredOn(Calendar actualEventOccurredOn) {
        // Check argument
        if (null == actualEventOccurredOn) {
            throw new IllegalArgumentException("Null argument!");
        }
        // Clone argument!
        Calendar aeoo = (Calendar) actualEventOccurredOn.clone();
        Calendar actual = tRsTourState.getActualEventOccurredOn();
        // Check state change
        if (stateChangeAccepted) {
            if (null != actual && aeoo.before(actual)) {
                logger.fine(sdcardInfo + ": Disabling state change");
                stateChangeAccepted = false;
            }
        } else if (!stateChangeAccepted) {
            if (null != actual && (aeoo.compareTo(actual) == 0 || aeoo.after(actual))) {
                logger.fine(sdcardInfo + ": Enabling state change");
                stateChangeAccepted = true;
            }
        }
        //
        if (stateChangeAccepted) {
            // Save actual event occurred time as last event occurred time
            tRsTourState.setLastEventOccurredOn(tRsTourState.getActualEventOccurredOn());
            tRsTourState.setActualEventOccurredOn(aeoo);
            // Event in same second as last one?
            Calendar cal = tRsTourState.getLastEventOccurredOn();
            if (null != cal && aeoo.compareTo(cal) == 0) {
                logger.fine("Event is in same second: actual=" + actual.getTime() + " == " +
                            tRsTourState.getLastEventOccurredOn().getTime());
                eventSameSecond = true;
            } else {
                // Add 1 second to 'driven seconds'
                tRsTourState.setDrivenSeconds(tRsTourState.getDrivenSeconds() + 1);
                eventSameSecond = false;
            }
        } else {
            logger.warning(sdcardInfo + ": No state change possible");
        }
    }

    public Calendar getActualEventOccurredOn() {
        return tRsTourState.getActualEventOccurredOn();
    }

    public long calculateDifference(TimeUnit timeUnit) {
        // Check if there was a event before
        if (null != tRsTourState.getActualEventOccurredOn() && null != tRsTourState.getLastEventOccurredOn()) {
            return timeUnit.convert(tRsTourState.getActualEventOccurredOn().getTimeInMillis() -
                                    tRsTourState.getLastEventOccurredOn().getTimeInMillis(), TimeUnit.MILLISECONDS);
        } else {
            // No event before, relative time is zero
            return -1L;
        }
    }

    public long getSecondsSinceLastEvent() {
        return calculateDifference(TimeUnit.SECONDS);
    }

    /**
     * Return missing seconds (between actual and last event).
     * @return
     */
    public long getMissingSeconds() {
        return getSecondsSinceLastEvent() - 1;
    }

    /**
     * Get missing seconds between actualDateEventOccuredOn an the argument.
     * @param date
     */
    public long getMissingSeconds(Calendar date) {
        return TimeUnit.SECONDS.convert(date.getTimeInMillis() -
                                        tRsTourState.getActualEventOccurredOn().getTimeInMillis(),
                                        TimeUnit.MILLISECONDS) - 1;
    }

    public long getLastRecordNumber() {
        return tRsTourState.getLastRecordNumber();
    }

    public void setActualRecordNumber(Long actualRecordNumber) {
        if (stateChangeAccepted) {
            // Save actual record number as last record number
            tRsTourState.setLastRecordNumber(tRsTourState.getActualRecordNumber());
            tRsTourState.setActualRecordNumber(actualRecordNumber);
        } else {
            logger.warning(sdcardInfo + ": No state change possible");
        }
    }

    public long getActualRecordNumber() {
        return tRsTourState.getActualRecordNumber();
    }

    public SensorEvent getLastSensorEvent() {
        return tRsTourState.getLastSensorEvent();
    }

    public void setActualSensorEvent(SensorEvent actualSensorEvent) {
        if (stateChangeAccepted) {
            tRsTourState.setLastSensorEvent(tRsTourState.getActualSensorEvent());
            tRsTourState.setActualSensorEvent(actualSensorEvent);
        } else {
            logger.warning(sdcardInfo + ": No state change possible");
        }
    }

    public SensorEvent getActualSensorEvent() {
        return tRsTourState.getActualSensorEvent();
    }

    public long getTourCount() {
        return tRsTourState.getTourCount();
    }

    public void increaseTourCount() {
        tRsTourState.setTourCount(tRsTourState.getTourCount() + 1);
    }

    public TRsTourState getTRsTourState() {
        return tRsTourState;
    }

    public void setTRsTourState(TRsTourState tRsTourState) {
        this.tRsTourState = tRsTourState;
    }

    public PointsAccount getPointsAccount() {
        return tRsTourState.getPointsAccount();
    }

    public TRsTourLog getTRsTourLog() {
        TRsTourLog tRsTourLog = new TRsTourLog();
        tRsTourLog.setSdcardInfo(sdcardInfo);
        tRsTourLog.setCreatedon(getActualEventOccurredOn());
        tRsTourLog.setSensorEvent(getActualSensorEvent());
        tRsTourLog.setDrivenSeconds(tRsTourState.getDrivenSeconds());
        tRsTourLog.setPointsAccount(tRsTourState.getPointsAccount());
        tRsTourLog.setTourCount(tRsTourState.getTourCount());
        return tRsTourLog;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TourState)) {
            return false;
        }
        final TourState other = (TourState) object;
        if (!(sdcardInfo == null ? other.sdcardInfo == null : sdcardInfo.equals(other.sdcardInfo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((sdcardInfo == null) ? 0 : sdcardInfo.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[SdcardInfo=" + sdcardInfo + "]";
    }

}
