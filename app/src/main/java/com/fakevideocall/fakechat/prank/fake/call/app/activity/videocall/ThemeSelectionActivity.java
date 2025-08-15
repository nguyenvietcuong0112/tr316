package com.fakevideocall.fakechat.prank.fake.call.app.activity.videocall;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.model.CallThemeType;

import java.io.Serializable;

public class ThemeSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selection);

        findViewById(R.id.cardTheme1).setOnClickListener(v -> returnTheme(CallThemeType.THEME_1));
        findViewById(R.id.cardTheme2).setOnClickListener(v -> returnTheme(CallThemeType.THEME_2));
        findViewById(R.id.cardTheme3).setOnClickListener(v -> returnTheme(CallThemeType.THEME_3));

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

    }

    private void returnTheme(CallThemeType theme) {
        Intent result = new Intent();
        result.putExtra("selected_theme", theme);
        setResult(RESULT_OK, result);
        finish();
    }
}

