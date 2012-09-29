package de.prozesscontrol.risksensor.analyze.event.observer.impl;

import de.prozesscontrol.risksensor.analyze.entity.TRsTourData;
import de.prozesscontrol.risksensor.analyze.event.observer.AbstractObserver;
import de.prozesscontrol.risksensor.analyze.event.observer.intf.PotHoleObserver;
import de.prozesscontrol.risksensor.analyze.tourstate.TourState;

import java.util.logging.Logger;

public class PotHoleObserverImpl extends AbstractObserver implements PotHoleObserver {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(PotHoleObserverImpl.class.getName());
    }

    public PotHoleObserverImpl(TourState tourState) {
        super(tourState);
    }

    public void processPotHoleEvent(TRsTourData tourData) {
        logger.info(tourData.getDeviceid() + "/" + tourData.getCreatedon().getTime() + "/" + tourData.getSdcardid() +
                    "/" + tourData.getRecordnum() + "/" + tourData.getEventid() + "/mg=" +
                    tourData.getEventthreshold() + "/" + tourData.getAbsolutemg() + "/" + tourData.getRelativemg() +
                    "/" + tourData.getPctthrexceeded() + "/ms=" + tourData.getDurationthreshold() + "/" +
                    tourData.getDurationms() + "/" + tourData.getRelativedurationms() + "/" +
                    tourData.getPctdurexceeded() + "/points=" + tourData.getPoints());
        tourState.getPointsAccount().addPotholePointsSecond(tourData.getPoints());
    }

}
