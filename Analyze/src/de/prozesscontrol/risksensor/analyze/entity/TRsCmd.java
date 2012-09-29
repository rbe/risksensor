package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsCmd.findAll", query = "select o from TRsCmd o")
        } )
@Table(name = "T_RS_CMD")
public class TRsCmd implements Serializable {

    @Column(nullable = false)
    private String cmdprefix;

    private String description;

    @Column(nullable = false)
    private Long eventid;

    @Column(nullable = false)
    private Long foreventid;

    @Id
    @Column(nullable = false)
    private Long id;

    public TRsCmd() {
    }

    public TRsCmd(String cmdprefix, String description, Long eventid, Long foreventid, Long id) {
        this.cmdprefix = cmdprefix;
        this.description = description;
        this.eventid = eventid;
        this.foreventid = foreventid;
        this.id = id;
    }

    public String getCmdprefix() {
        return cmdprefix;
    }

    public void setCmdprefix(String cmdprefix) {
        this.cmdprefix = cmdprefix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEventid() {
        return eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public Long getForeventid() {
        return foreventid;
    }

    public void setForeventid(Long foreventid) {
        this.foreventid = foreventid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
