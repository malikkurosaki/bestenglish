package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelperCheckMentorProfile {

    interface CheckOnMentorHasUpdateProfile{
        void hasUpdat(String mentorPhoneNumber);
        void updateYet();
    }

    private Context context;
    private Activity activity;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private String mentorPhoneNumber;
    public HelperCheckMentorProfile(Context context) {
        this.context = context;
        this.activity = (Activity)context;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    void addOnmentorUpdateProfile(final CheckOnMentorHasUpdateProfile updateProfile){

        if (user != null){
            mentorPhoneNumber = user.getPhoneNumber();
        }else {
            Toast.makeText(activity.getApplicationContext(),"please check your internet connection",Toast.LENGTH_LONG).show();
            return;
        }

        databaseReference.child("mentors/profile/"+mentorPhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("bio")){
                    updateProfile.hasUpdat(mentorPhoneNumber);
                }else {
                    updateProfile.updateYet();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
