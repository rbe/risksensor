package de.prozesscontrol.risksensor.analyze.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "TRsLpn.findAll", query = "select o from TRsLpn o")
        } )
@Table(name = "T_RS_LPN")
public class TRsLpn implements Serializable {

    @Id
    @Column(nullable = false)
    private String deviceid;

    @Column(nullable = false)
    private String lpn;

    public TRsLpn() {
    }

    public TRsLpn(String deviceid, String lpn) {
        this.deviceid = deviceid;
        this.lpn = lpn;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

}
