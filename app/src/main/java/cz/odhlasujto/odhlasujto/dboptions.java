package cz.odhlasujto.odhlasujto;

import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dboptions extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "dboptions";
    private static final String TABLE_NAME = "options";
    static final String COL_NAME_JMENO = "optionName"; // lepší deklarace jména sloupce
    static final String ID = "ID";
    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("+ ID + COL_NAME_JMENO + " TEXT, sumID INT,sum BOOL);";

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

    public void insertOption(ArrayList<Options> options) {
        for (Options option : options) {
            // použití čistého SQL dotazu by bylo rychlejší, zvláště u velkých objemů dat
            ContentValues values = new ContentValues();
            values.put(COL_NAME_JMENO, option.getOptionName());
            getWritableDatabase().insert(TABLE_NAME, null, values);
        }
    }

    public Cursor getOptions(){
        SQLiteDatabase dboptions = this.getReadableDatabase();
        return dboptions.rawQuery("SELECT optionName FROM options", null);
    }

}
