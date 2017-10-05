# AndroidStepCounter
Android Step Counter classes

Consists of 3 classes:
1. MainActivity
  extends AppCompatActivity
2. AlarmReceiver
  extends BroadcastReceiver
3. StepCountService
  extends IntentService implements SensorEventListener

Log tag "XXX" implemented for debugging

Description
This simple step counter implements the device's step counter sensor to track steps.
Every sensored change from the stepcounter service will be notified to the user and is logged in a saved CSV file.
Step file name is declared as "userSteps.csv"
