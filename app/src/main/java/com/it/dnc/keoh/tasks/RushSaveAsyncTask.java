package com.it.dnc.keoh.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.it.dnc.keoh.appdata.AppDatabase;
import com.it.dnc.keoh.appdata.model.Contact;
import com.it.dnc.keoh.appdata.model.Rush;
import com.it.dnc.keoh.util.CollectionUtil;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

/**
 * Created by dnc on 21/08/18.
 */

public class RushCreateAsyncTask extends AsyncTask<Rush, Void, Rush> {

    private WeakReference<Activity> weakActivity;


    public RushCreateAsyncTask(Activity activity) {
        this.weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected Rush doInBackground(Rush[] rushs) {

        Rush rush = rushs[0];
        rush.setPossibleContacts("");
        AppDatabase appDatabase = AppDatabase.getAppDatabase(weakActivity.get().getApplicationContext());
        appDatabase.rushDao().insert(rush);

        return rush;
    }

}
