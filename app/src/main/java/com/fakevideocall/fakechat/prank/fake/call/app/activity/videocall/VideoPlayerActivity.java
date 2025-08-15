package com.fakevideocall.fakechat.prank.fake.call.app.activity.videocall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.HomeActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.IntroActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.audio.AudioCallActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.audio.AudioPlayerActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.model.CallThemeType;
import com.fakevideocall.fakechat.prank.fake.call.app.model.Video;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.ActivityLoadNativeFull;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.Constant;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.LoadNativeFullNew;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.SharePreferenceUtils;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.mallegan.ads.callback.InterCallback;
import com.mallegan.ads.util.Admob;

import java.util.Collections;

public class VideoPlayerActivity extends AppCompatActivity {

    private static final String TAG = "VideoPlayerActivity";
    private static final int CAMERA_PERMISSION_REQUEST = 100;
    private boolean isCameraOn = false;

    private TextureView cameraPreview;
    private CameraDevice cameraDevice;
    private CameraCaptureSession captureSession;
    private CameraManager cameraManager;
    private String cameraId;
    private CallThemeType selectedTheme;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        selectedTheme = (CallThemeType) getIntent().getSerializableExtra("theme");

        if (selectedTheme != null) {
            FrameLayout callPanelContainer = findViewById(R.id.callPanelContainer);
            View panel = LayoutInflater.from(this).inflate(selectedTheme.panelLayoutResId, callPanelContainer, false);
            callPanelContainer.removeAllViews();
            callPanelContainer.addView(panel);

            View cancel = panel.findViewById(R.id.btnCancel);
            if (cancel != null) cancel.setOnClickListener(v -> {
                Intent intent = new Intent(VideoPlayerActivity.this, VideoCallActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }


//        ImageView btnCancel = (ImageView) findViewById(R.id.btnCancel);
//        ImageView btnVideo = (ImageView) findViewById(R.id.btnVideo);

        cameraPreview = (TextureView) findViewById(R.id.cameraPreview);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        if (cameraPreview.isAvailable()) {
            openCamera();
        } else {
            cameraPreview.setSurfaceTextureListener(textureListener);
        }


//        btnCancel.setOnClickListener(v -> {
//            if (Constant.interBackVideo != null) {
//                Admob.getInstance().showInterAds(VideoPlayerActivity.this, Constant.interBackVideo, new InterCallback() {
//                    @Override
//                    public void onNextAction() {
//                        super.onNextAction();
//                        Intent intent = new Intent(VideoPlayerActivity.this, VideoCallActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
//                    }
//                    @Override
//                    public void onAdClosedByUser() {
//                        super.onAdClosedByUser();
//                        if (!SharePreferenceUtils.isOrganic(getApplicationContext())) {
//                            Intent intent = new Intent(VideoPlayerActivity.this, LoadNativeFullNew.class);
//                            intent.putExtra(LoadNativeFullNew.EXTRA_NATIVE_AD_ID, getString(R.string.native_full_back_video));
//                            startActivity(intent);
//                        }
//
//                    }
//                });
//
//            } else {
//                Intent intent = new Intent(VideoPlayerActivity.this, VideoCallActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            }
//
//        });
//        btnVideo.setOnClickListener(v -> {
//            if (isCameraOn) {
//                closeCamera();
//                btnVideo.setImageResource(R.drawable.ic_video_off);
//                cameraPreview.setVisibility(View.GONE);
//            } else {
//                openCamera();
//                btnVideo.setImageResource(R.drawable.ic_video);
//                cameraPreview.setVisibility(View.VISIBLE);
//            }
//            isCameraOn = !isCameraOn;
//        });


        int videoResId = getIntent().getIntExtra("videoPath", -1);

        if (videoResId != -1) {
            VideoView videoView = findViewById(R.id.videoView);
            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
            videoView.setVideoURI(videoUri);
            videoView.start();
        }

        loadInter();
    }

    private void closeCamera() {
        if (captureSession != null) {
            captureSession.close();
            captureSession = null;
        }
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
        Toast.makeText(this, "Camera đã tắt", Toast.LENGTH_SHORT).show();
    }

    private final TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    private void openCamera() {
        try {
            for (String id : cameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);

                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    cameraId = id;
                    break;
                }
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
                return;
            }

            cameraManager.openCamera(cameraId, stateCallback, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            startCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
            cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            cameraDevice = null;
        }
    };

    private void startCameraPreview() {
        try {
            SurfaceTexture surfaceTexture = cameraPreview.getSurfaceTexture();
            if (surfaceTexture == null) {
                Log.e(TAG, "SurfaceTexture is null");
                return;
            }

            surfaceTexture.setDefaultBufferSize(1920, 1080);
            Surface surface = new Surface(surfaceTexture);

            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            builder.addTarget(surface);

            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    captureSession = session;
                    try {
                        captureSession.setRepeatingRequest(builder.build(), null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(VideoPlayerActivity.this, "Camera configuration failed", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadInter() {
        if(!SharePreferenceUtils.isOrganic(VideoPlayerActivity.this)){
            Admob.getInstance().loadInterAds(this, getString(R.string.inter_back_video_call), new InterCallback() {
                @Override
                public void onInterstitialLoad(InterstitialAd interstitialAd) {
                    super.onInterstitialLoad(interstitialAd);
                    Constant.interBackVideo = interstitialAd;
                }
            });
        }
    }
}