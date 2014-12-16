package cz.odhlasujto.odhlasujto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dboptions extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "dboptions";
    private static final String TABLE_NAME = "options";
    private static final String COL_NAME_JMENO = "optionName"; // lepší deklarace jména sloupce
    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" + COL_NAME_JMENO + " TEXT, sumID INT,sum BOOL);";

    public dboptions(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dboptions) {
        dboptions.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dboptions, int oldVersion, int newVersion) {
        onCreate(dboptions);

    }
}
