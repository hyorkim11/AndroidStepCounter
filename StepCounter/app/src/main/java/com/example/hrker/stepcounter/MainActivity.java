package com.example.hrker.stepcounter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
// AlarmManager sends an Intent to a class that extends
// BroadcastReceiver and that class will start your service

    private Button btnCancel, btnReadFile;
    private TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtView = (TextView) findViewById(R.id.textView);

        setSupportActionBar(toolbar);
        btnCancel = (Button) findViewById(R.id.btn_cancel_alarm);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarmService();
            }
        });
        btnReadFile = (Button) findViewById(R.id.btn_readFile);
        btnReadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readStepsFile();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleIntent();
            }
        });
    }

    public void readStepsFile() {
        // read the last line of the CSV file
        try {
            StringBuffer line = new StringBuffer("");
            FileInputStream fileInputStream = openFileInput("userSteps.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String s = reader.readLine();
            while (s != null) {
                line.append(s);
                s = reader.readLine();
            }

            inputStreamReader.close();
            txtView.setText(s.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scheduleIntent() {
        // Start the Alarm
        AlarmManager alarmManager = (AlarmManager)this.getBaseContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this.getBaseContext(), AlarmReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(this.getBaseContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pending);
    }


    public void cancelAlarmService() {
        // Kill the Alarm
        Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pending = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pending);
        Toast.makeText(this, "Alarm has been killed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
