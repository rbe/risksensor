package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsTour.findAll", query = "select o from TRsTour o")
        } )
@Table(name = "T_RS_TOUR")
public class TRsTour implements Serializable {

    @Column(nullable = false)
    private Timestamp analyzedon;

    @Column(nullable = false)
    private String deviceid;

    //private Integer durationts;

    private Timestamp ended;

    private Long exceedcount;

    @Id
    @Column(nullable = false)
    private Long id;

    private Double points;

    private String sdcardid;

    @Column(nullable = false)
    private Timestamp started;

    @OneToMany(mappedBy = "TRsTour")
    private List<TRsTourData> TRsTourDataList;

    public TRsTour() {
    }

    public TRsTour(Timestamp analyzedon, String deviceid, /*, Integer durationts*/Timestamp ended, Long exceedcount,
                   Long id, Double points, String sdcardid, Timestamp started) {
        this.analyzedon = analyzedon;
        this.deviceid = deviceid;
        //this.durationts = durationts;
        this.ended = ended;
        this.exceedcount = exceedcount;
        this.id = id;
        this.points = points;
        this.sdcardid = sdcardid;
        this.started = started;
    }

    public Timestamp getAnalyzedon() {
        return analyzedon;
    }

    public void setAnalyzedon(Timestamp analyzedon) {
        this.analyzedon = analyzedon;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    /*public Integer getDurationts() {
        return durationts;
    }

    public void setDurationts(Integer durationts) {
        this.durationts = durationts;
    }*/

    public Timestamp getEnded() {
        return ended;
    }

    public void setEnded(Timestamp ended) {
        this.ended = ended;
    }

    public Long getExceedcount() {
        return exceedcount;
    }

    public void setExceedcount(Long exceedcount) {
        this.exceedcount = exceedcount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getSdcardid() {
        return sdcardid;
    }

    public void setSdcardid(String sdcardid) {
        this.sdcardid = sdcardid;
    }

    public Timestamp getStarted() {
        return started;
    }

    public void setStarted(Timestamp started) {
        this.started = started;
    }

    public List<TRsTourData> getTRsTourDataList() {
        return TRsTourDataList;
    }

    public void setTRsTourDataList(List<TRsTourData> TRsTourDataList) {
        this.TRsTourDataList = TRsTourDataList;
    }

    public TRsTourData addTRsTourData(TRsTourData TRsTourData) {
        getTRsTourDataList().add(TRsTourData);
        TRsTourData.setTRsTour(this);
        return TRsTourData;
    }

    public TRsTourData removeTRsTourData(TRsTourData TRsTourData) {
        getTRsTourDataList().remove(TRsTourData);
        TRsTourData.setTRsTour(null);
        return TRsTourData;
    }

}
