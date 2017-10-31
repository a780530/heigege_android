package com.tianfu.cutton.activity.purchase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.MicronAverage;
import com.tianfu.cutton.model.SerializableMap;
import com.tianfu.cutton.utils.ListToListString;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.DoubleSeekBar;
import com.tianfu.cutton.widget.MultiCheckGroupView;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.R.id.bt_c1;
import static com.tianfu.cutton.R.id.bt_c2;
import static com.tianfu.cutton.R.id.tv_elasticity;


public class ReleasePurchaseActivity extends BaseActivity {
    @BindView(R.id.tv_dismiss)
    TextView tvDismiss;
    @BindView(R.id.iv_black_keyword)
    ImageView ivBlackKeyword;
    @BindView(R.id.rl_keyword)
    AutoRelativeLayout rlKeyword;
    @BindView(R.id.keyword_group)
    MultiCheckGroupView keywordGroup;
    @BindView(R.id.iv_black_location)
    ImageView ivBlackLocation;
    @BindView(R.id.rl_location)
    AutoRelativeLayout rlLocation;
    @BindView(R.id.location_group)
    MultiCheckGroupView locationGroup;
    @BindView(R.id.iv_black_origin)
    ImageView ivBlackOrigin;
    @BindView(R.id.rl_origin)
    AutoRelativeLayout rlOrigin;
    @BindView(R.id.origin_group)
    MultiCheckGroupView originGroup;
    @BindView(R.id.iv_black_color)
    ImageView ivBlackColor;
    @BindView(R.id.rl_color)
    AutoRelativeLayout rlColor;
    @BindView(R.id.color_group)
    MultiCheckGroupView colorGroup;
    @BindView(bt_c1)
    Button btC1;
    @BindView(bt_c2)
    Button btC2;
    @BindView(R.id.doubleseekbar_house)
    DoubleSeekBar doubleseekbarHouse;
    @BindView(R.id.tv_house)
    TextView tvHouse;
    @BindView(R.id.bt_B1)
    Button btB1;
    @BindView(R.id.bt_A)
    Button btA;
    @BindView(R.id.bt_B2)
    Button btB2;
    @BindView(tv_elasticity)
    TextView tvElasticity;
    @BindView(R.id.doubleseekbar_elasticity)
    DoubleSeekBar doubleseekbarElasticity;
    @BindView(R.id.tv_length)
    TextView tvLength;
    @BindView(R.id.doubleseekbar_length)
    DoubleSeekBar doubleseekbarLength;
    @BindView(R.id.bt_save_white)
    Button btSaveWhite;
    @BindView(R.id.iv_black_date)
    ImageView ivBlackDate;
    @BindView(R.id.rl_date)
    AutoRelativeLayout rlDate;
    @BindView(R.id.date_group)
    MultiCheckGroupView dateGroup;
    @BindView(R.id.tv_trash)
    TextView tvTrash;
    @BindView(R.id.doubleseekbar_trash)
    DoubleSeekBar doubleseekbarTrash;
    @BindView(R.id.tv_moisture)
    TextView tvMoisture;
    @BindView(R.id.doubleSeekBar_moisture)
    DoubleSeekBar doubleSeekBarMoisture;
    private HashMap<String, String> mapAll;
    private Map<String, String> mapKeyword;
    private Map<String, String> mapLocation;
    private Map<String, String> mapType;
    private Map<String, String> mapColor;
    private Map<String, String> dataMap;
    private String isLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_purchase);
        ButterKnife.bind(this);
        initData();
        mapAll = new HashMap<>();
    }

    private void initData() {
        doubleseekbarHouse.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tvHouse.setText(lowValue + "-" + highValue);
                if ((lowValue + "").equals("3.5") && (highValue + "").equals("3.6")) {
                    btB1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    btB1.setTextColor(getResources().getColor(R.color.white));
                } else if ((lowValue + "").equals("3.7") && (highValue + "").equals("4.2")) {
                    btA.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    btA.setTextColor(getResources().getColor(R.color.white));
                } else if ((lowValue + "").equals("4.3") && (highValue + "").equals("4.9")) {
                    btB2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    btB2.setTextColor(getResources().getColor(R.color.white));
                } else if (lowValue == highValue) {
                    if ((highValue + "").equals("3.4")) {
                        btC1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                        btC1.setTextColor(getResources().getColor(R.color.white));
                        tvHouse.setText("3.4及以下");
                    } else if (lowValue == 5.0) {
                        tvHouse.setText("5.0及以上");
                        btC2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                        btC2.setTextColor(getResources().getColor(R.color.white));
                    }
                } else {
                    btC1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    btC2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    btA.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    btB1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    btB2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    btC1.setTextColor(getResources().getColor(R.color.drop_color));
                    btC2.setTextColor(getResources().getColor(R.color.drop_color));
                    btA.setTextColor(getResources().getColor(R.color.drop_color));
                    btB1.setTextColor(getResources().getColor(R.color.drop_color));
                    btB2.setTextColor(getResources().getColor(R.color.drop_color));
                }
            }
        });

        doubleseekbarElasticity.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tvElasticity.setText((int) lowValue + "-" + (int) highValue);
                if (((int) lowValue) == ((int) highValue)) {
                    if ((int) lowValue == 24) {
                        tvElasticity.setText("24及以下");
                    } else if ((int) lowValue == 31) {
                        tvElasticity.setText("31及以上");
                    }
                }
            }
        });


        doubleseekbarLength.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tvLength.setText((int) lowValue + "-" + (int) highValue);
                if ((int) lowValue == (int) highValue) {
                    if ((int) lowValue == 25) {
                        tvLength.setText("25及以下");
                    } else if ((int) lowValue == 32) {
                        tvLength.setText("32及以上");
                    }
                }
                if (!TextUtils.isEmpty(isLong) && isLong.equals("长绒棉")) {
                    if ((int) lowValue == (int) highValue) {
                        if ((int) lowValue == 33) {
                            tvLength.setText("33及以下");
                        } else if ((int) lowValue == 39) {
                            tvLength.setText("39及以上");
                        }
                    }
                }
            }
        });

        doubleseekbarTrash.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tvTrash.setText((int) lowValue + "-" + (int) highValue);
                if ((int) lowValue == (int) highValue) {
                    if ((int) lowValue == 0) {
                        tvTrash.setText("0以下");
                    } else if ((int) lowValue == 5) {
                        tvTrash.setText("5以上");
                    }
                }
            }
        });
        doubleSeekBarMoisture.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tvMoisture.setText((int) lowValue + "-" + (int) highValue);
                if ((int) lowValue == (int) highValue) {
                    if ((int) lowValue == 0) {
                        tvMoisture.setText("0以下");
                    } else if ((int) lowValue == 10) {
                        tvMoisture.setText("10以上");
                    }
                }
            }
        });
        mapColor = new TreeMap<>();
        mapColor.put("0", "白棉1级");
        mapColor.put("1", "白棉2级");
        mapColor.put("2", "白棉3级");
        mapColor.put("3", "白棉4级");
        mapColor.put("4", "白棉5级");
        mapColor.put("5", "淡点污棉1级");
        mapColor.put("6", "淡点污棉2级");
        mapColor.put("7", "淡点污棉3级");
        mapColor.put("8", "淡黄染棉1级");
        mapColor.put("9", "淡黄染棉2级");
        mapColor.put("90", "淡黄染棉3级");
        mapColor.put("91", "黄染棉1级");
        mapColor.put("92", "黄染棉2级");
        colorGroup.addValues(mapColor);
        mapKeyword = new TreeMap<>();
        mapKeyword.put("0", "手摘棉");
        mapKeyword.put("1", "机采棉");
        mapKeyword.put("2", "皮辊棉");
        mapKeyword.put("3", "长绒棉");
        keywordGroup.setMultiCheck(false);
        keywordGroup.setOnItemClickListener(new MultiCheckGroupView.OnItemClickListener() {
            @Override
            public void itemClick(View view, String value) {
                isLong = value;
                int id = view.getId();
                System.out.println("id:"+id);
                doubleseekbarLength.setLowHighValue(25, 32, 25, 32);
                doubleseekbarLength.setValues(25, 32);
                tvLength.setText("25-32");
                if (value.equals("手采棉") || value.equals("机采棉")) {
                    mapColor.put("0", "白棉1级");
                    mapColor.put("1", "白棉2级");
                    mapColor.put("2", "白棉3级");
                    mapColor.put("3", "白棉4级");
                    mapColor.put("4", "白棉5级");
                    mapColor.put("5", "淡点污棉1级");
                    mapColor.put("6", "淡点污棉2级");
                    mapColor.put("7", "淡点污棉3级");
                    mapColor.put("8", "淡黄染棉1级");
                    mapColor.put("9", "淡黄染棉2级");
                    mapColor.put("90", "淡黄染棉3级");
                    mapColor.put("91", "黄染棉1级");
                    mapColor.put("92", "黄染棉2级");
                    colorGroup.addValues(mapColor);
                } else {
                    mapColor.clear();
                    mapColor.put("0", "1级");
                    mapColor.put("1", "2级");
                    mapColor.put("2", "3级");
                    mapColor.put("3", "4级");
                    mapColor.put("4", "5级");
                    mapColor.put("5", "6级");
                    mapColor.put("6", "7级");
                    colorGroup.addValues(mapColor);
                    if (value.equals("长绒棉")) {
                        mapColor.clear();
                        mapColor.put("0", "1级");
                        mapColor.put("1", "2级");
                        mapColor.put("2", "3级");
                        mapColor.put("3", "4级");
                        mapColor.put("4", "5级");
                        colorGroup.addValues(mapColor);
                        doubleseekbarLength.setLowHighValue(33, 39, 33, 39);
                        doubleseekbarLength.setValues(33, 39);
                        tvLength.setText("33-39");
                    }
                    if (keywordGroup.getSelectedList().size() < 1) {
                        doubleseekbarLength.setLowHighValue(25, 32, 25, 32);
                        doubleseekbarLength.setValues(25, 32);
                        tvLength.setText("25-32");
                        mapColor.put("0", "白棉1级");
                        mapColor.put("1", "白棉2级");
                        mapColor.put("2", "白棉3级");
                        mapColor.put("3", "白棉4级");
                        mapColor.put("4", "白棉5级");
                        mapColor.put("5", "淡点污棉1级");
                        mapColor.put("6", "淡点污棉2级");
                        mapColor.put("7", "淡点污棉3级");
                        mapColor.put("8", "淡黄染棉1级");
                        mapColor.put("9", "淡黄染棉2级");
                        mapColor.put("90", "淡黄染棉3级");
                        mapColor.put("91", "黄染棉1级");
                        mapColor.put("92", "黄染棉2级");
                        colorGroup.addValues(mapColor);
                    }
                }
            }
        });
        keywordGroup.addValues(mapKeyword);

        dataMap = new TreeMap<>();
        dataMap.put("0", "2017");
        dataMap.put("1", "2016");
        dateGroup.addValues(dataMap);

        mapLocation = new TreeMap<>();
        mapLocation.put("2", "新疆棉");
        mapLocation.put("3", "地产棉");
        mapLocation.put("4", "进口棉");
        locationGroup.addValues(mapLocation);

        mapType = new TreeMap<>();
        mapType.put("0", "锯齿细绒棉");
        mapType.put("1", "锯齿机采棉");
        mapType.put("2", "皮辊细绒棉");
        originGroup.addValues(mapType);

    }

    @OnClick({R.id.tv_dismiss, R.id.rl_keyword, R.id.rl_location, R.id.rl_origin, R.id.rl_color, bt_c1, bt_c2, R.id.bt_B1, R.id.bt_A, R.id.bt_B2, R.id.bt_save_white})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dismiss:
                onBackPressed();
                break;
            case R.id.rl_keyword:
                if (keywordGroup.isShowMyLayout()) {
                    keywordGroup.hideMyLayout();
                    ivBlackKeyword.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    ivBlackKeyword.setImageResource(R.mipmap.ic_sort_down);
                    keywordGroup.showMyLayout();
                }
                break;
            case R.id.rl_location:
                if (locationGroup.isShowMyLayout()) {
                    locationGroup.hideMyLayout();
                    ivBlackLocation.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    ivBlackLocation.setImageResource(R.mipmap.ic_sort_down);
                    locationGroup.showMyLayout();
                }
                break;
            case R.id.rl_origin:
                if (originGroup.isShowMyLayout()) {
                    originGroup.hideMyLayout();
                    ivBlackOrigin.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    ivBlackOrigin.setImageResource(R.mipmap.ic_sort_down);
                    originGroup.showMyLayout();
                }
                break;
            case R.id.rl_color:
                if (colorGroup.isShowMyLayout()) {
                    colorGroup.hideMyLayout();
                    ivBlackColor.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    ivBlackColor.setImageResource(R.mipmap.ic_sort_down);
                    colorGroup.showMyLayout();
                }
                break;
            case R.id.bt_c1:
                tvHouse.setText("3.4及以下");
