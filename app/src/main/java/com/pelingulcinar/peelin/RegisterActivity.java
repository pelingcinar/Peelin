package com.pelingulcinar.peelin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreateBtn;



    private Toolbar mToolbar;

    private DatabaseReference mDatabase;

    //progress Dialog
    private ProgressDialog mRegProgress;


    //Firebase Auth

    private FirebaseAuth mAuth;

    public RegisterActivity(ProgressDialog mRegProgress) {
        this.mRegProgress = mRegProgress;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Toolbar Set
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Android Fields
        mDisplayName = (TextInputLayout) findViewById(R.id.reg_display_name);
        mEmail = (TextInputLayout) findViewById(R.id.reg_email);
        mPassword = (TextInputLayout) findViewById(R.id.reg_password);
        mCreateBtn = (Button) findViewById(R.id.reg_creat_btn);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String display_name = mDisplayName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if (!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {


                   /* mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account!");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();*/

                    register_user(display_name, email, password);

                }
            }
        });
    }


    /*public void setSupportActionBar(Toolbar mToolbar) {
    }*/

    private void register_user(final String display_name, String email, String password) {

       mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

               if (task.isSuccessful()) {

                   FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                   String uid = current_user.getUid();

                   mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                   String device_token = FirebaseInstanceId.getInstance().getToken();

                   HashMap<String, String> userMap = new HashMap<>();
                   userMap.put("name", display_name);
                   userMap.put("status", "Hi There I'm Using Hi Chat.");
                   userMap.put("image", "default");
                   userMap.put("thumb_image","default");
                   userMap.put("device_token", device_token);

                   //Bug fixes videosunda eklediği için device_token ekledim, ne işe yarıyor bilmiyorum

                   mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           if(task.isSuccessful()) {

                               Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                               mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               startActivity(mainIntent);
                               finish();

                              // mRegProgress.dismiss();

                   /*Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                   mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(mainIntent);
                   finish();*/

                  //Üstü yorum yapmışım, geri ekliyorum

                           }

                       }
                   });




               } else {


                   //mRegProgress.hide();
                   Toast.makeText(RegisterActivity.this, "Cannot Sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();

               }
           }
       });
    }
}
