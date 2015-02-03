package cz.odhlasujto.odhlasujto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import cz.odhlasujto.odhlasujto.Models.Options;
import cz.odhlasujto.odhlasujto.Models.Poll;

public class FragmentCreate extends SherlockFragment {

    private static final String LOG = MainActivity.class.getSimpleName(); //printing out LOGs

    //for future fragment management
    SherlockFragment fragment;
    public android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    private ArrayList<Poll> newPollArrayL;
    private ArrayList<Options> newOptionArrayL;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.create_poll, container, false);
        final Button btnAddOption = (Button) view.findViewById(R.id.btnAdd);
        final EditText option = (EditText) view.findViewById(R.id.txtItem);
        final EditText pollName = (EditText) view.findViewById(R.id.pollName);
        final EditText pollDesc = (EditText) view.findViewById(R.id.pollDesc);

        // Instanciace ArrayListu & deklarace ArrayAdapteru
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter;

        newPollArrayL = new ArrayList<Poll>();
        newOptionArrayL = new ArrayList<Options>();

        //region DYNAMIC ADDING of TextViews to ListView
        btnAddOption.setOnClickListener(new View.OnClickListener() {

            ListView lv = (ListView) view.findViewById(R.id.list);

            @Override
            public void onClick(View v) {

                // This is the array adapter, it takes the context of the activity as a
                // first parameter, the type of list view as a second parameter and your
                // array as a third parameter.

                if (option.getText().toString().isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(FragmentCreate.this.getActivity().getWindow().getContext()).create();
                    alertDialog.setTitle(getString(R.string.chyba));
                    alertDialog.setMessage(getString(R.string.chyba2));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {

                    list.add(option.getText().toString());

                    lv.setAdapter(new ArrayAdapter<String>(
                            getActivity().getApplicationContext(),
                            R.layout.listview_item_create_option,
                            list));

                    String ziskaneOptionName = option.getText().toString();

                    //předání do setterů
                    Options newOption = new Options();
                    newOption.setOptionName(ziskaneOptionName);
                    //dosazení do ArrayListu & do DB
                    newOptionArrayL.add(newOption);
                    db db = new db(getActivity().getApplicationContext());
                    db.insertOption(newOptionArrayL);

                    Toast.makeText(getActivity().getApplicationContext(), "Option:  " + ziskaneOptionName + " created.", Toast.LENGTH_SHORT).show();
                    option.setText("");
                    //TODO adapter.notifyDataSetChanged(); STILL NOT WORKING
                }
            }
        });
        //endregion

        //region SUBMIT Poll
        final Button submitPoll = (Button) view.findViewById(R.id.savePollBtn);
        submitPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pollName.getText().toString().isEmpty() ||
                        pollDesc.getText().toString().isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(FragmentCreate.this.getActivity().getWindow().getContext()).create();
                    alertDialog.setTitle(getString(R.string.chyba));
                    alertDialog.setMessage(getString(R.string.chyba1));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {

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

                    //feedback toasts
                    Toast.makeText(getActivity().getApplicationContext(), "Poll was saved with these values:", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity().getApplicationContext(), "Poll name: " + ziskanePollName + "\n Poll desc: " + ziskanePollDesc, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity().getApplicationContext(), "Options:  " + list, Toast.LENGTH_SHORT).show();
                    //Log.d("newPollArrayL", String.valueOf(newPollArrayL));

                    //region Replacing FragmentCreate with FRAGMENT VOTE (Outdated method)
//                LinearLayout fragmentCreateLayout = (LinearLayout) view.findViewById(R.id.fragment1);
//                fragmentCreateLayout.removeAllViews();
//
//                fragment = new FragmentVote();
//                fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment, getString(R.string.createtab));
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                    //endregion
                    newOptionArrayL.clear();
                    list.clear();
                }
                pollDesc.setText("");
                pollName.setText("");
            }
        });//endregion
        return view;
    }

    //region přidání položek do DB / refactor!
    public void insertPoll(Poll paraPoll) {
        db newDB = new db(getActivity().getApplicationContext());
        SQLiteDatabase sqliteDatabase = newDB.getWritableDatabase();
    } //endregion
}
