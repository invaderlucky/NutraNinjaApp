package lucky.nutrininjaapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * This controls the sunday window. All the other ___dayActivity's work
 * just like this one, the only change is the setContentView() in onCreate().
 * Also the reason all these extend AppCompatActivity is because that
 * allows the back button and toolbar at the top.
 */
public class SundayActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    // What the activity does.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // THIS IS WHAT LINKS IT TO THE SUNDAY LAYOUT
        setContentView(R.layout.activity_sunday);
        // DO NOT MESS WITH THIS IT TOOK ME FOREVER TO GET WORKING
        // I WANTED TO CRY
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }

    // Creates the menu at the top
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // I honestly don't know
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
