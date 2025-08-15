package com.fakevideocall.fakechat.prank.fake.call.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.message.ThemeSelectionMessageActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final List<Message> messages;
    private final Context context;

    public MessageAdapter(Context context, List<Message> message) {
        this.context = context;
        this.messages = message;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_call, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.imageView.setImageResource(message.getImageResId());
        holder.textView.setText(message.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ThemeSelectionMessageActivity.class);
            intent.putExtra("name", message.getName());
            intent.putExtra("image", message.getImageResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        MessageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textView = itemView.findViewById(R.id.item_name);
        }
    }
}