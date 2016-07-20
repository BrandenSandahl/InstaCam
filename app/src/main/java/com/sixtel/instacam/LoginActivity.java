package com.sixtel.instacam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.text.ParseException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private UiLifecycleHelper mUiLifecycleHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_birthday");  //asks for birthday permission too

        mUiLifecycleHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChanged(session, state, exception);

            }
        });
        mUiLifecycleHelper.onCreate(savedInstanceState);
    }


    private void onSessionStateChanged(final Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {

            /* This asks for a new permission to be added once a user has already signed in */
            List<String> permissions = Session.getActiveSession().getPermissions();
            boolean hasBirthdayPermission = false;
            for (String permission : permissions) {
                if (permission.equals("user_birthday")) {
                    hasBirthdayPermission = true;
                }
            }

            if (!hasBirthdayPermission) {
                Session.NewPermissionsRequest permissionsRequest = new Session.NewPermissionsRequest(this, "user_birthday");
                session.requestNewReadPermissions(permissionsRequest);
                return;
            }
            /* end of permission part */

            Bundle parameters = new Bundle();
            parameters.putString("fields", "picture,first_name,last_name,birthday");

            Request request = new Request(session, "/me", parameters, HttpMethod.GET, new Request.Callback() {
                @Override
                public void onCompleted(Response response) {
                    if (session == Session.getActiveSession()) {
                        if (response.getGraphObject() != null) {
                            try {
                                User.setCurrentUser(response.getGraphObject());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                    if (response.getError() != null) {
                        Log.d(TAG, "Error id " + response.getError());
                    }

                }
            });
            request.executeAsync();

        } else {
            Log.d(TAG, "Closed");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUiLifecycleHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUiLifecycleHelper.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUiLifecycleHelper.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUiLifecycleHelper.onDestroy();
    }
}

