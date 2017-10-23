package com.tianfu.cutton.activity.store;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.VisitorTrack;
import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.fragment.adapter.InquiryDetailsBundleAdpater;
import com.tianfu.cutton.fragment.store.StoreBatchCountFragment;
import com.tianfu.cutton.fragment.store.StoreBatchMapFragment;
import com.tianfu.cutton.fragment.store.StoreBatchMessageFragment;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.model.CountBean;
import com.tianfu.cutton.model.StoreKunMessage;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CallKefu;
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

import static com.tianfu.cutton.R.id.call_phone;

public class StoreBatchActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tlMain)
    TabLayout tlMain;
    @BindView(R.id.vpMain)
    ViewPager vpMain;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.go_home)
    AutoLinearLayout goHome;
    @BindView(R.id.ll_batch_collection)
    AutoLinearLayout llBatchCollection;
    @BindView(call_phone)
    AutoLinearLayout callPhone;
    @BindView(R.id.ll_kefu)
    AutoLinearLayout llKefu;
    @BindView(R.id.iv_store_collection)
    ImageView ivStoreCollection;
    @BindView(R.id.ll_nodata)
    AutoLinearLayout llNodata;
    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("汇总表");
        add("信息");
        add("图表");
    }};
    private InquiryDetailsBundleAdpater adapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private String mobileBaojia;
    private String eMUserName;
    private String eMPassword;
    private Boolean isLogin;
    private String mobile;
    private String code;
    private HashMap<String, String> hashMap;
    Boolean isFavorites = false;
    private String batchType;
    private String productId;
    private HashMap<String, String> map;
    private String desc;
    private String price;
    private PopupWindow popWindowShare;
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_batch);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mobileBaojia = intent.getStringExtra("mobileBaojia");
        code = intent.getStringExtra("code");
        batchType = intent.getStringExtra("batchType");
        productId = intent.getStringExtra("productId");
        initIsOn();
        StoreBatchMessageFragment storeBatchMessageFragment = new StoreBatchMessageFragment();
        StoreBatchCountFragment storeBatchCountFragment = new StoreBatchCountFragment();
        StoreBatchMapFragment storeBatchMapFragment = new StoreBatchMapFragment();
        fragmentList.add(storeBatchCountFragment);
        fragmentList.add(storeBatchMessageFragment);
        fragmentList.add(storeBatchMapFragment);
        adapter = new InquiryDetailsBundleAdpater(getSupportFragmentManager(), titleList, fragmentList);
        vpMain.setAdapter(adapter);
        tlMain.setupWithViewPager(vpMain);
        tlMain.post(new Runnable() {
            @Override
            public void run() {
//                setIndicator(tlMain, 5,  5);

                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tlMain.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);

                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tlMain);

                    int dp10 = dip2px(BaseApplication.getContextObject(),10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });
        map = new HashMap<>();
        map.put("code", code);
        map.put("deviceNo", Common.deviceNo);
        map.put("productId", productId);
        map.put("isProduct", "1");
        map.put("batchType", batchType);
        hashMap = new HashMap<>();
    }

    private void initKefuData() {
        StoreKunMessage.ValueBean data = ((StoreBatchMessageFragment) fragmentList.get(0)).getData();
        String codeKefu = data.code;
        String bagCountKefu = data.bagCount;
        String batchCountKefu = data.batchCount;
        String propertyKefu = data.property;
        String typeKefu = data.type;
        String storageKefu = data.storage;
        String priceKefu = data.price;
        String takeTypeKefu = data.takeType;
        price = "";
        desc = codeKefu + "(" + bagCountKefu + "/" + batchCountKefu + ")";
        if (propertyKefu != null && !"".equals(propertyKefu)) {
            desc = desc + "   " + propertyKefu;
        }
        if (typeKefu != null && !"".equals(typeKefu)) {
            desc = desc + "\n" + typeKefu;
            if (storageKefu != null && !"".equals(storageKefu)) {
                desc = desc + "   " + storageKefu;
            }
        } else {
            if (storageKefu != null && !"".equals(storageKefu)) {
                desc = desc + "\n" + storageKefu;
            }
        }

        if (priceKefu.equals("0")|| TextUtils.isEmpty(priceKefu)) {
            price = "价格面议";
        } else {
            price = "￥" + priceKefu;
        }
        if (takeTypeKefu != null && !"".equals(takeTypeKefu)) {
            if (takeTypeKefu.equals("1")) {
                price = price + "(自提)";
            } else {
                price = price + "(送到)";
            }
        }
    }

    private void initIsOn() {
        Map mapIsOn = new HashMap();
        mapIsOn.put("productIds", productId);
        HttpManager.getServerApi().getStoreKunMessage(mapIsOn).enqueue(new CallBack<StoreKunMessage>() {
            @Override
            public void success(StoreKunMessage data) {
                if (data.success) {
                    ivShare.setEnabled(true);
                    llNodata.setVisibility(View.VISIBLE);
                } else {
                    ivShare.setEnabled(false);
                    llNodata.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
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

    @OnClick({R.id.iv_back, R.id.iv_share, R.id.go_home, R.id.ll_batch_collection, call_phone, R.id.ll_kefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_share:
                boolean weixinAvilible = Common.isWeixinAvilible(StoreBatchActivity.this);
                if (!weixinAvilible){
                    ToastUtil.show(BaseApplication.getContextObject(),"请先安装微信后再分享");
                    return;
                }
                goShare();
                break;
            case R.id.go_home:
                Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_batch_collection:
                if (!isLogin) {
                    startActivity(new Intent(BaseApplication.getContextObject(), LoginActivity.class));
                    return;
                }
                if (!isFavorites) {
                    HttpManager.getServerApi().addCollection(map).enqueue(new CallBack<CodeValidate>() {
                        @Override
                        public void success(CodeValidate data) {
                            if (data.success) {
                                ToastUtil.showImage(BaseApplication.getContextObject(), "收藏成功");
                                ivStoreCollection.setImageResource(R.mipmap.ic_collection);
                                isFavorites = true;
                            } else {
                                ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                            }
                        }

                        @Override
                        public void failure(ErrorType type, int httpCode) {

                        }
                    });
                } else {
                    HttpManager.getServerApi().deleteCollection(map).enqueue(new CallBack<CodeValidate>() {
                        @Override
                        public void success(CodeValidate data) {
                            if (data.success) {
                                isFavorites = false;
                                ivStoreCollection.setImageResource(R.mipmap.ic_common_collect);
                                ToastUtil.showImage(BaseApplication.getContextObject(), "取消成功");
                            } else {
                                ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                            }
                        }

                        @Override
                        public void failure(ErrorType type, int httpCode) {

                        }
                    });
                }
                break;
            case call_phone:
                Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobileBaojia));
                intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentCall);
                break;
            case R.id.ll_kefu:
                LayoutInflater inflater = (LayoutInflater) this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View conentView = inflater.inflate(R.layout.call_kefu, null);
//        final PopWindow popWindow = new PopWindow(this, conentView);
                final PopupWindow popWindow = new PopupWindow();
                Button call_phone = (Button) conentView.findViewById(R.id.call_phone);
                call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                                + "0991-3671111"));//电话号码
                        startActivity(intent);
                        popWindow.dismiss();
                    }
                });
                final Button call_onLine = (Button) conentView.findViewById(R.id.call_onLine);
                call_onLine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popWindow.dismiss();
                        if (!isLogin) {
                            startActivity(new Intent(BaseApplication.getContextObject(), LoginActivity.class));
                        } else {
                            CallKefu.callOnLine(StoreBatchActivity.this, eMUserName, eMPassword);
                            initKefuData();
                            Message message = Message.createTxtSendMessage("", Common.IM);
                            message.addContent(createVisitorTrack());
                            ChatClient.getInstance().chatManager().sendMessage(message);
                        }

                    }
                });
                Button dissmiss = (Button) conentView.findViewById(R.id.dissmiss);
                dissmiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popWindow.dismiss();
                    }
                });
                popWindow.setContentView(conentView);
                // 设置SelectPicPopupWindow弹出窗体的宽
                popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                // 设置SelectPicPopupWindow弹出窗体的高
                popWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popWindow.setFocusable(true);//响应弹框以外的按钮的点击事件
                popWindow.setOutsideTouchable(true);
                //防止虚拟软键盘被遮住
                popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                // 刷新状态
                popWindow.update();
                popWindow.setBackgroundDrawable(new BitmapDrawable());
                backgroundAlpha(0.5f);//llSupplier
                popWindow.showAtLocation(llKefu, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });
                break;
        }
    }

    private void goShare() {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.pop_share, null);
        popWindowShare = new PopupWindow();
        initView(conentView);
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
        popWindowShare.showAtLocation(llKefu, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindowShare.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    private void initView(View conentView) {
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
        String url = Common.shareUrl + "cotton/fenXiang/batch.htm?";
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

/*        String url = Common.shareUrl + "cotton/fenXiang/batch.htm?";
        url = url + "productId=" + productId + "&isProduct=1" + "&" + "batchType=" + batchType + "&code=" + code;
        UMWeb web = new UMWeb(url);
        web.setTitle(code + "详情--XJM");//标题
        web.setDescription("发现了一批不错的棉花，赶紧来看看吧。");//描述
        new ShareAction(StoreBatchActivity.this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(uShareListener)
                .open();*/
    }

    private VisitorTrack createVisitorTrack() {
        VisitorTrack track = ContentFactory.createVisitorTrack(null);
        track.title("我正在看:")
                .price(price)
                .desc(desc)
                .imageUrl(Common.shareUrl + "static/cotton.jpg")
                .itemUrl(Common.shareUrl + "cottonBatchDetails" + "/" + code + "/" + "1" + "/" + batchType + "/" + productId);
        return track;
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    private UMShareListener uShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Map<String, String> mapShare = new HashMap<>();
            mapShare.put("isProduct", "1");
            mapShare.put("productId", productId);
            mapShare.put("batchType", batchType);
            mapShare.put("code", code);
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

    @Override
    protected void onStart() {
        super.onStart();
        eMUserName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMUserName");
        eMPassword = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMPassword");
        isLogin = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isLogin");
        mobile = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile");
        map.put("mobile", mobile);
        hashMap.put("mobile", mobile);
        initData();
    }

    private void initData() {
        hashMap.put("code", code);
        hashMap.put("deviceNo", Common.deviceNo);
        hashMap.put("productId", productId);
        hashMap.put("isProduct", "1");
        HttpManager.getServerApi().isCollection(hashMap).enqueue(new CallBack<CodeValidate>() {
            @Override
            public void success(CodeValidate data) {
                if (data.success) {
                    if (data.value) {
                        isFavorites = false;
                        ivStoreCollection.setImageResource(R.mipmap.ic_common_collect);
                    } else {
                        isFavorites = true;
                        ivStoreCollection.setImageResource(R.mipmap.ic_collection);
                    }
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.dissMiss();
    }
}
