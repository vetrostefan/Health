package de.programmierenlernenhq.health;

/**
 * Created by Vetro on 08.01.2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;



public class HealtMemoDataSource {

    private static final String LOG_TAG = HealtMemoDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private HealthMemoDbHelper dbHelper;

    private String[] columns = {
            HealthMemoDbHelper.COLUMN_ID,
            HealthMemoDbHelper.COLUMN_VORNAME,
            HealthMemoDbHelper.COLUMN_NACHNAME,
            HealthMemoDbHelper.COLUMN_GERÄT1,
            HealthMemoDbHelper.COLUMN_A1,
            HealthMemoDbHelper.COLUMN_B1,
            HealthMemoDbHelper.COLUMN_C1
    };


    public HealtMemoDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new HealthMemoDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public HealthMemo createHealthMemo(String vorname, String nachname, String gerät1, String a1, String b1, String c1) {
        ContentValues values = new ContentValues();
        values.put(HealthMemoDbHelper.COLUMN_VORNAME, vorname);
        values.put(HealthMemoDbHelper.COLUMN_NACHNAME, nachname);
        values.put(HealthMemoDbHelper.COLUMN_GERÄT1, gerät1);
        values.put(HealthMemoDbHelper.COLUMN_A1, a1);
        values.put(HealthMemoDbHelper.COLUMN_B1, b1);
        values.put(HealthMemoDbHelper.COLUMN_C1, c1);

        long insertId = database.insert(HealthMemoDbHelper.TABLE_HEALTH_LIST, null, values);

        Cursor cursor = database.query(HealthMemoDbHelper.TABLE_HEALTH_LIST,
                columns, HealthMemoDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        HealthMemo appMemo = cursorToAppMemo(cursor);
        cursor.close();

        return appMemo;
    }

    public void deleteHealthMemo(HealthMemo healthMemo) {
        long id = healthMemo.getId();

        database.delete(HealthMemoDbHelper.TABLE_HEALTH_LIST,
                HealthMemoDbHelper.COLUMN_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id + " Inhalt: " + healthMemo.toString());
    }

    public HealthMemo updateHealthMemo(long id, String newVorname, String newNachname, String newA, String newB, String newC) {
        ContentValues values = new ContentValues();
        values.put(HealthMemoDbHelper.COLUMN_VORNAME, newVorname);
        values.put(HealthMemoDbHelper.COLUMN_NACHNAME, newNachname);
        values.put(HealthMemoDbHelper.COLUMN_A1, newA);
        values.put(HealthMemoDbHelper.COLUMN_B1, newB);
        values.put(HealthMemoDbHelper.COLUMN_C1, newC);

        database.update(HealthMemoDbHelper.TABLE_HEALTH_LIST,
                values,
                HealthMemoDbHelper.COLUMN_ID + "=" + id,
                null);

        Cursor cursor = database.query(HealthMemoDbHelper.TABLE_HEALTH_LIST,
                columns, HealthMemoDbHelper.COLUMN_ID + "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        HealthMemo shoppingMemo = cursorToAppMemo(cursor);
        cursor.close();

        return shoppingMemo;
    }

    private HealthMemo cursorToAppMemo(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(HealthMemoDbHelper.COLUMN_ID);
        int idVorname = cursor.getColumnIndex(HealthMemoDbHelper.COLUMN_VORNAME);
        int idNachname = cursor.getColumnIndex(HealthMemoDbHelper.COLUMN_NACHNAME);
        int idGerät1 = cursor.getColumnIndex(HealthMemoDbHelper.COLUMN_GERÄT1);
        int idA1 = cursor.getColumnIndex(HealthMemoDbHelper.COLUMN_A1);
        int idB1 = cursor.getColumnIndex(HealthMemoDbHelper.COLUMN_B1);
        int idC1 = cursor.getColumnIndex(HealthMemoDbHelper.COLUMN_C1);


        String vorname = cursor.getString(idVorname);
        String nachname = cursor.getString(idNachname);
        String gerät1 = cursor.getString(idGerät1);
        String a1 = cursor.getString(idA1);
        String b1 = cursor.getString(idB1);
        String c1 = cursor.getString(idC1);
        long id = cursor.getLong(idIndex);

        HealthMemo appMemo = new HealthMemo(vorname, nachname,gerät1,a1,b1,c1, id);

        return appMemo;
    }

    public List<HealthMemo> getAllHealthMemos() {
        List<HealthMemo> appMemoList = new ArrayList<>();

        Cursor cursor = database.query(HealthMemoDbHelper.TABLE_HEALTH_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        HealthMemo appMemo;

        while(!cursor.isAfterLast()) {
            appMemo = cursorToAppMemo(cursor);
            appMemoList.add(appMemo);
            Log.d(LOG_TAG, "ID: " + appMemo.getId() + ", Inhalt: " + appMemo.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return appMemoList;
    }

    public List<HealthMemo> getSearchHealthMemos(String suche) {
        List<HealthMemo> appMemoList = new ArrayList<>();

        Cursor cursor = database.query(HealthMemoDbHelper.TABLE_HEALTH_LIST,
                columns, HealthMemoDbHelper.COLUMN_NACHNAME + "='" + suche + "'",
                null, null, null, null);

        cursor.moveToFirst();
        HealthMemo appMemo;

        while(!cursor.isAfterLast()) {
            appMemo = cursorToAppMemo(cursor);
            appMemoList.add(appMemo);
            Log.d(LOG_TAG, "ID: " + appMemo.getId() + ", Inhalt: " + appMemo.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return appMemoList;
    }


}
