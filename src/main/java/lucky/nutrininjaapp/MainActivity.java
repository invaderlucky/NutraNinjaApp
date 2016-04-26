package lucky.nutrininjaapp;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.GregorianCalendar;

// This is the home screen for the app and parent of all the things.
public class MainActivity extends AppCompatActivity {
    // Start and stop buttons for the daily alarm.
    private Button start, stop;
    // Controls notifications (not currently used)
    NotificationManager notificationManager;
    boolean isNotificationActive = false;
    // This was a random number
    int notificationID = 33;
    // Buttons for each day of the week (b1 = sunday)
    Button b1, b2, b3, b4, b5, b6, b7;
    // The thing that pops up when start and stop are pressed.
    private Toast toast;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the main layout.
        setContentView(R.layout.activity_main);

        // Links the buttons in the layout to the button objects here.
        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);

/* Notification code that I couldn't get working.

        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        setAlarm(v);
                    }
                }
        );

        stop.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        stopNotification(v);
                    }
                }
        );
*/
        // Sets what the start button does
       start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Creates what you want to happen
                Intent intent = new Intent(MainActivity.this, AlarmReceiverActivity.class);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(MainActivity.this, 2, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);
                // Sets up the alarm
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                // THIS IS SET TO REPEAT EVERY 15 SECONDS FOR DEMO PURPOSES
                am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (15 * 1000),
                        (15 * 1000), pendingIntent);

                if (toast != null) {
                    toast.cancel();
                }

                // Pop up to notify the user that the alarm was set when they hit the button.
                toast = Toast.makeText(getApplicationContext(),
                        "Repeating daily alarm set",
                        Toast.LENGTH_LONG);
                // Actually shows the pop up
                toast.show();
            }
        });

        // Sets what the stop button does
        // This will stop the alarm from repeating
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates what you want to happen
                Intent intent = new Intent(MainActivity.this, AlarmReceiverActivity.class);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(MainActivity.this, 2, intent, 0);
                // Gets the alarm
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                // Kills the alarm
                am.cancel(pendingIntent);

                if (toast != null) {
                    toast.cancel();
                }

                // Pop up to notify the user that the alarm was stopped when they hit the button.
                toast = Toast.makeText(getApplicationContext(),
                        "Repeating alarm canceled",
                        Toast.LENGTH_LONG);
                // Actually shows the pop up
                toast.show();
            }
        });

        // Sunday button
        // Basically just changes the current view
        b1 = (Button) findViewById(R.id.sundayButton);
        b1.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        sunday(v);
                    }
                }
        );

        //monday
        b2 = (Button) findViewById(R.id.mondayButton);
        b2.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        monday(v);
                    }
                }
        );

        //tuesday
        b3 = (Button) findViewById(R.id.tuesdayButton);
        b3.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        tuesday(v);
                    }
                }
        );

        //wednesday
        b4 = (Button) findViewById(R.id.wednesdayButton);
        b4.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        wednesday(v);
                    }
                }
        );

        //thursday
        b5 = (Button) findViewById(R.id.thursdayButton);
        b5.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        thursday(v);
                    }
                }
        );

        //friday
        b6 = (Button) findViewById(R.id.fridayButton);
        b6.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        friday(v);
                    }
                }
        );

        //saturday
        b7 = (Button) findViewById(R.id.saturdayButton);
        b7.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        saturday(v);
                    }
                }
        );
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    // This was for the notification thingy. Not used but maybe for future?
    public void showNotification(View view) {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Time to workout")
                        .setContentText("Go do today's workout!")
                        .setTicker("Daily alert")
                        .setSmallIcon(R.mipmap.ic_launcher_ninja);

        Intent moreInfoIntent = new Intent(this, MoreInfoNotification.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MoreInfoNotification.class);
        taskStackBuilder.addNextIntent(moreInfoIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notificationBuilder.build());

        isNotificationActive = true;
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(getApplicationContext(),
                "Repeating daily alarm set",
                Toast.LENGTH_LONG);
        toast.show();
    }

    public void stopNotification(View view) {
        if (isNotificationActive) {
            notificationManager.cancel(notificationID);
        }
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(getApplicationContext(),
                "Repeating alarm canceled",
                Toast.LENGTH_LONG);
        toast.show();
    }

    public void setAlarm(View view) {
        Long alertTime = new GregorianCalendar().getTimeInMillis() +
                10 * 1000;
        Intent alertIntent = new Intent(this, AlertReceiver.class);
        showNotification(view);
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime,
                alertTime, PendingIntent.getBroadcast(this, 1, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // What will actually happen when the sunday button is hit
    public void sunday(View view) {
        Intent i = new Intent(this, SundayActivity.class);
        startActivity(i);
    }

    public void monday(View view) {
        Intent i = new Intent(this, MondayActivity.class);
        startActivity(i);
    }

    public void tuesday(View view) {
        Intent i = new Intent(this, TuesdayActivity.class);
        startActivity(i);
    }

    public void wednesday(View view) {
        Intent i = new Intent(this, WednesdayActivity.class);
        startActivity(i);
    }

    public void thursday(View view) {
        Intent i = new Intent(this, ThursdayActivity.class);
        startActivity(i);
    }

    public void friday(View view) {
        Intent i = new Intent(this, FridayActivity.class);
        startActivity(i);
    }

    public void saturday(View view) {
        Intent i = new Intent(this, SaturdayActivity.class);
        startActivity(i);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://lucky.nutrininjaapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://lucky.nutrininjaapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
