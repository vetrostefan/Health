package de.programmierenlernenhq.health;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class search extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private HealtMemoDataSource dataSource;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        if(getIntent().hasExtra("eingabe") == true) {
            String wert = getIntent().getExtras().getString("eingabe");
            Toast toast = Toast.makeText(this, "" + wert, Toast.LENGTH_SHORT);
            toast.show();


            dataSource = new HealtMemoDataSource(this);

            dataSource.open();

            showAllListEntries(wert);


        }
    }



    private void showAllListEntries (String wert) {


        List<HealthMemo> shoppingMemoList = dataSource.getSearchHealthMemos(wert);

        ArrayAdapter<HealthMemo> shoppingMemoArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                shoppingMemoList);

        ListView shoppingMemosListView = (ListView) findViewById(R.id.listview_search_memos);
        shoppingMemosListView.setAdapter(shoppingMemoArrayAdapter);
    }

    public void onStop(){
        super.onStop();
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }


}