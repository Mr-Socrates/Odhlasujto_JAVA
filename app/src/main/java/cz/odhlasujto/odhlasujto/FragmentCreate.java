package cz.odhlasujto.odhlasujto;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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

    private ArrayList<Poll> newPollArrayL;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.create_poll, container, false);

        final Button btnAddOption = (Button) view.findViewById(R.id.btnAdd);
        final EditText option = (EditText) view.findViewById(R.id.txtItem);
        final EditText pollName = (EditText) view.findViewById(R.id.pollName);
        final EditText pollDesc = (EditText) view.findViewById(R.id.pollDesc);

        newPollArrayL = new ArrayList<Poll>();

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

                //něco zkoušim dát do databáze
                String ziskanePollName = pollName.getText().toString();
                String ziskanePollDesc = pollDesc.getText().toString();

                //předání do setterů
                Poll newPoll = new Poll();
                newPoll.setPollName(ziskanePollName);
                newPoll.setPollDesc(ziskanePollDesc);

                //dosazení do ArrayListu
                newPollArrayL.add(newPoll);
                db db = new db(getActivity().getApplicationContext());
                db.insertPolls(newPollArrayL);

            }
        });
        return view;
    }

//endregion

        //region přidání položek

//        public void insertPoll(Poll paraPoll){
//        db newDB = new db(getActivity().getApplicationContext());
//        SQLiteDatabase sqliteDatabase = newDB.getWritableDatabase();




    }

        //endregion
    //region ARRAY FOR SAVING ITEMS
/*    final ArrayList<String> list = new ArrayList<String>();
    final ArrayAdapter<String> adapter;

    public class ListAdapter extends ArrayAdapter {
        public ArrayAdapter<String> adapter;
        public ArrayList<String> list = new ArrayList<String>();

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }
    }
    adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, list);
//endregion

    //region ADDING TextViews
    @Override
    btnAddOption.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            list.add(option.getText().toString());
            option.setText("");
            adapter.notifyDataSetChanged();
        }
    });
    //setListAdapter(adapter);*/
//endregion

