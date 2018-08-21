package com.it.dnc.keoh.appdata.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


/**
 * Created by dnc on 21/08/18.
 */

@Entity(tableName = "RushWins")
public class RushWin {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_contact")
    private Integer contact;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "id_rush")
    private Integer rush;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "wwd")
    private String wwd;


    public RushWin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWwd() {
        return wwd;
    }

    public void setWwd(String wwd) {
        this.wwd = wwd;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public Integer getRush() {
        return rush;
    }

    public void setRush(Integer rush) {
        this.rush = rush;
    }
}
