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
@NamedQueries( { @NamedQuery(name = "TRsThreshold.findAll", query = "select o from TRsThreshold o")
        } )
@Table(name = "T_RS_THRESHOLD")
@IdClass(TRsThresholdPK.class)
public class TRsThreshold implements Serializable {

    @Id
    @Column(nullable = false)
    private String cmdprefix;

    @Column(nullable = false)
    private Long cmdvalue;

    @Id
    @Column(nullable = false)
    private String deviceid;

    @Column(nullable = false)
    private Long eventid;

    @Column(nullable = false)
    private Timestamp importedon;

    @Column(nullable = false)
    private Long recordnum;

    @Id
    @Column(nullable = false)
    private Timestamp validfrom;

    private Timestamp validto;

    public TRsThreshold() {
    }

    public TRsThreshold(String cmdprefix, Long cmdvalue, String deviceid, Long eventid, Timestamp importedon,
                        Long recordnum, Timestamp validfrom, Timestamp validto) {
        this.cmdprefix = cmdprefix;
        this.cmdvalue = cmdvalue;
        this.deviceid = deviceid;
        this.eventid = eventid;
        this.importedon = importedon;
        this.recordnum = recordnum;
        this.validfrom = validfrom;
        this.validto = validto;
    }

    public String getCmdprefix() {
        return cmdprefix;
    }

    public void setCmdprefix(String cmdprefix) {
        this.cmdprefix = cmdprefix;
    }

    public Long getCmdvalue() {
        return cmdvalue;
    }

    public void setCmdvalue(Long cmdvalue) {
        this.cmdvalue = cmdvalue;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
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

    public Timestamp getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Timestamp validfrom) {
        this.validfrom = validfrom;
    }

    public Timestamp getValidto() {
        return validto;
    }

    public void setValidto(Timestamp validto) {
        this.validto = validto;
    }

}
