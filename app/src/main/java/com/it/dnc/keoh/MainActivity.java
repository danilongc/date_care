package com.it.dnc.keoh;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.it.dnc.keoh.appdata.model.Contact;
import com.it.dnc.keoh.appdata.model.Rush;
import com.it.dnc.keoh.tasks.RushCreateAsyncTask;
import com.it.dnc.keoh.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity  extends AppCompatActivity implements  ContactItemFragment2.OnListFragmentInteractionListener  {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ContactItemFragment2 contactListFragment;
    private Menu menu;

    private String[] permissions = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //contactListFragment.clearSelection();

                Intent intent = new Intent(view.getContext(), EditContactActivity.class);
                startActivityForResult(intent, 0);


                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
            }
        });



        permissions = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.VIBRATE};

        checkPermissions();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data.getExtras().get("result").toString().equals("created")){
            finish();
            startActivity(getIntent());
        }

    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_rush){
            createRush();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createRush(){
        List<Contact> selectedContacts = contactListFragment.getSelection();

        Rush rush =  new Rush();
        rush.setDtStart(new Date());
        rush.setDtEnd(new Date());

        String[] contactIds = new String[selectedContacts.size()];
        int index = 0;
        for(Contact contact : selectedContacts){
            contactIds[index] = String.valueOf(contact.getId());
            index =  index + 1;
        }

        rush.setContactList(CollectionUtil.getStrList(contactIds));

        RushCreateAsyncTask asyncTask  = new RushCreateAsyncTask(this);

        try {
            Rush createdRush = asyncTask.execute(rush).get();
            if(createdRush != null){
                String okTxt = "Rush com " + selectedContacts.size() + " contados criado.";
                Snackbar.make(contactListFragment.getView() , okTxt, Snackbar.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onListFragmentInteraction(Contact item) {

        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("CONTACT_ID", item.getId());
        startActivity(intent);

    }

    @Override
    public void onMultiFragmentInteraction(Boolean multi, List<Contact> contacts) {
        if(multi){
            this.menu.findItem(R.id.action_rush).setVisible(true);
        }else{
            this.menu.findItem(R.id.action_rush).setVisible(false);
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    contactListFragment = ContactItemFragment2.newInstance();
                    return contactListFragment;
                case 1:
                    return BlankFragment.newInstance("","");
            };

            return BlankFragment.newInstance("","");
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
