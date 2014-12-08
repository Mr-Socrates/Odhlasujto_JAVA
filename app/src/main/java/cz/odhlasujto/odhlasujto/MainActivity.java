package cz.odhlasujto.odhlasujto;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.net.wifi.WifiManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Context;

import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

//import com.example.sharedpreferences.*;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;

// Sherlock ActionBars lib if needed
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.*;

public class MainActivity extends SherlockFragmentActivity implements TabListener{

    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOGs

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ActionBar.Tab TabCreate,TabVote,TabResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);    // Create Actionbar Tabs
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        //TODO REFACTORING
        // Set Tab Icon and Titles
        TabCreate = actionBar.newTab().setText("Create");
        TabVote = actionBar.newTab().setText("Vote");
        TabResults = actionBar.newTab().setText("Results");

        // Set Tab Listeners
        TabCreate.setTabListener(new SherlockTabListener<FragmentCreate>(R.id.container, this, "Create",
                FragmentCreate.class));
        TabVote.setTabListener(new SherlockTabListener<FragmentVote>(R.id.container, this, "Vote",
                FragmentVote.class));
        TabResults.setTabListener(new SherlockTabListener<FragmentResults>(R.id.container, this, "Results",
                FragmentResults.class));

        // Add tabs to actionbar
        actionBar.addTab(TabCreate);
        actionBar.addTab(TabVote);
        actionBar.addTab(TabResults);
        actionBar.selectTab(TabVote);

/*        Tab tabCreate = actionBar
                .newTab()
                .setText("Create")
                .setIcon(R.drawable.ic_launcher)
                .setTabListener(
                    new TabListener<TabFragment>(R.id., "tab1", TabFragment.class));
                // tabRed.setTabListener(new TabListener<TabFragment>(this, "tab1", TabFragment.class));
        actionBar.addTab(tabCreate);
        actionBar.selectTab(tabCreate);

        Tab tabVote = actionBar
                .newTab()
                .setText("Vote")
                .setTabListener(this);
                // tabBlue.setTabListener(new TabListener<TabFragment>(this, tabBlue, TabFragment.class));
        actionBar.addTab(tabVote);

        Tab tabResults = actionBar
                .newTab()
                .setText("Results")
                .setTabListener(this);
                // tabBlue.setTabListener(new TabListener<TabFragment>(this, tabBlue, TabFragment.class));
        actionBar.addTab(tabResults);
*/

        //region CALLING FRAGMENTS
        /*
        Button createPoll = (Button) findViewById(R.id.create);
        Button vote = (Button) findViewById(R.id.vote);
        Button results = (Button) findViewById(R.id.results);

        createPoll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                    Log.d(LOG, "Clicked on CreatePoll Btn - 0 state");
                FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
                    Log.d(LOG, "Clicked on CreatePoll Btn - declaration");
                activityMainLayout.removeAllViews();
                    Log.d(LOG, "Clicked on CreatePoll Btn - removeallViews");
                fragment = new FragmentCreate();
                    Log.d(LOG, "Clicked on CreatePoll Btn - create Frag");
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                    Log.d(LOG, "Clicked on CreatePoll Btn - transaction");
                fragmentTransaction.add(R.id.container, fragment);
                fragmentTransaction.commit();
                    Log.d(LOG, "Clicked on CreatePoll Btn");
            }
        });

        vote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "Clicked on Vote Btn");
                FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
                activityMainLayout.removeAllViews();
                fragment = new FragmentVote();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, fragment);
                fragmentTransaction.commit();

            }
        });

        results.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
                activityMainLayout.removeAllViews();
                fragment = new FragmentResults();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, fragment);
                fragmentTransaction.commit();
                Log.d(LOG, "Clicked on Results Btn");
            }
        });
        */
//endregion

        //region Wi-Fi Switch
        Switch toggle = (Switch) findViewById(R.id.wifi_switch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleWiFi(true);
                    Toast.makeText(getApplicationContext(), "Wi-Fi Enabled!", Toast.LENGTH_LONG).show();
                } else {
                    toggleWiFi(false);
                    Toast.makeText(getApplicationContext(), "Wi-Fi Disabled!", Toast.LENGTH_LONG).show();
                }
            }
        });//endregion
    }
    //region Wi-Fi Service
    public void toggleWiFi(boolean status) {
        WifiManager wifiManager = (WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        if (status == true && !wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        } else if (status == false && wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }//endregion

    //region MENU
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_about:
                Log.d(LOG, "Clicked on About");
                FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
                activityMainLayout.removeAllViews();

                LayoutInflater inflater = getLayoutInflater();
                activityMainLayout.addView(inflater.inflate(R.layout.about_application, null));
                return true;
            case R.id.action_exit:
                finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
//endregion

    //region IMPLEMENTED SHERLOCK METHODS (possible refactoring https://github.com/codepath/android_guides/wiki/ActionBar-Tabs-with-Fragments )
    @Override
    public void onTabSelected(Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
    }
    @Override
    public void onTabUnselected(Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
    }
    @Override
    public void onTabReselected(Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
    }//endregion

    //region Adding DATA TO ARRAYS
/*
    public void addOptions(View view) {

        *//** Defining the ArrayAdapter to set items to ListView *//*

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        *//** Defining a click event listener for the button "Add" *//*
        EditText edit = (EditText) findViewById(R.id.txtItem);
        list.add(edit.getText().toString());
        edit.setText("");
        adapter.notifyDataSetChanged();

    }*/
//endregion

    //region PLACEHOLDER FRAGMENT
    /**
     * Contains simple views
     */
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
//endregion

    //region HYPERLINKS
    /**
     * Sets a hyperlink style to the textView.
     */
    public static void makeTextViewHyperlink(TextView tv) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(tv.getText());
        ssb.setSpan(new URLSpan("#"), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb, TextView.BufferType.SPANNABLE);
    }

    /**
     * Adding Hyperlinks to anykind of textViews
     * TextView tv = (TextView) findViewById(R.id.aboutLink);
     * Utils.makeTextViewHyperlink(tv);
     * tv.setOnClickListener(new OnClickListener() {
     *
     * @Override public void onClick(View v) {
     * Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
     * startActivity(intent);
     * }
     * });
     */
//endregion

    //region SAVING PREFERENCES
    public static final String MyPREFERENCES = "MyPrefs" ;
//    TODO saving
//endregion

    //region HW BUTTONS
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Haha! :-)", Toast.LENGTH_SHORT).show();
        FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
        activityMainLayout.removeAllViews();

        LayoutInflater inflater = getLayoutInflater();
        activityMainLayout.addView(inflater.inflate(R.layout.activity_main, null));
//                fragment = new PlaceholderFragment();
//                fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.container, fragment);
//                fragmentTransaction.commit();

//        super.onBackPressed();
        Log.d(LOG, "Pressed Back");
    }
//endregion

}