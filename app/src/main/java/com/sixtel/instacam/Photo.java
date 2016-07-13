package com.sixtel.instacam;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

/**
 * Created by branden on 7/6/16.
 */
public class Photo {
    private static final File sDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM); //directory path to where photos are stored
    private UUID mID;

    public Photo() {
        mID = UUID.randomUUID();
    }


    public UUID getID() {
        return mID;
    }



    public File getFile() {
        return new File(sDirectory, mID.toString());
    }


}
