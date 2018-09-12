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

public class ContactListGetAsyncTask extends AsyncTask<List<Integer>, Void, List<Contact>>{

    private WeakReference<Activity> weakActivity;

    public ContactListGetAsyncTask(Activity activity){
        this.weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected List<Contact> doInBackground(List<Integer>... params) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(weakActivity.get().getApplicationContext());
        return appDatabase.contactDao().findContactsByIds (params[0]);
    }


    @Override
    protected void onPostExecute(List<Contact> list) {
        super.onPostExecute(list);

    }
}
