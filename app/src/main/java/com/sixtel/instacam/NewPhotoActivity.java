package com.sixtel.instacam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class NewPhotoActivity extends AppCompatActivity implements NewPhotoFragment.Contract {
    private static final int CAMERA_REQUEST = 10;
    public static final String PHOTO_EXTRA = "PHOTO_EXTRA";
//    private static final String PHOTO_STATE_EXTRA = "PHOTO";
    private Photo mPhoto;
    private NewPhotoFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment = (NewPhotoFragment) getSupportFragmentManager().findFragmentById(R.id.new_photo_frag_container);
        if (fragment == null) {
            fragment = new NewPhotoFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.new_photo_frag_container, fragment)
                    .commit();

        }

    }


    public void launchCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mPhoto = new Photo();

        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto.getFile())); //lets the phone know where to save the photo

        startActivityForResult(i, CAMERA_REQUEST);
    }

    @Override
    public void finishedPhoto(Photo photo) {
        Intent i = new Intent();
        i.putExtra(PHOTO_EXTRA, photo);
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                fragment.updatePhoto(mPhoto);
            }
        }
    }

}
