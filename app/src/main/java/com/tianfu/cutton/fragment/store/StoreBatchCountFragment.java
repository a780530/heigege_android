package com.tianfu.cutton.fragment.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.StoreKunMessage;
import com.tianfu.cutton.model.TestBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ImageUtil;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.StringJudgeUtils;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.PinchImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.R.id.ss_data;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreBatchCountFragment extends BaseFragment {
    /*    @BindView(R.id.bt_count_bag)
        Button btCountBag;*/
    @BindView(ss_data)
    LinearLayout ssData;
    /*    @BindView(R.id.nodata)
        AutoLinearLayout nodata;*/
    @BindView(R.id.imageCount)
    PinchImageView imageCount;
    @BindView(R.id.mCheckDate)
    TextView mCheckDate;
    @BindView(R.id.collectBatchCode)
    TextView collectBatchCode;
    @BindView(R.id.mTotalBag)
    TextView mTotalBag;
    @BindView(R.id.mFactoryCode)
    TextView mFactoryCode;
    @BindView(R.id.mType)
    TextView mType;
    @BindView(R.id.mStandard)
    TextView mStandard;
    @BindView(R.id.mOrigin)
    TextView mOrigin;
    @BindView(R.id.mWeightCode)
    TextView mWeightCode;
    @BindView(R.id.mGrossweight)
    TextView mGrossweight;
    @BindView(R.id.mMoisture)
    TextView mMoisture;
    @BindView(R.id.mTareweight)
    TextView mTareweight;
    @BindView(R.id.mTrash)
    TextView mTrash;
    @BindView(R.id.mNetweight)
    TextView mNetweight;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.mStdweight)
    TextView mStdweight;
    @BindView(R.id.mQualityCode)
    TextView mQualityCode;
    @BindView(R.id.mYgP1)
    TextView mYgP1;
    @BindView(R.id.mYgP2)
    TextView mYgP2;
    @BindView(R.id.mYgP3)
    TextView mYgP3;
    @BindView(R.id.mWhite1)
    TextView mWhite1;
    @BindView(R.id.mWhite2)
    TextView mWhite2;
    @BindView(R.id.mWhite3)
    TextView mWhite3;
    @BindView(R.id.mWhite4)
    TextView mWhite4;
    @BindView(R.id.mWhite5)
    TextView mWhite5;
    @BindView(R.id.mLightSpotted1)
    TextView mLightSpotted1;
    @BindView(R.id.mLightSpotted2)
    TextView mLightSpotted2;
    @BindView(R.id.mLightSpotted3)
    TextView mLightSpotted3;
    @BindView(R.id.mYellowish1)
    TextView mYellowish1;
    @BindView(R.id.mYellowish2)
    TextView mYellowish2;
    @BindView(R.id.mYellowish3)
    TextView mYellowish3;
    @BindView(R.id.mYellow1)
    TextView mYellow1;
    @BindView(R.id.mYellow2)
    TextView mYellow2;
    @BindView(R.id.mBreakLoadAverage)
    TextView mBreakLoadAverage;
    @BindView(R.id.mBreakLoadMax)
    TextView mBreakLoadMax;
    @BindView(R.id.mBreakLoadMin)
    TextView mBreakLoadMin;
    @BindView(R.id.mBreakLoad1)
    TextView mBreakLoad1;
    @BindView(R.id.mBreakLoad1Rate)
    TextView mBreakLoad1Rate;
    @BindView(R.id.mBreakLoad2)
    TextView mBreakLoad2;
    @BindView(R.id.mBreakLoad2Rate)
    TextView mBreakLoad2Rate;
    @BindView(R.id.mBreakLoad3)
    TextView mBreakLoad3;
    @BindView(R.id.mBreakLoad3Rate)
    TextView mBreakLoad3Rate;
    @BindView(R.id.mBreakLoad4)
    TextView mBreakLoad4;
    @BindView(R.id.mBreakLoad4Rate)
    TextView mBreakLoad4Rate;
    @BindView(R.id.mBreakLoad5)
    TextView mBreakLoad5;
    @BindView(R.id.mBreakLoad5Rate)
    TextView mBreakLoad5Rate;
    @BindView(R.id.mRdAverage)
    TextView mRdAverage;
    @BindView(R.id.mRdMax)
    TextView mRdMax;
    @BindView(R.id.mRdMin)
    TextView mRdMin;
    @BindView(R.id.mPlusBAverage)
    TextView mPlusBAverage;
    @BindView(R.id.mPlusBMax)
    TextView mPlusBMax;
    @BindView(R.id.mPlusBMin)
    TextView mPlusBMin;
    @BindView(R.id.mLengthGrade)
    TextView mLengthGrade;
    @BindView(R.id.mLengthAverage)
    TextView mLengthAverage;
    @BindView(R.id.mLengthMax)
    TextView mLengthMax;
    @BindView(R.id.mLengthMin)
    TextView mLengthMin;
    @BindView(R.id.mLength25)
    TextView mLength25;
    @BindView(R.id.mLength26)
    TextView mLength26;
    @BindView(R.id.mLength27)
    TextView mLength27;
    @BindView(R.id.mLength28)
    TextView mLength28;
    @BindView(R.id.mLength29)
    TextView mLength29;
    @BindView(R.id.mLength30)
    TextView mLength30;
    @BindView(R.id.mLength31)
    TextView mLength31;
    @BindView(R.id.mLength32)
    TextView mLength32;
    @BindView(R.id.mMicronGrade)
    TextView mMicronGrade;
    @BindView(R.id.mMicronAverage)
    TextView mMicronAverage;
    @BindView(R.id.mMicronMax)
    TextView mMicronMax;
    @BindView(R.id.mMicronMin)
    TextView mMicronMin;
    @BindView(R.id.mMicronA)
    TextView mMicronA;
    @BindView(R.id.mMicronB)
    TextView mMicronB;
    @BindView(R.id.mMicronC)
    TextView mMicronC;
    @BindView(R.id.mMicronGradeA1)
    TextView mMicronGradeA1;
    @BindView(R.id.mMicronGradeA1Rate)
    TextView mMicronGradeA1Rate;
    @BindView(R.id.mMicronGradeB1)
    TextView mMicronGradeB1;
    @BindView(R.id.mMicronGradeB1Rate)
    TextView mMicronGradeB1Rate;
    @BindView(R.id.mMicronGradeB2)
    TextView mMicronGradeB2;
    @BindView(R.id.mMicronGradeB2Rate)
    TextView mMicronGradeB2Rate;
    @BindView(R.id.mMicronGradeC1)
    TextView mMicronGradeC1;
    @BindView(R.id.mMicronGradeC1Rate)
    TextView mMicronGradeC1Rate;
    @BindView(R.id.mMicronGradeC2)
    TextView mMicronGradeC2;
    @BindView(R.id.mMicronGradeC2Rate)
    TextView mMicronGradeC2Rate;
    @BindView(R.id.mUniformityIndexAverage)
    TextView mUniformityIndexAverage;
    @BindView(R.id.mUniformityIndexMax)
    TextView mUniformityIndexMax;
    @BindView(R.id.mUniformityIndexMin)
    TextView mUniformityIndexMin;
    @BindView(R.id.uniformityIndex1)
    TextView uniformityIndex1;
    @BindView(R.id.mUniformityIndex1Rate)
    TextView mUniformityIndex1Rate;
    @BindView(R.id.uniformityIndex2)
    TextView uniformityIndex2;
    @BindView(R.id.mUniformityIndex2Rate)
    TextView mUniformityIndex2Rate;
    @BindView(R.id.uniformityIndex3)
    TextView uniformityIndex3;
    @BindView(R.id.mUniformityIndex3Rate)
    TextView mUniformityIndex3Rate;
    @BindView(R.id.uniformityIndex4)
    TextView uniformityIndex4;
    @BindView(R.id.mUniformityIndex4Rate)
    TextView mUniformityIndex4Rate;
    @BindView(R.id.uniformityIndex5)
    TextView uniformityIndex5;
    @BindView(R.id.mUniformityIndex5Rate)
    TextView mUniformityIndex5Rate;
    @BindView(R.id.mRemark)
    TextView mRemark;
    @BindView(R.id.mLab)
    TextView mLab;
    @BindView(R.id.mCreateTime)
    TextView mCreateTime;
    @BindView(R.id.mColorGrade)
    TextView mColorGrade;
    @BindView(R.id.mCompany)
    TextView mCompany;
    /*   @BindView(R.id.pic)
       PinchImageView pic;*/
    private View mRootview;
    private String code;
    private String productId;
    private LinearLayout llCount;
    private PopupWindow popWindowShare;
    private Bitmap bitmap;
    Handler handler = new Handler();
    ;
    private LinearLayout ll_goCount;
    private Integer hight;
    private ImageView ivNodata;
    private Bitmap watermarkBitmap;

    public StoreBatchCountFragment() {
        // Required empty public constructor
    }

    private Map<String, String> map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootview != null) {
            return mRootview;
        } else {
            mRootview = inflater.inflate(R.layout.fragment_store_batch_count, container, false);
            ViewGroup parent = (ViewGroup) mRootview.getParent();
            if (parent != null) {
                parent.removeView(mRootview);
            }
            ButterKnife.bind(this, mRootview);
            showProgressBar("", true);
            ivNodata = (ImageView) mRootview.findViewById(R.id.nodata);
            llCount = (LinearLayout) mRootview.findViewById(R.id.ll_count);
            Intent intent = getActivity().getIntent();
            code = intent.getStringExtra("code");
            String fromKun = intent.getStringExtra("fromKun");
            productId = intent.getStringExtra("productId");
            if (!TextUtils.isEmpty(productId)) {
//                initIsOn();
            } else {
                ssData.setVisibility(View.VISIBLE);
//                nodata.setVisibility(View.GONE);
            }

            map = new HashMap<>();
            map.put("code", code);
            map.put("fromKun", fromKun);
            map.put("deviceNo", Common.deviceNo);
            map.put("from", Common.from);
            map.put("version", Common.versionNo);
//            ll_goCount = (LinearLayout) mRootview.findViewById(R.id.ll_goCount);
            initData();
            imageCount.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    boolean weixinAvilible = Common.isWeixinAvilible(getActivity());
                   /* if (!weixinAvilible) {
                        ToastUtil.show(BaseApplication.getContextObject(), "请先安装微信后再分享");
                        return true;
                    }*/
//                    goShare();
                    saveImage(watermarkBitmap);
                    shareSingleImage(imageCount);
                    return false;
                }
            });
            return mRootview;
        }
    }
    private void saveImage(Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
        {    // 获取SDCard指定目录下
            String  sdCardDir = Environment.getExternalStorageDirectory()+ "/CoolImage";
            File dirFile  = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile .exists()) {              //如果不存在，那就建立这个文件夹
                dirFile .mkdirs();
            }                          //文件夹有啦，就可以保存图片啦
            File file = new File(sdCardDir, "cotton.jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名
            if (file.exists()) {
                file.delete();
            }

            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if (bos != null) {
                    try {
                        bos.flush();
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void shareSingleImage(View view) {
        String imagePath = Environment.getExternalStorageDirectory() + File.separator + "CoolImage/cotton.jpg";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    private void goShare() {
        LayoutInflater inflater = (LayoutInflater) getActivity()
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
        popWindowShare.showAtLocation(imageCount, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindowShare.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
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
        UMImage image = new UMImage(getActivity(), watermarkBitmap);//bitmap文件
        image.compressStyle = UMImage.CompressStyle.QUALITY;
        new ShareAction(getActivity())
                .setPlatform(weixin)//传入平台
                .withMedia(image)
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

    public void aaa(LinearLayout scrollView) {
        int h = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        hight = SharedPreferencesUtil.getIntValue(BaseApplication.getContextObject(), "hight");
        final int mW = widthPixels * h / (heightPixels - hight);//需要动态计算
        View view = scrollView.getChildAt(0);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = mW;
        view.setLayoutParams(layoutParams);
        final int finalH = h;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shotScrollView(ssData, imageCount, mW, finalH);
            }
        }, 1000);
    }

    private void shotScrollView(LinearLayout ssData, ImageView iv, int mW, int finalH) {
        bitmap = Bitmap.createBitmap(mW, finalH, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        ssData.draw(canvas);
        iv.setImageBitmap(bitmap);
        ssData.setVisibility(View.GONE);
        ivNodata.setVisibility(View.GONE);
        dismissProgressBar();
//        分享
 /*       ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        bitmap = decodeSampledBitmapFromByte(b, bitmap.getWidth()/8, bitmap.getHeight()/8);*/
        Bitmap waterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_common_slogo);
        watermarkBitmap = ImageUtil.createWaterMaskCenter(bitmap, waterBitmap);

    }
    private Bitmap decodeSampledBitmapFromByte(byte[] bytes, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    private void initIsOn() {
        Map mapIsOn = new HashMap();
        mapIsOn.put("productIds", productId);
        HttpManager.getServerApi().getStoreKunMessage(mapIsOn).enqueue(new CallBack<StoreKunMessage>() {
            @Override
            public void success(StoreKunMessage data) {
                if (data.success) {
                    ssData.setVisibility(View.VISIBLE);
//                    nodata.setVisibility(View.GONE);
                } else {
//                    nodata.setVisibility(View.VISIBLE);
                    ssData.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initData() {
        HttpManager.getServerApi().getTest(map).enqueue(new CallBack<TestBean>() {
            @Override
            public void success(TestBean data) {
                if (data != null && data.success) {
                    TestBean.ValueBean item = data.value;
                    if (item != null) {
                        StringJudgeUtils.judgeStringText(item.batchCode, collectBatchCode);
                        StringJudgeUtils.judgeStringText(item.totalBag + "", mTotalBag);
                        StringJudgeUtils.judgeStringText(item.factoryCode, mFactoryCode);
                        StringJudgeUtils.judgeStringText(item.type, mType);
                        StringJudgeUtils.judgeStringText(item.standard, mStandard);
                        StringJudgeUtils.judgeStringText(item.uploadTime, mCreateTime);
                        StringJudgeUtils.judgeStringText(item.origin, mOrigin);
                        if (TextUtils.isEmpty(item.checkDate)) {
                            mCheckDate.setText("");
                        } else {
                            mCheckDate.setText(item.checkDate);
                        }
                        if (TextUtils.isEmpty(item.weightCode)) {
                            mWeightCode.setText("重量结果（证书编号：）");
                        } else {
                            mWeightCode.setText("重量结果（证书编号：" + item.weightCode + "）");
                        }
//                    mQualityCode
                        if (TextUtils.isEmpty(item.qualityCode)) {
                            mQualityCode.setText("质量结果（证书编号：）");
                        } else {
                            mQualityCode.setText("质量结果（证书编号：" + item.qualityCode + "）");
                        }

                        StringJudgeUtils.judgeStringText(item.grossweight, mGrossweight);
                        if (TextUtils.isEmpty(item.netweight)) {
                            mNetweight.setText("");
                        } else if (Float.parseFloat(item.netweight) == 0) {
                            mNetweight.setText("0");
                        } else {
                            BigDecimal bigDecimal = new BigDecimal(Float.parseFloat(item.netweight) / 1000);
                            float v = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
                            DecimalFormat format = new DecimalFormat("##0.000");
                            String format1 = format.format(v);
                            mNetweight.setText(format1 + "");
                        }
                        if (TextUtils.isEmpty(item.stdweight)) {
                            mStdweight.setText("");
                        } else if (Float.parseFloat(item.stdweight) == 0) {
                            mStdweight.setText("0");
                        } else {
                            BigDecimal bigDecimal = new BigDecimal(Double.parseDouble(item.stdweight) / 1000);
                            float v = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
                            DecimalFormat format = new DecimalFormat("##0.000");
                            String format1 = format.format(v);
                            mStdweight.setText(format1 + "");
                        }
                        StringJudgeUtils.judgeStringText(item.moisture + "", mMoisture);
                        StringJudgeUtils.judgeStringText(item.tareweight, mTareweight);
                        StringJudgeUtils.judgeStringText(item.trash + "", mTrash);

                        if (TextUtils.isEmpty(item.lengthGrade + "")) {
                            mLengthGrade.setText("长度级：29毫米");
                        } else {
                            mLengthGrade.setText("长度级：" + item.lengthGrade + "毫米");
                        }
                        if (TextUtils.isEmpty(item.lengthAverage + "")) {
                            mLengthAverage.setText("平均值：");
                        } else {
                            mLengthAverage.setText("平均值:" + item.lengthAverage);
                        }
                        StringJudgeUtils.judgeStringText(item.lengthMax + "", mLengthMax);
                        StringJudgeUtils.judgeStringText(item.lengthMin + "", mLengthMin);
                        StringJudgeUtils.judgeStringText(item.length25, mLength25);
                        StringJudgeUtils.judgeStringText(item.length26, mLength26);
                        StringJudgeUtils.judgeStringText(item.length27, mLength27);
                        StringJudgeUtils.judgeStringText(item.length28, mLength28);
                        StringJudgeUtils.judgeStringText(item.length29, mLength29);
                        StringJudgeUtils.judgeStringText(item.length30, mLength30);
                        StringJudgeUtils.judgeStringText(item.factory, mCompany);
                        StringJudgeUtils.judgeStringText(item.length31, mLength31);
                        StringJudgeUtils.judgeStringText(item.length32, mLength32);
                        StringJudgeUtils.judgeStringText(item.ygP1, mYgP1);
                        StringJudgeUtils.judgeStringText(item.ygP2, mYgP2);
                        StringJudgeUtils.judgeStringText(item.ygP3, mYgP3);
                        if (TextUtils.isEmpty(item.colorGrade)) {
                            mColorGrade.setText("主体颜色级\n");
                        } else {
                            mColorGrade.setText("主体颜色级\n" + item.colorGrade);
                        }
                        StringJudgeUtils.judgeStringText(item.white1, mWhite1);
                        StringJudgeUtils.judgeStringText(item.white2, mWhite2);
                        StringJudgeUtils.judgeStringText(item.white3, mWhite3);
                        StringJudgeUtils.judgeStringText(item.white4, mWhite4);
                        StringJudgeUtils.judgeStringText(item.white5, mWhite5);
                        StringJudgeUtils.judgeStringText(item.lightSpotted1, mLightSpotted1);
                        StringJudgeUtils.judgeStringText(item.lightSpotted2, mLightSpotted2);
                        StringJudgeUtils.judgeStringText(item.lightSpotted3, mLightSpotted3);

                        StringJudgeUtils.judgeStringText(item.yellowish1, mYellowish1);
                        StringJudgeUtils.judgeStringText(item.yellowish2, mYellowish2);
                        StringJudgeUtils.judgeStringText(item.yellowish3, mYellowish3);
                        StringJudgeUtils.judgeStringText(item.yellow1, mYellow1);
                        StringJudgeUtils.judgeStringText(item.yellow1, mYellow2);
                        if (TextUtils.isEmpty(item.micronGrade)) {
                            mMicronGrade.setText("主体马克隆值级:B");
                        } else {
                            mMicronGrade.setText("主体马克隆值级:" + item.micronGrade);
                        }
                        if (TextUtils.isEmpty(item.micronAverage + "")) {
                            mMicronAverage.setText("平均值：");
                        } else {
                            mMicronAverage.setText("平均值:" + item.micronAverage);
                        }
                        StringJudgeUtils.judgeStringText(item.micronMax + "", mMicronMax);
                        StringJudgeUtils.judgeStringText(item.micronMin + "", mMicronMin);
                        StringJudgeUtils.judgeStringText(item.micronA, mMicronA);
                        StringJudgeUtils.judgeStringText(item.micronB, mMicronB);
                        StringJudgeUtils.judgeStringText(item.micronC, mMicronC);
                        StringJudgeUtils.judgeStringText(item.micronGradeA1, mMicronGradeA1);
                        StringJudgeUtils.judgeStringText(item.micronGradeB1, mMicronGradeB1);
                        StringJudgeUtils.judgeStringText(item.micronGradeB2, mMicronGradeB2);
                        StringJudgeUtils.judgeStringText(item.micronGradeC1, mMicronGradeC1);
                        StringJudgeUtils.judgeStringText(item.micronGradeC2, mMicronGradeC2);
                        StringJudgeUtils.judgeStringText(item.micronGradeA1Rate, mMicronGradeA1Rate);
                        StringJudgeUtils.judgeStringText(item.micronGradeB1Rate, mMicronGradeB1Rate);
                        StringJudgeUtils.judgeStringText(item.micronGradeB2Rate, mMicronGradeB2Rate);
                        StringJudgeUtils.judgeStringText(item.micronGradeC1Rate, mMicronGradeC1Rate);
                        StringJudgeUtils.judgeStringText(item.micronGradeC2Rate, mMicronGradeC2Rate);
                        if (TextUtils.isEmpty(item.breakLoadAverage + "")) {
                            mBreakLoadAverage.setText("平均值：");
                        } else {
                            mBreakLoadAverage.setText("平均值:" + item.breakLoadAverage);
                        }

                        StringJudgeUtils.judgeStringText(item.breakLoadMax + "", mBreakLoadMax);
                        StringJudgeUtils.judgeStringText(item.breakLoadMin + "", mBreakLoadMin);
                        StringJudgeUtils.judgeStringText(item.breakLoad1, mBreakLoad1);
                        StringJudgeUtils.judgeStringText(item.breakLoad2, mBreakLoad2);
                        StringJudgeUtils.judgeStringText(item.breakLoad3, mBreakLoad3);
                        StringJudgeUtils.judgeStringText(item.breakLoad4, mBreakLoad4);
                        StringJudgeUtils.judgeStringText(item.breakLoad5, mBreakLoad5);
                        StringJudgeUtils.judgeStringText(item.breakLoad1Rate, mBreakLoad1Rate);
                        StringJudgeUtils.judgeStringText(item.breakLoad2Rate, mBreakLoad2Rate);
                        StringJudgeUtils.judgeStringText(item.breakLoad3Rate, mBreakLoad3Rate);
                        StringJudgeUtils.judgeStringText(item.breakLoad4Rate, mBreakLoad4Rate);
                        StringJudgeUtils.judgeStringText(item.breakLoad5Rate, mBreakLoad5Rate);
                        if (TextUtils.isEmpty(item.uniformityIndexAverage + "")) {
                            mUniformityIndexAverage.setText("平均值:");
                        } else {
                            mUniformityIndexAverage.setText("平均值:" + item.uniformityIndexAverage);
                        }

                        StringJudgeUtils.judgeStringText(item.uniformityIndexMax + "", mUniformityIndexMax);
                        StringJudgeUtils.judgeStringText(item.uniformityIndexMin + "", mUniformityIndexMin);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex1, uniformityIndex1);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex2, uniformityIndex2);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex3, uniformityIndex3);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex4, uniformityIndex4);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex5, uniformityIndex5);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex1Rate, mUniformityIndex1Rate);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex2Rate, mUniformityIndex2Rate);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex3Rate, mUniformityIndex3Rate);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex4Rate, mUniformityIndex4Rate);
                        StringJudgeUtils.judgeStringText(item.uniformityIndex5Rate, mUniformityIndex5Rate);
                        if (TextUtils.isEmpty(item.rdAverage + "")) {
                            mRdAverage.setText("Rd(%)\n平均值:");
                        } else {
                            mRdAverage.setText("Rd(%)\n平均值:" + item.rdAverage);
                        }
                        StringJudgeUtils.judgeStringText(item.rdMax + "", mRdMax);
                        StringJudgeUtils.judgeStringText(item.rdMin + "", mRdMin);
                        if (TextUtils.isEmpty(item.plusBAverage + "")) {
                            mPlusBAverage.setText("+b\n平均值：82.3");
                        } else {
                            mPlusBAverage.setText("+b\n平均值:" + item.plusBAverage);
                        }
                        StringJudgeUtils.judgeStringText(item.plusBMax + "", mPlusBMax);
                        StringJudgeUtils.judgeStringText(item.plusBMin + "", mPlusBMin);
                        StringJudgeUtils.judgeStringText(item.remark, mRemark);
                        if ("无".equals(mRemark.getText().toString().trim())){
                            mRemark.setText("");
                        }
                        StringJudgeUtils.judgeStringText(item.lab, mLab);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                aaa(ssData);
//                                llCount.setVisibility(View.GONE);
//                                ll_goCount.setVisibility(View.VISIBLE);

                            }
                        }, 1000);//3秒后执行Runnable中的run方法
                    } else {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                aaa(ssData);
//                                llCount.setVisibility(View.GONE);
//                                ll_goCount.setVisibility(View.VISIBLE);

                            }
                        }, 1000);//3秒后执行Runnable中的run方法
                    }

                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            aaa(ssData);
//                            llCount.setVisibility(View.GONE);
//                            ll_goCount.setVisibility(View.VISIBLE);

                        }
                    }, 1000);//3秒后执行Runnable中的run方法
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                ToastUtil.show(BaseApplication.getContextObject(), "网络异常");
            }
        });
    }


    @OnClick(R.id.imageCount)
    public void onViewClicked(View view) {
        switch (view.getId()) {
       /*     case R.id.bt_count_bag:
                Intent intent = new Intent(getActivity(), QualityDetailBagActivity.class);
                intent.putExtra("code", code);
                startActivity(intent);
                break;*/
            case R.id.imageCount:

                break;
        }
    }


}
