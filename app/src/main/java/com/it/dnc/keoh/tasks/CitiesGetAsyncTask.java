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

public class ContacGetAsyncTask extends AsyncTask<Integer, Void, List<Contact>>{

    private WeakReference<Activity> weakActivity;

    public ContacGetAsyncTask(Activity activity){
        this.weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected List<Contact> doInBackground(Integer... params) {

        AppDatabase appDatabase = AppDatabase.getAppDatabase(weakActivity.get().getApplicationContext());
        List<Contact> all = new ArrayList<>();
        if(params.length > 0){
            all.add(appDatabase.contactDao().findContactById(params[0]));
        }else{
            all = appDatabase.contactDao().getAll();
        }

        return all;
    }


    @Override
    protected void onPostExecute(List<Contact> list) {
        super.onPostExecute(list);

    }
}

/*

super.onPostExecute(list);
        Contact contact = list.get(0);

        AppDatabase appDatabase = AppDatabase.getAppDatabase(weakActivity.get().getApplicationContext());
        appDatabase.contactDao().insertAll(contact);*/
