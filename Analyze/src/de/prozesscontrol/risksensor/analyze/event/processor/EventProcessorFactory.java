package de.prozesscontrol.risksensor.analyze.event.processor;

import de.prozesscontrol.risksensor.analyze.event.observer.impl.BrakeObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.observer.impl.BumpObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.observer.impl.EmptySecondObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.observer.impl.LeftCurveObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.observer.impl.PotHoleObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.observer.impl.PowerOffObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.observer.impl.PowerOnObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.observer.impl.RightCurveObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.observer.impl.SpeedUpObserverImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.EmptySecondProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.PowerEventProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.XEventProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.YEventProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.impl.ZEventProcessorImpl;
import de.prozesscontrol.risksensor.analyze.event.processor.intf.EventProcessor;
import de.prozesscontrol.risksensor.analyze.tourstate.TourState;

import java.util.HashMap;
import java.util.Map;

public final class EventProcessorFactory {

    private static final Map<TourState, EventProcessor[]> cache;

    static {
        cache = new HashMap<TourState, EventProcessor[]>();
    }

    private EventProcessorFactory() {
    }

    public synchronized static EventProcessor[] getCacheEntry(TourState tourState) {
        EventProcessor[] ep = cache.get(tourState);
        if (null == ep) {
            ep = new EventProcessor[5];
            cache.put(tourState, ep);
        }
        return ep;
    }

    public synchronized static PowerEventProcessorImpl createPowerEventProcessor(TourState tourState) {
        int idx = 0;
        EventProcessor[] ep = getCacheEntry(tourState);
        PowerEventProcessorImpl p = null;
        if (null == ep[idx]) {
            p = new PowerEventProcessorImpl();
            p.registerPowerOnObserver(new PowerOnObserverImpl(tourState));
            p.registerPowerOffObserver(new PowerOffObserverImpl(tourState));
            ep[idx] = p;
        } else {
            p = (PowerEventProcessorImpl) ep[idx];
        }
        return p;
    }

    public synchronized static XEventProcessorImpl createXEventProcessor(TourState tourState) {
        int idx = 1;
        EventProcessor[] ep = getCacheEntry(tourState);
        XEventProcessorImpl x = null;
        if (null == ep[idx]) {
            x = new XEventProcessorImpl();
            x.registerSpeedUpObserver(new SpeedUpObserverImpl(tourState));
            x.registerBrakeObserver(new BrakeObserverImpl(tourState));
            ep[idx] = x;
        } else {
            x = (XEventProcessorImpl) ep[idx];
        }
        return x;
    }

    public synchronized static YEventProcessorImpl createYEventProcessor(TourState tourState) {
        int idx = 2;
        EventProcessor[] ep = getCacheEntry(tourState);
        YEventProcessorImpl y = null;
        if (null == ep[idx]) {
            y = new YEventProcessorImpl();
            y.registerLeftCurveObserver(new LeftCurveObserverImpl(tourState));
            y.registerRightCurveObserver(new RightCurveObserverImpl(tourState));
            ep[idx] = y;
        } else {
            y = (YEventProcessorImpl) ep[idx];
        }
        return y;
    }

    public synchronized static ZEventProcessorImpl createZEventProcessor(TourState tourState) {
        int idx = 3;
        EventProcessor[] ep = getCacheEntry(tourState);
        ZEventProcessorImpl z = null;
        if (null == ep[idx]) {
            z = new ZEventProcessorImpl();
            z.registerBumpObserver(new BumpObserverImpl(tourState));
            z.registerPotHoleObserver(new PotHoleObserverImpl(tourState));
            ep[idx] = z;
        } else {
            z = (ZEventProcessorImpl) ep[idx];
        }
        return z;
    }

    public synchronized static EmptySecondProcessorImpl createEmptySecondProcessor(TourState tourState) {
        int idx = 4;
        EventProcessor[] ep = getCacheEntry(tourState);
        EmptySecondProcessorImpl e = null;
        if (null == ep[idx]) {
            e = new EmptySecondProcessorImpl();
            e.registerEmptySecondObserver(new EmptySecondObserverImpl(tourState));
            ep[idx] = e;
        } else {
            e = (EmptySecondProcessorImpl) ep[idx];
        }
        return e;
    }

}
