package com.example.billyvalvo.myapplication;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.billyvalvo.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resizeButtons(4);

    }

    /**
     * Resizes demo launch buttons to be some fraction of screen size
     * However this is screen size, not display size, so does not resize
     * when a keyboard is popped up, for instance, as using weight would.
     *
     * @param fraction (1/fraction)*screen size for size of each button
     */
    private void resizeButtons(int fraction) {
        // Get display size
        Display screen = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        screen.getSize(screenSize);

        int buttonHeight = screenSize.y / fraction;

        // just testing
        System.out.println("screen y = " + screenSize.y);
        System.out.println("buttonHeight = " + buttonHeight);

        ViewGroup buttons = (ViewGroup) findViewById(R.id.button_group);

        for (int i = 0; i < buttons.getChildCount(); i++) {
            Button button = (Button) buttons.getChildAt(i);
            button.setHeight(buttonHeight);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();


        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSearch() {
        // this is not a real method just to illustrate how you would handle options menu
        Toast.makeText(this, "Search button pressed", Toast.LENGTH_SHORT).show();
    }
    private void openSettings() {
        // same as above
        Toast.makeText(this, "Settings button pressed", Toast.LENGTH_SHORT).show();
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {

        // An intent is an object that provides runtime binding between separate components
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        // This refers to this activity, and the second is the class
        // of the app component to which the system should deliver the intent.
        // In this case, the class of the activity that should be started, and receive intent data.

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        // Intents can carry data types as key value pairs called extras.
        // It's generally a good practice to define keys for intent extras
        // using your app's package name as a prefix. This ensures the keys are unique,
        // in case your app interacts with other apps.

        startActivity(intent);
        // The system receives this call and starts an instance of the activity specified
        // by this intent. (In this case, DisplayMessageActivity)
    }

    public void loadListDemo(View view) {

        Intent listDemo = new Intent(this, ListDemoActivity.class);

        // just starting a new page so nothing to do but
        startActivity(listDemo);


    }

    public void loadSpinnerDemo(View view) {
        Intent spinnerDemo = new Intent(this, SpinnerDemoActivity.class);
        startActivity(spinnerDemo);
    }

    public void loadGridDemo(View view) {
        Intent gridDemo = new Intent(this, GridDemoActivity.class);
        startActivity(gridDemo);
    }

    public void loadWebViewDemo(View view) {
        Intent webDemo = new Intent(this, WebViewDemoActivity.class);
        startActivity(webDemo);
    }

    public void loadActionBarDemo(View view) {
        Intent actionBarDemo = new Intent(this, ActionBarDemoActivity.class);
        startActivity(actionBarDemo);
    }

    public void loadLaunchDemo(View view) {
        Intent launchDemo = new Intent(this, LaunchDemoActivity.class);
        startActivity(launchDemo);
    }

    public void loadRotationFragmentDemo(View view) {
        Intent rotationFragmentDemo = new Intent(this, RotationFragmentDemoActivity.class);
        startActivity(rotationFragmentDemo);
    }

}
