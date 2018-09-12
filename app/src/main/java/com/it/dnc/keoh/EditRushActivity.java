package com.it.dnc.keoh;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.it.dnc.keoh.appdata.model.Contact;
import com.it.dnc.keoh.appdata.model.Rush;
import com.it.dnc.keoh.tasks.CitiesGetAsyncTask;
import com.it.dnc.keoh.tasks.ContactListGetAsyncTask;
import com.it.dnc.keoh.tasks.RushGetAsyncTask;
import com.it.dnc.keoh.tasks.RushSaveAsyncTask;
import com.it.dnc.keoh.util.CollectionUtil;
import com.it.dnc.keoh.util.StringUtil;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EditRushActivity extends AppCompatActivity implements  ContactRushFragment.OnListFragmentInteractionListener{


    private TextView txtStartDate;
    private TextView txtEndDate;
    private AutoCompleteTextView txtCity;
    private Spinner listStates;

    private RecyclerView viewContacts;
    private Rush mRush;

    private ImageButton btnSaveRush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rush);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        txtStartDate = (TextView) findViewById(R.id.txtRushStart);
        txtEndDate = (TextView) findViewById(R.id.txtRushEnd);
        txtCity =  (AutoCompleteTextView) findViewById(R.id.txtCity);
        listStates = findViewById(R.id.listStates);
        btnSaveRush = findViewById(R.id.btnSaveRush);


        viewContacts = findViewById(R.id.list_contact_rush);
        viewContacts.setItemAnimator(new DefaultItemAnimator());
        viewContacts.addItemDecoration(new DividerItemDecoration(viewContacts.getContext(), LinearLayoutManager.VERTICAL));


        createDatePickerDialog();
        configureAutoComplete();


        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("RUSH_ID")){
            String id = getIntent().getExtras().get("RUSH_ID").toString();

            RushGetAsyncTask getAsyncTask = new RushGetAsyncTask(this);
            try {
                List<Rush> rushes = getAsyncTask.execute(Integer.parseInt(id)).get();
                mRush = rushes.get(0);
                loadData();

                txtCity.setEnabled(false);
                txtCity.setFocusable(false);


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }


        btnSaveRush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRush();
            }
        });

    }


    private void saveRush(){
        if(mRush != null){
            RushSaveAsyncTask rushSaveAsyncTask = new RushSaveAsyncTask(this);
            try {
                Rush savedRush = rushSaveAsyncTask.execute(mRush).get();
                mRush = savedRush;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    private void loadData() {
        this.txtStartDate.setText(StringUtil.formatDate(mRush.getDtStart()));
        this.txtEndDate.setText(StringUtil.formatDate(mRush.getDtEnd()));
        this.txtCity.setText(mRush.getCity());
//        this.listStates.setSelection(mRush.get);



        List<Integer> contactIds = CollectionUtil.getList(mRush.getContactList());

        ContactListGetAsyncTask contactListGetAsyncTask= new ContactListGetAsyncTask(this);
        try {
            List<Contact> contacts = contactListGetAsyncTask.execute(contactIds).get();

            MyContactRushRecyclerViewAdapter adapter = new MyContactRushRecyclerViewAdapter(contacts, mRush, this);
            viewContacts.setAdapter(adapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private void configureAutoComplete(){

        CitiesGetAsyncTask citiesGetAsyncTask  = new CitiesGetAsyncTask(this);
        try {
            String[] cities = citiesGetAsyncTask.execute().get();
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);
            txtCity.setAdapter(cityAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



    private void createDatePickerDialog(){

        txtStartDate.setFocusable(false);
        txtEndDate.setFocusable(false);

        txtStartDate.setFocusableInTouchMode(false);
        txtEndDate.setFocusableInTouchMode(false);

        txtStartDate.setFreezesText(true);
        txtEndDate.setFreezesText(true);

        txtStartDate.setSingleLine(true);
        txtEndDate.setSingleLine(true);


        final EditRushActivity act = this;
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final Calendar newDateStart = Calendar.getInstance();

                final DatePickerDialog datePickerDialog2 = new DatePickerDialog(act, null , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog2.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        newDateStart.set(year, monthOfYear, dayOfMonth);
                        txtStartDate.setText(StringUtil.formatDate(newDateStart.getTime()));

                    }
                } );

                datePickerDialog2.show();
            }
        });


        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final Calendar newDateEnd = Calendar.getInstance();

                final DatePickerDialog datePickerDialog2 = new DatePickerDialog(act, null , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog2.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        newDateEnd.set(year, monthOfYear, dayOfMonth);
                        txtEndDate.setText(StringUtil.formatDate(newDateEnd.getTime()));

                    }
                } );

                datePickerDialog2.show();
            }
        });

    }


    @Override
    public void onListFragmentInteraction(Contact item) {
    }

    @Override
    public void onRushModified(Rush rush) {
        saveRush();
    }
}
