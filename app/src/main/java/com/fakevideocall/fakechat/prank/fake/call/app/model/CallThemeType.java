package com.fakevideocall.fakechat.prank.fake.call.app.model;

import com.fakevideocall.fakechat.prank.fake.call.app.R;

import java.io.Serializable;

public enum CallThemeType implements Serializable {
    THEME_1(R.drawable.theme1, R.layout.panel_buttons_1),
    THEME_2(R.drawable.theme2, R.layout.panel_buttons_2),
    THEME_3(R.drawable.theme3, R.layout.panel_buttons_3);

    public final int backgroundResId;
    public final int panelLayoutResId;

    CallThemeType(int bg, int panel) {
        this.backgroundResId = bg;
        this.panelLayoutResId = panel;
    }
}