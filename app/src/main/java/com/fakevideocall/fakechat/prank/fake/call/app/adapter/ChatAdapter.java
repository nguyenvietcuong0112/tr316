package com.fakevideocall.fakechat.prank.fake.call.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.model.ChatMessage;
import com.fakevideocall.fakechat.prank.fake.call.app.model.ChatTheme;

import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {
    private final List<ChatMessage> messages;
    private final Map<String, String> responses;
    private final ChatTheme theme;

    public ChatAdapter(List<ChatMessage> messages, Map<String, String> responses, ChatTheme theme) {
        this.messages = messages;
        this.responses = responses;
        this.theme = theme;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.bind(message, theme);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(String question, String response) {
        messages.add(new ChatMessage(question, true));
        notifyItemInserted(messages.size() - 1);

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(() -> {
            messages.add(new ChatMessage(response, false));
            notifyItemInserted(messages.size() - 1);
        }, 1000);
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText;
        private final TextView responseText;
        private final View userMessageLayout;
        private final View responseMessageLayout;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            responseText = itemView.findViewById(R.id.responseText);
            userMessageLayout = itemView.findViewById(R.id.userMessageLayout);
            responseMessageLayout = itemView.findViewById(R.id.responseMessageLayout);
        }

        void bind(ChatMessage message, ChatTheme theme) {
            if (message.isUser()) {
                messageText.setText(message.getText());
                userMessageLayout.setVisibility(View.VISIBLE);
                responseMessageLayout.setVisibility(View.GONE);

                // Áp dụng theme cho user message
                if (theme != null) {
                    userMessageLayout.setBackgroundResource(theme.getUserMessageBg());
                    messageText.setTextColor(itemView.getContext().getResources().getColor(theme.getTextColor()));
                }
            } else {
                responseText.setText(message.getText());
                userMessageLayout.setVisibility(View.GONE);
                responseMessageLayout.setVisibility(View.VISIBLE);

                // Áp dụng theme cho response message
                if (theme != null) {
                    responseMessageLayout.setBackgroundResource(theme.getResponseMessageBg());
                    responseText.setTextColor(itemView.getContext().getResources().getColor(theme.getTextColor()));
                }
            }
        }
    }
}