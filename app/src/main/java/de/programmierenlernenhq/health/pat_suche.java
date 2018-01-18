package de.programmierenlernenhq.health;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.widget.AbsListView;


public class pat_suche extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private HealtMemoDataSource dataSource;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat_suche);

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

        HealthMemo testMemo = new HealthMemo("Birnen","Apfel","test","1","2","3",102);
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + testMemo.toString());

        dataSource = new HealtMemoDataSource(this);

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        HealthMemo shoppingMemo = dataSource.createHealthMemo("Testprodukt", "test","jaja","1","2","3");
        Log.d(LOG_TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(LOG_TAG, "ID: " + shoppingMemo.getId() + ", Inhalt: " + shoppingMemo.toString());

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showAllListEntries();

        initializeContextualActionBar();


    }

    private void initializeContextualActionBar() {

        final ListView healthMemosListView = (ListView) findViewById(R.id.listview_app_memos);
        healthMemosListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        healthMemosListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            int selCount = 0;

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selCount++;
                } else {
                    selCount--;
                }
                String cabTitle = selCount + " " + getString(R.string.cab_checked_string);
                mode.setTitle(cabTitle);
                mode.invalidate();

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getMenuInflater().inflate(R.menu.menu_contextual_action_bar, menu);
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                MenuItem item = menu.findItem(R.id.cab_change);
                if (selCount == 1) {
                    item.setVisible(true);
                } else {
                    item.setVisible(false);
                }

                return true;

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                boolean returnValue = true;
                SparseBooleanArray touchedShoppingMemosPositions = healthMemosListView.getCheckedItemPositions();

                switch (item.getItemId()) {
                    case R.id.cab_delete:
                        for (int i = 0; i < touchedShoppingMemosPositions.size(); i++) {
                            boolean isChecked = touchedShoppingMemosPositions.valueAt(i);
                            if (isChecked) {
                                int postitionInListView = touchedShoppingMemosPositions.keyAt(i);
                                HealthMemo healthMemo = (HealthMemo) healthMemosListView.getItemAtPosition(postitionInListView);
                                Log.d(LOG_TAG, "Position im ListView: " + postitionInListView + " Inhalt: " + healthMemo.toString());
                                dataSource.deleteHealthMemo(healthMemo);
                            }
                        }
                        showAllListEntries();
                        mode.finish();
                        break;

                    case R.id.cab_change:
                        Log.d(LOG_TAG, "Eintrag ändern");
                        for (int i = 0; i < touchedShoppingMemosPositions.size(); i++) {
                            boolean isChecked = touchedShoppingMemosPositions.valueAt(i);
                            if (isChecked) {
                                int postitionInListView = touchedShoppingMemosPositions.keyAt(i);
                                HealthMemo healthMemo = (HealthMemo) healthMemosListView.getItemAtPosition(postitionInListView);
                                Log.d(LOG_TAG, "Position im ListView: " + postitionInListView + " Inhalt: " + healthMemo.toString());

                                AlertDialog editShoppingMemoDialog = createEditHealthMemoDialog(healthMemo);
                                editShoppingMemoDialog.show();
                            }
                        }

                        mode.finish();
                        break;

                    default:
                        returnValue = false;
                        break;
                }
                return returnValue;
            }

            // In dieser Callback-Methode reagieren wir auf das Schließen der CAB
            // Wir setzen den Zähler auf 0 zurück
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selCount = 0;
            }
        });

    }

    private AlertDialog createEditHealthMemoDialog(final HealthMemo healthMemo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogsView = inflater.inflate(R.layout.dialog_edit_health_memo, null);

        final EditText editTextNewVorname = (EditText) dialogsView.findViewById(R.id.editText_new_vorname);
        editTextNewVorname.setText(String.valueOf(healthMemo.getVorname()));

        final EditText editTextNewNachname = (EditText) dialogsView.findViewById(R.id.editText_new_nachname);
        editTextNewNachname.setText(healthMemo.getNachname());

        final EditText editTextNewA = (EditText) dialogsView.findViewById(R.id.editText_new_a);
        editTextNewA.setText(healthMemo.getA1());

        final EditText editTextNewB = (EditText) dialogsView.findViewById(R.id.editText_new_b);
        editTextNewB.setText(healthMemo.getB1());

        final EditText editTextNewC = (EditText) dialogsView.findViewById(R.id.editText_new_c);
        editTextNewC.setText(healthMemo.getC1());

        builder.setView(dialogsView)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.dialog_button_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String vornameString = editTextNewVorname.getText().toString();
                        String nachnameString = editTextNewNachname.getText().toString();
                        String aString = editTextNewA.getText().toString();
                        String bString = editTextNewB.getText().toString();
                        String cString = editTextNewC.getText().toString();

                        if ((TextUtils.isEmpty(vornameString)) || (TextUtils.isEmpty(nachnameString)) || (TextUtils.isEmpty(aString)) || (TextUtils.isEmpty(bString)) || (TextUtils.isEmpty(cString))) {
                            Log.d(LOG_TAG, "Ein Eintrag enthielt keinen Text. Daher Abbruch der Änderung.");
                            return;
                        }


                        // An dieser Stelle schreiben wir die geänderten Daten in die SQLite Datenbank
                        HealthMemo updatedHealthMemo = dataSource.updateHealthMemo(healthMemo.getId(),vornameString,nachnameString,aString,bString,cString );

                        Log.d(LOG_TAG, "Alter Eintrag - ID: " + healthMemo.getId() + " Inhalt: " + healthMemo.toString());
                        Log.d(LOG_TAG, "Neuer Eintrag - ID: " + updatedHealthMemo.getId() + " Inhalt: " + updatedHealthMemo.toString());

                        showAllListEntries();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_button_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    private void showAllListEntries () {


        List<HealthMemo> shoppingMemoList = dataSource.getAllHealthMemos();

        ArrayAdapter<HealthMemo> shoppingMemoArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                shoppingMemoList);

        ListView shoppingMemosListView = (ListView) findViewById(R.id.listview_app_memos);
        shoppingMemosListView.setAdapter(shoppingMemoArrayAdapter);
    }

    public void onStop(){
        super.onStop();
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }


}
