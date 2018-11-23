package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelperCheckUserHasUpdateProfile {

    interface OnCheckUserHasUpdateProfile{
        void hasUpdateProfile();
        void HasUpdateProfileYet();
    }

    private Context context;
    private Activity activity;
    private DatabaseReference databaseReference;
    private HelperUserGetPhoneNumber updateProfilePhoneNumber;
    public HelperCheckUserHasUpdateProfile(Context context) {
        this.context = context;
        this.activity = (Activity)context;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        updateProfilePhoneNumber = new HelperUserGetPhoneNumber(context);
    }

    void setOnCheckUserHasUpdateProfile(final OnCheckUserHasUpdateProfile updateProfile){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final DataSnapshot snapshot = dataSnapshot.child("users/profile");
                updateProfilePhoneNumber.setOnUserGetPhoneNuber(new HelperUserGetPhoneNumber.OnUserGetPhoneNumber() {
                    @Override
                    public void phoneNumber(String phoneNumber) {
                        if (snapshot.hasChild(phoneNumber)){
                            updateProfile.hasUpdateProfile();
                        }else {
                            updateProfile.HasUpdateProfileYet();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
