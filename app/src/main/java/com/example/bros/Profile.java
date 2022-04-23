package com.example.bros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    CircleImageView profile_image;
    TextView userphone,name;
    ImageView back;
    LinearLayout ltusername,ltabout;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        back = findViewById(R.id.back);
        name = findViewById(R.id.name);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        profile_image = findViewById(R.id.pfimage);



        name.setText(getIntent().getStringExtra("name"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        ltusername = findViewById(R.id.userltout);
        ltusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile.this, "Username Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        ltabout = findViewById(R.id.aboutltout);
        ltabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile.this, "About Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        userphone = findViewById(R.id.userphone);
        userphone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
    }
}