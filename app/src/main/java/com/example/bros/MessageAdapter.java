package com.example.bros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter{
    ArrayList<ChatModel> messages;
    Context context;

    final int ITEM_SENT = 1;
    final int ITEM_RECIEVE = 2;

    public MessageAdapter(ArrayList<ChatModel> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT){
           View view1 = LayoutInflater.from(context).inflate(R.layout.sample_send_message,parent,false);
           return new senderviewholder(view1);
        }else {
            View view2 = LayoutInflater.from(context).inflate(R.layout.smaple_reciece_message,parent,false);
            return new reciever(view2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel chatModel = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(chatModel.getSenderid())){
            return ITEM_SENT;
        }else {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = messages.get(position);
        if(holder.getClass() == senderviewholder.class){
            senderviewholder viewholder = (senderviewholder) holder;
            viewholder.sentmessage.setText(chatModel.getMessage());
            viewholder.sent_time.setText(chatModel.getTimestamp());
        }else {
            reciever viewholder1 = (reciever) holder;
            viewholder1.recieved_message.setText(chatModel.getMessage());
            viewholder1.recieved_time.setText(chatModel.getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class senderviewholder extends RecyclerView.ViewHolder {
        TextView sentmessage,sent_time;
        public senderviewholder(@NonNull View itemView) {
            super(itemView);
            sentmessage = itemView.findViewById(R.id.sentmessage);
            sent_time = itemView.findViewById(R.id.send_time);
        }
    }
    public class reciever extends RecyclerView.ViewHolder {
        TextView recieved_message,recieved_time;
        public reciever(@NonNull View itemView) {
            super(itemView);
            recieved_message = itemView.findViewById(R.id.recieved_message);
            recieved_time = itemView.findViewById(R.id.recieved_time);
        }
    }
}
