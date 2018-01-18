package de.programmierenlernenhq.health;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_FROM_GALLERY = 101;
    int columnIndex;
    String attachmentFile;
    Uri URI = null;
    public HealtMemoDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dataSource = new HealtMemoDataSource(this);
        dataSource.open();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        final String mail = "stefan.vetro@arcor.de";
        Button senden = findViewById(R.id.sendButton);





        senden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = mail;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent,"Select Email app"));
            }
        });

        Button btnTXT = (Button) findViewById(R.id.txt);

        btnTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String filename = "myfile.txt";


                List<HealthMemo> test = dataSource.getAllHealthMemos();
                String[] numbers = test.toArray(new String[test.size()]);


                FileOutputStream outputStream ;
                File file;

                try {
                    file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                    outputStream = new FileOutputStream(file);
                    //outputStream = openFileOutput(filename, Context.MODE_APPEND);
                    for (String s : numbers) {
                        outputStream.write(s.getBytes());
                    }
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getApplicationContext().getFilesDir();

            }

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            attachmentFile = cursor.getString(columnIndex);
            Log.e("Attachment Path:", attachmentFile);
            URI = Uri.parse("file://" + attachmentFile);
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(MainActivity.this, pat_suche.class);
        startActivity(intent);
    }

    public void patSuche (View view) {
        Intent intent = new Intent(MainActivity.this, tp_erstellen.class);
        startActivity(intent);
    }

    public void suche(View view) {
        Intent intent = new Intent(MainActivity.this, search.class);

        EditText sucheEingabe = findViewById(R.id.suche);
        String sucheString = sucheEingabe.getText().toString();
        intent.putExtra("eingabe", sucheString);
        startActivity(intent);
    }

}
