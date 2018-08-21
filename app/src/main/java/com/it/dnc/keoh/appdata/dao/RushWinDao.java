package com.it.dnc.keoh.appdata.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.it.dnc.keoh.appdata.model.Contact;
import com.it.dnc.keoh.appdata.model.Rush;
import com.it.dnc.keoh.appdata.model.RushWin;

import java.util.List;

/**
 * Created by dnc on 12/08/18.
 */

@Dao
public interface RushWinDao {

    @Query("SELECT * FROM rushWins")
    List<RushWin> getAll();

    @Query("SELECT * FROM rushWins WHERE id = :id ")
    RushWin findRushWinById(int id);

    @Insert
    void insert(RushWin... repo);

    @Update
    void update(RushWin... repos);

    @Delete
    void delete(RushWin... repos);

}
