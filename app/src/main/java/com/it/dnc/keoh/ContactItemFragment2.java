package com.it.dnc.keoh;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it.dnc.keoh.appdata.model.Contact;
import com.it.dnc.keoh.tasks.ContactGetAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ContactItemFragment2 extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private ContactItemRecyclerViewAdapter contactItemRecyclerViewAdapter;

    public ContactItemFragment2() {
    }

    public static ContactItemFragment2 newInstance() {
        ContactItemFragment2 fragment = new ContactItemFragment2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            List<Contact> list = new ArrayList<>();
            try {
                ContactGetAsyncTask asyncTask = new ContactGetAsyncTask(getActivity());
                list = asyncTask.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            ContactItemRecyclerViewAdapter contactItemRecyclerViewAdapter = new ContactItemRecyclerViewAdapter(list, mListener);

            recyclerView.setAdapter(contactItemRecyclerViewAdapter);
            this.contactItemRecyclerViewAdapter = contactItemRecyclerViewAdapter;
            /*recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));*/

        }
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void clearSelection(){
        contactItemRecyclerViewAdapter.clearSelection();
    }

    public List<Contact> getSelection(){
        return contactItemRecyclerViewAdapter.getSelection();
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Contact item);
        void onMultiFragmentInteraction(Boolean multi, List<Contact> contacts);
        void onInstagramClick(Contact item);
        void onFacebookClick(Contact item);

    }
}
