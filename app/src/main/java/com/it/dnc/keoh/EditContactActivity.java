package com.it.dnc.keoh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.it.dnc.keoh.appdata.model.Contact;
import com.it.dnc.keoh.instagram.tasks.InstagramGetProfilePictureTask;
import com.it.dnc.keoh.tasks.ContacGetAsyncTask;
import com.it.dnc.keoh.tasks.ContactSaveAsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EditContactActivity extends AppCompatActivity {


    private TextInputEditText txtName;
    private TextInputEditText txtCity;
    private TextInputEditText txtInstagram;
    private TextInputEditText txtFacebook;
    private ImageView imgContact;

    private Spinner listStates;
    private boolean isEdit = false;
    private Contact contact = null;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ImageButton btnSave = (ImageButton)findViewById(R.id.btnSaveContact);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveContact(view);
            }
        });


        this.txtName = (TextInputEditText) findViewById(R.id.txtContactName);
        this.txtCity= (TextInputEditText) findViewById(R.id.txtContactCity);
        this.txtInstagram  = (TextInputEditText)  findViewById(R.id.txtContactInsta);
        this.imgContact = (ImageView) findViewById(R.id.imgContact);
        this.listStates = (Spinner)findViewById(R.id.listStates);
        this.txtFacebook = (TextInputEditText) findViewById(R.id.txtContactFace);

        this.listStates.setSelection(Arrays.binarySearch(getResources().getStringArray(R.array.states), 0 , 26, "MG"));


        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("CONTACT_ID")){

           Integer contact_id = (Integer)getIntent().getExtras().get("CONTACT_ID");
           isEdit =  true;

           ContacGetAsyncTask asyncTask  = new ContacGetAsyncTask(this);

           try {

               List<Contact> contacts = asyncTask.execute(contact_id).get();
               if(contacts != null){
                   contact = contacts.get(0);
               }
               loadContacts();
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (ExecutionException e) {
               e.printStackTrace();
           }
       }


       //When the focus go out from txtIsnstagram, try to get the profile pic from instagram
        txtInstagram.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    setInstagramProfilePicture();
                }
            }
        });
    }

    private void setInstagramProfilePicture() {
        String instaId = txtInstagram.getText().toString();
        if(instaId.length() > 0){
            InstagramGetProfilePictureTask instagramGetProfilePictureTask = new InstagramGetProfilePictureTask(this.imgContact);
            instagramGetProfilePictureTask.execute(instaId);
        }
    }

    private void loadContacts(){
        this.txtName.setText(contact.getName());
        this.txtCity.setText(contact.getCity());
        this.txtInstagram.setText(contact.getInstagram());

        //Load insta contact image
        File fileImg = new File(Environment.getExternalStorageDirectory() + "/keoh/", String.format(Constants.PREFIX_INSTA_PIC_FILE, contact.getInstagram()));
        if(fileImg.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(fileImg.getPath());
            this.imgContact.setImageBitmap(bitmap);
        }

    }


    private void saveContact(View view){


        Contact contact = validateContact();

        if(contact == null){
            return;
        }



        ContactSaveAsyncTask asyncTask  = new ContactSaveAsyncTask(this, contact);
        asyncTask.execute();

        Toast.makeText(view.getContext(), "Contato salvo...", Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "created");
        setResult(Activity.RESULT_OK ,returnIntent);
        finish();


        /*AppDatabase appDatabase = AppDatabase.getAppDatabase(view.getContext());
        appDatabase.contactDao().insertAll(newContact);*/

    }

    private Contact validateContact() {

        View focusView = null;
        boolean cancel = false;

        txtName.setError(null);
        txtCity.setError(null);

        String name = this.txtName.getText().toString();
        String city = this.txtCity.getText().toString();
        String instagram = this.txtInstagram.getText().toString();


        if (TextUtils.isEmpty(name)) {
            txtName.setError("Campo obrigatório.");
            cancel = true;
            //txtName.setError(getString(R.string.error_invalid_password));
            focusView = txtName;
        }


        if (TextUtils.isEmpty(city)) {
            txtCity.setError("Campo obrigatório.");
            cancel = true;
            //txtName.setError(getString(R.string.error_invalid_password));
            focusView = txtCity;
        }


        if(!cancel){
            Contact newContact = new Contact();
            newContact.setName(name);
            newContact.setCity(city);
            newContact.setInstagram(instagram);
            
            saveContactPictureIntoStorage();
            
            return newContact;
        }

        focusView.requestFocus();
        return null;

    }

    private void saveContactPictureIntoStorage() {

        String fileName = String.format(Constants.PREFIX_INSTA_PIC_FILE, txtInstagram.getText().toString());

        Bitmap bm = ((BitmapDrawable)imgContact.getDrawable()).getBitmap();

        File rootPath = new File(Environment.getExternalStorageDirectory(), "keoh");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        File dataFile = new File(rootPath, fileName + ".jpg");
        FileOutputStream ostream = null;
        try {
            ostream = new FileOutputStream(dataFile,false);

            bm.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
            ostream.flush();
            ostream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
