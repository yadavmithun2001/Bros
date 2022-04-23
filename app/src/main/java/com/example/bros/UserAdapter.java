package com.example.bros;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Usersviewholder>{
    ArrayList<User> users = new ArrayList<>();
    Context context;

    public UserAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public Usersviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_recycle,parent,false);
        return new Usersviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Usersviewholder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getName());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("chats")
                .child(user.getUid()+FirebaseAuth.getInstance().getUid())
                .child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ChatModel chatModel = snapshot1.getValue(ChatModel.class);
                    holder.lastmessage.setText(chatModel.getMessage());
                    holder.last_time.setText(chatModel.getTimestamp());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Glide.with(context).load(user.getProfileimage())
                .placeholder(R.drawable.flag)
                .into(holder.profile_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,Chats_Activity.class);
                i.putExtra("username",holder.username.getText().toString());
                i.putExtra("imageurl",user.getProfileimage());
                i.putExtra("uid",user.getUid());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class Usersviewholder extends RecyclerView.ViewHolder {
        TextView username,lastmessage,last_time;
        CircleImageView profile_image;
        public Usersviewholder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            lastmessage = itemView.findViewById(R.id.lastmessage);
            last_time = itemView.findViewById(R.id.last_time);
        }
    }
}
