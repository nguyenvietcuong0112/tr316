package com.fakevideocall.fakechat.prank.fake.call.app.activity.message;

import androidx.recyclerview.widget.GridLayoutManager;

import com.fakevideocall.fakechat.prank.fake.call.app.R;
import com.fakevideocall.fakechat.prank.fake.call.app.adapter.MessageAdapter;
import com.fakevideocall.fakechat.prank.fake.call.app.base.BaseActivity;
import com.fakevideocall.fakechat.prank.fake.call.app.databinding.ActivityMessageBinding;
import com.fakevideocall.fakechat.prank.fake.call.app.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {
    @Override
    public void bind() {
        ActivityMessageBinding binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("V", R.drawable.v));
        messages.add(new Message("Jungkook", R.drawable.jungkook));
        messages.add(new Message("Taylor Swift", R.drawable.taylorswift));
        messages.add(new Message("Jisoo", R.drawable.jisoo));
        messages.add(new Message("Jennie", R.drawable.jennie));
        messages.add(new Message("Lisa", R.drawable.lisa));
        messages.add(new Message("Rose", R.drawable.rose));
        messages.add(new Message("Adele", R.drawable.adele));
        messages.add(new Message("Messi", R.drawable.messi));
        messages.add(new Message("Neymar", R.drawable.neymar));
        messages.add(new Message("Mbappe", R.drawable.mpabbe));
        messages.add(new Message("Beonce'", R.drawable.beyonce));
        messages.add(new Message("Tom Holland", R.drawable.tomholland));
        messages.add(new Message("Ronaldo", R.drawable.ronaldo));
        messages.add(new Message("Cardi B", R.drawable.cardib));
        messages.add(new Message("Dwayne", R.drawable.dwayne));
        messages.add(new Message("Johnny Depp", R.drawable.johnnydeep));
        messages.add(new Message("Jennifer Lopez", R.drawable.jenniferlopez));


        MessageAdapter adapter = new MessageAdapter(this, messages);
        binding.recyclerView.setAdapter(adapter);

        binding.icback.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}