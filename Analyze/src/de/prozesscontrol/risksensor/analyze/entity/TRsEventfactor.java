package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsEventfactor.findAll", query = "select o from TRsEventfactor o")
        } )
@Table(name = "T_RS_EVENTFACTOR")
public class TRsEventfactor implements Serializable {

    private String deviceid;

    @Column(nullable = false)
    private Long eventid;

    @Column(nullable = false)
    private Double factor;

    @Id
    @Column(nullable = false)
    private Long id;

    private String sdcardid;

    @Column(nullable = false)
    private Double timefactor;

    @Column(nullable = false)
    private Timestamp validfrom;

    private Timestamp validto;

    @Column(nullable = false)
    private Double valuefactor;

    public TRsEventfactor() {
    }

    public TRsEventfactor(String deviceid, Long eventid, Double factor, Long id, String sdcardid, Double timefactor,
                          Timestamp validfrom, Timestamp validto, Double valuefactor) {
        this.deviceid = deviceid;
        this.eventid = eventid;
        this.factor = factor;
        this.id = id;
        this.sdcardid = sdcardid;
        this.timefactor = timefactor;
        this.validfrom = validfrom;
        this.validto = validto;
        this.valuefactor = valuefactor;
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

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSdcardid() {
        return sdcardid;
    }

    public void setSdcardid(String sdcardid) {
        this.sdcardid = sdcardid;
    }

    public Double getTimefactor() {
        return timefactor;
    }

    public void setTimefactor(Double timefactor) {
        this.timefactor = timefactor;
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

    public Double getValuefactor() {
        return valuefactor;
    }

    public void setValuefactor(Double valuefactor) {
        this.valuefactor = valuefactor;
    }

}
