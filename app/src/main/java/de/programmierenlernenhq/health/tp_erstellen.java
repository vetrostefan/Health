package de.programmierenlernenhq.health;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class tp_erstellen extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private HealtMemoDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp_erstellen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");
        dataSource = new HealtMemoDataSource(this);

        activateAddButton();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    private void activateAddButton() {
        Button buttonAddProduct = (Button) findViewById(R.id.speicherButton);
        final EditText editTextVorname = (EditText) findViewById(R.id.vorname);
        final EditText editTextNachname = (EditText) findViewById(R.id.nachname);
        final EditText editTextGerät = (EditText) findViewById(R.id.gerät);
        final EditText editTexta1 = (EditText) findViewById(R.id.a1);
        final EditText editTextb1 = (EditText) findViewById(R.id.b1);
        final EditText editTextc1 = (EditText) findViewById(R.id.c1);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vornameString = editTextVorname.getText().toString();
                String nachnameString = editTextNachname.getText().toString();
                String gerätString = editTextGerät.getText().toString();
                String a1String = editTexta1.getText().toString();
                String b1String = editTextb1.getText().toString();
                String c1String = editTextc1.getText().toString();

                if(TextUtils.isEmpty(vornameString)) {
                    editTextVorname.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if(TextUtils.isEmpty(nachnameString)) {
                    editTextNachname.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if(TextUtils.isEmpty(gerätString)) {
                    editTextGerät.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if(TextUtils.isEmpty(a1String)) {
                    editTexta1.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if(TextUtils.isEmpty(b1String)) {
                    editTextb1.setError(getString(R.string.editText_errorMessage));
                    return;
                }

                if(TextUtils.isEmpty(c1String)) {
                    editTextc1.setError(getString(R.string.editText_errorMessage));
                    return;
                }



                editTextVorname.setText("");
                editTextNachname.setText("");
                editTextGerät.setText("");
                editTexta1.setText("");
                editTextb1.setText("");
                editTextc1.setText("");

                dataSource.createHealthMemo(vornameString,nachnameString,gerätString,a1String,b1String,c1String);

                InputMethodManager inputMethodManager;
                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });

    }

}
