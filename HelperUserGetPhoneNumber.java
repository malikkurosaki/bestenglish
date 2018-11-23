package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HelperUserGetPhoneNumber {

    interface OnUserGetPhoneNumber{
        void phoneNumber(String phoneNumber);
    }
    private Context context;
    private Activity activity;
    private FirebaseUser userGetPhoneNumber;
    HelperUserGetPhoneNumber(Context context) {
        this.context = context;
        this.activity = (Activity)context;
        userGetPhoneNumber = FirebaseAuth.getInstance().getCurrentUser();
    }

    void setOnUserGetPhoneNuber(OnUserGetPhoneNumber getPhoneNuber){
        if (userGetPhoneNumber.getPhoneNumber() != null){
            getPhoneNuber.phoneNumber(userGetPhoneNumber.getPhoneNumber());
        }
    }
}
