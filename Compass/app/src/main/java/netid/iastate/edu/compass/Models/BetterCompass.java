package netid.iastate.edu.compass.Models;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import netid.iastate.edu.compass.Interfaces.SensorUpdateCallback;

/**
 * This class is used for managing a BetterCompass object, we use this to do business logic before updating the UI
 */
public class BetterCompass implements SensorEventListener {
    private SensorManager mSensorManager;//used to store the SensorManager for use throughout the model class
    private Sensor mMagField;//used to get and start/register the Sensor
    private Sensor mAcc;//used to get and start/register the Sensor
    private SensorUpdateCallback mCallback;//used to keep track of the activity to callback to

    private float[] mAccelerometerReading = new float[3];
    private float[] mMagnetometerReading = new float[3];

    public BetterCompass(Context context, SensorUpdateCallback callback) {
        mSensorManager = null; // TODO Get the Sensor Service using the application context
        mMagField = null; // TODO Get a magnetic field sensor
        mAcc = null; // TODO Get an accelerometer
        mCallback = callback;
    }

    /**
     * This method is called from the activity when the sensor listeners should be registered
     */
    public void start() {
        // TODO Register the magnetic field and accelerometer listeners
    }

    /**
     * This method is called from the activity when the sensor listeners should be unregistered
     */
    public void stop() {
        // TODO Unregister the magnetic field and accelerometer listeners
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // TODO Store magnetic field data in mMagnetometerReading
        }
        else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // TODO Store accelerometer data in mAccelerometerReading
        }

        // TODO Get orientation from magnetometer and accelerometer
        float orientation = 0.0f;

        mCallback.update(orientation);//use callback to call update() method in the activity
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing in our scenario
    }
}
