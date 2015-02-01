package cz.odhlasujto.odhlasujto;

import android.content.Intent;
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
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentResults extends SherlockFragment {

    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOGs

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.results, container, false);

        TextView textNAME = (TextView) view.findViewById(R.id.pollNameID);
        TextView textDESC = (TextView) view.findViewById(R.id.pollDescID);

        db db = new db(getActivity().getApplicationContext());
        String dataName = db.getPollName();
        String dataDesc = db.getPollDesc();
        db.close();
        textNAME.setText(dataName);
        textDESC.setText(dataDesc);

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
        return view;
    }
}