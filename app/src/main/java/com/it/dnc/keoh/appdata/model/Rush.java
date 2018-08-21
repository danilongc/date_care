package com.it.dnc.keoh.appdata.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


/**
 * Created by dnc on 21/08/18.
 */

@Entity(tableName = "Rushs")
public class Rush {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "dtStart")
    private Date dtStart;

    @ColumnInfo(name = "dtEnd")
    private Date dtEnd;

    @ColumnInfo(name = "contactList")
    private String contactList;

    @ColumnInfo(name = "possibleContacts")
    private String possibleContacts;


    public Rush() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDtStart() {
        return dtStart;
    }

    public void setDtStart(Date dtStart) {
        this.dtStart = dtStart;
    }

    public Date getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(Date dtEnd) {
        this.dtEnd = dtEnd;
    }

    public String getContactList() {
        return contactList;
    }

    public void setContactList(String contactList) {
        this.contactList = contactList;
    }

    public String getPossibleContacts() {
        return possibleContacts;
    }

    public void setPossibleContacts(String possibleContacts) {
        this.possibleContacts = possibleContacts;
    }
}
