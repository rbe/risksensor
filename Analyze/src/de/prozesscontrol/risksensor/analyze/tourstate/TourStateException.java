package de.prozesscontrol.risksensor.analyze.tourstate;

public class TourStateException extends Exception {

    public TourStateException(Throwable cause) {
        super(cause);
    }

    public TourStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TourStateException(String message) {
        super(message);
    }

}
