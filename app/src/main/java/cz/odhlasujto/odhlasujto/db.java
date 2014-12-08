package cz.odhlasujto.odhlasujto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class db extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "mydb";
    private static final String TABLE_NAME = "polls";
    private static final String COL_NAME_JMENO = "pollName"; // lepší deklarace jména sloupce
    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("+COL_NAME_JMENO+" TEXT, prijmeni TEXT,id INT);";

    db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {db.execSQL(SQL_CREATE);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);

    }

    public void insertPolls(ArrayList<Poll> polls){
        for (Poll poll:polls) {
            // použití čistého SQL dotazu by bylo rychlejší, zvláště u velkých objemů dat
            ContentValues values = new ContentValues();
            values.put(COL_NAME_JMENO,poll.getPollName());
            values.put("pollDesc",poll.getPollDesc());
            values.put("id",poll.getPollId());
            getWritableDatabase().insert(TABLE_NAME,null,values);
        }
    }

    public ArrayList<Poll> getPolls (String orderBy) {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + orderBy;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        ArrayList<Poll> dataOrdered = new ArrayList<Poll>();
        if (cursor.moveToFirst()) {
            do {
                Poll poll = new Poll();
                poll.setPollId(cursor.getInt(Integer.valueOf(cursor.getColumnIndex("id"))));
                poll.setPollName(cursor.getString(Integer.valueOf(cursor.getColumnIndex(COL_NAME_JMENO))));
                poll.setPollDesc(cursor.getString(Integer.valueOf(cursor.getColumnIndex("pollDesc"))));
                // Adding contact to list
                dataOrdered.add(poll);
            } while (cursor.moveToNext());
        }
        return dataOrdered;
    }
}