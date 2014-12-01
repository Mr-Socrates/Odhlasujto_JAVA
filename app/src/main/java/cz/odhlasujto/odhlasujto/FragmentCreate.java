package cz.odhlasujto.odhlasujto;

import android.app.Fragment;
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
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by martin on 23.11.14.
 */


public class FragmentCreate extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.create_poll, container, false);

        final Button btn = (Button) view.findViewById(R.id.btnAdd);

        final EditText edit = (EditText) view.findViewById(R.id.txtItem);

        final ArrayList<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter;




/*        public class ListAdapter extends ArrayAdapter {
            public ArrayAdapter<String> adapter;
            public ArrayList<String> list = new ArrayList<String>();

            public ListAdapter(Context context, int textViewResourceId) {
                super(context, textViewResourceId);
                // TODO Auto-generated constructor stub
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

        return view;

    }

}
