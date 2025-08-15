package com.fakevideocall.fakechat.prank.fake.call.app.activity.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.activity.HomeActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.adapter.ChatAdapter;
import com.fakevideocall.fakechat.prank.fake.call.app.adapter.QuestionAdapter;
import com.fakevideocall.fakechat.prank.fake.call.app.base.BaseActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivityChatBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.model.ChatTheme;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.ActivityLoadNativeFull;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.Constant;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.LoadNativeFullNew;
import com.fakevideocall.fakechat.prank.fake.call.app.utils.SharePreferenceUtils;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mallegan.ads.callback.InterCallback;
import com.mallegan.ads.util.Admob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChatActivity extends BaseActivity {

    private static final String EXTRA_NAME = "name";
    private static final String EXTRA_IMAGE = "image";

    private static final String EXTRA_THEME_ID = "theme_id";
    private static final String THEME_PREFS = "theme_preferences";
    private final Map<String, String> responses = new HashMap<>();
    private final List<String> questions = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private ActivityChatBinding binding;

    private ChatTheme currentTheme;


    @Override
    public void bind() {
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeViews();

        loadTheme();


        if (loadQuestionsFromRaw()) {
            setupRecyclerView();
            setupClickListeners();
        }
        loadInter();
    }

    private void loadTheme() {
        int themeId = getIntent().getIntExtra(EXTRA_THEME_ID, 1);
        SharedPreferences prefs = getSharedPreferences(THEME_PREFS, MODE_PRIVATE);

        // Lấy thông tin theme từ SharedPreferences
        String themeName = prefs.getString("theme_name", "Default");
        System.out.println("themenameeeee:" + themeName);
        int backgroundRes = prefs.getInt("background_res", R.drawable.theme_default_bg);
        int userMessageBg = prefs.getInt("user_message_bg", R.drawable.user_message_bg_default);
        int responseMessageBg = prefs.getInt("response_message_bg", R.drawable.response_message_bg_default);
        int textColor = prefs.getInt("text_color", R.color.white);

        currentTheme = new ChatTheme(themeId, themeName, backgroundRes,
                userMessageBg, responseMessageBg, textColor);

        // Áp dụng theme cho background
        if(themeName.equals("Default")) {
            binding.textNameTitle.setTextColor(Color.parseColor("#000000"));
            binding.status.setTextColor(Color.parseColor("#000000"));
            binding.textViewName.setTextColor(Color.parseColor("#000000"));
            binding.tvPlatform.setTextColor(Color.parseColor("#000000"));
            binding.showQuestion.setBackgroundResource(R.drawable.bg_chat);
        } else if(themeName.equals("Love")) {
            binding.textNameTitle.setTextColor(Color.parseColor("#FFFFFF"));
            binding.status.setTextColor(Color.parseColor("#FFFFFF"));
            binding.textViewName.setTextColor(Color.parseColor("#FFFFFF"));
            binding.tvPlatform.setTextColor(Color.parseColor("#FFFFFF"));
            binding.showQuestion.setBackgroundResource(R.drawable.bg_chat_love);

        } else {
            binding.textNameTitle.setTextColor(Color.parseColor("#FFFFFF"));
            binding.status.setTextColor(Color.parseColor("#FFFFFF"));
            binding.textViewName.setTextColor(Color.parseColor("#FFFFFF"));
            binding.tvPlatform.setTextColor(Color.parseColor("#FFFFFF"));
            binding.showQuestion.setBackgroundResource(R.drawable.bg_chat_coffee);
        }
        applyTheme();
    }

    private void applyTheme() {
        if (currentTheme != null) {
            binding.getRoot().setBackgroundResource(currentTheme.getBackgroundRes());

            // Có thể thêm các customization khác như màu text, etc.
        }
    }

    private void initializeViews() {
        String name = getIntent().getStringExtra(EXTRA_NAME);
        int imageRes = getIntent().getIntExtra(EXTRA_IMAGE, 0);
        binding.icBack.setOnClickListener(v -> {
            if (Constant.interBackMessage != null) {
                Admob.getInstance().showInterAds(ChatActivity.this, Constant.interBackMessage, new InterCallback() {
                    @Override
                    public void onNextAction() {
                        super.onNextAction();
                        onBackPressed();
                    }

                    @Override
                    public void onAdClosedByUser() {
                        super.onAdClosedByUser();
                        if (!SharePreferenceUtils.isOrganic(getApplicationContext())) {
                            Intent intent = new Intent(ChatActivity.this, LoadNativeFullNew.class);
                            intent.putExtra(LoadNativeFullNew.EXTRA_NATIVE_AD_ID, getString(R.string.native_full_message));
                            startActivity(intent);
                        }

                    }
                });


            } else {
                onBackPressed();


            }
        });

        if (name != null) {
            binding.textViewName.setText(name);
            binding.textNameTitle.setText(name);
        }

        if (imageRes != 0) {
            binding.imageViewAvatar.setImageResource(imageRes);
            binding.avatar.setImageResource(imageRes);
        }
    }

    private boolean loadQuestionsFromRaw() {
        try {
            String jsonContent = readJsonFromRaw();
            if (jsonContent == null) {
                showError("Error: Questions file not found");
                return false;
            }
            parseJsonContent(jsonContent);
            return true;
        } catch (IOException e) {
            showError("Error reading questions file: " + e.getMessage());
            return false;
        } catch (JSONException e) {
            showError("Error parsing questions: " + e.getMessage());
            return false;
        }
    }

    private String readJsonFromRaw() throws IOException {
        StringBuilder jsonString = new StringBuilder();
        InputStream inputStream = getResources().openRawResource(R.raw.message);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } finally {
            reader.close();
            inputStream.close();
        }

        return jsonString.toString();
    }

    private void parseJsonContent(String jsonContent) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonContent);
        JSONArray questionSets = jsonObject.getJSONArray("questionSets");
        Random random = new Random();
        int randomIndex = random.nextInt(questionSets.length());

        if (questionSets.length() > 0) {
            JSONObject selectedSet = questionSets.getJSONObject(randomIndex);
            extractQuestionsAndResponses(selectedSet);
        } else {
            showError("No question sets found");
        }
    }

    private void extractQuestionsAndResponses(JSONObject selectedSet) throws JSONException {
        JSONArray questionsArray = selectedSet.getJSONArray("questions");
        JSONObject responsesObject = selectedSet.getJSONObject("responses");

        questions.clear();
        responses.clear();

        for (int i = 0; i < questionsArray.length(); i++) {
            String question = questionsArray.getString(i);
            if (responsesObject.has(question)) {
                questions.add(question);
                responses.put(question, responsesObject.getString(question));
            }
        }

        if (questions.isEmpty()) {
            showError("No valid questions found");
        }
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(new ArrayList<>(), responses,currentTheme);
        binding.recyclerView.setAdapter(chatAdapter);
    }

    private void setupClickListeners() {
        binding.showQuestion.setOnClickListener(v -> showQuestionBottomSheet());
    }

    private void showQuestionBottomSheet() {
        if (questions.isEmpty()) {
            showError("No questions available");
            return;
        }

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_questions, null);
        bottomSheetDialog.setContentView(sheetView);

        setupQuestionsList(bottomSheetDialog, sheetView);
        bottomSheetDialog.show();
    }

    private void setupQuestionsList(@NonNull BottomSheetDialog dialog, @NonNull View sheetView) {
        RecyclerView recyclerViewQuestions = sheetView.findViewById(R.id.recyclerViewQuestions);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));

        QuestionAdapter questionAdapter = new QuestionAdapter(questions, question -> {
            chatAdapter.addMessage(question, responses.get(question));
            dialog.dismiss();
        });

        recyclerViewQuestions.setAdapter(questionAdapter);
    }

    private void loadInter() {
//        if(!SharePreferenceUtils.isOrganic(ChatActivity.this)){
        Admob.getInstance().loadInterAds(this, getString(R.string.inter_back_message), new InterCallback() {
            @Override
            public void onInterstitialLoad(InterstitialAd interstitialAd) {
                super.onInterstitialLoad(interstitialAd);
                Constant.interBackMessage = interstitialAd;
            }
        });
//        }
    }
}