package cz.odhlasujto.odhlasujto;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by martin on 23.11.14.
 */


public class FragmentCreate extends Fragment {

    private static final String LOG = MainActivity.class.getSimpleName(); //printing out LOG msgs
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.create_poll, container, false);

        final Button btn = (Button) view.findViewById(R.id.btnAdd);

        final EditText edit = (EditText) view.findViewById(R.id.txtItem);

        final ArrayList<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter;

/*      array for saving items
        public class ListAdapter extends ArrayAdapter {
            public ArrayAdapter<String> adapter;
            public ArrayList<String> list = new ArrayList<String>();

            public ListAdapter(Context context, int textViewResourceId) {
                super(context, textViewResourceId);
            }

            adapter = new ArrayAdapter<String>(FragmentCreate.this, android.R.layout.simple_list_item_1, list);
        }*/

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, list);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list.add(edit.getText().toString());
                edit.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        //setListAdapter(adapter);

        final Button submitPoll = (Button) view.findViewById(R.id.savePollBtn);

        submitPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout fragmentCreateLayout = (FrameLayout) view.findViewById(R.id.fragment_create_poll);
                Log.d(LOG, "Clicked on Submit Poll: declaration");

                fragment = new FragmentVote();
                fragmentCreateLayout.removeAllViews();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, fragment);
                fragmentTransaction.commit();
                Log.d(LOG, "Clicked on Submit Poll: commited");
//                View view = inflater.inflate(R.layout.vote, container, false);
//                Log.d(LOG, "Clicked on Submit POLL Btn");
            }
        });


        return view;

    }

}
