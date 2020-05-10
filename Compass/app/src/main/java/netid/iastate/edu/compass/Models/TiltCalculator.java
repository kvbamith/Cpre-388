package netid.iastate.edu.compass.Models;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import netid.iastate.edu.compass.Interfaces.SensorUpdateCallback;

/**
 * This class is used for managing a TiltCalculator object, we use this to do business logic before updating the UI
 */
public class TiltCalculator implements SensorEventListener {
    private SensorManager mSensorManager;//used to store the SensorManager for use throughout the model class
    private Sensor mAcc;//used to get and start/register the Sensor
    private SensorUpdateCallback mCallback;//used to keep track of the activity to callback to

    public TiltCalculator(Context context, SensorUpdateCallback callback) {
        mSensorManager = null; // TODO Get the Sensor Service using the application context
        mAcc = null; // TODO Get an accelerometer
        mCallback = callback;
    }

    /**
     * This method is called from the activity when the sensor listener should be registered
     */
    public void start() {
        // TODO Register accelerometer sensor listener
    }

    /**
     * This method is called from the activity when the sensor listener should be unregistered
     */
    public void stop() {
        // TODO Unregister accelerometer sensor listener
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float orientation = 0.0f;
        double pitch = 0; // TODO Determine pitch from accelerometer

        double roll = 0; // TODO Determine roll from accelerometer

        orientation = 0; // TODO Determine orientation from pitch and roll
        
        mCallback.update(orientation);//use callback to call update() in activity
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
