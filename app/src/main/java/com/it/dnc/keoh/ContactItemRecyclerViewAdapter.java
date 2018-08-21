package com.it.dnc.keoh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.dnc.keoh.ContactItemFragment2.OnListFragmentInteractionListener;
import com.it.dnc.keoh.appdata.model.Contact;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ContactItemRecyclerViewAdapter extends RecyclerView.Adapter<ContactItemRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mValues;
    private final List<Contact> selectedValues;
    private final List<ContactItemRecyclerViewAdapter.ViewHolder> vAdapters;

    private final OnListFragmentInteractionListener mListener;

    public ContactItemRecyclerViewAdapter(List<Contact> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        selectedValues = new ArrayList<>();
        vAdapters = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()) .inflate(R.layout.fragment_contactitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Contact item) {

                selectedValues.add(item);
                mListener.onMultiFragmentInteraction(true, selectedValues);
            }

            @Override
            public void onItemUnselected(Contact item) {
                selectedValues.remove(item);

                if(selectedValues.size() == 0){
                    mListener.onMultiFragmentInteraction(false, null);
                }

            }
        });
        vAdapters.add(viewHolder);
        return  viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txtContactName.setText(mValues.get(position).getName());
        holder.txtContactCity.setText(String.format("%s, %s", mValues.get(position).getCity(), "MG" ));

        File fileImg = new File(Environment.getExternalStorageDirectory() + "/keoh/", String.format(Constants.PREFIX_INSTA_PIC_FILE, mValues.get(position).getInstagram()));
        if(fileImg.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(fileImg.getPath());
            holder.imgContact.setImageBitmap(bitmap);
        }

        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                    if(selectedValues.size() == 0){
                        mListener.onListFragmentInteraction(holder.mItem);
                    }else{
                        if(holder.isChecked()){
                            holder.setChecked(false);
                            selectedValues.remove(holder.mItem);
                        }else{
                            holder.setChecked(true);
                            selectedValues.add(holder.mItem);
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void clearSelection(){
        for(ViewHolder view : vAdapters){
            if(view.isChecked()){
                view.setChecked(false);
            }
        }
        this.selectedValues.clear();
    }

    public List<Contact> getSelection(){
        return this.selectedValues;
    }

    /***
     * View Holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
       // public final TextView mContentView;

        public Contact mItem;

        public final TextView txtContactName;
        public final TextView txtContactCity;
        public final ImageView imgContact;

        OnItemSelectedListener itemSelectedListener;


        public ViewHolder(View view, OnItemSelectedListener listener) {

            super(view);
            mView = view;
            itemSelectedListener = listener;

            txtContactName = (TextView) view.findViewById(R.id.txtContactName);
            txtContactCity = (TextView) view.findViewById(R.id.txtContactCity);
            imgContact = (ImageView) view.findViewById(R.id.imgContact);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (mItem.isSelected()) {
                        setChecked(false);
                        itemSelectedListener.onItemUnselected(mItem);
                    } else {
                        setChecked(true);
                        itemSelectedListener.onItemSelected(mItem);
                    }

                    return true;
                }
            });

            // mIdView = (TextView) view.findViewById(R.id.)s;
            //mContentView = (TextView) view.findViewById(R.id.content);
        }


        public void setChecked(boolean value) {
            if (value) {
                mView.setBackground(ContextCompat.getDrawable(mView.getContext(), R.drawable.selected_card_rounds));
            } else {
                itemSelectedListener.onItemUnselected(mItem);
                mView.setBackground(ContextCompat.getDrawable(mView.getContext(), R.drawable.card_rounds));
            }
            mItem.setSelected(value);
            mItem.setChecked(value);
            //txtContactName.setche .setChecked(value);
        }

        public boolean isChecked(){
            return mItem.isChecked();
        }


        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Contact item);
        void onItemUnselected(Contact item);
    }
}
