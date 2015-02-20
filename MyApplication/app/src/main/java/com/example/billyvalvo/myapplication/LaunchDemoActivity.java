package com.example.billyvalvo.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class LaunchDemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_demo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMe(View v) {
        EditText url = (EditText) findViewById(R.id.url);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.getText().toString()));
        startActivity(intent);
        // Android takes the URI (like a GET request, or some other instruction) and
        // attempts to do something with it, in this case the action 'view' (ACTION_VIEW.)
        // In this case, http:// MUST BE INCLUDED in the url or else the system does not know
        // how to handle it. Consider it following proper syntax of the URI.
    }
}
