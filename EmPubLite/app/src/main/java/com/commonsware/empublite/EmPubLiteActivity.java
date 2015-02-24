package com.commonsware.empublite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EmPubLiteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;

            case R.id.about:
<<<<<<< HEAD
                Intent intent = new Intent(this, SimpleContentActivity.class);
                startActivity(intent);
=======
                Intent i = new Intent(this, SimpleContentActivity.class);
                startActivity(i);
>>>>>>> 486e43b2e268ec61ba200dde609563c81911b178

                return true;

            case R.id.help:
<<<<<<< HEAD
                intent = new Intent(this, SimpleContentActivity.class);
                startActivity(intent);
=======
                i = new Intent(this, SimpleContentActivity.class);
                startActivity(i);

>>>>>>> 486e43b2e268ec61ba200dde609563c81911b178
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
