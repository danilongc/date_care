package com.it.dnc.keoh.appdata.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.it.dnc.keoh.appdata.model.Contact;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by dnc on 12/08/18.
 */

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts")
    List<Contact> getAll();

    @Query("SELECT * FROM contacts WHERE id = :id ")
    Contact findContactById(int id);


    @Insert
    void insertAll(Contact... contacts);

}
