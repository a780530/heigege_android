package com.tianfu.cutton.activity.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CountBean;
import com.tianfu.cutton.model.PremiumJsonBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.StringJudgeUtils;
import com.tianfu.cutton.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.R.id.sts_colorGrade;

public class StsDetadilsActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.ll_title)
    AutoLinearLayout llTitle;
    @BindView(R.id.sts_price)
    TextView stsPrice;
    @BindView(R.id.sts_sts)
    TextView stsSts;
    @BindView(sts_colorGrade)
    TextView stsColorGrade;
    @BindView(R.id.sts_color)
    TextView stsColor;
    @BindView(R.id.lengthAverage)
    TextView lengthAverage;
    @BindView(R.id.sts_lengthAverage)
    TextView stsLengthAverage;
    @BindView(R.id.micronAverage)
    TextView micronAverage;
    @BindView(R.id.sts_micronAverage)
    TextView stsMicronAverage;
    @BindView(R.id.breakLoadAverage)
    TextView breakLoadAverage;
    @BindView(R.id.sts_breakLoadAverage)
    TextView stsBreakLoadAverage;
    @BindView(R.id.uniformityIndexAverage)
    TextView uniformityIndexAverage;
    @BindView(R.id.sts_uniformityIndexAverage)
    TextView stsUniformityIndexAverage;
    @BindView(R.id.ygP1)
    TextView ygP1;
    @BindView(R.id.ygP2)
    TextView ygP2;
    @BindView(R.id.ygP3)
    TextView ygP3;
    @BindView(R.id.sts_ygP)
    TextView stsYgP;
    @BindView(R.id.yxxw)
    TextView yxxw;
    @BindView(R.id.sts_yxxw)
    TextView stsYxxw;
    @BindView(R.id.origin)
    TextView origin;
    @BindView(R.id.sts_origin)
    TextView stsOrigin;
    private PopupWindow popWindowShare;
    private String code;
    private String beasePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sts_detadils);
        ButterKnife.bind(this);
        ivMessage.setImageResource(R.mipmap.ic_common_share);
        Intent intent = getIntent();
        beasePrice = intent.getStringExtra("beasePrice");
        PremiumJsonBean premiumJsonBean = (PremiumJsonBean) intent.getSerializableExtra("premiumJsonBean");
        StringJudgeUtils.judgeStringNumber(beasePrice,stsPrice);
        code = premiumJsonBean.code;
        tvTitle.setText(code + "-升贴水详情");
        initData(premiumJsonBean);
    }

    private void initData(PremiumJsonBean premiumJsonBean) {
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts + "", stsSts);
        if (TextUtils.isEmpty(premiumJsonBean.colorGrade)) {
            stsColorGrade.setText("无主体颜色级");
        } else {
            stsColorGrade.setText("主体：" + premiumJsonBean.colorGrade);
        }
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts_color + "", stsColor);//颜色级Sts
//                    lengthAverage
        StringJudgeUtils.judgeStringNumber(premiumJsonBean.lengthAverage + "", lengthAverage);//长度
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts_lengthAverage + "", stsLengthAverage);//长度Sts
//                    micronAverage
        if (!TextUtils.isEmpty(premiumJsonBean.micronAverage) && !TextUtils.isEmpty(premiumJsonBean.micronAverageDw)) {
            micronAverage.setText(premiumJsonBean.micronAverageDw + " (" + premiumJsonBean.micronAverage + ")");
        }
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts_micronAverage + "", stsMicronAverage);//马克Sts
//                    breakLoadAverage
        if (!TextUtils.isEmpty(premiumJsonBean.breakLoadAverage) && !TextUtils.isEmpty(premiumJsonBean.breakLoadAverageDw)) {
            breakLoadAverage.setText(premiumJsonBean.breakLoadAverageDw + " (" + premiumJsonBean.breakLoadAverage + ")");
        }
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts_breakLoadAverage + "", stsBreakLoadAverage);//强力Sts
//                    uniformityIndexAverage
        if (!TextUtils.isEmpty(premiumJsonBean.uniformityIndexAverage) && !TextUtils.isEmpty(premiumJsonBean.uniformityIndexAverageDw)) {
            uniformityIndexAverage.setText(premiumJsonBean.uniformityIndexAverageDw + " (" + premiumJsonBean.uniformityIndexAverage + ")");
        }
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts_uniformityIndexAverage + "", stsUniformityIndexAverage);//长度整齐度Sts
//                    ygP
                 /*   if (!TextUtils.isEmpty(premiumJsonBean.ygP1) && !TextUtils.isEmpty(premiumJsonBean.ygP2) && !TextUtils.isEmpty(premiumJsonBean.ygP3)) {
                        ygP.setText("P1:" + premiumJsonBean.ygP1 + "%;" + " P2:" + premiumJsonBean.ygP2 + "%;" + " P3:" + premiumJsonBean.ygP3 + "%");
                    }*/
        if (!TextUtils.isEmpty(premiumJsonBean.ygP1) && !premiumJsonBean.ygP1.equals("0")) {
            ygP1.setText("P1:" + premiumJsonBean.ygP1 + "%;");
            ygP1.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(premiumJsonBean.ygP2) && premiumJsonBean.ygP2.equals("0") && !TextUtils.isEmpty(premiumJsonBean.ygP3) && premiumJsonBean.ygP3.equals("0")) {
                ygP1.setText("P1:" + premiumJsonBean.ygP1 + "%");
            }
        } else {
            ygP1.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(premiumJsonBean.ygP2) && !premiumJsonBean.ygP2.equals("0")) {
            ygP2.setText("P2:" + premiumJsonBean.ygP2 + "%;");
            ygP2.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(premiumJsonBean.ygP3) && premiumJsonBean.ygP3.equals("0")) {
                ygP2.setText("P2:" + premiumJsonBean.ygP2 + "%");
            }
        } else {
            ygP2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(premiumJsonBean.ygP3) && !premiumJsonBean.ygP3.equals("0")) {
            ygP3.setText("P3:" + premiumJsonBean.ygP3 + "%");
            ygP3.setVisibility(View.VISIBLE);
        } else {
            ygP3.setVisibility(View.GONE);
        }
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts_yg + "", stsYgP);//轧工质量Sts
//                    yxxw
        StringJudgeUtils.judgeStringNumber(premiumJsonBean.yxxw, yxxw);
        if (!TextUtils.isEmpty(premiumJsonBean.yxxw)) {
            yxxw.setText(premiumJsonBean.yxxw);
        } else {
            yxxw.setText("0");
        }
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts_yxxw + "", stsYxxw);
//                    origin
        StringJudgeUtils.judgeStringNumber(premiumJsonBean.origin, origin);
        StringJudgeUtils.judgeStringSts(premiumJsonBean.sts_isXinjiang + "", stsOrigin);
    }

    @OnClick({R.id.iv_back, R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_message:
                boolean weixinAvilible = Common.isWeixinAvilible(this);
                if (!weixinAvilible) {
                    ToastUtil.show(BaseApplication.getContextObject(), "请先安装微信后再分享");
                    return;
                }
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
        popWindowShare.showAtLocation(ivMessage, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindowShare.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {
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
        String url = Common.shareUrl + "cotton/fenXiang/batch.htm?";
        url = url + "productId=(null)&isProduct=0" + "&" + "batchType=" + "2" + "&code=" + code;
        UMWeb web = new UMWeb(url);
        web.setThumb(new UMImage(this, R.mipmap.xjm));  //缩略图
        web.setTitle(code + "详情--XJCE");//标题
        web.setDescription("发现了一批不错的棉花，赶紧来看看吧。");//描述
        new ShareAction(this)
                .setPlatform(weixin)
                .setCallback(umShareListener)
                .withMedia(web)
                .share();
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            HashMap<String, String> mapShare = new HashMap<>();
            mapShare.put("isProduct", "0");
            mapShare.put("productId", "null");
            mapShare.put("batchType", "2");
            mapShare.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
            mapShare.put("code", code);

            HttpManager.getServerApi().addShare(mapShare).enqueue(new CallBack<CountBean>() {
                @Override
                public void success(CountBean data) {

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
}
