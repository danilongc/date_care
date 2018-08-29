package com.it.dnc.keoh.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.it.dnc.keoh.appdata.AppDatabase;
import com.it.dnc.keoh.appdata.model.Contact;

import java.lang.ref.WeakReference;

/**
 * Created by dnc on 12/08/18.
 */

public class ContactSaveAsyncTask extends AsyncTask<Void, Void, Contact> {

    private WeakReference<Activity> weakActivity;
    private Contact contact;

    public ContactSaveAsyncTask(Activity activity, Contact contact){
        this.weakActivity = new WeakReference<>(activity);
        this.contact = contact;
    }

    @Override
    protected Contact doInBackground(Void... voids) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(weakActivity.get().getApplicationContext());

        if(contact.getId() != null){
            appDatabase.contactDao().update(contact);
        }else{
            appDatabase.contactDao().insert(contact);
        }

        return contact;
    }

    @Override
    protected void onPostExecute(Contact contact) {
        super.onPostExecute(contact);

    }

}
