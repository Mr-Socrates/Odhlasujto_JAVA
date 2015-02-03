package cz.odhlasujto.odhlasujto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;  //used in Menus
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import android.text.style.URLSpan;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

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

//Animations -9OLD Androids

// Sherlock ActionBars lib
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;

import cz.odhlasujto.odhlasujto.Adapters.Adapter;
import cz.odhlasujto.odhlasujto.Models.Poll;

public class MainActivity extends SherlockFragmentActivity implements TabListener {

    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOGs
    public static final String PREFS_NAME = "MyPrefsFile";

    private final Handler handler = new Handler();
    ActionBar.Tab TabCreate, TabVote, TabResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //region VALIDACE & Inernet connection status
        InternetStatus.setContext(this.getApplicationContext());
        if (InternetStatus.isOnline()) {
            setContentView(R.layout.activity_main);
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(getString(R.string.oznameni));
            alertDialog.setMessage(getString(R.string.oznameni1));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            setContentView(R.layout.activity_main);
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(getString(R.string.oznameni));
            alertDialog.setMessage(getString(R.string.oznameni2));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }//endregion

      //region Retrieving onSaveInstanceState(Bundle) - only for dynamic data
      if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }//endregion

        //VYMAZÁNÍ DB pro zkoušku ON DELETE CASCADE
        db db = new db(this);
        //db.getWritableDatabase().delete("polls", null, null);

        //region ACTION BAR
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);    // Create Actionbar Tabs
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        // Set Tab Icon and Titles
        TabCreate = actionBar.newTab().setText(getString(R.string.createtab));
        TabVote = actionBar.newTab().setText(getString(R.string.votetab));
        TabResults = actionBar.newTab().setText(getString(R.string.resultstab));

        // Set Tab Listeners
        TabCreate.setTabListener(new SherlockTabListener<FragmentCreate>(R.id.container, this, getString(R.string.createtab),
                FragmentCreate.class));
        TabVote.setTabListener(new SherlockTabListener<FragmentVote>(R.id.container, this, getString(R.string.votetab),
                FragmentVote.class));
        TabResults.setTabListener(new SherlockTabListener<FragmentResults>(R.id.container, this, getString(R.string.resultstab),
                FragmentResults.class));

        // Add tabs to actionbar
        actionBar.addTab(TabCreate);
        actionBar.addTab(TabVote);
        actionBar.addTab(TabResults);
        actionBar.selectTab(TabCreate);

        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        //endregion

        //region CALLING FRAGMENTS from OnClicks (redundant)
//        Button createPoll = (Button) findViewById(R.id.btnSubmitVote);
//
//        createPoll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(LOG, "CLICKED ON BTN VOTE - 0");
//                LinearLayout fragmentCreateLayout = (LinearLayout) findViewById(R.id.fragment1);
//                fragmentCreateLayout.removeAllViews();
//
//                fragment = new FragmentVote();
//                fragmentManager = getSupportFragmentManager();
//                Log.d(LOG, "CLICKED ON BTN VOTE - 1");
//                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment, getString(R.string.createtab));
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });

//        vote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(LOG, "Clicked on Vote Btn");
//                FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
//                activityMainLayout.removeAllViews();
//                fragment = new FragmentVote();
//                fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.commit();
////            }
//        });
//
//        results.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
//                activityMainLayout.removeAllViews();
//                fragment = new FragmentResults();
//                fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.container, fragment);
//                fragmentTransaction.commit();
//                Log.d(LOG, "Clicked on Results Btn");
//            }
//        });

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
//    }//endregion

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

    //region READING /\ Adding DATA from JSON TO ARRAYS, DB
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

        ListView v = (ListView) findViewById(R.id.listResults);
        v.setAdapter(adapter);
    }

    // SORTED ARRAY
    // SORT MÚŽE BÝT POTOM DLE SUMY A ID (relevantní?)
    private ArrayList<Poll> order(ArrayList<Poll> data, final String by) {
        Collections.sort(data, new Comparator<Poll>() {
            @Override
            public int compare(Poll s1, Poll s2) {
                if (by.equals("pollDesc")) return s1.getPollDesc().compareTo(s2.getPollDesc());
                else return s1.getPollName().compareTo(s2.getPollName());
                //else /*if (by.equals("id"))*/ return (s1.getPollId() - s2.getPollId());
            }
        });
        return data;
    }//endregion

    //region DOWNLOAD TASK w/ AsyncTask
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
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }//endregion

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
     * Adding Hyperlinks to any kind of textViews
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

    //region SAVING PREFERENCES on change and onStop()

      // get a set WiFi Settings
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

    //region LIFE-CYCLE region
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("param", value);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    //endregion

    //region HW BUTTONS
    /*
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Pressed BACK", Toast.LENGTH_SHORT).show();
        FrameLayout activityMainLayout = (FrameLayout) findViewById(R.id.container);
        activityMainLayout.removeAllViews();

        LayoutInflater inflater = getLayoutInflater();
        activityMainLayout.addView(inflater.inflate(R.layout.activity_main, null));
                fragment = new PlaceholderFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, fragment);
                fragmentTransaction.commit();

        super.onBackPressed();
        Log.d(LOG, "Pressed Back");
    }*/
    //endregion
}