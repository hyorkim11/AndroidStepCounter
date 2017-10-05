package com.example.hrker.stepcounter;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.util.Calendar;


public class StepCountService extends IntentService implements SensorEventListener {

    private SensorManager sensorManager;

    public StepCountService() {
        super("StepCountService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i("XXX", "STEPCOUNT SERVICE FIRED!");
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (countSensor != null) {
            // register listener
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
            // GRAB THE STEPS
            try {
                writeToFile2(countSensor.TYPE_STEP_COUNTER);

                Log.i("XXX", "write to file called with " + countSensor.TYPE_STEP_COUNTER + " steps");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Write to File Failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void writeToFile2(float step) {
        String filename = "userSteps.csv";
        String string = Float.toString(step + ',' + Calendar.HOUR_OF_DAY + Calendar.DAY_OF_MONTH + Calendar.YEAR + '\n');
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
            Toast.makeText(this, string + " SAVED!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "steps FAILED!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
