package com.tianfu.cutton.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.AgentInfo;
import com.tianfu.cutton.ChatActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.common.Common;

import java.util.List;

/**
 * Created by admin on 2017/8/4.
 */

public class CallKefu {
    public static void callOnLine(final Context context, String eMUserName, String eMPassword) {
        UIProvider.getInstance().setUserProfileProvider(new UIProvider.UserProfileProvider() {
            @Override
            public void setNickAndAvatar(Context context, Message message, ImageView userAvatarView, TextView usernickView) {
                if (message.direct() == Message.Direct.RECEIVE) {
                    //设置接收方的昵称和头像
//                    UserUtil.setAgentNickAndAvatar(context, message, userAvatarView, usernickView);
                    AgentInfo agentInfo = com.hyphenate.helpdesk.model.MessageHelper.getAgentInfo(message);
                    if (usernickView != null) {
                        usernickView.setText("在线客服");
                        if (agentInfo != null) {
                            if (!TextUtils.isEmpty(agentInfo.getNickname())) {
                                usernickView.setText("在线客服");
                            }
                        }
                    }
                    if (userAvatarView != null) {
                        userAvatarView.setImageResource(com.hyphenate.helpdesk.R.drawable.hd_default_avatar);
                        if (agentInfo != null) {
                            if (!TextUtils.isEmpty(agentInfo.getAvatar())) {
                                String strUrl = agentInfo.getAvatar();
                                // 设置客服头像
                                if (!TextUtils.isEmpty(strUrl)) {
                                    if (!strUrl.startsWith("http")) {
                                        strUrl = "http:" + strUrl;
                                    }
                                }
                            }
                        }

                    }
                } else {
                    //此处设置当前登录用户的头像，
                    if (userAvatarView != null) {
                        userAvatarView.setImageResource(R.mipmap.ic_mine_head);
                    }
                }
            }
        });
        if (ChatClient.getInstance().isLoggedInBefore()) {
            Intent intent = new IntentBuilder(context)
                    .setTargetClass(ChatActivity.class)
                    .setShowUserNick(true)
                    .setServiceIMNumber(Common.IM) //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                    .build();
            context.startActivity(intent);
        } else {
            ChatClient.getInstance().login(eMUserName, eMPassword, new Callback() {
                @Override
                public void onSuccess() {
                    Intent intent = new IntentBuilder(context)
                            .setTargetClass(ChatActivity.class)
                            .setServiceIMNumber(Common.IM) //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                            .setShowUserNick(true)
                            .build();
                    context.startActivity(intent);
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }
    public static void addMessageListener(final ImageView imageView){
        ChatClient.getInstance().getChat().addMessageListener(new ChatManager.MessageListener() {
            @Override
            public void onMessage(List<Message> list) {
                imageView.setImageResource(R.mipmap.ic_message);
            }

            @Override
            public void onCmdMessage(List<Message> list) {

            }

            @Override
            public void onMessageStatusUpdate() {

            }

            @Override
            public void onMessageSent() {

            }
        });
    };
}
