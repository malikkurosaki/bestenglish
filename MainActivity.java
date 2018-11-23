package com.malik.bestenglish;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class MainActivity extends AppCompatActivity implements LayoutEditProfile.OnGetPhotoProfileView,LayoutEditProfile.OnTryGetBitmapPhoto,LayoutProfile.OnUserProfileLoad{

    //import custom class
    private HelperUserLogin mainUserLogin;
    private HelperLoginActivity mainLoginActivity;
    private HelperToas mainToas;
    private HelperCheckUserHasUpdateProfile mainUserCheckUpdateProfile;
    private HelperLoading mainLoading;
    private HelperPermission mainPermission;
    private HelperUploadFoto mainUploadPhoto;
    private BridgeHelper mainPhotoProfile;
    private HelperGetProfileProperty mainGetProperty;
    private HelperCircleImage circleImage;
    private TextView myAppName;
    private HelperCheckMentorProfile checkMentorProfile;
    private MentorHelperAddBio mentorHelperAddBio;

    //import class
    Bitmap mainBitmapPhoto;

    //import view
    BottomNavigationView mainNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define custom class
        mainUserLogin = new HelperUserLogin(this);
        mainLoginActivity = new HelperLoginActivity(this);
        mainToas = new HelperToas(this);
        mainUserCheckUpdateProfile = new HelperCheckUserHasUpdateProfile(this);
        mainLoading = new HelperLoading(this);
        mainPermission = new HelperPermission(this);
        mainUploadPhoto = new HelperUploadFoto(this);
        mainUploadPhoto = new HelperUploadFoto(this);
        mainPhotoProfile = new BridgeHelper(this);
        mainGetProperty = new HelperGetProfileProperty(this);
        circleImage = new HelperCircleImage(this);
        checkMentorProfile = new HelperCheckMentorProfile(this);
        mentorHelperAddBio = new MentorHelperAddBio(this);

        //define view
        mainNavigation = findViewById(R.id.mainnavigation);
        myAppName = findViewById(R.id.myAppName);
        //set on menu cicked


        //mentor pipe

        //show loading if internet connection is down
        mainLoading.loadingShow("connect to server",R.color.colorPutih);
        mainUserLogin.setOnUserHasLogin(new HelperUserLogin.OnCheckUserLogin() {
            @Override
            public void hasLogin() {
                Toast.makeText(getApplicationContext(),"sudah login",Toast.LENGTH_LONG).show();
                mainUserCheckUpdateProfile.setOnCheckUserHasUpdateProfile(new HelperCheckUserHasUpdateProfile.OnCheckUserHasUpdateProfile() {
                    @Override
                    public void hasUpdateProfile() {
                        mainPermission.tryRequestPermission(HelperPermission.PERMISSION_CODE);
                        mainLoading.loadingHide();
                        mentorPipeActivity();
                        //ternyataKamuMentor();
                        Toast.makeText(getApplicationContext(),"has update profile",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void HasUpdateProfileYet() {
                        mainLoading.loadingHide();
                        FragmentTransaction updateYettransaction = getSupportFragmentManager().beginTransaction();
                        updateYettransaction.replace(R.id.mainContainer,LayoutEditProfile.newInstance());
                        updateYettransaction.commit();

                    }
                });
            }

            @Override
            public void hasLoginYet() {
                Toast.makeText(getApplicationContext(),"BElum losgin",Toast.LENGTH_LONG).show();
                mainLoginActivity.tryLogin(HelperLoginActivity.USER_LOGIN_CODE);
                mainLoading.loadingHide();
            }
        });

        //navigation menu bottom
        navigationMenuBottom();

    }

    //onActivity Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperLoginActivity.USER_LOGIN_CODE && resultCode == RESULT_OK){
            mainToas.infoNya("selamat anda berhasil login");
            finish();
            startActivity(getIntent());
        }
        if (requestCode == HelperUploadFoto.PICTURE){
            mainBitmapPhoto = (Bitmap) data.getExtras().get("data");
            mainPhotoProfile.setOnGetView(mainBitmapPhoto);
            mainUploadPhoto.tryUploadPhoto(mainBitmapPhoto);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == HelperPermission.PERMISSION_CODE){
            Toast.makeText(getApplicationContext(),"akses telah diberikan, pastikan semua telah diberi akses, pilih pengaturan pada settng aplikasi",Toast.LENGTH_LONG).show();
        }

    }


    void defaultView(){
        FragmentTransaction defauldTransacctions = getSupportFragmentManager().beginTransaction();
        defauldTransacctions.replace(R.id.mainContainer,LayoutHome.newInstance());
        defauldTransacctions.commit();
    }

    void navigationMenuBottom(){
        mainNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment itemView = LayoutHome.newInstance();
                switch (menuItem.getItemId()){
                    case R.id.menu1:
                        itemView = LayoutHome.newInstance();
                        break;
                    case R.id.menu2:
                        itemView = LayoutProduct.newInstance();
                        break;
                    case R.id.menu3:
                        itemView = LayoutTranslate.neeInstance();
                        break;
                    case R.id.menu4:
                        itemView = LayoutArticle.newInstance();
                        break;
                    case R.id.menu5:
                        itemView = LayoutProfile.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainContainer,itemView);
                transaction.commit();
                return true;
            }
        });

    }

    @Override
    public void profileView(final ImageView editProfileImageUser) {
        mainPhotoProfile.addOnGetView(new BridgeHelper.OnBridgePhotoProfileView() {
            @Override
            public void profileView(Bitmap bitmapPhoto) {
                editProfileImageUser.setImageBitmap(bitmapPhoto);
            }
        });
    }

    @Override
    public Bitmap photoBitmap() {

        return mainBitmapPhoto;
    }

    @Override
    public void propertyLoad(final ImageView profilePhotoUserView, final TextView profileUserName, TextView profileSaldo, TextView profileStatus, final TextView profileInfoName, final TextView profileInfoSex, final TextView profileInfoAge, final TextView profileInfoPhoneNumber) {
        mainGetProperty.addOnGetProfileProfile(new HelperGetProfileProperty.OnGetProfileProperty() {
            @Override
            public void propertyProfile(String name, String sex, String age, String phoneNumber, String imageProfile) {
                profileUserName.setText(name);
                profileInfoName.setText(name);
                profileInfoAge.setText(age);
                profileInfoSex.setText(sex);
                profileInfoPhoneNumber.setText(phoneNumber);


                final Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(1)
                        .cornerRadiusDp(60)
                        .oval(false)
                        .build();
                StorageReference getUrl = FirebaseStorage.getInstance().getReference();
                getUrl.child("users/profile/"+phoneNumber+"/photoProfile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).transform(transformation).into(profilePhotoUserView);
                    }
                });
            }
        });
    }

    //mentor pipe
    void mentorPipeActivity(){
        myAppName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MentorCheckBolehNambah bolehKah = new MentorCheckBolehNambah(MainActivity.this);
                bolehKah.apakahBolehTambahMentor(new MentorCheckBolehNambah.OnCheckApakahBolehNambahListener() {
                    @Override
                    public void jawabannya(String apakahBoleh) {
                        if (!apakahBoleh.equals("true")){
                            myAppName.setTextColor(getResources().getColor(R.color.colorMerah));
                            return;
                        }else {
                            myAppName.setTextColor(getResources().getColor(R.color.colorBiru));
                            final Dialog mentorDialog = new Dialog(MainActivity.this);
                            mentorDialog.setCanceledOnTouchOutside(false);

                            final View mentorDialogView = getLayoutInflater().inflate(R.layout.layout_input_mentor_code,null,false);
                            mentorDialog.setContentView(mentorDialogView);

                            final HelperDapatkanMentorCode mentorCode = new HelperDapatkanMentorCode(MainActivity.this);
                            mentorCode.SetOnDapatkanCode(new HelperDapatkanMentorCode.DapatkanCode() {
                                @Override
                                public void code(final long theCode) {
                                    mentorDialog.show();
                                    Button enterCode = mentorDialogView.findViewById(R.id.mentorEnterCode);

                                    //on dialog mentor enter
                                    enterCode.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            EditText inputCode = mentorDialogView.findViewById(R.id.mentorCodeInput);
                                            final TextView mentorInfo = mentorDialogView.findViewById(R.id.mentorDialogInfo);

                                            inputCode.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    mentorInfo.setText(" ");
                                                }
                                            });

                                            String hasilCode = inputCode.getText().toString().trim();
                                            if (TextUtils.isEmpty(hasilCode)){
                                                inputCode.setError("not be empty seriously!");
                                                return;
                                            }
                                            String theCodeString = String.valueOf(theCode);

                                            if (theCodeString.equals(hasilCode)){
                                                Toast.makeText(getApplicationContext(),"awesome baby",Toast.LENGTH_LONG).show();
                                                final Intent pindahActivity = new Intent(MainActivity.this,MainMentorActivity.class);
                                                checkMentorProfile.addOnmentorUpdateProfile(new HelperCheckMentorProfile.CheckOnMentorHasUpdateProfile() {
                                                    @Override
                                                    public void hasUpdat(String mentorPhoneNumber) {
                                                        finish();
                                                        startActivity(pindahActivity);
                                                    }

                                                    @Override
                                                    public void updateYet() {
                                                        Toast.makeText(getApplicationContext(),"no udate profile yet",Toast.LENGTH_LONG).show();
                                                        mentorHelperAddBio.setOnMentorAddBio(new MentorHelperAddBio.OnMentorAddBio() {
                                                            @Override
                                                            public void addBio() {
                                                                finish();
                                                                startActivity(pindahActivity);
                                                            }
                                                        });
                                                    }
                                                });
                                            }else {
                                                mentorInfo.setText("you got a mistake baby , fix it or i'll kick you as posible");
                                            }
                                        }
                                    });


                                }
                            });
                        }
                    }
                });

                return true;
            }
        });

    }

    void ternyataKamuMentor(){
        checkMentorProfile.addOnmentorUpdateProfile(new HelperCheckMentorProfile.CheckOnMentorHasUpdateProfile() {
            @Override
            public void hasUpdat(String mentorPhoneNumber) {
                finish();
                Intent pindahKursi = new Intent(MainActivity.this,MainMentorActivity.class);
                startActivity(pindahKursi);
            }

            @Override
            public void updateYet() {

            }
        });
    }
}
