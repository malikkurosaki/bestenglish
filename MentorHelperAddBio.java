package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MentorHelperAddBio {

    interface OnMentorAddBio{
        void addBio();
    }

    private Context context;
    private Activity activity;
    private DatabaseReference databaseReference;
    private HelperGetUserProperty userProperty;
    private HelperLoading helperLoading;

    MentorHelperAddBio(Context context) {
        this.context = context;
        this.activity = (Activity)context;

        userProperty = new HelperGetUserProperty(context);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        helperLoading = new HelperLoading(context);
    }

    void setOnMentorAddBio(final OnMentorAddBio mentorAddBio){
        //helperLoading.loadingShow("connect to server",R.color.fui_transparent);
        userProperty.setOnUserGetProperty(new HelperGetUserProperty.OnUserGetProperty() {
            @Override
            public void userProperty(String name, String sex, String age, final String phoneNumber, String imageProfile) {
                PropertyUserObject mentorObject = new PropertyUserObject();
                mentorObject.name = name;
                mentorObject.sex = sex;
                mentorObject.age = age;
                mentorObject.imageProfile = imageProfile;
                mentorObject.phoneNumber = phoneNumber;
                databaseReference.child("mentors/profile/"+phoneNumber+"/bio").setValue(mentorObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity.getApplicationContext(),"update mentor profile successfuly",Toast.LENGTH_LONG).show();


                        Map<String, Object> tambahBio = new HashMap<>();
                        tambahBio.put("phone",phoneNumber);
                        tambahBio.put("tanggal",10);
                        tambahBio.put("bulan",10);
                        tambahBio.put("tahun",2018);
                        tambahBio.put("jam",10);


                        databaseReference.child("mentors/profile/"+phoneNumber+"/jobs").push().child("job").setValue(tambahBio).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mentorAddBio.addBio();
                               // helperLoading.loadingHide();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity.getApplicationContext(),"internet error",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
