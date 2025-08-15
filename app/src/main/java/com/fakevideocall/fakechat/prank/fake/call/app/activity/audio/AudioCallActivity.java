package com.fakevideocall.fakechat.prank.fake.call.app.activity.audio;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.videocall.VideoCallDetailsActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.adapter.AudioCallAdapter;
import com.fakevideocall.fakechat.prank.fake.call.app.adapter.VideoCallAdapter;
import com.fakevideocall.fakechat.prank.fake.call.app.base.BaseActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivityAudiocallBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivityVideocallBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.model.Video;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.SharePreferenceUtils;
import com.mallegan.ads.util.Admob;

import java.util.ArrayList;
import java.util.List;

public class AudioCallActivity  extends BaseActivity {
    private List<Video> allVideos = new ArrayList<>();
    private List<Video> filteredVideos = new ArrayList<>();
    private AudioCallAdapter adapter;
    private ActivityAudiocallBinding binding;

    private static final String CATEGORY_STAR = "star";
    private static final String CATEGORY_FOOTBALLER = "footballer";
    private static final String CATEGORY_ANIMAL = "animal";
    private String currentCategory = CATEGORY_STAR;

    @Override
    public void bind() {
        binding = ActivityAudiocallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        setupTabBar();
        loadVideos();
        filterVideos(currentCategory);

        binding.icback.setOnClickListener(v -> {
            onBackPressed();
        });
    }




    private void setupTabBar() {
        // Tab Star
        binding.tabStar.setOnClickListener(v -> {
            currentCategory = CATEGORY_STAR;
            updateTabSelection();
            filterVideos(currentCategory);
        });

        binding.tabFootballer.setOnClickListener(v -> {
            currentCategory = CATEGORY_FOOTBALLER;
            updateTabSelection();
            filterVideos(currentCategory);
        });

        binding.tabAnimal.setOnClickListener(v -> {
            currentCategory = CATEGORY_ANIMAL;
            updateTabSelection();
            filterVideos(currentCategory);
        });

        updateTabSelection();
    }

    private void updateTabSelection() {



        switch (currentCategory) {
            case CATEGORY_STAR:
                binding.tvStar.setTextColor(Color.parseColor("#000000"));
                binding.tvFootballer.setTextColor(Color.parseColor("#848484"));
                binding.tvAnimal.setTextColor(Color.parseColor("#848484"));
                binding.tabStar.setBackgroundResource(R.drawable.bg_white_tabbar);
                binding.tabFootballer.setBackgroundResource(R.drawable.bg_trans_tabbar);
                binding.tabAnimal.setBackgroundResource(R.drawable.bg_trans_tabbar);
                break;
            case CATEGORY_FOOTBALLER:
                binding.tvStar.setTextColor(Color.parseColor("#848484"));
                binding.tvFootballer.setTextColor(Color.parseColor("#000000"));
                binding.tvAnimal.setTextColor(Color.parseColor("#848484"));
                binding.tabStar.setBackgroundResource(R.drawable.bg_trans_tabbar);
                binding.tabFootballer.setBackgroundResource(R.drawable.bg_white_tabbar);
                binding.tabAnimal.setBackgroundResource(R.drawable.bg_trans_tabbar);
                break;
            case CATEGORY_ANIMAL:
                binding.tvStar.setTextColor(Color.parseColor("#848484"));
                binding.tvFootballer.setTextColor(Color.parseColor("#848484"));
                binding.tvAnimal.setTextColor(Color.parseColor("#000000"));
                binding.tabStar.setBackgroundResource(R.drawable.bg_trans_tabbar);
                binding.tabFootballer.setBackgroundResource(R.drawable.bg_trans_tabbar);
                binding.tabAnimal.setBackgroundResource(R.drawable.bg_white_tabbar);
                break;
        }
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new AudioCallAdapter(this, filteredVideos, this::showSetTimeDialog);
        binding.recyclerView.setAdapter(adapter);
    }

    private void loadVideos() {
        allVideos.add(new Video("V- BTS", R.drawable.v, R.raw.v, CATEGORY_STAR));
        allVideos.add(new Video("Jungkook", R.drawable.jungkook, R.raw.jungkook, CATEGORY_STAR));
        allVideos.add(new Video("Taylor Swift", R.drawable.taylorswift, R.raw.taylorswift, CATEGORY_STAR));
        allVideos.add(new Video("Jisoo", R.drawable.jisoo, R.raw.jisoo, CATEGORY_STAR));
        allVideos.add(new Video("Jennie", R.drawable.jennie, R.raw.jennie, CATEGORY_STAR));
        allVideos.add(new Video("Lisa", R.drawable.lisa, R.raw.lisa, CATEGORY_STAR));
        allVideos.add(new Video("Rose", R.drawable.rose, R.raw.rose, CATEGORY_STAR));
        allVideos.add(new Video("Adele", R.drawable.adele, R.raw.adele, CATEGORY_STAR));
        allVideos.add(new Video("Beonce'", R.drawable.beyonce, R.raw.beyonce, CATEGORY_STAR));
        allVideos.add(new Video("Tom Holland", R.drawable.tomholland, R.raw.tomholland, CATEGORY_STAR));
        allVideos.add(new Video("Cardi B", R.drawable.cardib, R.raw.cardib, CATEGORY_STAR));
        allVideos.add(new Video("Dwayne", R.drawable.dwayne, R.raw.dwayne, CATEGORY_STAR));
        allVideos.add(new Video("Johnny Depp", R.drawable.johnnydeep, R.raw.johnnydeep, CATEGORY_STAR));
        allVideos.add(new Video("Jennifer Lopez", R.drawable.jenniferlopez, R.raw.jenniferlopez, CATEGORY_STAR));

        allVideos.add(new Video("Messi", R.drawable.messi, R.raw.messi, CATEGORY_FOOTBALLER));
        allVideos.add(new Video("Neymar", R.drawable.neymar, R.raw.neymar, CATEGORY_FOOTBALLER));
        allVideos.add(new Video("Mbappe", R.drawable.mpabbe, R.raw.mpabbe, CATEGORY_FOOTBALLER));
        allVideos.add(new Video("Ronaldo", R.drawable.ronaldo, R.raw.ronaldo, CATEGORY_FOOTBALLER));

        allVideos.add(new Video("Husky", R.drawable.husky, R.raw.husky, CATEGORY_ANIMAL));
        allVideos.add(new Video("Shihtzu", R.drawable.shihtzu, R.raw.shihtzu, CATEGORY_ANIMAL));
        allVideos.add(new Video("Puppy", R.drawable.puppy, R.raw.puppy, CATEGORY_ANIMAL));
        allVideos.add(new Video("Gangster", R.drawable.gangster, R.raw.gangster, CATEGORY_ANIMAL));
        allVideos.add(new Video("Kitten", R.drawable.kitten, R.raw.kitten, CATEGORY_ANIMAL));
        allVideos.add(new Video("Origin", R.drawable.origin, R.raw.origin, CATEGORY_ANIMAL));
    }

    private void filterVideos(String category) {
        filteredVideos.clear();
        for (Video video : allVideos) {
            if (video.getCategory().equals(category)) {
                filteredVideos.add(video);
            }
        }
        adapter.notifyDataSetChanged();
    }
    private void showSetTimeDialog(Video video) {
        Intent intent = new Intent(this, VideoCallDetailsActivity.class);
        intent.putExtra("video", video);
        intent.putExtra("isAudio", "isAudio");
        startActivity(intent);
    }
}
