package com.it.dnc.keoh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.it.dnc.keoh.RushItemFragment.OnListFragmentInteractionListener;
import com.it.dnc.keoh.appdata.model.Rush;
import com.it.dnc.keoh.util.CollectionUtil;
import com.it.dnc.keoh.util.StringUtil;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Rush} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRushItemRecyclerViewAdapter extends RecyclerView.Adapter<MyRushItemRecyclerViewAdapter.ViewHolder> {

    private final List<Rush> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyRushItemRecyclerViewAdapter(List<Rush> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_rushitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txtCity.setText(mValues.get(position).getCity());
        holder.txtStartDate.setText(StringUtil.formatDate(mValues.get(position).getDtStart()));
        holder.txtEndDate.setText(StringUtil.formatDate(mValues.get(position).getDtEnd()));
        holder.txtContactCount.setText(String.format("Contatos no rush: %s", String.valueOf(CollectionUtil.getCountOnStrList(mValues.get(position).getContactList())) ));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (null != mListener) {
                mListener.onListFragmentInteraction(holder.mItem);
            }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView txtStartDate;
        public final TextView txtEndDate;
        public final TextView txtContactCount;
        public final TextView txtCity;
        public Rush mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtStartDate = (TextView) view.findViewById(R.id.txtStartDate);
            txtEndDate = (TextView) view.findViewById(R.id.txtEndDate);
            txtContactCount = (TextView) view.findViewById(R.id.txtContactCount);
            txtCity = (TextView) view.findViewById(R.id.txtCity);


        }
    }
}
