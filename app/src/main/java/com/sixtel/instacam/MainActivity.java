package com.sixtel.instacam;


import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.File;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    private static final int CAMERA_REQUEST = 10;
    private static final String TAG = "MainActivity";
    private File mPhoto;
    private FeedFragment mFeedFragment;
    private MaterialTabHost mTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.feed_container, mFeedFragment)
                    .commit();


        }
    }



        public void onClick(View v) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM); //directory path to where photos are stored
            //random file name
            mPhoto = new File(directory, "sample.jpeg");

            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto)); //lets the phone know where to save the photo

            startActivityForResult(i, CAMERA_REQUEST);

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(mPhoto), "image/jpeg");

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
        mTabBar.setSelectedNavigationItem(tab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
}
