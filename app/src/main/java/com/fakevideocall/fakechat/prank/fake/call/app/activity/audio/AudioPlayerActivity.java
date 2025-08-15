package com.fakevideocall.fakechat.prank.fake.call.app.activity.audio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.model.Video;

public class AudioPlayerActivity extends AppCompatActivity {

    private TextView tvCallerTime;
    private int secondsPassed = 0;
    private Handler handler = new Handler();
    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            secondsPassed++;
            int minutes = secondsPassed / 60;
            int seconds = secondsPassed % 60;
            String timeFormatted = String.format("%02d:%02d", minutes, seconds);
            tvCallerTime.setText(timeFormatted);
            handler.postDelayed(this, 1000);
        }
    };

    private ImageView btnDecline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_player_activity);
        ImageView imageAvatar = findViewById(R.id.imageViewAvatar);
        TextView tv_name = findViewById(R.id.tv_name);
        tvCallerTime = findViewById(R.id.tv_caller_time);
        btnDecline = findViewById(R.id.btn_decline);
        handler.post(updateTimeRunnable);

        btnDecline.setOnClickListener(view -> {
            Intent intent = new Intent(AudioPlayerActivity.this, AudioCallActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        Video video = (Video) getIntent().getSerializableExtra("video");
        if(video!=null){
            tv_name.setText(video.getName());
            imageAvatar.setImageResource(video.getImageResId());
        }


        int videoResId = getIntent().getIntExtra("videoPath", -1);

        if (videoResId != -1) {
            VideoView videoView = findViewById(R.id.videoView);
            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
            videoView.setVideoURI(videoUri);
            videoView.start();
        }

    }
}
