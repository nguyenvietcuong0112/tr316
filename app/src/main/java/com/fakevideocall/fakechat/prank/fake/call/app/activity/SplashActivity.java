package com.fakevideocall.fakechat.prank.fake.call.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Process;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.base.BaseActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivitySplashBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.LoadNativeFullNew;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.SharePreferenceUtils;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.SharedClass;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.SystemUtil;
import com.mallegan.ads.callback.InterCallback;
import com.mallegan.ads.util.Admob;
import com.mallegan.ads.util.ConsentHelper;

import java.util.Map;


public class SplashActivity extends BaseActivity {

    private SharePreferenceUtils sharePreferenceUtils;


    @Override
    public void bind() {
        SystemUtil.setLocale(this);
        ActivitySplashBinding activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        getWindow().setFlags(1024, 1024);
        setContentView(activitySplashBinding.getRoot());
        sharePreferenceUtils = new SharePreferenceUtils(this);

        new Thread(() -> {
            for (int progress = 0; progress <= 95; progress++) {
                final int currentProgress = progress;
                runOnUiThread(() -> {
                    activitySplashBinding.progressBar.setProgress(currentProgress);
                    activitySplashBinding.tvLoading.setText(currentProgress + "%");
                });
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        loadAds();

    }

    private void loadAds() {
        int counterValue = sharePreferenceUtils.getCurrentValue();
        InterCallback interCallback = new InterCallback() {
            @Override
            public void onNextAction() {
                super.onNextAction();
                if (counterValue == 1) {
                    startActivity(new Intent(SplashActivity.this, InterestActivity.class));
                } else if(counterValue == 0) {
                    startActivity(new Intent(SplashActivity.this, LanguageActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                }
            }
            @Override
            public void onAdClosedByUser() {
                super.onAdClosedByUser();
                if(counterValue != 0 &&  counterValue != 1) {
                    if (!SharePreferenceUtils.isOrganic(getApplicationContext())) {
                        Intent intent = new Intent(SplashActivity.this, LoadNativeFullNew.class);
                        intent.putExtra(LoadNativeFullNew.EXTRA_NATIVE_AD_ID, getString(R.string.native_full_splash));
                        startActivity(intent);
                    }
                }

            }
        };

        ConsentHelper consentHelper = ConsentHelper.getInstance(this);
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset();
        }
        consentHelper.obtainConsentAndShow(this, () -> {
            Admob.getInstance().loadSplashInterAds2(SplashActivity.this, getString(R.string.inter_splash), 3000, interCallback);
        });


        if (SharePreferenceUtils.isOrganic(this)) {
            AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {

                @Override
                public void onConversionDataSuccess(Map<String, Object> conversionData) {
                    String mediaSource = (String) conversionData.get("media_source");
                    SharePreferenceUtils.setOrganicValue(getApplicationContext(), mediaSource == null || mediaSource.isEmpty() || mediaSource.equals("organic"));
                }

                @Override
                public void onConversionDataFail(String s) {
                    // Handle conversion data failure
                }

                @Override
                public void onAppOpenAttribution(Map<String, String> map) {
                    // Handle app open attribution
                }

                @Override
                public void onAttributionFailure(String s) {
                    // Handle attribution failure
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitApp();
    }

    public void ExitApp() {
        moveTaskToBack(true);
        finish();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}

