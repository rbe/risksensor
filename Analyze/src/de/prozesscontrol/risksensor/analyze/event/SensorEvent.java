package de.prozesscontrol.risksensor.analyze.event;

public enum SensorEvent {

    NOTHING(0),

    PWRON(1),

    PWROFF(2),

    SPEEDUP(7),

    BRAKE(11),

    LEFTCURVE(8),

    RIGHTCURVE(12),

    BUMP(9),

    POTHOLE(13);

    private int eventId;

    private SensorEvent(int eventId) {
        this.eventId = eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public static SensorEvent getById(int eventId) {
        SensorEvent e = null;
        switch (eventId) {
            case 0: e = NOTHING;
                break;
            case 1: e = PWRON;
                break;
            case 2: e = PWROFF;
                break;
            case 7: e = SPEEDUP;
                break;
            case 11: e = BRAKE;
                break;
            case 8: e = LEFTCURVE;
                break;
            case 12: e = RIGHTCURVE;
                break;
            case 9: e = BUMP;
                break;
            case 13: e = POTHOLE;
                break;
            default: e = null;
                break;
        }
        return e;
    }

}
