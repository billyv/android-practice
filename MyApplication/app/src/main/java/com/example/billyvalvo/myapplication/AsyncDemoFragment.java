package com.example.billyvalvo.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.billyvalvo.myapplication.dummy.DummyContent;

import java.util.ArrayList;

public class AsyncDemoFragment extends ListFragment {

    private static final String[] items={"lorem", "ipsum", "dolor",
            "sit", "amet",
            "consectetuer", "adipiscing", "elit", "morbi", "vel",
            "ligula", "vitae", "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat", "placerat", "ante",
            "porttitor", "sodales", "pellentesque", "augue", "purus"};
    private ArrayList<String> model = null;
    private ArrayAdapter<String> adapter = null;
    private AddStringTask task = null;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AsyncDemoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        // If model null, aka this is our first time loading the fragment (or loading from scratch)
        // then make a new model to be used by our task, which will add strings to it (the ArrayList)
        if (model == null) {
            model = new ArrayList<String>();
            task = new AddStringTask();
            task.execute();
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, model);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        // Here we simply attach our adapter
        getListView().setScrollbarFadingEnabled(false);
        setListAdapter(adapter);
    }

    @Override
    synchronized public void onDestroy() {
        if (task != null) {
            task.cancel(false);
        }

        // We cancel for two reasons.
        // 1. Efficieny. Don't need to keep doing the work if we destroy this fragment.
        // 2. Avoid crashing by raising a toast on a destroyed activity.

        super.onDestroy();
    }

    synchronized private void clearTask() {
        task = null;
    }

    // On the data types: no info needed for config so first is Void,
    // we pass String back to main thread in onProgressUpdate so second is String,
    // and other than that we are not returning anything so last type is also Void.
    class AddStringTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... unused) {
            for (String item : items) {
                if (isCancelled())
                    break;

                // We just publishProgress (which does actual work of updating UI aka adding strings)
                // and then sleep to simulate work being done, so we can see visually that
                // the thread is running in the background.
                publishProgress(item);
                SystemClock.sleep(400);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... item) {
            if (!isCancelled()) {
                // Add an item to adapter (which is passed to UI thread so we can see it happening)
                adapter.add(item[0]);
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            // Just a toast to inform the user that we finished.
            if (!isCancelled()) {
                Toast.makeText(getActivity(), R.string.done, Toast.LENGTH_SHORT).show();
            }

            clearTask();
        }

    }


}
