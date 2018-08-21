package com.it.dnc.keoh.appdata;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.it.dnc.keoh.appdata.dao.ContactDao;
import com.it.dnc.keoh.appdata.dao.RushDao;
import com.it.dnc.keoh.appdata.dao.RushWinDao;
import com.it.dnc.keoh.appdata.model.Contact;
import com.it.dnc.keoh.appdata.model.Converters;
import com.it.dnc.keoh.appdata.model.Rush;
import com.it.dnc.keoh.appdata.model.RushWin;

/**
 * Created by dnc on 12/08/18.
 */

@Database(entities = {Contact.class, Rush.class, RushWin.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {


    private static String DATA_BASE_NAME = "Keoh-Contacts-db";
    private static AppDatabase INSTANCE;

    public abstract ContactDao contactDao();
    public abstract RushDao rushDao();
    public abstract RushWinDao rushWinDao();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATA_BASE_NAME) .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
