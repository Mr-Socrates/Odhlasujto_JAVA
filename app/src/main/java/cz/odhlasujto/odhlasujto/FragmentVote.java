package cz.odhlasujto.odhlasujto;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FragmentVote extends SherlockFragment {

    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOGs

    SherlockFragment fragment;
    public android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    String options = "options";
    private List optionNames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.vote, container, false);

        final Button submitVote = (Button) view.findViewById(R.id.btnSubmitVote);
        TextView textNAME = (TextView) view.findViewById(R.id.pollNameID);
        TextView textDESC = (TextView) view.findViewById(R.id.pollDescID);
        ListView textOPTION = (ListView) view.findViewById(R.id.listOption);
        SimpleCursorAdapter dataAdapter;


        optionNames = new ArrayList();

        db db = new db(getActivity().getApplicationContext());
        dboptions dboptions = new dboptions(getActivity().getApplicationContext());

        String dataName = db.getPollName();
        String dataDesc = db.getPollDesc();
        db.close();
        textNAME.setText(dataName);
        textDESC.setText(dataDesc);

        final ArrayList<String> list = new ArrayList<String>();
        //ArrayAdapter<String> adapter;

        Cursor cursor = dboptions.getOptions();

        String from [] = new String[]{dboptions.ID};
        int to [] = new int[] {R.id.textT};

        while(cursor.moveToNext()) {
            dataAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.rimmer, cursor, from, to, 0);
            textOPTION.setAdapter(dataAdapter);
        }
        while(cursor.moveToNext()) {

            Log.d(LOG, cursor.getString(cursor.getColumnIndex("optionName")));
        }


//        submitVote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////              FrameLayout fragmentCreateLayout = (FrameLayout) view.findViewById(android.R.id.content);
//                Log.d(LOG, "Clicked on Submit Poll: declaration");
//
////                fragmentCreateLayout.removeAllViews();
//                Log.d(LOG, "Clicked on Submit Poll: remove all views");
//                fragment = new FragmentResults();
//                Log.d(LOG, "Clicked on Submit Poll: called fragment VOTE");
//                fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                Log.d(LOG, "Clicked on Submit Poll: commited");
////                View view = inflater.inflate(R.layout.vote, container, false);
////                Log.d(LOG, "Clicked on Submit POLL Btn");
//            }
//        });

        return view;
    }
}