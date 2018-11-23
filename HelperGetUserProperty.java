package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelperGetUserProperty {


    interface OnUserGetProperty{
        void userProperty(String name, String sex, String age, String phoneNumber, String imageProfile);
    }

    private Context context;
    private Activity activity;
    private DatabaseReference helperPropertyDatabase;
    private HelperUserGetPhoneNumber helperPropertyPhoneNumber;
    public HelperGetUserProperty(Context context) {
        this.context = context;
        this.activity = (Activity)context;

        helperPropertyDatabase = FirebaseDatabase.getInstance().getReference();
        helperPropertyPhoneNumber = new HelperUserGetPhoneNumber(context);
    }

    void setOnUserGetProperty(final OnUserGetProperty getProperty){
        helperPropertyPhoneNumber.setOnUserGetPhoneNuber(new HelperUserGetPhoneNumber.OnUserGetPhoneNumber() {
            @Override
            public void phoneNumber(String phoneNumber) {
                helperPropertyDatabase.child("users/profile/"+phoneNumber).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        PropertyUserObject property = dataSnapshot.getValue(PropertyUserObject.class);
                        getProperty.userProperty(property.name,property.sex,property.age,property.phoneNumber,property.imageProfile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}
