package cz.odhlasujto.odhlasujto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.valueOf;

public class db extends SQLiteOpenHelper {
    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOGs

    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "mydb";

    public static final String TABLE_POLLS = "polls";
    public static final String COL_NAME_POLL = "pollName";
    public static final String POLL_ID = "pollId";
    public static final String SQL_CREATE_POLLS =
            "CREATE TABLE " + TABLE_POLLS + " ("
                    + POLL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME_POLL + " TEXT, pollDesc TEXT "
                    + ");";

    public static final String TABLE_OPTIONS = "options";
    public static final String COL_NAME_OPTION = "optionName";
    public static final String ID = "_id";
    public static final String CREATED_POLL = "createdPoll";
    public static final String SUM = "sum";
    public static final String SQL_CREATE_OPTIONS =
            "CREATE TABLE " + TABLE_OPTIONS + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME_OPTION + " TEXT NOT NULL, "
                    + SUM + " INTEGER, "
                    + CREATED_POLL + " integer, "
                    + "FOREIGN KEY ("+CREATED_POLL+") REFERENCES "+TABLE_POLLS+" ("+POLL_ID+"));";

    public db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_POLLS);
        db.execSQL(SQL_CREATE_OPTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(LOG, "Upgrading the database from version " + oldVersion + " to " + newVersion);
        // dropni
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POLLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);
        // vytvor
        onCreate(db);
    }

    public void insertPolls(ArrayList<Poll> polls) {
        for (Poll poll : polls) {
            // použití čistého SQL dotazu by bylo rychlejší, zvláště u velkých objemů dat
            ContentValues values = new ContentValues();
            values.put(COL_NAME_POLL, poll.getPollName());
            values.put("pollDesc", poll.getPollDesc());
            values.put("pollId", poll.getPollId());
            getWritableDatabase().insert(TABLE_POLLS, null, values);
        }
    }

    public ArrayList<Poll> getPolls(String orderBy) {
        String query = "SELECT * FROM " + TABLE_POLLS + " ORDER BY " + orderBy;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        ArrayList<Poll> dataOrdered = new ArrayList<Poll>();
        if (cursor.moveToFirst()) {
            do {
                Poll poll = new Poll();
                poll.setPollId(cursor.getString(Integer.valueOf(cursor.getColumnIndex("pollId"))));
                poll.setPollName(cursor.getString(Integer.valueOf(cursor.getColumnIndex(COL_NAME_POLL))));
                poll.setPollDesc(cursor.getString(Integer.valueOf(cursor.getColumnIndex("pollDesc"))));
                // Adding poll to list
                dataOrdered.add(poll);
            } while (cursor.moveToNext());
        }
        Log.d("query", query);
        Log.d("getPolls()", dataOrdered.toString());
        return dataOrdered;
    }

    public List<Poll> getAllPolls() {
        List<Poll> polls = new LinkedList<Poll>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_POLLS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Poll poll = null;
        if (cursor.moveToFirst()) {
            do {
                poll = new Poll();
                poll.setPollName(cursor.getString(0));
                poll.setPollDesc(cursor.getString(1));

                // Add book to books
                polls.add(poll);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPolls()", polls.toString());
        // return books
        return polls;
    }

    public String getPollName() {
        String pollNamefromDB = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT pollName FROM polls";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            {
                cursor.moveToFirst();
                do {
                    pollNamefromDB = cursor.getString(0);
                } while (cursor.moveToNext());
            }
        }
        return pollNamefromDB;
    }

    public String getPollDesc() {
        String pollDescfromDB = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT pollDesc FROM polls";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            {
                cursor.moveToFirst();
                do {
                    pollDescfromDB = cursor.getString(0);
                } while (cursor.moveToNext());
            }
        }
        return pollDescfromDB;
    }

    public void insertOption(ArrayList<Options> options) {
        for (Options option : options) {
            // použití čistého SQL dotazu by bylo rychlejší, zvláště u velkých objemů dat
            ContentValues values = new ContentValues();
            SQLiteDatabase db = this.getReadableDatabase();

            values.put(COL_NAME_OPTION, option.getOptionName());
            //values.put(CREATED_POLL, valueOf(db.rawQuery("SELECT pollId FROM polls", null)));
            Cursor createdPoll = null;
            Cursor c = db.rawQuery("SELECT pollId FROM polls ORDER BY pollId DESC LIMIT 1", null);
            //values.put(CREATED_POLL, valueOf(c));
            Log.d("XXXXXX", DatabaseUtils.dumpCursorToString(c));


            if (c != null && c.getCount() > 0) {
                {
                    c.moveToFirst();
                    do {
                        createdPoll = c;
                        Log.d(LOG, "CP: " + createdPoll.getString(0));
                    } while (c.moveToNext());
                }
            }
            Log.d("XXXXXX", DatabaseUtils.dumpCursorToString(c));

            getWritableDatabase().insert(TABLE_OPTIONS, null, values);
        }
    }

    public Cursor getOptions() {
        Cursor optionNamefromDB = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT _id, optionName FROM polls, options WHERE polls.pollId = options.createdPoll";
        //String selectQuery = "SELECT " + COL_NAME_POLL + " FROM " + TABLE_POLLS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            {
                cursor.moveToFirst();
                do {
                    optionNamefromDB = cursor;
                    Log.d(LOG, "getOptions: " + optionNamefromDB.getString(0));
                } while (cursor.moveToNext());
            }
        }
        Log.d("QUERY getOptions: ", selectQuery);

        return optionNamefromDB;
    }

    //region AndroidDatabaseMANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"mesage"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }//endregion
}

