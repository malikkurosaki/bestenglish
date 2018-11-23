package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelperDapatkanMentorCode {

    interface DapatkanCode{
        void code(long theCode);
    }

    private Context context;
    private Activity activity;
    private DatabaseReference databaseReference;

    HelperDapatkanMentorCode(Context context) {
        this.context = context;
        this.activity = (Activity)context;
    }

    void SetOnDapatkanCode(final DapatkanCode codeya){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users/property/jobs/mentor/code").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long theCode = dataSnapshot.getValue(long.class);
                codeya.code(theCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
