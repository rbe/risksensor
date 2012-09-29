package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsData.findAll", query = "select o from TRsData o")
        } )
@Table(name = "T_RS_DATA")
@IdClass(TRsDataPK.class)
public class TRsData implements Serializable {

    @Column(nullable = false)
    private Timestamp createddate;

    @Id
    @Column(nullable = false)
    private Timestamp createdon;

    @Column(nullable = false)
    private Timestamp createdtime;

    private String datewarning;

    @Id
    @Column(nullable = false)
    private String deviceid;

    private Long durationms;

    @Id
    @Column(nullable = false)
    private Long eventid;

    @Column(nullable = false)
    private Timestamp importedon;

    @Id
    @Column(nullable = false)
    private Long recordnum;

    @Id
    @Column(nullable = false)
    private String sdcardid;

    private String timewarning;

    private Long xmg;

    private Long ymg;

    private Long zmg;

    public TRsData() {
    }

    public TRsData(Timestamp createddate, Timestamp createdon, Timestamp createdtime, String datewarning,
                   String deviceid, Long durationms, Long eventid, Timestamp importedon, Long recordnum,
                   String sdcardid, String timewarning, Long xmg, Long ymg, Long zmg) {
        this.createddate = createddate;
        this.createdon = createdon;
        this.createdtime = createdtime;
        this.datewarning = datewarning;
        this.deviceid = deviceid;
        this.durationms = durationms;
        this.eventid = eventid;
        this.importedon = importedon;
        this.recordnum = recordnum;
        this.sdcardid = sdcardid;
        this.timewarning = timewarning;
        this.xmg = xmg;
        this.ymg = ymg;
        this.zmg = zmg;
    }

    public Timestamp getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Timestamp createddate) {
        this.createddate = createddate;
    }

    public Timestamp getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Timestamp createdon) {
        this.createdon = createdon;
    }

    public Timestamp getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Timestamp createdtime) {
        this.createdtime = createdtime;
    }

    public String getDatewarning() {
        return datewarning;
    }

    public void setDatewarning(String datewarning) {
        this.datewarning = datewarning;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Long getDurationms() {
        return durationms;
    }

    public void setDurationms(Long durationms) {
        this.durationms = durationms;
    }

    public Long getEventid() {
        return eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public Timestamp getImportedon() {
        return importedon;
    }

    public void setImportedon(Timestamp importedon) {
        this.importedon = importedon;
    }

    public Long getRecordnum() {
        return recordnum;
    }

    public void setRecordnum(Long recordnum) {
        this.recordnum = recordnum;
    }

    public String getSdcardid() {
        return sdcardid;
    }

    public void setSdcardid(String sdcardid) {
        this.sdcardid = sdcardid;
    }

    public String getTimewarning() {
        return timewarning;
    }

    public void setTimewarning(String timewarning) {
        this.timewarning = timewarning;
    }

    public Long getXmg() {
        return xmg;
    }

    public void setXmg(Long xmg) {
        this.xmg = xmg;
    }

    public Long getYmg() {
        return ymg;
    }

    public void setYmg(Long ymg) {
        this.ymg = ymg;
    }

    public Long getZmg() {
        return zmg;
    }

    public void setZmg(Long zmg) {
        this.zmg = zmg;
    }

}
