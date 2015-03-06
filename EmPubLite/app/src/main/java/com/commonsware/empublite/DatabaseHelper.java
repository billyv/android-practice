package com.commonsware.empublite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.*;
import android.os.Process;

import de.greenrobot.event.EventBus;

/**
 * Created by Billy on 2015-03-06.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="empublite.db";
    private static final int SCHEMA_VERSION = 1;
    private static  DatabaseHelper singleton = null;

    // Here we get the database helper from the application context itself so that it cannot be leaked.
    // It will never be referring to a destroyed activity and cause a memory leak.
    //
    // A note on synchronized methods -- it means that any other synchronized method working with
    // this object must now wait for this method to finish. Basically two synchronized methods
    // will not work on the same object at same time. So if an object is visible to more than one
    // thread, you should declare any methods that work on it as synchronized to prevent inconsistency.
    synchronized static DatabaseHelper getInstance(Context context) {
        if (singleton == null) {
            singleton = new DatabaseHelper(context.getApplicationContext());
        }

        return singleton;
    }

    // You must implement this.
    // It is also private so that only DatabaseHelper can create instances.
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This creates a table called notes with two columns, position which is the key column,
        // and also indicate which chapter the note is on, and a prose column, of type text
        // which will store the note itself.
        db.execSQL("CREATE TABLE notes (position INTEGER PRIMARY KEY, prose TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // We are not going to change the schema so this should never be called, but
        // it is required that we implement this method.
        throw new RuntimeException("This should not be called, sucka");
    }

    void loadNote(int position) {
        new LoadThread(position).start();
    }

    void updateNote(int position, String prose) {
        new UpdateThread(position, prose).start();
    }

    // We have the background thread class in our SQLiteOpenHelper class to consolidate
    // as much db logic in one place as possible. This has same name as another private
    // LoadThread class in ModelFragment but this is different.
    private class LoadThread extends Thread {
        private int position = -1;

        LoadThread(int position) {
            super();

            this.position = position;
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        }

        @Override
        public void run() {
            String[] args = { String.valueOf(position) };
            Cursor c = getReadableDatabase()
                    .rawQuery("SELECT prose FROM notes WHERE position = ?", args);

            // If there are no rows there is no note so don't do this
            if(c.getCount() > 0) {
                // The result should be the prose where position = position, so move to first
                // entry and post event with the first value of result set. Should be the
                // only value there.
                c.moveToFirst();
                EventBus.getDefault().post(new NoteLoadedEvent(position, c.getString(0)));
            }

            c.close();
        }
    }

    // Here is a background thread that updates note in database.
    // Note that we do not post an event in this one because there is nothing in the app
    // that needs to hear about notes being updated other than the database itself.
    private class UpdateThread extends Thread {
        private int position = -1;
        private String prose = null;

        UpdateThread(int position, String prose) {
            super();

            this.position = position;
            this.prose = prose;
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        }

        @Override
        public void run() {
            String[] args = { String.valueOf(position), prose };

            getWritableDatabase()
                    .execSQL("INSERT OR REPLACE INTO notes (position, prose) VALUES (?, ?)", args);
        }
    }
}
