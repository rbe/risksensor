package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class SdcardInfo implements Serializable {

    private String deviceId;

    private String sdcardId;

    public SdcardInfo() {
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setSdcardId(String sdcardId) {
        this.sdcardId = sdcardId;
    }

    public String getSdcardId() {
        return sdcardId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SdcardInfo)) {
            return false;
        }
        final SdcardInfo other = (SdcardInfo) object;
        if (!(deviceId == null ? other.deviceId == null : deviceId.equals(other.deviceId))) {
            return false;
        }
        if (!(sdcardId == null ? other.sdcardId == null : sdcardId.equals(other.sdcardId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((deviceId == null) ? 0 : deviceId.hashCode());
        result = PRIME * result + ((sdcardId == null) ? 0 : sdcardId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + deviceId + "/" + sdcardId + "]";
    }

}
