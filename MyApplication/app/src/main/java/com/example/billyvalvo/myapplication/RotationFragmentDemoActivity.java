package com.example.billyvalvo.myapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RotationFragmentDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getFragmentManager().findFragmentById(android.R.id.content)==null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new RotationFragment()).commit();
        }
    }

}
