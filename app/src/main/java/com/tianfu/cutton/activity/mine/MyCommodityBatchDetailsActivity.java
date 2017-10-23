package com.tianfu.cutton.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.tianfu.cutton.fragment.adapter.InquiryDetailsBundleAdpater;
import com.tianfu.cutton.fragment.store.StoreBatchCountFragment;
import com.tianfu.cutton.fragment.store.StoreBatchMapFragment;
import com.tianfu.cutton.fragment.store.StoreBatchMessageFragment;
import com.tianfu.cutton.model.CountBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCommodityBatchDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tlMain)
    TabLayout tlMain;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.vpMain)
    ViewPager vpMain;
    @BindView(R.id.ll_ButtonDetails)
    Button llButtonDetails;


    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("汇总表");
        add("信息");
        add("图表");
    }};
    private InquiryDetailsBundleAdpater adapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new StoreBatchCountFragment());
        add(new StoreBatchMessageFragment());
        add(new StoreBatchMapFragment());
    }};
    private Map<String, String> chageMap;
    private String productId;
    private String code;
    private String batchType;
    private PopupWindow popWindowShare;
    private String state;
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commodity_details);
        ButterKnife.bind(this);
        chageMap = new HashMap<String, String>();
        adapter = new InquiryDetailsBundleAdpater(getSupportFragmentManager(), titleList, fragmentList);
        vpMain.setAdapter(adapter);
        tlMain.setupWithViewPager(vpMain);
        Intent intent = getIntent();
        tlMain.post(new Runnable() {
            @Override
            public void run() {

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
        state = intent.getStringExtra("state");
        productId = intent.getStringExtra("productId");
        code = intent.getStringExtra("code");
        batchType = intent.getStringExtra("batchType");
        chageMap.put("id", productId);
        if (state.equals("ON")) {
//            llButtonDetails.setVisibility(View.VISIBLE);
//            btShangjia.setText("下架");
        } else if (state.equals("OFF")) {
//            llButtonDetails.setVisibility(View.VISIBLE);
//            btShangjia.equals("上架");
        } else {
//            llButtonDetails.setVisibility(View.GONE);
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
            System.out.println("mobile:------" + mobile);
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

    @OnClick({R.id.iv_back, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.share:
                goShare();
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
        popWindowShare.showAtLocation(llButtonDetails, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindowShare.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
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
        String url = Common.shareUrl + "cotton/fenXiang/batch.htm?";
        url = url + "productId=" + productId + "&isProduct=1" + "&" + "batchType=" + batchType + "&code=" + code;
        UMWeb web = new UMWeb(url);
        web.setThumb(new UMImage(this, R.mipmap.xjm));  //缩略图
        web.setTitle(code + "详情--XJCE");//标题
        web.setDescription("发现了一批不错的棉花，赶紧来看看吧。");//描述
        new ShareAction(this)
                .setPlatform(weixin)
                .setCallback(uShareListener)
                .withMedia(web)
                .share();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
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
    private int requestCode;
    @OnClick(R.id.ll_ButtonDetails)
    public void onViewClicked() {
        Intent intent = new Intent(this,ChangeBatchActivity.class);
        intent.putExtra("productId",productId);
        intent.putExtra("state",state);
        requestCode = 1;
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (data != null) {
                    String stringExtra = data.getStringExtra("1");
                    if (stringExtra != null) {
                        if (stringExtra.equals("close")) {
                            finish();
                        }
                    }
                }
                break;
        }
    }
}
