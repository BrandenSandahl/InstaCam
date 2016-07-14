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

public class NewPhotoActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 10;
    public static final String PHOTO_EXTRA = "PHOTO_EXTRA";
    private static final String PHOTO_STATE_EXTRA = "PHOTO";
    private Photo mPhoto;
    private ImageView mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mPreview = (ImageView) findViewById(R.id.photo_preview);
        Button saveButton = (Button) findViewById(R.id.save_new_photo);
        final EditText caption = (EditText) findViewById(R.id.new_photo_caption);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoto.setCaption(caption.getText().toString());
                Intent i = new Intent();
                i.putExtra(PHOTO_EXTRA, mPhoto);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        if (savedInstanceState != null) {
            mPhoto = (Photo) savedInstanceState.getSerializable(PHOTO_STATE_EXTRA);
        }

        if (mPhoto == null) {
            launchCamera();
        } else {
            loadThumbnail(mPhoto);
        }

    }


    private void launchCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mPhoto = new Photo();

        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto.getFile())); //lets the phone know where to save the photo

        startActivityForResult(i, CAMERA_REQUEST);
    }

    private void loadThumbnail(Photo photo) {
        Picasso.with(this).load(mPhoto.getFile()).into(mPreview);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                loadThumbnail(mPhoto);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(PHOTO_STATE_EXTRA, mPhoto);
    }
}
