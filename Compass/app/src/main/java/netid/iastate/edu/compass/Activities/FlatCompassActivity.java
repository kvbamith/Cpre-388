package netid.iastate.edu.compass.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import netid.iastate.edu.compass.Interfaces.SensorUpdateCallback;
import netid.iastate.edu.compass.Models.FlatCompass;
import netid.iastate.edu.compass.R;

/**
 * This is the activity for the Flat Compass implementation
 */
public class FlatCompassActivity extends AppCompatActivity implements SensorUpdateCallback {
    private FlatCompass mCompass;//used to store the FlatCompass object
    private ImageView mArrow;//used for modifying the shown image view

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        mCompass = null; // TODO Instantiate a FlatCompass object

        mArrow = findViewById(R.id.image);
        mArrow.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.compass));

        Button leftButton = findViewById(R.id.btn1);
        leftButton.setText(R.string.tilt_text);


        Button rightButton = findViewById(R.id.btn2);
        rightButton.setText(R.string.better_compass_text);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCompass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCompass.stop();
    }

    @Override
    public void update(float orientation) {
        // TODO Rotate compass to orientation
    }

    public void leftButtonClicked(View view){
        startActivity(new Intent(this, TiltActivity.class));
    }

    public void rightButtonClicked(View view){
        startActivity(new Intent(this, BetterCompassActivity.class));
    }


}