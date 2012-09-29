package de.prozesscontrol.risksensor.analyze.entity;

import de.prozesscontrol.risksensor.analyze.event.SensorEvent;

import java.io.Serializable;

import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsTourState.findAll", query = "select o from TRsTourState o")
        ,
        @NamedQuery(name = "TRsTourState.findByDeviceId", query = "select o from TRsTourState o where o.sdcardInfo.deviceId = :deviceId")
        ,
        @NamedQuery(name = "TRsTourState.findBySdcardId", query = "select o from TRsTourState o where o.sdcardInfo.sdcardId = :sdcardId")
        ,
        @NamedQuery(name = "TRsTourState.findByDeviceIdSdcardId", query = "select o from TRsTourState o where o.sdcardInfo.deviceId = :deviceId and o.sdcardInfo.sdcardId = :sdcardId")
        } )
@Table(name = "T_RS_TOUR_STATE")
public class TRsTourState implements Serializable {

    @Version
    private Integer version;

    @EmbeddedId
    private SdcardInfo sdcardInfo;

    private boolean onRoad;

    @Enumerated(EnumType.STRING)
    private SensorEvent lastSensorEvent;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar lastEventOccurredOn;

    @Enumerated(EnumType.STRING)
    private SensorEvent actualSensorEvent;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar actualEventOccurredOn;

    private long lastRecordNumber;

    private long actualRecordNumber;

    @Embedded
    private PointsAccount pointsAccount;

    private long drivenSeconds;

    private long tourCount;

    public TRsTourState() {
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setOnRoad(boolean onRoad) {
        this.onRoad = onRoad;
    }

    public boolean isOnRoad() {
        return onRoad;
    }

    public void setLastSensorEvent(SensorEvent lastSensorEvent) {
        this.lastSensorEvent = lastSensorEvent;
    }

    public SensorEvent getLastSensorEvent() {
        return lastSensorEvent;
    }

    public void setLastEventOccurredOn(Calendar lastEventOccurredOn) {
        this.lastEventOccurredOn = lastEventOccurredOn;
    }

    public Calendar getLastEventOccurredOn() {
        return lastEventOccurredOn;
    }

    public void setActualSensorEvent(SensorEvent actualSensorEvent) {
        this.actualSensorEvent = actualSensorEvent;
    }

    public SensorEvent getActualSensorEvent() {
        return actualSensorEvent;
    }

    public void setActualEventOccurredOn(Calendar actualEventOccurredOn) {
        this.actualEventOccurredOn = actualEventOccurredOn;
    }

    public Calendar getActualEventOccurredOn() {
        return actualEventOccurredOn;
    }

    public void setLastRecordNumber(Long lastRecordNumber) {
        this.lastRecordNumber = lastRecordNumber;
    }

    public long getLastRecordNumber() {
        return lastRecordNumber;
    }

    public void setActualRecordNumber(long actualRecordNumber) {
        this.actualRecordNumber = actualRecordNumber;
    }

    public long getActualRecordNumber() {
        return actualRecordNumber;
    }

    public void setSdcardInfo(SdcardInfo sdcardInfo) {
        this.sdcardInfo = sdcardInfo;
    }

    public SdcardInfo getSdcardInfo() {
        return sdcardInfo;
    }

    public void setPointsAccount(PointsAccount pointsAccount) {
        this.pointsAccount = pointsAccount;
    }

    public PointsAccount getPointsAccount() {
        return pointsAccount;
    }

    public void setDrivenSeconds(long drivenSeconds) {
        this.drivenSeconds = drivenSeconds;
    }

    public long getDrivenSeconds() {
        return drivenSeconds;
    }

    public void setTourCount(long tourCount) {
        this.tourCount = tourCount;
    }

    public long getTourCount() {
        return tourCount;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[version=" + version + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TRsTourState)) {
            return false;
        }
        final TRsTourState other = (TRsTourState) object;
        if (!(sdcardInfo == null ? other.sdcardInfo == null : sdcardInfo.equals(other.sdcardInfo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((sdcardInfo == null) ? 0 : sdcardInfo.hashCode());
        return result;
    }

}
