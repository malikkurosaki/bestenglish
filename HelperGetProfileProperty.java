package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelperGetProfileProperty {

    private OnGetProfileProperty mProperty;
    interface OnGetProfileProperty{
        void propertyProfile(String name, String sex, String age, String phoneNumber, String imageProfile);
    }

    private Context context;
    private Activity activity;
    private HelperUserGetPhoneNumber propertyPhone;
    private DatabaseReference propertyDatabase;


    HelperGetProfileProperty(Context context) {
        this.context = context;
        this.activity = (Activity)context;

        propertyPhone = new HelperUserGetPhoneNumber(context);
        propertyDatabase = FirebaseDatabase.getInstance().getReference();


    }

    void addOnGetProfileProfile(final OnGetProfileProperty propertyProperty){
        propertyPhone.setOnUserGetPhoneNuber(new HelperUserGetPhoneNumber.OnUserGetPhoneNumber() {
            @Override
            public void phoneNumber(String phoneNumber) {
               propertyDatabase.child("users/profile/"+phoneNumber).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       PropertyUserObject objectUser = dataSnapshot.getValue(PropertyUserObject.class);
                       if (objectUser != null) {
                           propertyProperty.propertyProfile(objectUser.name, objectUser.sex, objectUser.age, objectUser.phoneNumber, objectUser.imageProfile);
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
            }
        });
    }
}
