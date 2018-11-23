package com.malik.bestenglish;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LayoutEditProfile extends Fragment{

    OnGetPhotoProfileView photoProfileView;
    interface OnGetPhotoProfileView{
        void profileView(ImageView editProfileImageUser);
    }

    OnTryGetBitmapPhoto bitmapPhoto;
    interface OnTryGetBitmapPhoto{
        Bitmap photoBitmap();
    }


    //define class
    private Context context;
    private Activity activity;
    private HelperCheckUserHasUpdateProfile editProfileCheckUserUpdate;
    private HelperUserGetPhoneNumber editProfilePhoneNumber;
    private HelperUploadFoto editUloadPhoto;
    private HelperGetPhotoProfileStorage editPhotoProfileStorage;
    private HelperToas editToas;
    private HelperLoading editLoading;
    private HelperGetProfileProperty profileProperty;

    public static LayoutEditProfile newInstance(){
        return new LayoutEditProfile();
    }

    //define view
    Button editProfileSaveButton;

    //define string
    String finalName,finalSex,finalAge,finalPhoneNumber,finalImageProfile;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity)context;
        this.photoProfileView = (OnGetPhotoProfileView)context;
        this.bitmapPhoto = (OnTryGetBitmapPhoto)context;

        editProfileCheckUserUpdate = new HelperCheckUserHasUpdateProfile(context);
        editProfilePhoneNumber = new HelperUserGetPhoneNumber(context);
        editUloadPhoto = new HelperUploadFoto(context);
        editPhotoProfileStorage = new HelperGetPhotoProfileStorage(context);
        editToas = new HelperToas(context);
        editLoading = new HelperLoading(context);
        profileProperty = new HelperGetProfileProperty(context);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View editeProvileView = inflater.inflate(R.layout.layout_edit_profile,container,false);
        editeProfileActivity(editeProvileView);
        return editeProvileView;
    }

    void editeProfileActivity(View editeProvileView){
        final ImageView editProfileImageUser = editeProvileView.findViewById(R.id.editProfileImageUser);
        final Button editeProfileImageButton = editeProvileView.findViewById(R.id.editeProfileImageButton);
        final EditText editeProfileEdtName = editeProvileView.findViewById(R.id.editeProfileEdtName);
        final Spinner editProfileSpinerJenisKelamin = editeProvileView.findViewById(R.id.editProfileSpinerJenisKelamin);
        final Spinner editProfileSpinerAge = editeProvileView.findViewById(R.id.editProfileSpinerAge);
        final TextView editProfilePhoneNumberInfo = editeProvileView.findViewById(R.id.editProfilePhoneNumberInfo);
        editProfileSaveButton = editeProvileView.findViewById(R.id.editProfileSaveButton);

        photoProfileView.profileView(editProfileImageUser);

        //define spiner sex
        final String[] spinerSex = {"choose sex","male","female"};
        ArrayAdapter<String> spinerSexAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item,spinerSex);
        spinerSexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editProfileSpinerJenisKelamin.setAdapter(spinerSexAdapter);

        //define spiner age
        List<String> spinerAge = new ArrayList<>();
        spinerAge.add("choose age");
        String[] spinerAgeExted = {"choose age"};
        for (int n=10;n<70;n++){
            spinerAge.add(String.valueOf(n));
        }
        spinerAgeExted = spinerAge.toArray(new String[spinerAge.size()]);

        ArrayAdapter<String> spinerAgeAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item,spinerAgeExted);
        spinerAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editProfileSpinerAge.setAdapter(spinerAgeAdapter);



        editProfilePhoneNumber.setOnUserGetPhoneNuber(new HelperUserGetPhoneNumber.OnUserGetPhoneNumber() {
            @Override
            public void phoneNumber(String phoneNumber) {
                editProfilePhoneNumberInfo.setText(phoneNumber);
            }
        });

        editeProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUloadPhoto.tryTakePhoto(HelperUploadFoto.PICTURE);
            }
        });

        final String[] finalSpinerAgeExted = spinerAgeExted;
        editProfileCheckUserUpdate.setOnCheckUserHasUpdateProfile(new HelperCheckUserHasUpdateProfile.OnCheckUserHasUpdateProfile() {
            @Override
            public void hasUpdateProfile() {
                profileProperty.addOnGetProfileProfile(new HelperGetProfileProperty.OnGetProfileProperty() {
                    @Override
                    public void propertyProfile(String name, String sex, String age, String phoneNumber, String imageProfile) {
                        editeProfileEdtName.setText(name);
                        int sexPosition = new ArrayList<String>(Arrays.asList(spinerSex)).indexOf(sex);
                        int agePosition = new ArrayList<String>(Arrays.asList(finalSpinerAgeExted)).indexOf(age);

                        editeProfileEdtName.setText(name);
                        editProfileSpinerJenisKelamin.setSelection(sexPosition);
                        editProfileSpinerAge.setSelection(agePosition);

                        final Transformation transformation = new RoundedTransformationBuilder()
                                .borderColor(Color.RED)
                                .borderWidth(1)
                                .cornerRadius(80)
                                .oval(false)
                                .build();
                        StorageReference getUrl = FirebaseStorage.getInstance().getReference();
                        getUrl.child("users/profile/"+phoneNumber+"/photoProfile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).transform(transformation).into(editProfileImageUser);
                            }
                        });


                        editeProfileImageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                editUloadPhoto.tryTakePhoto(HelperUploadFoto.PICTURE);

                            }
                        });
                        editProfileSaveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bitmap bbMap = bitmapPhoto.photoBitmap();
                                finalName = editeProfileEdtName.getText().toString().trim();
                                finalSex = editProfileSpinerJenisKelamin.getSelectedItem().toString().trim();
                                finalAge = editProfileSpinerAge.getSelectedItem().toString().trim();
                                finalPhoneNumber = editProfilePhoneNumberInfo.getText().toString().trim();

                                editPhotoProfileStorage.setOnGetPhotoProfileStorage(new HelperGetPhotoProfileStorage.OnGetPhotoProfileStorage() {
                                    @Override
                                    public void fotoProfile(Uri uri) {
                                        finalImageProfile = String.valueOf(uri);
                                        PropertyUserObject userObject = new PropertyUserObject();
                                        userObject.name = finalName;
                                        userObject.age = finalAge;
                                        userObject.sex = finalSex;
                                        userObject.phoneNumber = finalPhoneNumber;
                                        userObject.imageProfile = finalImageProfile;

                                        DatabaseReference editDatabase = FirebaseDatabase.getInstance().getReference().child("users/profile/"+finalPhoneNumber);
                                        editDatabase.setValue(userObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                editLoading.loadingHide();
                                                editToas.infoNya("sucsess update your profile");
                                            }
                                        });
                                    }
                                });
                            }
                        });


                    }
                });
            }

            @Override
            public void HasUpdateProfileYet() {
                editProfileSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editLoading.loadingShow("try connect to server",R.color.fui_transparent);
                        editProfilePhoneNumber.setOnUserGetPhoneNuber(new HelperUserGetPhoneNumber.OnUserGetPhoneNumber() {
                            @Override
                            public void phoneNumber(final String phoneNumber) {
                                Bitmap bbMap = bitmapPhoto.photoBitmap();
                                finalName = editeProfileEdtName.getText().toString().trim();
                                finalSex = editProfileSpinerJenisKelamin.getSelectedItem().toString().trim();
                                finalAge = editProfileSpinerAge.getSelectedItem().toString().trim();
                                finalPhoneNumber = editProfilePhoneNumberInfo.getText().toString().trim();
                                if (bbMap == null){
                                    Toast.makeText(activity.getApplicationContext(),"Please Upload your Photo First",Toast.LENGTH_LONG).show();
                                    editLoading.loadingHide();
                                    return;
                                }
                                if (TextUtils.isEmpty(finalName)){
                                    editToas.infoNya("name not be empty");
                                    editLoading.loadingHide();
                                    return;
                                }

                                if (finalSex.equals("choose sex")){
                                    editToas.infoNya("sex not be empty");
                                    editLoading.loadingHide();
                                    return;
                                }
                                if (finalAge.equals("choose age")){
                                    editToas.infoNya("age not be empty");
                                    editLoading.loadingHide();
                                    return;
                                }
                                editPhotoProfileStorage.setOnGetPhotoProfileStorage(new HelperGetPhotoProfileStorage.OnGetPhotoProfileStorage() {
                                    @Override
                                    public void fotoProfile(Uri uri) {
                                        finalImageProfile = String.valueOf(uri);
                                        PropertyUserObject userObject = new PropertyUserObject();
                                        userObject.name = finalName;
                                        userObject.age = finalAge;
                                        userObject.sex = finalSex;
                                        userObject.phoneNumber = finalPhoneNumber;
                                        userObject.imageProfile = finalImageProfile;
                                        DatabaseReference editDatabase = FirebaseDatabase.getInstance().getReference().child("users/profile/"+phoneNumber);
                                        editDatabase.setValue(userObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                editLoading.loadingHide();
                                                editToas.infoNya("sucsess update your profile");
                                            }
                                        });
                                    }
                                });

                            }
                        });
                    }
                });

            }
        });
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }
}
