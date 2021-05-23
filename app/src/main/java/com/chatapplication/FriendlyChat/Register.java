package com.chatapplication.FriendlyChat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class Register extends AppCompatActivity{
    EditText fullName,emailAddress,password,rePassword,phoneNumber;
    Button signUpButton;
    ProgressBar progressBar;
    private FirebaseAuth auth;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullName = findViewById(R.id.full_Name);
        emailAddress = findViewById(R.id.email_id);
        password = findViewById(R.id.password1);
        rePassword =  findViewById(R.id.password2);
        signUpButton =(Button)findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.progressBar2);
        phoneNumber = findViewById(R.id.phone_number);

        progressBar.setVisibility(View.GONE);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                registerNewUser();
            }
        });

    }

    private void registerNewUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String fName = fullName.getText().toString();
        String email = emailAddress.getText().toString();
        String phone_number = phoneNumber.getText().toString();
        String pass = password.getText().toString();
        String pass2 = rePassword.getText().toString();
        if (TextUtils.isEmpty(fName)){
            Toast.makeText(getApplicationContext(),"please provide your Full Name",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"please provide your email",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(),"please check your password",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(phone_number)){
            Toast.makeText(getApplicationContext(),"please check your phone number",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(phone_number.length() != 10){
            Toast.makeText(getApplicationContext(),"Phone Number should be 10 digit for India",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (pass.length() < 8){
            Toast.makeText(getApplicationContext(),"password contains grater then 8 charector!!",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(pass2)){
            Toast.makeText(getApplicationContext(),"please Re-enter password",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!pass.equals(pass2)){
            Toast.makeText(getApplicationContext(),"password not matched",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            private final String TAG =null ;

            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Welcome to Frindly Chat App!!\nBy Jignesh Baria",Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                    createNewUser();
                    Intent intent = new Intent(Register.this,login.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Registration failed!!"
                                    + " Please try again later",
                            Toast.LENGTH_LONG)
                            .show();

                    // hide the progress bar
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }
    private void createNewUser() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        assert firebaseUser != null;
        userModel newUser = new userModel(fullName.getText().toString(),
                firebaseUser.getUid(), emailAddress.getText().toString(), phoneNumber.getText().toString());
        db.collection("users").document(firebaseUser.getUid()).set(newUser);

//        db.collection("users").document(firebaseUser.getUid()).set(newUser);

    }

    public void navigate_sign_in(View v){
        Intent inent = new Intent(this, login.class);
        startActivity(inent);
    }
}