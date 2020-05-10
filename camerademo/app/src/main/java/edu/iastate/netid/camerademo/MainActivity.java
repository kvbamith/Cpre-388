package edu.iastate.netid.camerademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.DIRECTORY_PICTURES;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_TAKE_PHOTO = 1;
    private static final int REQ_PERMISSIONS_CAMERA = 2;

    private static final String TAG = "MainActivity";

    private Button takePhotoButton;

    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePhotoButton = findViewById(R.id.takePhotoButton);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Don't have permission to use camera.");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQ_PERMISSIONS_CAMERA);
        }

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                    /* Thumbnail version */
                    //startActivityForResult(takePhotoIntent, REQ_TAKE_PHOTO);
                    /* End thumbnail version */

                    /* Full photo version */
                    File photoFile = null;
                    try {
                        photoFile = createPhotoFile();
                    } catch (IOException e) {
                        Log.d(TAG, "Error getting photo file.", e);
                        return;
                    }
                    if(photoFile != null) {
                        photoURI = FileProvider.getUriForFile(view.getContext(),
                                "edu.iastate.netid.camerademo", photoFile);
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePhotoIntent, REQ_TAKE_PHOTO);
                    }
                    /* End full photo version */
                }
            }
        });
    }

    private File createPhotoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "DEMO_" + timeStamp;
        File storageDir = getExternalFilesDir(DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        switch(requestCode) {
            case REQ_PERMISSIONS_CAMERA:
                if(grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permissions granted.");
                } else {
                    Log.d(TAG, "Permissions not granted.");
                }
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode == REQ_TAKE_PHOTO) {
            if(resultCode != RESULT_OK) {
                Log.d(TAG, "Didn't get a photo.");
                return;
            }

            ImageView imageView = findViewById(R.id.imageView);

            /* Thumbnail version */
            //Bundle extras = (Bundle) data.getExtras();
            //Bitmap thumbnail = (Bitmap) extras.get("data");
            //imageView.setImageBitmap(thumbnail);
            /* End thumbnail version */

            /* Full photo version */
            try {
                Bitmap bitmap;
                if(Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                } else {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoURI);
                    bitmap = ImageDecoder.decodeBitmap(source);
                }
                imageView.setImageBitmap(bitmap);
            } catch(IOException e) {
                Log.d(TAG, "Unable to create bitmap.");
            }
            /* End full photo version */
        }
    }
}
