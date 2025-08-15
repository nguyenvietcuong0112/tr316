package com.fakevideocall.fakechat.prank.fake.call.app.activity.videocall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.HomeActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.model.Video;

public class WaitingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        String isAudio = (String) getIntent().getSerializableExtra("isAudio");
        Intent intent = getIntent();
        Video video = (Video) intent.getSerializableExtra("video");
        int delay = intent.getIntExtra("delay", 0);

        new Handler().postDelayed(() -> {
            Intent callIntent = new Intent(WaitingActivity.this, CallComingActivity.class);
            callIntent.putExtra("video", video);
            callIntent.putExtra("isAudio", isAudio);
            callIntent.putExtra("theme", getIntent().getSerializableExtra("theme"));

            startActivity(callIntent);
            finish();
        }, delay);
    }
}