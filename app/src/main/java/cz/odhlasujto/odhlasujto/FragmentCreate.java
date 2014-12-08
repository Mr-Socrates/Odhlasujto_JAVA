package cz.odhlasujto.odhlasujto;

import android.os.Bundle;
import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class FragmentCreate extends SherlockFragment {

    private static final String LOG = MainActivity.class.getSimpleName(); //printing out LOGs

    SherlockFragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.create_poll, container, false);

        final Button btnAddOption = (Button) view.findViewById(R.id.btnAdd);
        final EditText option = (EditText) view.findViewById(R.id.txtItem);

        final ArrayList<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter;

    //region ARRAY FOR SAVING ITEMS
/*      public class ListAdapter extends ArrayAdapter {
            public ArrayAdapter<String> adapter;
            public ArrayList<String> list = new ArrayList<String>();

            public ListAdapter(Context context, int textViewResourceId) {
                super(context, textViewResourceId);
            }

            adapter = new ArrayAdapter<String>(FragmentCreate.this, android.R.layout.simple_list_item_1, list);
        }*/
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, list);
//endregion

    //region ADDING TextViews
        btnAddOption.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list.add(option.getText().toString());
                option.setText("");
                adapter.notifyDataSetChanged();
            }
        });
        //setListAdapter(adapter);
//endregion

    //region SUBMIT Poll
        final Button submitPoll = (Button) view.findViewById(R.id.savePollBtn);
        submitPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////              FrameLayout fragmentCreateLayout = (FrameLayout) view.findViewById(android.R.id.content);
////              fragmentCreateLayout.removeAllViews();
//                Log.d(LOG, "Clicked on Submit Poll: remove all views");
//
//                fragment = new FragmentVote();
//                Log.d(LOG, "Clicked on Submit Poll: called fragment VOTE");
//                fragmentManager = getFragmentManager();
//
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                Log.d(LOG, "Clicked on Submit Poll: commited");
////                View view = inflater.inflate(R.layout.vote, container, false);
////                Log.d(LOG, "Clicked on Submit POLL Btn");
            }
        });
        return view;
//endregion
    }
}
