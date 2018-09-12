package com.it.dnc.keoh.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.it.dnc.keoh.appdata.AppDatabase;
import com.it.dnc.keoh.appdata.model.Contact;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnc on 12/08/18.
 */

public class CitiesGetAsyncTask extends AsyncTask<Void, Void, String[]>{

    private WeakReference<Activity> weakActivity;

    public CitiesGetAsyncTask(Activity activity){
        this.weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected String[] doInBackground(Void... params) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(weakActivity.get().getApplicationContext());
        return appDatabase.contactDao().findAllCities();
    }


    @Override
    protected void onPostExecute(String[] list) {
        super.onPostExecute(list);

    }
}
