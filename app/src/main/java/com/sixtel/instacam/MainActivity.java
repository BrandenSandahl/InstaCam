package com.sixtel.instacam;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.io.File;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    private static final int CAMERA_REQUEST = 10;
    private static final String TAG = "MainActivity";
    private Photo mPhoto;
    private FeedFragment mFeedFragment;
    private ProfileFragment mProfileFragment;
    private MaterialTabHost mTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ImageButton cameraFAB = (ImageButton) findViewById(R.id.camera_fab);
        cameraFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mPhoto = new Photo();

                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto.getFile())); //lets the phone know where to save the photo

                startActivityForResult(i, CAMERA_REQUEST);
            }
        });



        mTabBar = (MaterialTabHost) findViewById(R.id.tab_bar);
        mTabBar.addTab(mTabBar.newTab().setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_home, null)).setTabListener(this));
        mTabBar.addTab(mTabBar.newTab().setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_profile, null)).setTabListener(this));


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                "android.permission.CAMERA")
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{"android.permission.CAMERA"},
                    0);
        }


        mFeedFragment = (FeedFragment) getSupportFragmentManager().findFragmentById(R.id.feed_container);
        if (mFeedFragment == null) {
            mFeedFragment = new FeedFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.feed_container, mFeedFragment)
                    .commit();


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(mPhoto.getFile()), "image/jpeg");
                startActivity(i);
            }
        }
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




    /** Material Tab library stuff */
    @Override
    public void onTabSelected(MaterialTab tab) {
        int position = tab.getPosition();
        mTabBar.setSelectedNavigationItem(position);


        Fragment fragment;
        switch (position) {
            case 0:
                fragment = mFeedFragment;
                break;
            case 1:
                if (mProfileFragment == null) {
                    mProfileFragment = new ProfileFragment();
                }
                fragment = mProfileFragment;
                break;
            default:
                fragment = mFeedFragment;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.feed_container, fragment)
                .commit();

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
}
