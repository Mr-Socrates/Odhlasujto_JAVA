package cz.odhlasujto.odhlasujto;

import android.os.Bundle;
import android.os.Handler;  //used in Menus
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.FrameLayout;
import android.widget.TextView;

import android.text.style.URLSpan;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.net.wifi.WifiManager;

    import android.widget.ArrayAdapter;
    import android.os.AsyncTask;
    import org.json.JSONObject;
    import org.json.JSONException;
    import org.json.JSONArray;
    import java.io.IOException;
    import java.io.InputStream;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.io.InputStreamReader;
    import java.io.Reader;
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.Comparator;

// SharedPreferences
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//Animations -9OLD Androids
import com.nineoldandroids.animation.AnimatorSet;

// Sherlock ActionBars lib
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

public class MainActivity extends SherlockFragmentActivity implements TabListener {

    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOGs
    public static final String PREFS_NAME = "MyPrefsFile";
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private final Handler handler = new Handler();
    ActionBar.Tab TabCreate, TabVote, TabResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      //TODO PLACEHOLDER FRAGMENT - find out if its needed
//      if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        db db = new db(this);
        db.getWritableDatabase().delete("polls", null, null);

        Button btnDownload = (Button) findViewById(R.id.btnDownload);
        /*btnDownload.setOnClickListener (new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getString(R.string.loading), Toast.LENGTH_SHORT).show();
                Log.d(LOG, "Clicked on CreatePoll Btn - after");
           //     new DownloadWebpageTask().execute(URL);
            }
        });*/

        //region ACTION BAR
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);    // Create Actionbar Tabs
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

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
        actionBar.selectTab(TabCreate);
        //endregion

        //region CALLING FRAGMENTS from OnClicks (redundant)
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

    }

    //region OPTIONS MENU | Canvas Menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getSupportMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//            case R.id.action_about:
//                Log.d(LOG, "Clicked on About");
//                FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
//                activityMainLayout.removeAllViews();
//
//                LayoutInflater inflater = getLayoutInflater();
//                activityMainLayout.addView(inflater.inflate(R.layout.about_application, null));
//                return true;
//            case R.id.action_exit:
//                finish();
//                System.exit(0);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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

    //region READING /\ Adding DATA TO ARRAYS
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
//            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            return readIt(is, 2000000);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private void parseJson(String result) {
        JSONObject jsonObject;
        ArrayList<Poll> data = new ArrayList<Poll>();
        JSONArray jarray = new JSONArray();
        try {
            jsonObject = new JSONObject(result);
            jarray = jsonObject.getJSONArray("studenti");
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, "Chyba načítání JSON", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < jarray.length(); i++) {
            try {
                JSONObject object = jarray.getJSONObject(i);
                Poll polls = new Poll();
                //polls.setJmeno(object.getString("jmeno"));
                //polls.setPrijmeni(object.getString("prijmeni"));
                //polls.setId(Integer.valueOf(object.getString("id")));
                data.add(polls);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //region DB /\ SORTING
        db db = new db(this);
        // jelikož nám zbyli nejspíš studenti z minula
        db.getWritableDatabase().delete("polls", null, null);

        db.insertPolls(data);

        // podle čeho řadíme?
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String by = sharedPref.getString("radit_list", "jmeno");
//        Ukládáme do db?
        Boolean save = sharedPref.getBoolean("db_save", false);

        // setřídíme/vybereme setříděná data
        ArrayList<Poll> dataOrdered; //= new ArrayList<Student>(); --> pokud byste chtěli kolekci sami plnit (dataOrdered.add...)
        if (!save) dataOrdered = order(data, by);
        else {
            dataOrdered = db.getPolls(by);
        }
        db.close();// neplýtváme prostředky
        Adapter adapter = new Adapter(this, dataOrdered);

        ListView v = (ListView) findViewById(R.id.listView);
        v.setAdapter(adapter);
    }

    // SORTED ARRAY
    private ArrayList<Poll> order(ArrayList<Poll> data, final String by) {
        Collections.sort(data, new Comparator<Poll>() {
            @Override
            public int compare(Poll s1, Poll s2) {
    // TODO JEN NAMÁTKOU - SORT BUDE POTOM DLE SUMY A ID
                if (by.equals("pollDesc")) return s1.getPollDesc().compareTo(s2.getPollDesc());
                else if (by.equals("pollName")) return s1.getPollName().compareTo(s2.getPollName());
                else /*if (by.equals("id"))*/ return (s1.getPollId() - s2.getPollId());
            }
        });
        return data;
    }
    //endregion

    // TODO DYNAMIC ADDING of OPTIONS / duplicated from FragmentCreate
    public void addOptions(View view) {

        /* Defining the ArrayAdapter to set items to ListView */
/*
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        /** Defining a click event listener for the button "Add" *//*
        EditText edit = (EditText) findViewById(R.id.txtItem);
        list.add(edit.getText().toString());
        edit.setText("");
        adapter.notifyDataSetChanged();
*/
    }
    //endregion

    //region DOWNLOAD WEBPAGE / AsyncTask
    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
    }//endregion

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

    //region SAVING WIFI PREFERENCES on change and onStop()

//    // get a set WiFi Settings
//    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//    Boolean spWiFiSetting = sharedPref.getBoolean("db_WiFi", false);


//    @Override
//    protected void onStop(){
//        super.onStop();
//
//        // We need an Editor object to make preference changes.
//        // All objects are from android.context.Context
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putBoolean("PREFS_NAME", status);
//
//        // Commit the edits!
//        editor.commit();
//    }//endregion

    //region HW BUTTONS
    /*
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
    }*/
    //endregion
}