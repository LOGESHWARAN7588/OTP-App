package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class entermobilenumber extends AppCompatActivity {
    EditText enternumber;
    Button getoptbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entermobilenumber);

        enternumber = findViewById(R.id.input_mobile_number);
        getoptbutton = findViewById(R.id.buttongetotp);


        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);


        getoptbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!enternumber.getText().toString().trim().isEmpty()) {
                    if ((enternumber.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        getoptbutton.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(), 60, TimeUnit.SECONDS,
                                entermobilenumber.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getoptbutton.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getoptbutton.setVisibility(View.VISIBLE);
                                        Toast.makeText(entermobilenumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        progressBar.setVisibility(View.GONE);
                                        getoptbutton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), verificationpage.class);
                                        intent.putExtra("mobile", enternumber.getText().toString());
                                        intent.putExtra("backotp", backendotp);
                                        startActivity(intent);
                                    }
                                });


                    } else {
                        Toast.makeText(entermobilenumber.this, "please enter correct numner", Toast.LENGTH_LONG);
                    }
                } else {
                    Toast.makeText(entermobilenumber.this, "enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
