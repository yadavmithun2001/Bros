package com.example.bros;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Set_profile extends AppCompatActivity {
    CircleImageView pfimage;
    EditText pf_name;
    Button final_submit;
    FloatingActionButton update_image;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedimage;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        pfimage = findViewById(R.id.set_profile_image);
        pf_name = findViewById(R.id.editName);
        final_submit = findViewById(R.id.submit);
        update_image = findViewById(R.id.updatepfimage);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading Profile");
        dialog.setMessage("Please Wait");

        update_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });





        final_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pf_name.getText().toString().isEmpty()){
                    FancyToast.makeText(Set_profile.this,"Field Cannot be Empty", Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }else {
                    if(selectedimage != null){
                        dialog.show();
                        StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                        reference.putFile(selectedimage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String Imageuri = uri.toString();
                                            String uid = auth.getUid();
                                            String phone = auth.getCurrentUser().getPhoneNumber();
                                            String name = pf_name.getText().toString();

                                            User user = new User(uid,name,phone,Imageuri);
                                            database.getReference()
                                                    .child("users")
                                                    .child(auth.getUid())
                                                    .setValue(user).
                                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            dialog.dismiss();
                                                            Intent i = new Intent(Set_profile.this,MainActivity.class);
                                                            i.putExtra("name",pf_name.getText().toString());
                                                            startActivity(i);
                                                            finish();
                                                        }
                                                    });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(data.getData() != null){
                pfimage.setImageURI(data.getData());
                selectedimage = data.getData();
            }
        }
    }
}