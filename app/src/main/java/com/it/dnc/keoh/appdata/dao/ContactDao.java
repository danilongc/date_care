package com.it.dnc.keoh.appdata.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.it.dnc.keoh.appdata.model.Contact;

import java.util.List;

/**
 * Created by dnc on 12/08/18.
 */

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts")
    List<Contact> getAll();

    @Query("SELECT * FROM contacts WHERE id = :id ")
    Contact findContactById(int id);

    @Query("SELECT city FROM contacts GROUP BY city")
    String[] findAllCities();

    @Query("SELECT * FROM contacts WHERE id IN (:ids)")
    List<Contact> findContactsByIds(List<Integer> ids);


    @Insert
    void insert(Contact... contacts);

    @Update
    void update(Contact... repos);

    @Delete
    void delete(Contact... contacts);

}
