package cz.odhlasujto.cviceni_3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        final EditText editText4 = (EditText) findViewById(R.id.editText4);
        final EditText editText3 = (EditText) findViewById(R.id.editText3);
        Button button2 = (Button) findViewById(R.id.button2);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton);
        final RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jmeno = editText.getText().toString();
                String email = editText4.getText().toString().trim();
                String telefon = editText3.getText().toString();

                //name validation
                if (jmeno.equalsIgnoreCase("")) {
                    editText.setError("Name is required");
                }

                //email validation
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                CharSequence inputStr = email;
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(inputStr);
                if (!matcher.matches()) {
                    editText4.setError("Email format is invalid");
                }

                //phone validation
                if (!(telefon != null && telefon.matches("00420[0-9]{9}"))) {
                    editText3.setError("Telephone format is not international '00420[0-9]{9}'");
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                spinner.setSelection(0);
                radioButton.setChecked(false);
                radioButton2.setChecked(false);
                checkBox.setChecked(false);
                toggleButton.setChecked(false);
            }
        });
        Button buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
