package cz.odhlasujto.odhlasujto;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;

import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.app.AlertDialog;
import android.content.DialogInterface;

// Sherlock ActionBars lib if needed
//import com.actionbarsherlock.view.MenuItem;
//import com.actionbarsherlock.app.SherlockActivity;
//import com.actionbarsherlock.view.MenuInflater;

public class MainActivity extends Activity {

    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOG msgs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
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
                FrameLayout activityMainLayout = (FrameLayout)findViewById(R.id.container);
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
    }

    /**
     * A placeholder fragment containing a simple view.
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

    /**
     * Sets a hyperlink style to the textView.
     */
    public static void makeTextViewHyperlink(TextView tv) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(tv.getText());
        ssb.setSpan(new URLSpan("#"), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb, TextView.BufferType.SPANNABLE);
    }

    /** Adding Hyperlinks to anykind of textViews
    TextView tv = (TextView) findViewById(R.id.aboutLink);
    Utils.makeTextViewHyperlink(tv);
    tv.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
            startActivity(intent);
        }
    });*/

//region HW BUTTONS
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(LOG, "Pressed Back");
    }

//endregion

}