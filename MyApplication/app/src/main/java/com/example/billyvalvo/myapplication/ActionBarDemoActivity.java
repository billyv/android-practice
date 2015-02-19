package com.example.billyvalvo.myapplication;

import android.app.ActionBar;
import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ActionBarDemoActivity extends ActionBarActivity {

    private static final String[] items= { "lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
            "vel", "erat", "placerat", "ante", "porttitor", "sodales",
            "pellentesque", "augue", "purus" };
    private ArrayList<String> words=null;
    private ArrayAdapter<String> adapter=null;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_demo);

        lv = (ListView) findViewById(R.id.list);

        initAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar_demo, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.add:
                addWords();

                return true;

            case R.id.reset:
                initAdapter();

                return true;

            case R.id.about:
                Toast.makeText(this, R.string.about_toast, Toast.LENGTH_SHORT).show();

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void initAdapter() {
        words=new ArrayList<String>();

        for (int i=0;i<5;i++) {
            words.add(items[i]);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);

        lv.setAdapter(adapter);
    }

    private void addWords() {
        if (adapter.getCount()<items.length) {
            adapter.add(items[adapter.getCount()]);
        }
    }

}
