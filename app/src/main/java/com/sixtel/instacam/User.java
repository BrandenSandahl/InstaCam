package com.sixtel.instacam;

import com.facebook.model.GraphObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by branden on 7/20/16.
 */
public class User implements Serializable {


    private String mFirstName;
    private String mLastName;
    private Date mBirthday;
    private String mProfilePictureURL;


    private static User sCurrentUser;

    public static User getCurrentUser() {
        return sCurrentUser;
    }

    public static void setCurrentUser(GraphObject graphObject) throws ParseException {
        if (sCurrentUser == null) {
            sCurrentUser = new User(graphObject);
        }
    }

    public User(GraphObject graphObject) throws ParseException {
        this.mFirstName = (String) graphObject.getProperty("first_name");
        this.mLastName = (String) graphObject.getProperty("last_name");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.mBirthday = simpleDateFormat.parse((String)graphObject.getProperty("birthday"));
        this.mProfilePictureURL = (String) graphObject.getPropertyAs("picture", GraphObject.class)
            .getPropertyAs("data", GraphObject.class)
            .getProperty("url");
    }


    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public Date getBirthday() {
        return mBirthday;
    }

    public String getProfilePictureURL() {
        return mProfilePictureURL;
    }

    @Override
    public String toString() {
        return "User{" +
                "mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mBirthday=" + mBirthday +
                ", mProfilePictureURL='" + mProfilePictureURL + '\'' +
                '}';
    }
}
