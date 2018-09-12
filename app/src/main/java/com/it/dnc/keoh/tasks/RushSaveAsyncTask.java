package com.it.dnc.keoh.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.it.dnc.keoh.appdata.AppDatabase;
import com.it.dnc.keoh.appdata.model.Rush;

import java.lang.ref.WeakReference;

/**
 * Created by dnc on 21/08/18.
 */

public class RushSaveAsyncTask extends AsyncTask<Rush, Void, Rush> {

    private WeakReference<Activity> weakActivity;


    public RushSaveAsyncTask(Activity activity) {
        this.weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected Rush doInBackground(Rush[] rushs) {
        Rush rush = rushs[0];
        AppDatabase appDatabase = AppDatabase.getAppDatabase(weakActivity.get().getApplicationContext());
        appDatabase.rushDao().update(rush);
        return rush;
    }

}
