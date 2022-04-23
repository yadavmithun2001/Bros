package com.example.bros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Log_in extends AppCompatActivity {
    private EditText etphonenumber;
    private Button sendotp;
    ProgressBar pg;
    String verificationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        etphonenumber = findViewById(R.id.editphonenumber);
        sendotp  =findViewById(R.id.sendotp);
        pg = findViewById(R.id.progressBar);
        pg.setVisibility(View.INVISIBLE);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etphonenumber.getText().equals("")){
                    FancyToast.makeText(Log_in.this,"Phone Number Cann't be Empty",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }else {
                    if(etphonenumber.getText().toString().length() != 10){
                       FancyToast.makeText(Log_in.this,"Please Enter Valid Phone Number",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }else {
                        pg.setVisibility(View.VISIBLE);
                        sendotp.setVisibility(View.INVISIBLE);
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + etphonenumber.getText().toString(),
                                30,
                                TimeUnit.SECONDS,
                                Log_in.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        FancyToast.makeText(Log_in.this,"Code Sent Successfully",Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        FancyToast.makeText(Log_in.this,"Error! "+e.getMessage(),Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                        pg.setVisibility(View.INVISIBLE);
                                        sendotp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(s, forceResendingToken);
                                        verificationid = s;
                                        Intent i = new Intent(Log_in.this,Otp_verification.class);
                                        i.putExtra("verificationcode",verificationid);
                                        i.putExtra("phone",etphonenumber.getText().toString());
                                        startActivity(i);
                                        finish();
                                        pg.setVisibility(View.INVISIBLE);
                                    }
                                }
                        );
                    }

                }

            }
        });








        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent i = new Intent(Log_in.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }

}