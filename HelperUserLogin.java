package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HelperUserLogin {

    private OnCheckUserLogin userLogin;
    interface OnCheckUserLogin{
        void hasLogin();
        void hasLoginYet();
    }
    private Context context;
    private Activity activity;
    private FirebaseUser user;
    public HelperUserLogin(Context context) {
        this.context = context;
        this.activity = (Activity)context;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    void setOnUserHasLogin(OnCheckUserLogin login){
        if (user != null){
            login.hasLogin();
        }else {
            login.hasLoginYet();
        }
    }
}
