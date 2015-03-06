package com.commonsware.empublite;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Billy on 2015-03-06.
 */
public class NoteActivity extends Activity implements NoteFragment.Contract {

    // Note that this key is the same as KEY_POSITION in NoteFragment
    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fragments by default take the ID of their containing view, in this case content.
        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
            int position = getIntent().getIntExtra(EXTRA_POSITION, -1);

            if (position > 0) {
                Fragment frag = NoteFragment.newInstance(position);

                // Once we add the NoteFragment, it itself inflates the view which is why we do not do
                // that in this class.
                getFragmentManager().beginTransaction().add(android.R.id.content, frag).commit();
            }
        }
    }

    public void closeNotes() {
        finish();
    }

}
