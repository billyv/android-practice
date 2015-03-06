package com.commonsware.empublite;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.res.AssetManager;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import de.greenrobot.event.EventBus;
import com.google.gson.Gson;

public class ModelFragment extends Fragment {

    private BookContents contents = null;
    private SharedPreferences prefs = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity host) {
        super.onAttach(host);

        // Form a LoadThread to populate book.
        // Cannot getAssets until attached to host, so do this here
        // instead of in onCreate().
        if (contents == null) {
            new LoadThread(host).start();
        }
    }

    public BookContents getBook() {
        return contents;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    private class LoadThread extends Thread {
        private Context context = null;

        LoadThread(Context context) {
            super();

            this.context = context.getApplicationContext();
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        }

        @Override
        public void run() {
            prefs = PreferenceManager.getDefaultSharedPreferences(context);

            Gson gson = new Gson();

            try {
                InputStream is = context.getAssets().open("book/contents.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                // How does fromJson know to create it properly as a BookContents object?
                contents = gson.fromJson(reader, BookContents.class);
                // Post our defined event to the event bus.
                EventBus.getDefault().post(new BookLoadedEvent(contents));

            }
            catch (IOException e) {
                Log.e(getClass().getSimpleName(), "Exception parsing Json", e);
            }
        }
    }

}
