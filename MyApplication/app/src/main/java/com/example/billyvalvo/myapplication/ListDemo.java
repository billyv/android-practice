package com.example.billyvalvo.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ListDemo extends ListActivity {

    private TextView selection;
    private static final String[] items={"lorem", "ipsum", "dolor",
            "sit", "amet",
            "consectetuer", "adipiscing", "elit", "morbi", "vel",
            "ligula", "vitae", "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat", "placerat", "ante",
            "porttitor", "sodales", "pellentesque", "augue", "purus"};


    @Override
    //some people used icicle because it used to be 'freeze' to save instance state
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_list_demo);

        // this creates the adapter itself using this view, the format of android supplied layout,
        // and populating with items. (you can define your own formats)
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

        selection = (TextView) findViewById(R.id.selection);
    }

    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
        // set the text of the label above the list (selection) as the item you clicked,
        // signified by position (0 based counter for items in list, represented as 'items'
        selection.setText(items[position]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // for some reason, you can't use a custom theme with listview or else action bar disappears
        // unless there was a way to force action bar in your custom theme as holo etc does...
        getMenuInflater().inflate(R.menu.menu_list_demo, menu);
        return (super.onCreateOptionsMenu(menu));
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
