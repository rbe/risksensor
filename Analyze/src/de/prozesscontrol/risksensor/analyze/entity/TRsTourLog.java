package de.prozesscontrol.risksensor.analyze.entity;

import de.prozesscontrol.risksensor.analyze.event.SensorEvent;

import java.io.Serializable;

import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsTourLog.findAll", query = "select o from TRsTourLog o")
        } )
@Table(name = "T_RS_TOUR_LOG")
@SequenceGenerator(name = "t_rs_tour_log_seq", sequenceName = "T_RS_TOUR_LOG_SEQ", allocationSize = 1,
                   initialValue = 1)
public class TRsTourLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_rs_tour_log_seq")
    private Integer id;

    @Version
    private Integer version;

    @Embedded
    private SdcardInfo sdcardInfo;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar createdon;

    private long tourCount;

    @Enumerated(value = EnumType.STRING)
    private SensorEvent sensorEvent;

    @Embedded
    private PointsAccount pointsAccount;

    private long drivenSeconds;

    public TRsTourLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setSdcardInfo(SdcardInfo sdcardInfo) {
        this.sdcardInfo = sdcardInfo;
    }

    public SdcardInfo getSdcardInfo() {
        return sdcardInfo;
    }

    public void setCreatedon(Calendar createdon) {
        this.createdon = createdon;
    }

    public Calendar getCreatedon() {
        return createdon;
    }

    public void setPointsAccount(PointsAccount journeyPoints) {
        this.pointsAccount = journeyPoints;
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

    public void setSensorEvent(SensorEvent sensorEvent) {
        this.sensorEvent = sensorEvent;
    }

    public SensorEvent getSensorEvent() {
        return sensorEvent;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TRsTourLog)) {
            return false;
        }
        final TRsTourLog other = (TRsTourLog) object;
        if (!(sdcardInfo == null ? other.sdcardInfo == null : sdcardInfo.equals(other.sdcardInfo))) {
            return false;
        }
        if (!(createdon == null ? other.createdon == null : createdon.equals(other.createdon))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((sdcardInfo == null) ? 0 : sdcardInfo.hashCode());
        result = PRIME * result + ((createdon == null) ? 0 : createdon.hashCode());
        return result;
    }

}
