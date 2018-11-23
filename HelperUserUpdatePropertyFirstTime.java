package com.malik.bestenglish;

import android.app.Activity;
import android.app.AliasActivity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HelperUserUpdatePropertyFirstTime {



    private Context context;
    private Activity activity;
    private DatabaseReference firstPropertyDatabase;
    public HelperUserUpdatePropertyFirstTime(Context context) {
        this.context = context;
        this.activity = (Activity)context;

        firstPropertyDatabase = FirebaseDatabase.getInstance().getReference();
    }

    void addOnUpdateProperty(String name,String sex,String age,String phoneNumber,String imageProfile){
        PropertyUserObject firstObjectProperty = new PropertyUserObject();
        firstObjectProperty.name = name;
        firstObjectProperty.sex = sex;
        firstObjectProperty.age = age;
        firstObjectProperty.phoneNumber = phoneNumber;
        firstObjectProperty.imageProfile = imageProfile;

        firstPropertyDatabase.child("users/profile/"+phoneNumber).setValue(firstObjectProperty).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity.getApplicationContext(),"data sucsess updated",Toast.LENGTH_LONG).show();
            }
        });
    }
}
