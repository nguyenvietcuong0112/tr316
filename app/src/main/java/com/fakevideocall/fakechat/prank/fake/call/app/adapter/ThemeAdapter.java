package com.fakevideocall.fakechat.prank.fake.call.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.model.ChatTheme;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {

    public interface OnThemeSelectedListener {
        void onThemeSelected(ChatTheme theme);
    }

    private final Context context;
    private final List<ChatTheme> themes;
    private final OnThemeSelectedListener listener;
    private int selectedPosition = -1;

    public ThemeAdapter(Context context, List<ChatTheme> themes, OnThemeSelectedListener listener) {
        this.context = context;
        this.themes = themes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_theme, parent, false);
        return new ThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        ChatTheme theme = themes.get(position);

        holder.textViewThemeName.setText(theme.getName());
        holder.imageViewPreview.setImageResource(theme.getBackgroundRes());

        holder.previewUserMessage.setBackgroundResource(theme.getUserMessageBg());
        holder.previewResponseMessage.setBackgroundResource(theme.getResponseMessageBg());

        if (selectedPosition == position) {
            holder.cardView.setCardElevation(8f);
        } else {
            holder.cardView.setCardElevation(4f);
        }

        holder.itemView.setOnClickListener(v -> {
            int oldPosition = selectedPosition;
            selectedPosition = position;

            // Refresh các item để update UI
            if (oldPosition != -1) {
                notifyItemChanged(oldPosition);
            }
            notifyItemChanged(selectedPosition);

            // Callback
            if (listener != null) {
                listener.onThemeSelected(theme);
            }
        });
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    static class ThemeViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewPreview;
        TextView textViewThemeName;
        View previewUserMessage;
        View previewResponseMessage;

        ThemeViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewTheme);
            imageViewPreview = itemView.findViewById(R.id.imageViewThemePreview);
            textViewThemeName = itemView.findViewById(R.id.textViewThemeName);
            previewUserMessage = itemView.findViewById(R.id.previewUserMessage);
            previewResponseMessage = itemView.findViewById(R.id.previewResponseMessage);
        }
    }
}
