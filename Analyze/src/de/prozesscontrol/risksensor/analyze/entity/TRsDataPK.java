package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import java.sql.Timestamp;

public class TRsDataPK implements Serializable {

    public Timestamp createdon;

    public String deviceid;

    public Long eventid;

    public Long recordnum;

    public String sdcardid;

    public TRsDataPK() {
    }

    public TRsDataPK(Timestamp createdon, String deviceid, Long eventid, Long recordnum, String sdcardid) {
        this.createdon = createdon;
        this.deviceid = deviceid;
        this.eventid = eventid;
        this.recordnum = recordnum;
        this.sdcardid = sdcardid;
    }

    public boolean equals(Object other) {
        if (other instanceof TRsDataPK) {
            final TRsDataPK otherTRsDataPK = (TRsDataPK) other;
            final boolean areEqual =
                (otherTRsDataPK.createdon.equals(createdon) && otherTRsDataPK.deviceid.equals(deviceid) &&
                 otherTRsDataPK.eventid.equals(eventid) && otherTRsDataPK.recordnum.equals(recordnum) &&
                 otherTRsDataPK.sdcardid.equals(sdcardid));
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

}
