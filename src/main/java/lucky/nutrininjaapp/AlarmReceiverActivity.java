package lucky.nutrininjaapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;


/**
 * Created by Lucky on 4/8/2016.
 * This is the thing that makes the alarm go off.
 */
public class AlarmReceiverActivity extends AppCompatActivity {
    // Accesses the most annoying music on my phone.
    private MediaPlayer mediaPlayer;
    // Used to wake my poor phone when locked.
    private PowerManager.WakeLock wakeLock;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     * I have no idea what this does, it was auto made, just leave it or
     * you will break all the things.
     */
    private GoogleApiClient client;

    @Override
    // This is the thing that gets executed when the activity starts.
    public void onCreate(Bundle savedInstanceState) {
        // Has to be called, don't mess with.
        super.onCreate(savedInstanceState);
        // Creates a power manager to control waking the lock.
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        // Makes the actual part that will wake the lock.
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Wake Log");
        // Gets the lock on the lock (this makes more sense after taking CSC246)
        wakeLock.acquire();
        // These are a bunch options that I added because the dude in the tutorial
        // told me to. Most are self explanatory, all others can be googled.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.alarm);

        // Creates the functionality behind the button that will stop the alarm.
        // THIS IS A ONE TIME THING, THE ALARM WILL STILL GO OFF AGAIN LATER.
        // The actual alarmBtn is in the layout for the main activity.
        Button stopAlarm = (Button) findViewById(R.id.alarmBtn);
        // Stops the most annoying sound on my phone.
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mediaPlayer.stop();
                finish();
            }
        });
        playSound(this, getAlarmUri());
    }

    private void playSound(Context context, Uri alert){
        // Allows you to access all the annoying sounds on a phone.
        mediaPlayer = new MediaPlayer();
        // Sets up the default sound. If it can't found an audio file (which
        // is probably not gonna happen), throws an error.
        try {
            mediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager =
                    (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IOException e) {
            Log.i("AlarmReceiver", "No audio files are found!");
        }
    }

    private Uri getAlarmUri() {
        // Basically checks everywhere for some kind of default sound.
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }

    protected void onStop() {
        // Needs to be called.
        super.onStop();
        // Releases the lock on the lock (see CSC246).
        wakeLock.release();
    }
}
