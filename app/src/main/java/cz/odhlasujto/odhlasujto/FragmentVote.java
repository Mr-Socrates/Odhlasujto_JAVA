package cz.odhlasujto.odhlasujto;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.vote, container, false);

        db db = new db(getActivity().getApplicationContext());

        final Button submitVote = (Button) view.findViewById(R.id.btnSubmitVote);

        //region GET n SET PollName /\ PollDesc
        TextView textNAME = (TextView) view.findViewById(R.id.pollNameID);
        TextView textDESC = (TextView) view.findViewById(R.id.pollDescID);

        String dataName = db.getPollName();
        String dataDesc = db.getPollDesc();

        textNAME.setText(dataName);
        textDESC.setText(dataDesc);
        //endregion

        //region Plnění Listu Možnostmi a Checkboxy na hlasy
        final ListView textOPTION = (ListView) view.findViewById(R.id.listOption);
        SimpleCursorAdapter dataAdapter;

        //final ArrayList<String> list = new ArrayList<String>();
        //ArrayAdapter<String> adapter;

        Cursor cursor = db.getOptions();

        String[] from = new String[]{db.COL_NAME_OPTION};
        int[] to = new int[]{R.id.name_option};

        dataAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.rimmer, cursor, from, to, 0);
        textOPTION.setAdapter(dataAdapter);

        Log.v("DUMP CURSOR[getOptions]", DatabaseUtils.dumpCursorToString(cursor));
        Toast.makeText(getActivity().getApplicationContext(), "textOPTION:  " +textOPTION, Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity().getApplicationContext(), "COUNT of List ITEMS:  " +textOPTION.getCount(), Toast.LENGTH_SHORT).show();

        Toast.makeText(getActivity().getApplicationContext(), "getCheckedItemPosition:  " +textOPTION.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();

        Toast.makeText(getActivity().getApplicationContext(), "getCheckedItemPosition2:  " +textOPTION.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
        //endregion

        submitVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "txt Option:  " +textOPTION.getCount(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity().getApplicationContext(), "COUNT of List ITEMS:  " +textOPTION.getCount(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity().getApplicationContext(), "getChecked:  " +textOPTION.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
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
            }
        });
        db.close();
        return view;
    }
}