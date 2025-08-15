package com.fakevideocall.fakechat.prank.fake.call.app.activity.message;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.adapter.ThemeAdapter;
import com.fakevideocall.fakechat.prank.fake.call.app.base.BaseActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivityTemplateSelectionMessageBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivityThemeSelectionBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.model.ChatTheme;

import java.util.ArrayList;
import java.util.List;

public class ThemeSelectionMessageActivity extends BaseActivity {
    private static final String EXTRA_NAME = "name";
    private static final String EXTRA_IMAGE = "image";
    private static final String THEME_PREFS = "theme_preferences";
    private static final String SELECTED_THEME_ID = "selected_theme_id";

    private ActivityTemplateSelectionMessageBinding binding;
    private String contactName;
    private int contactImage;

    @Override
    public void bind() {
        binding = ActivityTemplateSelectionMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nhận data từ MessageActivity
        contactName = getIntent().getStringExtra(EXTRA_NAME);
        contactImage = getIntent().getIntExtra(EXTRA_IMAGE, 0);

        initializeViews();
        setupThemeList();
    }

    private void initializeViews() {

        // Hiển thị thông tin contact
        if (contactName != null) {
            binding.textViewContactName.setText(contactName);
        }

        if (contactImage != 0) {
            binding.imageViewContact.setImageResource(contactImage);
        }

        binding.icback.setOnClickListener(v -> onBackPressed());
    }

    private void setupThemeList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerViewThemes.setLayoutManager(gridLayoutManager);

        List<ChatTheme> themes = createThemeList();

        ThemeAdapter adapter = new ThemeAdapter(this, themes, theme -> {
            saveSelectedTheme(theme);

            Intent intent = new Intent(ThemeSelectionMessageActivity.this, ChatActivity.class);
            intent.putExtra("name", contactName);
            intent.putExtra("image", contactImage);
            intent.putExtra("theme_id", theme.getId());
            startActivity(intent);
            finish();
        });

        binding.recyclerViewThemes.setAdapter(adapter);
    }

    private List<ChatTheme> createThemeList() {
        List<ChatTheme> themes = new ArrayList<>();

        // Theme mặc định
        themes.add(new ChatTheme(1, "Default",
                R.drawable.theme_default_bg,
                R.drawable.user_message_bg_default,
                R.drawable.response_message_bg_default,
                R.color.black));

        // Theme đêm
        themes.add(new ChatTheme(2, "Love",
                R.drawable.thememessage2,
                R.drawable.user_message_bg2,
                R.drawable.response_message_bg_2,
                R.color.white));

        // Theme tình yêu
        themes.add(new ChatTheme(3, "Coffee",
                R.drawable.thememessage3,
                R.drawable.user_message_bg3,
                R.drawable.response_message_bg_3,
                R.color.white));


        return themes;
    }

    private void saveSelectedTheme(ChatTheme theme) {
        SharedPreferences prefs = getSharedPreferences(THEME_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(SELECTED_THEME_ID, theme.getId());
        editor.putString("theme_name", theme.getName());
        editor.putInt("background_res", theme.getBackgroundRes());
        editor.putInt("user_message_bg", theme.getUserMessageBg());
        editor.putInt("response_message_bg", theme.getResponseMessageBg());
        editor.putInt("text_color", theme.getTextColor());

        editor.apply();
    }
}