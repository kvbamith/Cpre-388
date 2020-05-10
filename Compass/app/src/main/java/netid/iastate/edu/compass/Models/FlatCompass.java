package netid.iastate.edu.compass.Models;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import netid.iastate.edu.compass.Interfaces.SensorUpdateCallback;

/**
 * This class is used for managing a FlatCompass object, we use this to do business logic before updating the UI
 */
public class FlatCompass implements SensorEventListener {
    private SensorManager mSensorManager;//used to store the SensorManager for use throughout the model class
    private Sensor mMagField;//used to get and start/register the Sensor
    private SensorUpdateCallback mCallback;//used to keep track of the activity to callback to

    public FlatCompass(Context context, SensorUpdateCallback callback) {
        mSensorManager = null; // TODO Get the Sensor Service using the application context
        mMagField = null; // TODO Get a magnetic field sensor
        mCallback = callback;
    }

    /**
     * This method is called from the activity when the sensor listener should be registered
     */
    public void start() {
        // TODO Register magnetic field sensor listener

    }

    /**
     * This method is called from the activity when the sensor listener should be unregistered
     */
    public void stop() {
        // TODO Unregister magnetic field sensor listener
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Calculate the orientation
        float orientation = 0.0f;

        mCallback.update(orientation);//use callback to call update() method in the activity
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing in our scenario
    }
}