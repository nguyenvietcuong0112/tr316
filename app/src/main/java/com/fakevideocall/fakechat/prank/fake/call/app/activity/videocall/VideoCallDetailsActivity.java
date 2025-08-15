package com.fakevideocall.fakechat.prank.fake.call.app.activity.videocall;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivityTemplateSelectionBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.model.CallThemeType;
import com.fakevideocall.fakechat.prank.fake.call.app.model.Video;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class VideoCallDetailsActivity extends AppCompatActivity {

    private ActivityTemplateSelectionBinding binding;
    private Video video;
    private String selectedCallTime = "Now";
    private int selectedDelay = 0;

    private static final int REQUEST_SELECT_THEME = 101;
    private CallThemeType selectedTheme = CallThemeType.THEME_1;
    private String isAudio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTemplateSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isAudio = (String) getIntent().getSerializableExtra("isAudio");

        video = (Video) getIntent().getSerializableExtra("video");
        if (video == null) {
            finish();
            return;
        }

        binding.ivBack.setOnClickListener(view -> onBackPressed());

        binding.ivVideoPreview.setImageResource(video.getImageResId());

        binding.tvVideoName.setText(video.getName());

        binding.layoutCallTime.setOnClickListener(v -> showCallTimeDialog());

        binding.layoutTheme.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThemeSelectionActivity.class);
            startActivityForResult(intent, REQUEST_SELECT_THEME);
        });
        binding.btnCall.setOnClickListener(v -> startCall());
    }


    private void showCallTimeDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_set_time);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView title = dialog.findViewById(R.id.dialog_title);
        title.setText("Set time after");

        ListView listView = dialog.findViewById(R.id.time_list);
        String[] times = {"Now", "5s", "10s", "15s", "30s", "1m"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.list_item_checked, R.id.checked_text, times);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        int checkedPosition = 0;
        switch (selectedDelay) {
            case 5000: checkedPosition = 1; break;
            case 10000: checkedPosition = 2; break;
            case 15000: checkedPosition = 3; break;
            case 30000: checkedPosition = 4; break;
            case 60000: checkedPosition = 5; break;
        }
        listView.setItemChecked(checkedPosition, true);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < listView.getChildCount(); i++) {
                View child = listView.getChildAt(i);
                if (i == position) {
                    child.setBackgroundColor(getResources().getColor(R.color.purple));
                } else {
                    child.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
            }
        });

        Button okButton = dialog.findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> {
            int selectedPosition = listView.getCheckedItemPosition();
            selectedDelay = 0;
            selectedCallTime = "Now";

            switch (selectedPosition) {
                case 1:
                    selectedDelay = 5000;
                    selectedCallTime = "5s";
                    break;
                case 2:
                    selectedDelay = 10000;
                    selectedCallTime = "10s";
                    break;
                case 3:
                    selectedDelay = 15000;
                    selectedCallTime = "15s";
                    break;
                case 4:
                    selectedDelay = 30000;
                    selectedCallTime = "30s";
                    break;
                case 5:
                    selectedDelay = 60000;
                    selectedCallTime = "1m";
                    break;
            }

            binding.tvCallTime.setText(selectedCallTime);
            dialog.dismiss();
        });

        dialog.show();
    }



    private void startCall() {
        Intent intent = new Intent(this, WaitingActivity.class);
        intent.putExtra("video", video);
        intent.putExtra("delay", selectedDelay);
        intent.putExtra("theme", selectedTheme);
        intent.putExtra("isAudio", isAudio);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_THEME && resultCode == RESULT_OK) {
            selectedTheme = (CallThemeType) data.getSerializableExtra("selected_theme");
            TextView themeNameView = binding.tvTheme;
            if (themeNameView != null && selectedTheme != null) {
                String name = "";
                switch (selectedTheme) {
                    case THEME_1:
                        name = "Messenger";
                        break;
                    case THEME_2:
                        name = "Zalo";
                        break;
                    case THEME_3:
                        name = "Telegram";
                        break;
                }
                themeNameView.setText(name);
            }
        }
    }
}