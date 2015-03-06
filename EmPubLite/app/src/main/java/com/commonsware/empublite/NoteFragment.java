package com.commonsware.empublite;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.greenrobot.event.EventBus;

/**
 * Created by Billy on 2015-03-06.
 */
public class NoteFragment extends Fragment {

    public interface Contract {
        void closeNotes();
    }

    private static final String KEY_POSITION = "position";
    private EditText editor = null;

    static NoteFragment newInstance(int position) {
        NoteFragment frag = new NoteFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            editor.setText(null);
            // Here we get our contract with hosting activity and ask them
            // to implement the closeNotes() method, which should close us,
            // the fragment being hosted by them.
            getContract().closeNotes();
            // NOTE that this is only illustrative of another way to communicate
            // to other activities (in addition to EventBus, etc) but in fact
            // this is merely asking the hosting activity to finish() which could
            // be accomplished much more easily here by calling
            // getActivity().finish(); 

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        View result = inflater.inflate(R.layout.editor, container, false);

        editor = (EditText) result.findViewById(R.id.editor);

        return result;
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);

        if (TextUtils.isEmpty(editor.getText())) {
            DatabaseHelper db = DatabaseHelper.getInstance(getActivity());

            db.loadNote(getPosition());
        }
    }

    @Override
    public void onPause() {
        // Every time we pause we save whatever was written in the note to the database
        // However this will update the db even if nothing was written or nothing changed.
        DatabaseHelper.getInstance(getActivity())
                .updateNote(getPosition(), editor.getText().toString());


        EventBus.getDefault().unregister(this);

        super.onPause();
    }

    public void onEventMainThread(NoteLoadedEvent event) {
        // Here, assuming we have the right note for this page,
        // we fill in the text field with whatever should already be there,
        // whatever the user already wrote into it and saved.
        if (event.getPosition() == getPosition()) {
            editor.setText(event.getProse());
        }
    }

    private int getPosition() {
        // This -1 is what the value will fall through to. Kind of an error message.
        return this.getArguments().getInt(KEY_POSITION, -1);
    }


    // Here we basically bind the hosting activity (getAcitity()) to our contract,
    // which dictates that they implement closeNotes()
    private Contract getContract() {
        return (Contract)getActivity();
    }

}
