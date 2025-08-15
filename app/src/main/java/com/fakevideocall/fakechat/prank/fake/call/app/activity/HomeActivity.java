package com.fakevideocall.fakechat.prank.fake.call.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.audio.AudioCallActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.message.MessageActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.videocall.VideoCallActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.base.BaseActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivityHomeBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.ActivityLoadNativeFull;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.Constant;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.LoadNativeFullNew;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.SharePreferenceUtils;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.mallegan.ads.callback.InterCallback;
import com.mallegan.ads.callback.NativeCallback;
import com.mallegan.ads.util.Admob;

public class HomeActivity extends BaseActivity {

    ActivityHomeBinding binding;

    private SharePreferenceUtils sharePreferenceUtils;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isFirstLoad = true;

    private Runnable loadTask = new Runnable() {
        @Override
        public void run() {
            loadNativeExpnad();
            handler.postDelayed(this, 15000);
        }
    };

    @Override
    public void bind() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharePreferenceUtils = new SharePreferenceUtils(this);
        sharePreferenceUtils.incrementCounter();

        binding.ivSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });

        binding.btnAudiocall.setOnClickListener(v -> {
            binding.btnAudiocall.setEnabled(false);
            if (!SharePreferenceUtils.isOrganic(this)) {

                Admob.getInstance().loadSplashInterAds2(HomeActivity.this, getString(R.string.inter_home), 0, new InterCallback() {
                    @Override
                    public void onNextAction() {
                        super.onNextAction();
                        Intent intent = new Intent(HomeActivity.this, AudioCallActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdClosedByUser() {
                        super.onAdClosedByUser();
                        if (!SharePreferenceUtils.isOrganic(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this, LoadNativeFullNew.class);
                            intent.putExtra(LoadNativeFullNew.EXTRA_NATIVE_AD_ID, getString(R.string.native_full_home));
                            startActivity(intent);
                        }
                    }
                });
            } else {
                Intent intent = new Intent(HomeActivity.this, AudioCallActivity.class);
                startActivity(intent);
            }


        });


        binding.btnVideocall.setOnClickListener(v -> {
            binding.btnVideocall.setEnabled(false);
            if (!SharePreferenceUtils.isOrganic(this)) {

                Admob.getInstance().loadSplashInterAds2(HomeActivity.this, getString(R.string.inter_home), 0, new InterCallback() {
                    @Override
                    public void onNextAction() {
                        super.onNextAction();
                        Intent intent = new Intent(HomeActivity.this, VideoCallActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdClosedByUser() {
                        super.onAdClosedByUser();
                        if (!SharePreferenceUtils.isOrganic(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this, LoadNativeFullNew.class);
                            intent.putExtra(LoadNativeFullNew.EXTRA_NATIVE_AD_ID, getString(R.string.native_full_home));
                            startActivity(intent);
                        }
                    }
                });
            } else {
                Intent intent = new Intent(HomeActivity.this, VideoCallActivity.class);
                startActivity(intent);
            }


        });

        binding.btnMessage.setOnClickListener(v -> {
            binding.btnMessage.setEnabled(false);

            if (!SharePreferenceUtils.isOrganic(this)) {
                Admob.getInstance().loadSplashInterAds2(HomeActivity.this, getString(R.string.inter_home), 0, new InterCallback() {
                    @Override
                    public void onNextAction() {
                        super.onNextAction();
                        Intent intent = new Intent(HomeActivity.this, MessageActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdClosedByUser() {
                        super.onAdClosedByUser();
                        showNativeFullAd();
                    }


                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        showNativeFullAd();
                    }

                });
            } else {
                Intent intent = new Intent(HomeActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });


    }

    private void showNativeFullAd() {
        if (!SharePreferenceUtils.isOrganic(getApplicationContext())) {
            Intent intent = new Intent(HomeActivity.this, LoadNativeFullNew.class);
            intent.putExtra(LoadNativeFullNew.EXTRA_NATIVE_AD_ID, getString(R.string.native_full_home));
            startActivity(intent);
        }
    }


    private void loadAds() {
        Admob.getInstance().loadNativeAd(this, getString(R.string.native_home), new NativeCallback() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                super.onNativeAdLoaded(nativeAd);
                NativeAdView adView = new NativeAdView(HomeActivity.this);
                if (!SharePreferenceUtils.isOrganic(HomeActivity.this)) {
                    adView = (NativeAdView) LayoutInflater.from(HomeActivity.this).inflate(R.layout.layout_native_home, null);
                } else {
                    adView = (NativeAdView) LayoutInflater.from(HomeActivity.this).inflate(R.layout.layout_native_language, null);
                }
                binding.frAds.removeAllViews();
                binding.frAds.addView(adView);
                Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);
            }

            @Override
            public void onAdFailedToLoad() {
                super.onAdFailedToLoad();
                binding.frAds.setVisibility(View.GONE);
            }
        });
    }

    private void loadNativeCollap(@Nullable final Runnable onLoaded) {
        Log.d("Truowng", "loadNativeCollapA: ");
        binding.frAdsHomeTop.removeAllViews();
        Admob.getInstance().loadNativeAd(getApplicationContext(), getString(R.string.native_collap_home), new NativeCallback() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                NativeAdView adView = (NativeAdView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_native_home_collap, null);
                binding.frAdsCollap.removeAllViews();
                binding.frAdsCollap.addView(adView);
                Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);
                if (onLoaded != null) {
                    onLoaded.run();
                }

            }

            @Override
            public void onAdFailedToLoad() {
                binding.frAdsCollap.removeAllViews();
                if (onLoaded != null) {
                    onLoaded.run();
                }
            }
        });
    }


    private void loadNativeExpnad() {

        Log.d("Truong", "loadNativeCollapB: ");
        Context context = getApplicationContext();

        Admob.getInstance().loadNativeAd(context, getString(R.string.native_expand_home), new NativeCallback() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {

                Context context = getApplicationContext();
                NativeAdView adView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.layout_native_home_expnad, null);

                binding.frAdsHomeTop.removeAllViews();

                MediaView mediaView = adView.findViewById(R.id.ad_media);
                ImageView closeButton = adView.findViewById(R.id.close);
                closeButton.setOnClickListener(v -> {
                    mediaView.performClick();
                });

                Log.d("Truong", "onNativeAdLoaded: ");
                binding.frAdsHomeTop.addView(adView);
                Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);
            }

            @Override
            public void onAdFailedToLoad() {
                    binding.frAdsHomeTop.removeAllViews();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        loadAds();
        binding.btnMessage.setEnabled(true);
        binding.btnVideocall.setEnabled(true);
        binding.btnAudiocall.setEnabled(true);

        if (!SharePreferenceUtils.isOrganic(getApplicationContext())) {
            if (isFirstLoad) {
                loadNativeCollap(() -> handler.postDelayed(() -> {
                    loadNativeExpnad();
                    handler.postDelayed(loadTask, 15000);
                    isFirstLoad = false;
                }, 1000));
            } else {
                loadNativeCollap(null);
                handler.postDelayed(loadTask, 15000);
            }
        } else {
            binding.frAdsHomeTop.removeAllViews();
            binding.frAdsCollap.removeAllViews();
        }
    }

}
