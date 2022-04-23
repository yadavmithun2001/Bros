package com.example.bros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Otp_verification extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5,et6;
    TextView cnf_phone,wrongnumber,resend;
    Button verifyotp;
    ProgressBar pg2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        et1 = findViewById(R.id.editotp1);
        et2 = findViewById(R.id.editotp2);
        et3 = findViewById(R.id.editotp3);
        et4 = findViewById(R.id.editotp4);
        et5 = findViewById(R.id.editotp5);
        et6 = findViewById(R.id.editotp6);
        pg2 = findViewById(R.id.progressBar2);
        pg2.setVisibility(View.INVISIBLE);
        wrongnumber = findViewById(R.id.wrongnumber);
        cnf_phone = findViewById(R.id.cnf_phone);
        resend = findViewById(R.id.resendotp);
        verifyotp = findViewById(R.id.verifyotp);
        String verificationid = getIntent().getStringExtra("verificationcode");
        cnf_phone.setText("+91 "+getIntent().getStringExtra("phone"));
        otpnext();
        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et1.getText().toString().isEmpty() && !et2.getText().toString().isEmpty() && !et3.getText().toString().isEmpty() &&
                        !et4.getText().toString().isEmpty() && !et5.getText().toString().isEmpty() && !et6.getText().toString().isEmpty()){
                    String code = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString()
                            + et5.getText().toString() + et6.getText().toString();
                    if(verificationid != null){
                        pg2.setVisibility(View.VISIBLE);
                        verifyotp.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationid,code);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent i = new Intent(Otp_verification.this,Set_profile.class);
                                    FancyToast.makeText(Otp_verification.this,"Verification Successful", Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                    startActivity(i);
                                    finish();

                                }else {
                                    pg2.setVisibility(View.INVISIBLE);
                                    verifyotp.setVisibility(View.VISIBLE);
                                    FancyToast.makeText(Otp_verification.this,"OTP Entered was Wrong",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                }
                            }
                        });

                    }

                }else {
                    FancyToast.makeText(Otp_verification.this,"OTP Cannot be Empty",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
            }
        });
        wrongnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Otp_verification.this,Log_in.class);
                startActivity(i);
                finish();
            }
        });


    }

    public void otpnext(){
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!et1.getText().toString().isEmpty()){
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!et1.getText().toString().isEmpty()){
                    et2.requestFocus();
                }
            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!et2.getText().toString().isEmpty()){
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!et2.getText().toString().isEmpty()){
                    et3.requestFocus();
                }else {
                    et1.requestFocus();
                }
            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!et3.getText().toString().isEmpty()){
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!et3.getText().toString().isEmpty()){
                    et4.requestFocus();
                }else {
                    et2.requestFocus();
                }
            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!et4.getText().toString().isEmpty()){
                    et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!et4.getText().toString().isEmpty()){
                    et5.requestFocus();
                }else {
                    et3.requestFocus();
                }
            }
        });
        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!et5.getText().toString().isEmpty()){
                    et6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!et5.getText().toString().isEmpty()){
                    et6.requestFocus();
                }else {
                    et4.requestFocus();
                }
            }
        });
        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et6.getText().toString().isEmpty()){
                    et5.requestFocus();
                }
            }
        });
    }
}