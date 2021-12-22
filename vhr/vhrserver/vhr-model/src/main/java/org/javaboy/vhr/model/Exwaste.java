package org.javaboy.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Exwaste {
    private int  id;
    private String enVlp;
    private int enVlpc;
    private String entollStationname;
    private String extollStationname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exTime;
    private String reMarks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnVlp() {
        return enVlp;
    }

    public void setEnVlp(String enVlp) {
        this.enVlp = enVlp;
    }

    public int getEnVlpc() {
        return enVlpc;
    }

    public void setEnVlpc(int enVlpc) {
        this.enVlpc = enVlpc;
    }

    public String getEntollStationname() {
        return entollStationname;
    }

    public void setEntollStationname(String entollStationname) {
        this.entollStationname = entollStationname;
    }

    public String getExtollStationname() {
        return extollStationname;
    }

    public void setExtollStationname(String extollStationname) {
        this.extollStationname = extollStationname;
    }

    public Date getExTime() {
        return exTime;
    }

    public void setExTime(Date exTime) {
        this.exTime = exTime;
    }

    public String getReMarks() {
        return reMarks;
    }

    public void setReMarks(String reMarks) {
        this.reMarks = reMarks;
    }

    @Override
    public String toString() {
        return "Exwaste{" +
                "id=" + id +
                ", enVlp='" + enVlp + '\'' +
                ", enVlpc=" + enVlpc +
                ", entollStationname='" + entollStationname + '\'' +
                ", extollStationname='" + extollStationname + '\'' +
                ", exTime=" + exTime +
                ", reMarks='" + reMarks + '\'' +
                '}';
    }
}
