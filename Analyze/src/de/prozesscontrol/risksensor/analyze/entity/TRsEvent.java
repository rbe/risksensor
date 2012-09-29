package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsEvent.findAll", query = "select o from TRsEvent o")
        } )
@Table(name = "T_RS_EVENT")
public class TRsEvent implements Serializable {

    @Column(nullable = false)
    private String description;

    private String eventname;

    @Id
    @Column(nullable = false)
    private Long id;

    public TRsEvent() {
    }

    public TRsEvent(String description, String eventname, Long id) {
        this.description = description;
        this.eventname = eventname;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
