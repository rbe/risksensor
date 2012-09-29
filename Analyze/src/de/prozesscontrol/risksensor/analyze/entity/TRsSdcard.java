package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsSdcard.findAll", query = "select o from TRsSdcard o")
        } )
@Table(name = "T_RS_SDCARD")
public class TRsSdcard implements Serializable {

    @Column(nullable = false)
    private String lastname;

    @EmbeddedId
    private SdcardInfo sdcardInfo;

    public TRsSdcard() {
    }

    public TRsSdcard(String lastname, SdcardInfo sdcardid) {
        this.lastname = lastname;
        this.sdcardInfo = sdcardid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public SdcardInfo getSdcardInfo() {
        return sdcardInfo;
    }

    public void setSdcardInfo(SdcardInfo sdcardid) {
        this.sdcardInfo = sdcardid;
    }

}
