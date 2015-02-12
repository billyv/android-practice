package com.example.billyvalvo.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;



public class DisplayMessageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Every activity is invoked by some intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Creating a new text view with message in this activity (not using xml)
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        // this 40 is 40px
        textView.setText(message);

        setContentView(textView);


//        // altering the text of a pre-existing xml text view in this activity
//        // currently NOT working
//        TextView textView1 = (TextView) findViewById(R.id.content_text);
//        textView1.setText(message);
//        setContentView(R.layout.activity_display_message);

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
}