//                doubleseekbarHouse.setValues(3.4f, 3.4f);
                doubleseekbarHouse.setValues(34, 34);
                btC1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                btC2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btA.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);

                btC1.setTextColor(getResources().getColor(R.color.white));
                btC2.setTextColor(getResources().getColor(R.color.drop_color));
                btA.setTextColor(getResources().getColor(R.color.drop_color));
                btB1.setTextColor(getResources().getColor(R.color.drop_color));
                btB2.setTextColor(getResources().getColor(R.color.drop_color));
                break;
            case bt_c2:
                tvHouse.setText("5.0及以上");
                doubleseekbarHouse.setValues(50, 50);
                btC1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btC2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                btA.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);

                btC1.setTextColor(getResources().getColor(R.color.drop_color));
                btC2.setTextColor(getResources().getColor(R.color.white));
                btA.setTextColor(getResources().getColor(R.color.drop_color));
                btB1.setTextColor(getResources().getColor(R.color.drop_color));
                btB2.setTextColor(getResources().getColor(R.color.drop_color));
                break;
            case R.id.bt_B1:
                tvHouse.setText("3.5-3.6");
                doubleseekbarHouse.setValues(35, 36);

                btC1.setTextColor(getResources().getColor(R.color.drop_color));
                btC2.setTextColor(getResources().getColor(R.color.drop_color));
                btA.setTextColor(getResources().getColor(R.color.drop_color));
                btB1.setTextColor(getResources().getColor(R.color.white));
                btB2.setTextColor(getResources().getColor(R.color.drop_color));
                btC1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btC2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btA.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                btB2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_A:
                tvHouse.setText("3.7-4.2");
                doubleseekbarHouse.setValues(37, 42);

                btC1.setTextColor(getResources().getColor(R.color.drop_color));
                btC2.setTextColor(getResources().getColor(R.color.drop_color));
                btA.setTextColor(getResources().getColor(R.color.white));
                btB1.setTextColor(getResources().getColor(R.color.drop_color));
                btB2.setTextColor(getResources().getColor(R.color.drop_color));
                btC1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btC2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btA.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                btB1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_B2:
                tvHouse.setText("4.3-4.9");
                doubleseekbarHouse.setValues(43, 49);

                btC1.setTextColor(getResources().getColor(R.color.drop_color));
                btC2.setTextColor(getResources().getColor(R.color.drop_color));
                btA.setTextColor(getResources().getColor(R.color.drop_color));
                btB1.setTextColor(getResources().getColor(R.color.drop_color));
                btB2.setTextColor(getResources().getColor(R.color.white));
                btC1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btC2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btA.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                break;
            case R.id.bt_save_white:
                List<String> selectedDate = dateGroup.getSelectedList();
                List<String> dateValue = new ArrayList<>();
                List<String> keywordList = keywordGroup.getSelectedList();
                if (keywordList.size()==0){
                    ToastUtil.show(BaseApplication.getContextObject(),"请选择品类");
                    return;
                }
                List<String> keyValue = new ArrayList<>();
                List<String> colordList = colorGroup.getSelectedList();
                List<String> colorValue = new ArrayList<>();
                List<String> originList = originGroup.getSelectedList();
                List<String> originValue = new ArrayList<>();
                List<String> locationList = locationGroup.getSelectedList();
                List<String> locationValue = new ArrayList<>();
                if (selectedDate.size() < 1) {
                    mapAll.put("createYear", "");
                } else {
                    for (String temp : selectedDate) {
                        String s = dataMap.get(temp);
                        dateValue.add(s);
                    }
                    mapAll.put("createYear", ListToListString.getString(dateValue));
                }
                if (keywordList.size() < 1) {
                    mapAll.put("keyword", "");
                } else {
                    for (String temp : keywordList) {
                        String s = mapKeyword.get(temp);
                        keyValue.add(s);
                    }
                    mapAll.put("keyword", ListToListString.getString(keyValue));
                }
                if (colordList.size() < 1) {
                    mapAll.put("colorGrade", "");
                } else {
                    for (String temp : colordList) {
                        String s = mapColor.get(temp);
                        if (s.equals("白棉1级")) {
                            s = "white1";
                            colorValue.add(s);
                        } else if (s.equals("白棉2级")) {
                            s = "white2";
                            colorValue.add(s);
                        } else if (s.equals("白棉3级")) {
                            s = "white3";
                            colorValue.add(s);
                        } else if (s.equals("白棉4级")) {
                            s = "white4";
                            colorValue.add(s);
                        } else if (s.equals("白棉5级")) {
                            s = "white5";
                            colorValue.add(s);
                        } else if (s.equals("淡点污棉1级")) {
                            s = "lightSpotted1";
                            colorValue.add(s);
                        } else if (s.equals("淡点污棉2级")) {
                            s = "lightSpotted2";
                            colorValue.add(s);
                        } else if (s.equals("淡点污棉3级")) {
                            s = "lightSpotted3";
                            colorValue.add(s);
                        } else if (s.equals("淡黄染棉1级")) {
                            s = "yellowish1";
                            colorValue.add(s);
                        } else if (s.equals("淡黄染棉2级")) {
                            s = "yellowish2";
                            colorValue.add(s);
                        } else if (s.equals("淡黄染棉3级")) {
                            s = "yellowish3";
                            colorValue.add(s);
                        } else if (s.equals("黄染棉1级")) {
                            s = "yellow1";
                            colorValue.add(s);
                        } else if (s.equals("黄染棉2级")) {
                            s = "yellow2";
                            colorValue.add(s);
                        } else {
                            colorValue.add(s);
                        }
                    }
                    mapAll.put("colorGrade", ListToListString.getString(colorValue));
                }
                if (originList.size() < 1) {
                    mapAll.put("type", "");
                } else {
                    for (String temp : originList) {
                        String s = mapType.get(temp);
                        originValue.add(s);
                    }
                    mapAll.put("type", ListToListString.getString(originValue));
                    System.out.println("----------------" + ListToListString.getString(originValue));
                }
                if (locationList.size() < 1) {
                    mapAll.put("origin", "");
                } else {
                    for (String temp : locationList) {
                        String s = mapLocation.get(temp);
                        locationValue.add(s);
                    }
                    mapAll.put("origin", ListToListString.getString(locationValue));
                }
                Gson gson = new Gson();
                String strHorse = tvHouse.getText().toString();
                if (strHorse.equals("4.3-4.9")) {
                    String[] split = strHorse.split("-");
                    MicronAverage micronAverage = new MicronAverage(split[0].toString(), split[1].toString());
                    String jsonString = gson.toJson(micronAverage);
                    mapAll.put("micronAverage", jsonString);
                    mapAll.put("micronGrade", "B2");
                } else if (strHorse.equals("3.7-4.2")) {
                    String[] split = strHorse.split("-");
                    MicronAverage micronAverage = new MicronAverage(split[0].toString(), split[1].toString());
                    String jsonString = gson.toJson(micronAverage);
                    mapAll.put("micronAverage", jsonString);
                    mapAll.put("micronGrade", "A");
                } else if (strHorse.equals("3.5-3.6")) {
                    String[] split = strHorse.split("-");
                    MicronAverage micronAverage = new MicronAverage(split[0].toString(), split[1].toString());
                    String jsonString = gson.toJson(micronAverage);
                    mapAll.put("micronAverage", jsonString);
                    mapAll.put("micronGrade", "B1");
                } else if (strHorse.equals("5.0及以上")) {
                    MicronAverage micronAverage = new MicronAverage("5.0", "5.0");
                    String jsonString = gson.toJson(micronAverage);
                    mapAll.put("micronAverage", jsonString);
                    mapAll.put("micronGrade", "C2");
                } else if (strHorse.equals("3.4及以下")) {
                    MicronAverage micronAverage = new MicronAverage("3.4", "3.4");
                    String jsonString = gson.toJson(micronAverage);
                    mapAll.put("micronAverage", jsonString);
                    mapAll.put("micronGrade", "C1");
                } else {
                    String[] split = strHorse.split("-");
                    MicronAverage micronAverage = new MicronAverage(split[0].toString(), split[1].toString());
                    String jsonString = gson.toJson(micronAverage);
                    mapAll.put("micronAverage", jsonString);
                    mapAll.put("micronGrade", "");
                }
                String strLength = tvLength.getText().toString();
                if (strLength.equals("25及以下")) {
                    MicronAverage micronAverage2 = new MicronAverage("25", "25");
                    String jsonString2 = gson.toJson(micronAverage2);
                    mapAll.put("lengthAverage", jsonString2);
                } else if (strLength.equals("32及以上")) {
                    MicronAverage micronAverage2 = new MicronAverage("32", "32");
                    String jsonString2 = gson.toJson(micronAverage2);
                    mapAll.put("lengthAverage", jsonString2);
                } else if (strLength.equals("39及以上")) {
                    MicronAverage micronAverage2 = new MicronAverage("39", "39");
                    String jsonString2 = gson.toJson(micronAverage2);
                    mapAll.put("lengthAverage", jsonString2);
                } else if (strLength.equals("33及以下")) {
                    MicronAverage micronAverage2 = new MicronAverage("33", "33");
                    String jsonString2 = gson.toJson(micronAverage2);
                    mapAll.put("lengthAverage", jsonString2);
                } else {
                    String[] split2 = strLength.split("-");
                    MicronAverage micronAverage2 = new MicronAverage(split2[0].toString(), split2[1].toString());
                    String jsonString2 = gson.toJson(micronAverage2);
                    mapAll.put("lengthAverage", jsonString2);
                }

                String strElasticity = tvElasticity.getText().toString();
                if (strElasticity.equals("24及以下")) {
                    MicronAverage micronAverage3 = new MicronAverage("24", "24");
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("breakLoadAverage", jsonString3);
                } else if (strElasticity.equals("31及以上")) {
                    MicronAverage micronAverage3 = new MicronAverage("31", "31");
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("breakLoadAverage", jsonString3);
                } else {
                    String[] split3 = strElasticity.split("-");
                    MicronAverage micronAverage3 = new MicronAverage(split3[0].toString(), split3[1].toString());
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("breakLoadAverage", jsonString3);
                }
                String strTrash = tvTrash.getText().toString();
                if (strTrash.equals("0及以下")) {
                    MicronAverage micronAverage3 = new MicronAverage("0", "0");
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("trash", jsonString3);
                } else if (strTrash.equals("5及以上")) {
                    MicronAverage micronAverage3 = new MicronAverage("5", "5");
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("trash", jsonString3);
                } else {
                    String[] split4 = strTrash.split("-");
                    MicronAverage micronAverage3 = new MicronAverage(split4[0].toString(), split4[1].toString());
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("trash", jsonString3);
                }
                String strMoisture = tvMoisture.getText().toString();
                if (strMoisture.equals("0及以下")) {
                    MicronAverage micronAverage3 = new MicronAverage("0", "0");
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("moisture", jsonString3);
                } else if (strMoisture.equals("10及以上")) {
                    MicronAverage micronAverage3 = new MicronAverage("10", "10");
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("moisture", jsonString3);
                } else {
                    String[] split5 = strMoisture.split("-");
                    MicronAverage micronAverage3 = new MicronAverage(split5[0].toString(), split5[1].toString());
                    String jsonString3 = gson.toJson(micronAverage3);
                    mapAll.put("moisture", jsonString3);
                }
                Intent intent = new Intent(ReleasePurchaseActivity.this, FillInTheBasicInformationActivity.class);
                final SerializableMap myMap = new SerializableMap();
                myMap.setMap(mapAll);//将map数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("map", myMap);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keywordGroup.clearFixed();
        originGroup.clearFixed();
        locationGroup.clearFixed();
        colorGroup.clearFixed();
    }

    @OnClick(R.id.rl_date)
    public void onViewClicked() {
        if (dateGroup.isShowMyLayout()) {
            dateGroup.hideMyLayout();
            ivBlackDate.setImageResource(R.mipmap.ic_sort_up);
        } else {
            ivBlackDate.setImageResource(R.mipmap.ic_sort_down);
            dateGroup.showMyLayout();
        }
    }
}
