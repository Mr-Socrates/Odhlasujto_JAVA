package cz.odhlasujto.odhlasujto;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentResults extends SherlockFragment {

    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOGs

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.results, container, false);
        db db = new db(getActivity().getApplicationContext());

        //region GET n SET PollName /\ PollDesc
        TextView textNAME = (TextView) view.findViewById(R.id.pollNameID);
        TextView textDESC = (TextView) view.findViewById(R.id.pollDescID);

        String dataName = db.getPollName();
        String dataDesc = db.getPollDesc();

        textNAME.setText(dataName);
        textDESC.setText(dataDesc);
        //endregion

        //region FILLING OPTIONS LSIT VIEW
        final ListView optionsLV = (ListView) view.findViewById(R.id.listResults);
        SimpleCursorAdapter dataAdapter;

        Cursor cursor = db.getOptions();

        String[] from = new String[]{db.COL_NAME_OPTION};
        int[] to = new int[]{R.id.name_option};

        dataAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.rimmer_results, cursor, from, to, 0);
        optionsLV.setAdapter(dataAdapter);

        Log.v("DUMP CURSOR[getOptions]", DatabaseUtils.dumpCursorToString(cursor));
        //Toast.makeText(getActivity().getApplicationContext(), "getCheckedItemPosition:  " +textOPTION.getCheckedItemPositions(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity().getApplicationContext(), "CheckedItem COUNT:  " +textOPTION.getCheckedItemCount(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity().getApplicationContext(), "CheckedItem IDs:  " +textOPTION.getCheckedItemIds(), Toast.LENGTH_SHORT).show();
        //endregion

        //region AndroidDatabaseMANAGER
        Button button =(Button) view.findViewById(R.id.SQLVIEWER);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(getActivity(),AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });//endregion

//        final Button btnBack = (Button) view.findViewById(R.id.btnBack);
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FrameLayout activityMainLayout = (FrameLayout) view.findViewById(R.id.container);
////                activityMainLayout.removeAllViews();
//
//                LayoutInflater inflater = getActivity().getLayoutInflater();
//                activityMainLayout.addView(inflater.inflate(R.layout.activity_main, null));
//            }
//        });
        db.close();
        return view;
    }
}