package com.sixtel.instacam;

import android.os.Environment;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by branden on 7/6/16.
 */
public class Photo implements Serializable {
    private static final File sDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM); //directory path to where photos are stored
    private UUID mID;
    private String mCaption;
    private User mUser;



    public Photo() {
        mID = UUID.randomUUID();
    }


    public UUID getID() {
        return mID;
    }



    public File getFile() {
        return new File(sDirectory, mID.toString());
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
