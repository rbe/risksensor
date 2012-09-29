package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import java.sql.Timestamp;

public class TRsThresholdPK implements Serializable {

    public String cmdprefix;

    public String deviceid;

    public Timestamp validfrom;

    public TRsThresholdPK() {
    }

    public TRsThresholdPK(String cmdprefix, String deviceid, Timestamp validfrom) {
        this.cmdprefix = cmdprefix;
        this.deviceid = deviceid;
        this.validfrom = validfrom;
    }

    public boolean equals(Object other) {
        if (other instanceof TRsThresholdPK) {
            final TRsThresholdPK otherTRsThresholdPK = (TRsThresholdPK) other;
            final boolean areEqual =
                (otherTRsThresholdPK.cmdprefix.equals(cmdprefix) && otherTRsThresholdPK.deviceid.equals(deviceid) &&
                 otherTRsThresholdPK.validfrom.equals(validfrom));
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

}
