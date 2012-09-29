package de.prozesscontrol.risksensor.analyze.tourstate;

public class TourStateRuntimeException extends RuntimeException {

    public TourStateRuntimeException(Throwable cause) {
        super(cause);
    }

    public TourStateRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TourStateRuntimeException(String message) {
        super(message);
    }

}
