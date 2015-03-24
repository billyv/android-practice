package com.commonsware.empublite;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ShareActionProvider;

import de.greenrobot.event.EventBus;

/**
 * Created by Billy on 2015-03-06.
 */
public class NoteFragment extends Fragment implements TextWatcher {

    public interface Contract {
        void closeNotes();
    }

    private static final String KEY_POSITION = "position";
    private EditText editor = null;
    private ShareActionProvider share = null;
    private Intent shareIntent = new Intent(Intent.ACTION_SEND).setType("text/plain"); // set MIME type of intent


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

        // ShareActionProvider is android's own solution for ACTION_SEND functionality.
        // Basically, they do the work of creating an action bare share item.
        // Part of the set up happens in the menu.
        // It creates an action bar share item that drops down within the view, and allows
        // user to decide how to ACTION_SEND whatever.
        share = (ShareActionProvider)menu.findItem(R.id.share).getActionProvider();
        share.setShareIntent(shareIntent);

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
        // Here we set the fragment to listen for changes to the text.
        // This is so that for every change we can update what will be
        // potentially shared if the user pressed share.
        editor.addTextChangedListener(this);

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

    // These following three methods are required by TextWatcher interface.
    @Override
    public void afterTextChanged(Editable s) {
        // Update share intent with each time text changes
        shareIntent.putExtra(Intent.EXTRA_TEXT, s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Ignored
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Ignored
    }

}
