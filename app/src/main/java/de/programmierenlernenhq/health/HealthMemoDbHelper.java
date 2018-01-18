package de.programmierenlernenhq.health;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vetro on 08.01.2018.
 */

public class HealthMemoDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = HealthMemoDbHelper.class.getSimpleName();

    public static final String DB_NAME = "app_list.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_HEALTH_LIST = "health_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VORNAME = "vorname";
    public static final String COLUMN_NACHNAME = "nachname";
    public static final String COLUMN_GERÄT1 = "gerät1";
    public static final String COLUMN_A1 = "a1";
    public static final String COLUMN_B1 = "b1";
    public static final String COLUMN_C1 = "c1";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_HEALTH_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_VORNAME + " TEXT NOT NULL, " +
                    COLUMN_NACHNAME + " TEXT NOT NULL, " +
                    COLUMN_GERÄT1 + " TEXT NOT NULL, " +
                    COLUMN_A1 + " TEXT NOT NULL, " +
                    COLUMN_B1 + " TEXT NOT NULL, " +
                    COLUMN_C1 + " TEXT NOT NULL );";


    public HealthMemoDbHelper(Context context) {
        //super(context, "PLATZHALTER_DATENBANKNAME", null, 1);
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
