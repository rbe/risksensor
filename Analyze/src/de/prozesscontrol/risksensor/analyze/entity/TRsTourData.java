package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsTourData.findAll",
                             query = "select o from TRsTourData o order by o.createdon asc, o.recordnum asc")
        } )
@Table(name = "T_RS_TOUR_DATA")
@IdClass(TRsTourDataPK.class)
public class TRsTourData implements Serializable {

    private Long absolutemg;

    @Id
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar /*Timestamp*/createdon;

    @Column(nullable = false)
    private String deviceid;

    private Long durationms;

    private Long durationthreshold;

    @Id
    @Column(nullable = false)
    private Long eventid;

    private Integer eventlastoccured;

    private Long eventthreshold;

    private Double factor;

    private Double pctdurexceeded;

    private Double pctthrexceeded;

    private Double points;

    @Id
    @Column(nullable = false)
    private Long recordnum;

    private Double relativedurationms;

    private Double relativemg;

    private String sdcardid;

    private Double timefactor;

    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    private Long tourid;

    private Double valuefactor;

    private Double vduration;

    private Double vmg;

    @ManyToOne
    @JoinColumn(name = "TOURID")
    private TRsTour TRsTour;

    public TRsTourData() {
    }

    public TRsTourData(Long absolutemg, Calendar createdon, Long durationms, Long durationthreshold, Long eventid,
                       Integer eventlastoccured, Long eventthreshold, Double factor, Double pctdurexceeded,
                       Double pctthrexceeded, Double points, Long recordnum, Double relativedurationms,
                       Double relativemg, Double timefactor, TRsTour TRsTour, Double valuefactor, Double vduration,
                       Double vmg) {
        this.absolutemg = absolutemg;
        this.createdon = createdon;
        this.durationms = durationms;
        this.durationthreshold = durationthreshold;
        this.eventid = eventid;
        this.eventlastoccured = eventlastoccured;
        this.eventthreshold = eventthreshold;
        this.factor = factor;
        this.pctdurexceeded = pctdurexceeded;
        this.pctthrexceeded = pctthrexceeded;
        this.points = points;
        this.recordnum = recordnum;
        this.relativedurationms = relativedurationms;
        this.relativemg = relativemg;
        this.timefactor = timefactor;
        this.TRsTour = TRsTour;
        this.valuefactor = valuefactor;
        this.vduration = vduration;
        this.vmg = vmg;
    }

    public Long getAbsolutemg() {
        return absolutemg;
    }

    public void setAbsolutemg(Long absolutemg) {
        this.absolutemg = absolutemg;
    }

    public Calendar getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Calendar createdon) {
        this.createdon = createdon;
    }

    public Long getDurationms() {
        return durationms;
    }

    public void setDurationms(Long durationms) {
        this.durationms = durationms;
    }

    public Long getDurationthreshold() {
        return durationthreshold;
    }

    public void setDurationthreshold(Long durationthreshold) {
        this.durationthreshold = durationthreshold;
    }

    public Long getEventid() {
        return eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public Integer getEventlastoccured() {
        return eventlastoccured;
    }

    public void setEventlastoccured(Integer eventlastoccured) {
        this.eventlastoccured = eventlastoccured;
    }

    public Long getEventthreshold() {
        return eventthreshold;
    }

    public void setEventthreshold(Long eventthreshold) {
        this.eventthreshold = eventthreshold;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public Double getPctdurexceeded() {
        return pctdurexceeded;
    }

    public void setPctdurexceeded(Double pctdurexceeded) {
        this.pctdurexceeded = pctdurexceeded;
    }

    public Double getPctthrexceeded() {
        return pctthrexceeded;
    }

    public void setPctthrexceeded(Double pctthrexceeded) {
        this.pctthrexceeded = pctthrexceeded;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Long getRecordnum() {
        return recordnum;
    }

    public void setRecordnum(Long recordnum) {
        this.recordnum = recordnum;
    }

    public Double getRelativedurationms() {
        return relativedurationms;
    }

    public void setRelativedurationms(Double relativedurationms) {
        this.relativedurationms = relativedurationms;
    }

    public Double getRelativemg() {
        return relativemg;
    }

    public void setRelativemg(Double relativemg) {
        this.relativemg = relativemg;
    }

    /*public INTERVALDS getRelativetourtime() {
        return relativetourtime;
    }

    public void setRelativetourtime(INTERVALDS relativetourtime) {
        this.relativetourtime = relativetourtime;
    }*/

    public Double getTimefactor() {
        return timefactor;
    }

    public void setTimefactor(Double timefactor) {
        this.timefactor = timefactor;
    }

    public Long getTourid() {
        return tourid;
    }

    public void setTourid(Long tourid) {
        this.tourid = tourid;
    }

    public Double getValuefactor() {
        return valuefactor;
    }

    public void setValuefactor(Double valuefactor) {
        this.valuefactor = valuefactor;
    }

    public Double getVduration() {
        return vduration;
    }

    public void setVduration(Double vduration) {
        this.vduration = vduration;
    }

    public Double getVmg() {
        return vmg;
    }

    public void setVmg(Double vmg) {
        this.vmg = vmg;
    }

    public TRsTour getTRsTour() {
        return TRsTour;
    }

    public void setTRsTour(TRsTour TRsTour) {
        this.TRsTour = TRsTour;
        if (TRsTour != null) {
            this.tourid = TRsTour.getId();
        }
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setSdcardid(String sdcardid) {
        this.sdcardid = sdcardid;
    }

    public String getSdcardid() {
        return sdcardid;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + deviceid + "/" + sdcardid + "]";
    }

}
