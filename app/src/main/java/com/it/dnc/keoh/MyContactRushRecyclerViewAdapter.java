package com.it.dnc.keoh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.dnc.keoh.ContactRushFragment.OnListFragmentInteractionListener;
import com.it.dnc.keoh.appdata.model.Contact;
import com.it.dnc.keoh.appdata.model.ContactsLikes;
import com.it.dnc.keoh.appdata.model.PossibleContacts;
import com.it.dnc.keoh.appdata.model.Rush;
import com.it.dnc.keoh.util.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class MyContactRushRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRushRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mValues;
    private final Rush mRush;
    private final OnListFragmentInteractionListener mListener;

    public MyContactRushRecyclerViewAdapter(List<Contact> items, Rush rush,  OnListFragmentInteractionListener listener) {
        mValues = items;
        mRush = rush;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contactrush, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.txtContactName.setText(mValues.get(position).getName());

        File fileImg = new File(Environment.getExternalStorageDirectory() + "/keoh/", String.format(Constants.PREFIX_INSTA_PIC_FILE, mValues.get(position).getInstagram()));
        if(fileImg.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(fileImg.getPath());
            holder.imgContact.setImageBitmap(bitmap);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                   mListener.onListFragmentInteraction(holder.mItem);
                   expand(holder);
                }
            }
        });

        if(!mRush.getPossibleContacts().equals("")){
            LinkedHashMap likes = JsonUtil.jsonToObject(mRush.getPossibleContacts(), LinkedHashMap.class);
            Contact contact = mValues.get(position);

            if(likes.containsKey(contact.getId().toString())){
                holder.likeCount.setText(likes.get(contact.getId().toString()).toString());
            }
        }


        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeAndComment(mValues.get(position), null, true);
                updateLike(mValues.get(position), holder);
            }
        });

        holder.btnUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unLike(mValues.get(position));
                updateLike(mValues.get(position), holder);
            }
        });

        holder.btnSalveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeAndComment(mValues.get(position), holder.txtCommment.getText().toString(), false);
            }
        });

        updateLike(mValues.get(position), holder);


        ViewGroup.LayoutParams layoutParams = holder.mView.getLayoutParams();
        layoutParams.height = 110;

    }



    private void expand(final ViewHolder holder){
        if(!holder.expanded){
            ViewGroup.LayoutParams layoutParams = holder.mView.getLayoutParams();
            layoutParams.height = 600;
            holder.mView.setLayoutParams(layoutParams);
            holder.txtCommment.setVisibility(View.VISIBLE);
            holder.expanded = true;

        }else{
            ViewGroup.LayoutParams layoutParams = holder.mView.getLayoutParams();
            layoutParams.height = 110;
            holder.mView.setLayoutParams(layoutParams);
            holder.txtCommment.setVisibility(View.INVISIBLE);
            holder.expanded = false;
        }
    }


    private void likeAndComment(Contact contact, String comment, boolean appendLike){
        if(mRush.getPossibleContacts().equals("")){

            PossibleContacts possibleContacts = new PossibleContacts();
            possibleContacts.setIds(Arrays.asList(contact.getId()));

            ArrayList<ContactsLikes> contactsLikes = new ArrayList<>();
            ContactsLikes like = new ContactsLikes();
            contactsLikes.add(like);

            if(appendLike){
                like.setLikes(1);
            }else{
                like.setLikes(0);
            }

            if(comment != null && !comment.isEmpty()){
                like.setComments(comment);
            }

            like.setId(contact.getId());

            possibleContacts.setContactsLikes(contactsLikes);

            mRush.setPossibleContacts(JsonUtil.objectToJson(possibleContacts));

        }else{
            PossibleContacts possibleContacts = JsonUtil.jsonToObject(mRush.getPossibleContacts(), PossibleContacts.class);

            if(possibleContacts.getIds().contains(contact.getId())){

                ContactsLikes byId = possibleContacts.getById(contact.getId());

                if(byId != null){

                    if(appendLike){
                        byId.setLikes(byId.getLikes() + 1);
                    }

                    if(comment != null && !comment.isEmpty()){
                        byId.setComments(comment);
                    }
                }
                mRush.setPossibleContacts(JsonUtil.objectToJson(possibleContacts));
            }else{

                List<ContactsLikes> contactsLikes = possibleContacts.getContactsLikes();
                ContactsLikes like = new ContactsLikes();
                contactsLikes.add(like);


                if(appendLike){
                    like.setLikes(1);
                }else{
                    like.setLikes(0);
                }

                if(comment != null && !comment.isEmpty()){
                    like.setComments(comment);
                }

                like.setId(contact.getId());

                possibleContacts.setContactsLikes(contactsLikes);
                possibleContacts.addId(contact.getId());

                mRush.setPossibleContacts(JsonUtil.objectToJson(possibleContacts));
            }
        }

        mListener.onRushModified(null);

    }

    private void unLike(Contact contact){


    }

    private void updateLike(Contact contact, final ViewHolder holder){

        if(!mRush.getPossibleContacts().equals("")){
            PossibleContacts possibleContacts = JsonUtil.jsonToObject(mRush.getPossibleContacts(), PossibleContacts.class);
            ContactsLikes byId = possibleContacts.getById(contact.getId());
            holder.likeCount.setText(byId.getLikes().toString());
            holder.txtCommment.setText(byId.getComments());
        }
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Contact mItem;

        public TextView txtContactName;
        public ImageView imgContact;
        public ImageView btnLike;
        public ImageView btnUnlike;
        public TextView likeCount;
        public EditText txtCommment;
        public boolean expanded = false;
        public Button btnSalveComment;


        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtContactName = mView.findViewById(R.id.txtContactName);
            imgContact = mView.findViewById(R.id.imgContact);
            btnLike = mView.findViewById(R.id.btnLike);
            btnUnlike= mView.findViewById(R.id.btnUnlike);
            likeCount = mView.findViewById(R.id.likeCount);
            txtCommment =  mView.findViewById(R.id.txtComment);
            btnSalveComment = mView.findViewById(R.id.btnSaveComment);

        }

    }
}
