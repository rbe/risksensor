package de.prozesscontrol.risksensor.analyze.tourstate;

import de.prozesscontrol.risksensor.analyze.entity.EntityServiceFacade;
import de.prozesscontrol.risksensor.analyze.entity.SdcardInfo;
import de.prozesscontrol.risksensor.analyze.entity.TRsTourState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class TourStateFactory {

    private static final Logger logger;

    private static final Map<SdcardInfo, TourState> cache;

    static {
        logger = Logger.getLogger(TourStateFactory.class.getName());
        cache = new HashMap<SdcardInfo, TourState>();
    }

    public synchronized static TourState getInstance(SdcardInfo sdcardInfo) {
        TourState tourState = cache.get(sdcardInfo);
        if (null == tourState) {
            try {
                // Get tour state
                List<TRsTourState> tRsTourStateList =
                    new EntityServiceFacade().queryTRsTourStateFindByDeviceIdSdcardId(sdcardInfo.getDeviceId(),
                                                                                      sdcardInfo.getSdcardId());
                if (tRsTourStateList.size() == 1) {
                    // Create TourState instance
                    tourState = new TourState(tRsTourStateList.get(0));
                    // Cache this TourState
                    cache.put(sdcardInfo, tourState);
                    //
                    logger.finest("Cached " + cache.size() + " TourState instances");
                } else if (tRsTourStateList.size() == 0) {
                    // Create empty tourState
                    tourState = new TourState(sdcardInfo);
                    // Cache this TourState
                    cache.put(sdcardInfo, tourState);
                    //
                    logger.finest("Cached " + cache.size() + " TourState instances");
                }
            } catch (Exception e) {
                throw new TourStateRuntimeException(e);
            }
        }
        //
        return tourState;
    }

    public synchronized static void persist() {
        // Get entity service facade
        EntityServiceFacade esf = new EntityServiceFacade();
        // Persist every cached TourState
        for (SdcardInfo sdcardInfo : cache.keySet()) {
            try {
                logger.info("Persisting TourState for " + sdcardInfo);
                cache.get(sdcardInfo).setTRsTourState((TRsTourState) esf.mergeEntity(cache.get(sdcardInfo).getTRsTourState()));
            } catch (Exception e) {
                throw new TourStateRuntimeException(e);
            }
        }
    }

}
