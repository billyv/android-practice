package com.example.billyvalvo.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RotationFragment extends Fragment implements View.OnClickListener {

    static final int PICK_REQUEST = 1337;
    Uri contact = null;


    public RotationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // This maintains the fragment across configuration changes.
        // So even if landscape changes, for example, the selected contact info remains.
        // This only works with dynamic fragments.
        // In this case, the buttons etc would be destroyed and rebuilt on config change,
        // but the fragment (which has no visual representation here, only contains data)
        // will remain intact.
        setRetainInstance(true);

        View result = inflater.inflate(R.layout.rotation_fragment, container, false);

        // This lets us listen to the pick button
        result.findViewById(R.id.pick).setOnClickListener(this);

        // This is the view button.
        View v = result.findViewById(R.id.view);

        // Here we are also listening to that button
        v.setOnClickListener(this);
        // And only enabling it if a contact has been selected
        v.setEnabled(contact != null);

        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If it was a pick request, signified by our unique ID
        if (requestCode == PICK_REQUEST) {
            // And if the user actually selected something (RESULT_OK)
            if (resultCode == Activity.RESULT_OK) {
                // This gets the actual result, the contact selected.
                // It is a Uri stored in intent.
                contact = data.getData();
                getView().findViewById(R.id.view).setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pick) {
            pickContact(v);
        }
        else {
            viewContact(v);
        }
    }

    public void pickContact(View v) {

        // When the user clicks pick button, they are sent to contact list to pick a contact

        Intent i =
                new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        // We start that activity expecting a result, associated with the unique ID, PICK_REQUEST

        startActivityForResult(i, PICK_REQUEST);

        // Once a user picks, control is returned and onActivityResult() is called
        // it is supplied our unique PICK_REQUEST, RESULT_OK or RESULT_CANCELED
        // and an Intent containing result, in this case a Uri representing contact
    }

    public void viewContact(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, contact));
    }

}
