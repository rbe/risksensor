package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import java.util.Calendar;

public class TRsTourDataPK implements Serializable {

    public Calendar createdon;

    public Long eventid;

    public Long recordnum;

    public Long tourid;

    public TRsTourDataPK() {
    }

    public TRsTourDataPK(Calendar createdon, Long eventid, Long recordnum, Long tourid) {
        this.createdon = createdon;
        this.eventid = eventid;
        this.recordnum = recordnum;
        this.tourid = tourid;
    }

    public boolean equals(Object other) {
        if (other instanceof TRsTourDataPK) {
            final TRsTourDataPK otherTRsTourDataPK = (TRsTourDataPK) other;
            final boolean areEqual =
                (otherTRsTourDataPK.createdon.equals(createdon) && otherTRsTourDataPK.eventid.equals(eventid) &&
                 otherTRsTourDataPK.recordnum.equals(recordnum) && otherTRsTourDataPK.tourid.equals(tourid));
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

}
