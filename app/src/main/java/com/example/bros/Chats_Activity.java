package com.example.bros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chats_Activity extends AppCompatActivity {
    CircleImageView imageView;
    TextView chat_username;
    FloatingActionButton sendmsg;
    ImageView back;
    EditText typeamessage;
    RecyclerView chatrecycle;
    ChatModel chatModel;
    ArrayList<ChatModel> list = new ArrayList<>();
    String senderRoom,recieverRoom;
    MessageAdapter messageAdapter;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        chat_username = findViewById(R.id.chat_username);
        chat_username.setText(getIntent().getStringExtra("username"));
        sendmsg  = findViewById(R.id.sendmessage);
        typeamessage = findViewById(R.id.typeamessage);
        typeamessage.addTextChangedListener(textWatcher);
        imageView = findViewById(R.id.profile_image_chat);
        back = findViewById(R.id.back);
        database = FirebaseDatabase.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Chats_Activity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        Glide.with(this).load(getIntent().getStringExtra("imageurl")).into(imageView);


        chatrecycle = findViewById(R.id.chat_recycle);
        messageAdapter = new MessageAdapter(list,this);
        chatrecycle.setAdapter(messageAdapter);
        chatrecycle.setLayoutManager(new LinearLayoutManager(this));
        chatrecycle.scrollToPosition(list.size());

        String recieveruid = getIntent().getStringExtra("uid");
        String senderuid = FirebaseAuth.getInstance().getUid();
        senderRoom = senderuid + recieveruid;
        recieverRoom = recieveruid + senderuid;

        database.getReference().child("chats")
                .child(senderRoom)
                .child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                            list.add(chatModel);
                        }
                        messageAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        String localTime = date.format(currentLocalTime);

        sendmsg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(typeamessage.getText().toString().isEmpty()){
                   typeamessage.setError("Please Enter a Message");
               }else {
                   String messagetxt = typeamessage.getText().toString();
                   ChatModel chatModel = new ChatModel(messagetxt,senderuid,localTime);
                   list.add(new ChatModel(messagetxt,senderuid,localTime));

                   typeamessage.setText("");
                   database.getReference().child("chats")
                           .child(senderRoom)
                           .child("messages")
                           .push()
                           .setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           database.getReference().child("chats")
                                   .child(recieverRoom)
                                   .child("messages")
                                   .push()
                                   .setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {

                               }
                           });
                       }
                   });

               }


           }
       });

       if(typeamessage.getText().toString().isEmpty()){
           sendmsg.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   Toast.makeText(Chats_Activity.this,"Recording Audio",Toast.LENGTH_SHORT).show();
                   return false;
               }
           });
       }

    }
    public TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(!typeamessage.getText().toString().isEmpty()){
                sendmsg.setImageResource(R.drawable.send_msg);
            }else {
                sendmsg.setImageResource(R.drawable.ic_baseline_keyboard_voice_24);
            }
            if(typeamessage.getText().toString().length() == 12){
                typeamessage.setHeight(140);
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!typeamessage.getText().toString().isEmpty()){
                sendmsg.setImageResource(R.drawable.send_msg);
            }else {
                sendmsg.setImageResource(R.drawable.ic_baseline_keyboard_voice_24);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!typeamessage.getText().toString().isEmpty()){
                sendmsg.setImageResource(R.drawable.send_msg);
            }else {
                sendmsg.setImageResource(R.drawable.ic_baseline_keyboard_voice_24);
            }
        }
    };
}