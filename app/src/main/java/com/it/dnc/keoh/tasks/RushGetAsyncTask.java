package com.it.dnc.keoh.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.it.dnc.keoh.appdata.AppDatabase;
import com.it.dnc.keoh.appdata.model.Rush;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnc on 21/08/18.
 */

public class RushGetAsyncTask extends AsyncTask<Integer, Void, List<Rush>> {

    private WeakReference<Activity> weakActivity;

    public RushGetAsyncTask(Activity activity) {
        this.weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected List<Rush> doInBackground(Integer[] idRush) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(weakActivity.get().getApplicationContext());
        List<Rush> all = new ArrayList<>();
        if(idRush.length > 0){
            Rush rushById = appDatabase.rushDao().findRushById(idRush[0]);
            all.add(rushById);
        }else{
            all = appDatabase.rushDao().getAll();
        }
        return all;
    }

}
