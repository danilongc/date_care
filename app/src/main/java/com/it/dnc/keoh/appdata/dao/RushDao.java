package com.it.dnc.keoh.appdata.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.it.dnc.keoh.appdata.model.Rush;

import java.util.List;

/**
 * Created by dnc on 12/08/18.
 */

@Dao
public interface RushDao {

    @Query("SELECT * FROM rushs")
    List<Rush> getAll();

    @Query("SELECT * FROM rushs WHERE id = :id ")
    Rush findRushById(int id);

    @Insert
    void insert(Rush... repo);

    @Update
    void update(Rush... repos);

    @Delete
    void delete(Rush... repos);


}
