package de.prozesscontrol.risksensor.analyze;

import de.prozesscontrol.risksensor.analyze.entity.EntityServiceFacade;
import de.prozesscontrol.risksensor.analyze.entity.SdcardInfo;
import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.SensorEvent;
import de.prozesscontrol.risksensor.analyze.event.processor.EventProcessorFactory;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.EmptySecondProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.PowerEventProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.XEventProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.YEventProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.ZEventProcessorImpl;
import de.prozesscontrol.risksensor.analyze.tourstate.TourState;
import de.prozesscontrol.risksensor.analyze.tourstate.TourStateFactory;

import java.util.List;
import java.util.logging.Logger;

public class AnalyzeTour {

    private static final Logger logger;

    private EntityServiceFacade esf;

    private PowerEventProcessorImpl powerEventProcessor;

    private XEventProcessorImpl xEventProcessor;

    private YEventProcessorImpl yEventProcessor;

    private ZEventProcessorImpl zEventProcessor;

    private EmptySecondProcessorImpl emptySecondProcessor;

    static {
        logger = Logger.getLogger(AnalyzeTour.class.getName());
    }

    private static final class Holder {

        private static final AnalyzeTour INSTANCE = new AnalyzeTour();

    }

    private AnalyzeTour() {
        esf = new EntityServiceFacade();
    }

    public static AnalyzeTour getInstance() {
        return Holder.INSTANCE;
    }

    private SdcardInfo createSdcardInfo(TRsTourData tourData) {
        // Set device id and sdcard id in SdcardInfo
        SdcardInfo sdcardInfo = new SdcardInfo();
        sdcardInfo.setDeviceId(tourData.getDeviceid());
        sdcardInfo.setSdcardId(tourData.getSdcardid());
        return sdcardInfo;
    }

    private void initEventProcessor(TourState tourState) {
        logger.finest("Initializing event processors for " + tourState);
        powerEventProcessor = EventProcessorFactory.createPowerEventProcessor(tourState);
        xEventProcessor = EventProcessorFactory.createXEventProcessor(tourState);
        yEventProcessor = EventProcessorFactory.createYEventProcessor(tourState);
        zEventProcessor = EventProcessorFactory.createZEventProcessor(tourState);
        emptySecondProcessor = EventProcessorFactory.createEmptySecondProcessor(tourState);
    }

    public void fireEvent(TourState tourState, TRsTourData tRsTourData) {
        logger.finest("Fireing event for " + tRsTourData);
        // Initialize/get event processors
        initEventProcessor(tourState);
        // Set actual event date in TourState
        tourState.setActualEventOccurredOn(tRsTourData.getCreatedon());
        // Set actual record number
        tourState.setActualRecordNumber(tRsTourData.getRecordnum());
        // Set actual sensor event
        tourState.setActualSensorEvent(SensorEvent.getById(tRsTourData.getEventid().intValue()));
        // Fire up event listeners
        switch (tRsTourData.getEventid().intValue()) {
                // EVT_PWR_ON
            case 1: powerEventProcessor.firePowerOnEvent(tRsTourData);
                break;
                // EVT_PWR_OFF
            case 2: powerEventProcessor.firePowerOffEvent(tRsTourData);
                break;
            case 7: xEventProcessor.fireSpeedUpEvent(tRsTourData);
                break;
            case 11: xEventProcessor.fireBrakeEvent(tRsTourData);
                break;
            case 8: yEventProcessor.fireLeftCurveEvent(tRsTourData);
                break;
            case 12: yEventProcessor.fireRightCurveEvent(tRsTourData);
                break;
            case 9: zEventProcessor.fireBumpEvent(tRsTourData);
                break;
            case 13: zEventProcessor.firePotHoleEvent(tRsTourData);
                break;
            default: logger.warning("Unknown/unhandled event!");
                break;
        }
        // Persist TRsTourLog if we are in a 'new' second
        if (!tourState.isEventSameSecond()) {
            esf.persistEntity(tourState.getTRsTourLog());
        }
    }

    private boolean findMissingSeconds(TourState tourState, TRsTourData tourData) {
        // Find missing seconds
        if (tourState.isOnRoad() && tourState.getMissingSeconds(tourData.getCreatedon()) > 0) {
            // Fire empty-second-event for missing seconds
            emptySecondProcessor.fireEmptySecondEvent(tourData);
            return true;
        } else {
            logger.finest("no empty-seconds: " + tourState.isOnRoad() + "/" + tourState.getMissingSeconds());
        }
        return false;
    }

    private void analyze() {
        int base = 0;
        int step = 1000;
        while (true) {
            // Get all tour data for device and/or sdcard
            List<TRsTourData> tRsTourDataList = esf.queryTRsTourDataFindAllByRange(base, step);
            if (tRsTourDataList.isEmpty()) {
                break;
            }
            logger.info("Read " + tRsTourDataList.size() + " entries");
            // TourState instance
            TourState tourState = null;
            for (TRsTourData tRsTourData : tRsTourDataList) {
                // Get TourState from factory
                tourState = TourStateFactory.getInstance(createSdcardInfo(tRsTourData));
                // Reset points in second
                tourState.getPointsAccount().resetSecond();
                // Check if seconds are missing
                findMissingSeconds(tourState, tRsTourData);
                // Fire event if state change will be accepted
                if (tourState.wouldStateChangeBeAccepted(tRsTourData.getCreatedon())) {
                    fireEvent(tourState, tRsTourData);
                }
            }
            // Persist TourStates
            TourStateFactory.persist();
            //
            if (tRsTourDataList.size() < step) {
                break;
            } else {
                base += step;
            }
        }
    }

    public static void main(String[] args) {
        AnalyzeTour.getInstance().analyze();
    }

}
