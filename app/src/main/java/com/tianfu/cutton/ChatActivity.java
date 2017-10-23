package com.tianfu.cutton;

import android.os.Bundle;

import com.hyphenate.helpdesk.easeui.recorder.MediaManager;
import com.hyphenate.helpdesk.easeui.ui.BaseActivity;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.ui.CustomChatFragment;
import com.tianfu.cutton.utils.StatusBarUtils;

public class ChatActivity extends BaseActivity {
    String toChatUsername;
    private ChatFragment chatFragment;
    public static ChatActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_activity_chat);
        instance = this;
        toChatUsername = Common.IM;
        chatFragment = new CustomChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        StatusBarUtils.setWindowStatusBarColor(ChatActivity.this, R.color.tabSetectColor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
        instance = null;
    }
}
