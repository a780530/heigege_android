package com.tianfu.cutton.activity.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.fragment.adapter.StoreDetailsBundleAdpater;
import com.tianfu.cutton.fragment.store.StoreKunMessageFragment;
import com.tianfu.cutton.fragment.store.StorekunBatchFragment;
import com.tianfu.cutton.model.CountBean;
import com.tianfu.cutton.model.MyStoreBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhy.autolayout.AutoLinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCommodityKunActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tlMain)
    TabLayout tlMain;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.vpMain)
    ViewPager vpMain;
    @BindView(R.id.bt_chushou)
    Button btChushou;
    @BindView(R.id.bt_xiaohao)
    Button btXiaohao;
    @BindView(R.id.bt_shangjia)
    Button btShangjia;
    @BindView(R.id.ll_buttoncommodity)
    AutoLinearLayout llButtoncommodity;
    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("信息");
        add("批次");
    }};

    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new StoreKunMessageFragment());
        add(new StorekunBatchFragment());
    }};
    private StoreDetailsBundleAdpater adapter;
    private Map<String, String> chageMap;
    private String productId;
    private String code;
    private String batchType;
    private PopupWindow popWindowShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commodity_kun);
        ButterKnife.bind(this);
        chageMap = new HashMap<String, String>();
        adapter = new StoreDetailsBundleAdpater(getSupportFragmentManager(), titleList, fragmentList);
        vpMain.setAdapter(adapter);
        tlMain.setupWithViewPager(vpMain);
        tlMain.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tlMain, 5, 5);
            }
        });
        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        code = intent.getStringExtra("code");
        batchType = intent.getStringExtra("batchType");
        productId = intent.getStringExtra("productId");
        chageMap.put("id", productId);
        if (state.equals("ON")) {
            llButtoncommodity.setVisibility(View.VISIBLE);
            btShangjia.setText("下架");
        } else if (state.equals("OFF")) {
            llButtoncommodity.setVisibility(View.VISIBLE);
            btShangjia.equals("上架");
        } else {
            llButtoncommodity.setVisibility(View.GONE);
        }
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }

    private UMShareListener uShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Map<String, String> mapShare = new HashMap<>();
            mapShare.put("isProduct", "1");
            mapShare.put("productId", productId);
            mapShare.put("batchType", batchType);
            mapShare.put("code", code);
            String mobile = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile");
            mapShare.put("mobile", mobile);
            HttpManager.getServerApi().addShare(mapShare).enqueue(new CallBack<CountBean>() {
                @Override
                public void success(CountBean data) {
                    System.out.println("分享成功");
                }

                @Override
                public void failure(ErrorType type, int httpCode) {

                }
            });
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

    @OnClick({R.id.iv_back, R.id.share, R.id.bt_chushou, R.id.bt_xiaohao, R.id.bt_shangjia})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.share:
               /* String url = Common.shareUrl + "cotton/fenXiang/batch.htm?";
                url = url + "productId=" + productId + "&isProduct=1" + "&" + "batchType=" + batchType + "&code=" + code;
                UMWeb web = new UMWeb(url);
                web.setTitle(code + "详情--XJM");//标题
                web.setDescription("发现了一批不错的棉花，赶紧来看看吧。");//描述
                new ShareAction(MyCommodityKunActivity.this)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(uShareListener)
                        .open();*/
               goShare();
                break;
            case R.id.bt_chushou:
                AlertDialog dialog = new AlertDialog.Builder(MyCommodityKunActivity.this)
                        .setMessage("是否将商品状态改为已出售？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                soldOut();
                            }
                        })
                        .create();
                dialog.show();

                break;
            case R.id.bt_xiaohao:
                AlertDialog dialog2 = new AlertDialog.Builder(MyCommodityKunActivity.this)
                        .setMessage("是否将商品状态改为已消耗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                deleteCommodity();
                            }
                        })
                        .create();
                dialog2.show();

                break;
            case R.id.bt_shangjia:
                final String s = btShangjia.getText().toString();
                AlertDialog dialog1 = new AlertDialog.Builder(MyCommodityKunActivity.this)
                        .setMessage("是否" + s + "此商品")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                offOrUp(s);
                            }
                        })
                        .create();
                dialog1.show();
                break;
        }
    }

    private void goShare() {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.pop_share, null);
        popWindowShare = new PopupWindow();
        initpopView(conentView);
        popWindowShare.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        popWindowShare.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        popWindowShare.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindowShare.setFocusable(true);//响应弹框以外的按钮的点击事件
        popWindowShare.setOutsideTouchable(true);
        //防止虚拟软键盘被遮住
        popWindowShare.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 刷新状态
        popWindowShare.update();
        popWindowShare.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.5f);//llSupplier
        popWindowShare.showAtLocation(llButtoncommodity, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindowShare.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    private void initpopView(View conentView) {
        TextView weChat = (TextView) conentView.findViewById(R.id.weChat);
        weChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN);
                popWindowShare.dismiss();
            }
        });
        TextView weChatFriend = (TextView) conentView.findViewById(R.id.weChatFriend);
        weChatFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                popWindowShare.dismiss();
            }
        });
        Button btCancle = (Button) conentView.findViewById(R.id.btCancle);
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowShare.dismiss();
            }
        });
    }

    private void share(SHARE_MEDIA weixin) {
        String url = Common.shareUrl + "cotton/fenXiang/kun.htm?";
        url = url + "productId=" + productId + "&isProduct=1" + "&" + "batchType=" + batchType + "&code=" + code;
        UMWeb web = new UMWeb(url);
        web.setThumb(new UMImage(this,R.mipmap.xjm));  //缩略图
        web.setTitle(code + "详情--XJCE");//标题
        web.setDescription("发现了一批不错的棉花，赶紧来看看吧。");//描述
        new ShareAction(this)
                .setPlatform(weixin)
                .setCallback(uShareListener)
                .withMedia(web)
                .share();
    }

    private void deleteCommodity() {
        chageMap.put("state", "DEPLETE");
        HttpManager.getServerApi().changeState(chageMap).enqueue(new CallBack<MyStoreBean>() {
            @Override
            public void success(MyStoreBean data) {
                if (data.success) {
                    onBackPressed();
                    ToastUtil.show(BaseApplication.getContextObject(), "消耗成功");
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void offOrUp(String s) {
        if (s.equals("上架")) {
            chageMap.put("state", "ON");
            HttpManager.getServerApi().changeState(chageMap).enqueue(new CallBack<MyStoreBean>() {
                @Override
                public void success(MyStoreBean data) {
                    onBackPressed();
                    ToastUtil.show(BaseApplication.getContextObject(), "上架成功");
                }

                @Override
                public void failure(ErrorType type, int httpCode) {

                }
            });
        } else {
            chageMap.put("state", "OFF");
            HttpManager.getServerApi().changeState(chageMap).enqueue(new CallBack<MyStoreBean>() {
                @Override
                public void success(MyStoreBean data) {
                    if (data.success) {
                        onBackPressed();
                        ToastUtil.show(BaseApplication.getContextObject(), "下架成功");
                    }
                }

                @Override
                public void failure(ErrorType type, int httpCode) {

                }
            });
        }
    }

    private void soldOut() {
        chageMap.put("state", "SOLD_OUT");
        HttpManager.getServerApi().changeState(chageMap).enqueue(new CallBack<MyStoreBean>() {
            @Override
            public void success(MyStoreBean data) {
                if (data.success) {
                    onBackPressed();
                    ToastUtil.show(BaseApplication.getContextObject(), "出售成功");
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }
}
