package com.tianfu.cutton.fragment.quality;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.activity.quality.QualityBatchDetailsActivity;
import com.tianfu.cutton.activity.quality.QualityKunDetailsActivity;
import com.tianfu.cutton.activity.quality.UpBatchAllActivity;
import com.tianfu.cutton.activity.store.SearchActivity;
import com.tianfu.cutton.adapter.ClassifyMainAdapter;
import com.tianfu.cutton.adapter.ClassifyMoreAdapter;
import com.tianfu.cutton.adapter.QualityRecylerAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.fragment.adapter.BackHandlerHelper;
import com.tianfu.cutton.fragment.adapter.FragmentBackHandler;
import com.tianfu.cutton.model.AllFactoryBean;
import com.tianfu.cutton.model.QualityBean;
import com.tianfu.cutton.model.QualityCountBean;
import com.tianfu.cutton.model.SerializableMap;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CallKefu;
import com.tianfu.cutton.utils.ListToListString;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.DoubleSeekBar;
import com.tianfu.cutton.widget.MultiCheckGroupView;
import com.tianfu.cutton.widget.PopWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class QualityFragment extends BaseFragment implements FragmentBackHandler, View.OnClickListener {

    private View mRootView;
    private RecyclerView qualityRecyler;
    private LinearLayout ll_showDrawer;
    private DrawerLayout drawerLayout;
    private LayoutInflater inflater;
    private LinearLayout ll_sortQuality;
    private LinearLayout ll_keyQuality;
    private LinearLayout ll_horseQuality;
    private TextView tv_sort_quality;
    private TextView tv_btoS_quality;
    private TextView tv_stoBig_quality;
    private ImageView iv_stoBig_quality;
    private ImageView iv_btoS_quality;
    private ImageView iv_sort_quality;
    public static String[] LISTVIEWTXT;
    public static String[][] MORELISTTXT ;
    private PopWindow popWindowSort;
    private TextView tv_quality_sort;
    private ImageView iv_quality_sort;
    private PopWindow popWindowKey;
    private TextView tv_sort_type1;
    private TextView tv_sort_type2;
    private TextView tv_sort_type3;
    private ImageView iv_sort_type1;
    private ImageView iv_sort_type2;
    private ImageView iv_sort_type3;
    private TextView tv_key;
    private ImageView iv_key;
    private TextView tv_horse_type1;
/*    private TextView tv_horse_type2;
    private TextView tv_horse_type3;
    private TextView tv_horse_type4;
    private TextView tv_horse_type5;*/
    private ImageView iv_horse_type1;
  /*  private ImageView iv_horse_type2;
    private ImageView iv_horse_type3;
    private ImageView iv_horse_type4;
    private ImageView iv_horse_type5;*/
    private PopWindow popWindowHors;
    private TextView tv_horseSelect;
    private ImageView iv_horseSelect;
    private RelativeLayout ll_drawer1;
    private RelativeLayout relativy_origin;
    private ImageView image_origin;
    private MultiCheckGroupView origin_group;
    //    private RelativeLayout rl_type;
    private ImageView iv_black_ty;
    //    private MultiCheckGroupView type_group;
    private LinearLayout bt_select;
    private RelativeLayout rl_color;
    private ImageView iv_black_color;
    private MultiCheckGroupView color_group;
    private RelativeLayout rl_date;
    private ImageView iv_date;
    private ListView mainlist;
    private ListView morelist;
    private List<Map<String, Object>> mainList;
    ClassifyMainAdapter mainAdapter;
    ClassifyMoreAdapter moreAdapter;
    private PtrClassicFrameLayout mPtrrame;
    private MultiCheckGroupView group_date;
    private TextView textView;
    private TextView tv_length;
    private TextView tv_trash;
    private TextView tv_moisture;
    private DoubleSeekBar doubleseekbar_elasticity;
    private DoubleSeekBar doubleseekbar_length;
    private DoubleSeekBar doubleseekbar_trash;
    private DoubleSeekBar doubleSeekBar_moisture;
    private Map<String, String> hashMap;
    private List<QualityBean.ValueBean.RowsBean> mDatas;
    private QualityRecylerAdapter qualityRecylerAdapter;
    private Gson gson;
    private TextView tv_shaixuan;
    private ImageView iv_shaixuan;
    private int pageNo = 1;
    private String eMUserName;
    private String eMPassword;
    private String mobileMy;
    private Boolean isLogin;
    private TextView tv_sort_type4;
    private ImageView iv_sort_type4;
    private boolean isKeyShow = false;
    private boolean isSortShow = false;
    private boolean isHorseShow = false;
    private boolean isUpDate = true;
    private Map<String, String> mapColor;
    private Map<String, String> mapType;
    private Map<String, String> mapLocation;
    //    private Map<String, String> mapDate;
    private TextView tv_house;
    private DoubleSeekBar doubleseekbar_house;
    private TextView tv_location;
    private PopupWindow popWindowList;
    private LinearLayout upLoadBatch;

    public QualityFragment() {
        // Required empty public constructor
    }

    private int sortType = 0;//记录排序规则
    private int keyType = 0;//记录排序规则
    private int horseType = 0;
    private Button bt_a;
    private Button bt_b1;
    private Button bt_b2;
    private Button bt_c1;
    private Button bt_c2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_quality, container, false);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            hashMap = new HashMap<>();
            hashMap.put("deviceNo", Common.deviceNo);
            hashMap.put("from", Common.from);
            hashMap.put("sourceTypeEnum", Common.sourceTypeEnum);
            hashMap.put("orderType", "1");
            hashMap.put("versionNo", Common.versionNo);
            hashMap.put("searchType", "3");
            initView();
            initDrawerView();
            initData();
            gson = new Gson();
            return mRootView;
        }
    }

    private void initData() {
        bt_a = (Button) mRootView.findViewById(R.id.bt_A);
        bt_a.setOnClickListener(this);
        bt_b1 = (Button) mRootView.findViewById(R.id.bt_B1);
        bt_b1.setOnClickListener(this);
        bt_b2 = (Button) mRootView.findViewById(R.id.bt_B2);
        bt_b2.setOnClickListener(this);
        bt_c1 = (Button) mRootView.findViewById(R.id.bt_c1);
        bt_c1.setOnClickListener(this);
        bt_c2 = (Button) mRootView.findViewById(R.id.bt_c2);
        bt_c2.setOnClickListener(this);
        tv_house = (TextView) mRootView.findViewById(R.id.tv_house);
        doubleseekbar_house = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_house);
        doubleseekbar_house.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tv_house.setText(lowValue + "-" + highValue);
                if ((lowValue + "").equals("3.5") && (highValue + "").equals("3.6")) {
                    bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    bt_b1.setTextColor(getResources().getColor(R.color.white));
                } else if ((lowValue + "").equals("3.7") && (highValue + "").equals("4.2")) {
                    bt_a.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    bt_a.setTextColor(getResources().getColor(R.color.white));
                } else if ((lowValue + "").equals("4.3") && (highValue + "").equals("4.9")) {
                    bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    bt_b2.setTextColor(getResources().getColor(R.color.white));
                } else if (lowValue == highValue) {
                    if ((highValue + "").equals("3.4")) {
                        bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                        bt_c1.setTextColor(getResources().getColor(R.color.white));
                        tv_house.setText("3.4及以下");
                    } else if (lowValue == 5.0) {
                        tv_house.setText("5.0及以上");
                        bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                        bt_c2.setTextColor(getResources().getColor(R.color.white));
                    }
                } else {
                    bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                    bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                    bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                    bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                    bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                    bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
                }

            }
        });
        doubleseekbar_elasticity.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                textView.setText((int) lowValue + "-" + (int) highValue);
                if (((int) lowValue) == ((int) highValue)) {
                    if ((int) lowValue == 24) {
                        textView.setText("24以下");
                    } else if ((int) lowValue == 31) {
                        textView.setText("31以上");
                    }
                }
            }
        });

        doubleseekbar_length.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tv_length.setText((int) lowValue + "-" + (int) highValue);
                if ((int) lowValue == (int) highValue) {
                    if ((int) lowValue == 25) {
                        tv_length.setText("25以下");
                    } else if ((int) lowValue == 32) {
                        tv_length.setText("32以上");
                    }
                }
            }
        });

        doubleseekbar_trash.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tv_trash.setText((int) lowValue + "-" + (int) highValue);
            }
        });
        doubleSeekBar_moisture.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tv_moisture.setText((int) lowValue + "-" + (int) highValue);
            }
        });
   /*     mapDate = new TreeMap<>();
        mapDate.put("0", "2017");
        mapDate.put("1", "2016");
        mapDate.put("2", "2015");
        mapDate.put("3", "2014");
        mapDate.put("4", "2013");
        mapDate.put("5", "2012");
        group_date.addValues(mapDate);*/

        mapLocation = new TreeMap<>();
        mapLocation.put("0", "新疆棉");
        mapLocation.put("3", "地产棉");
        mapLocation.put("4", "进口棉");
        origin_group.addValues(mapLocation);

/*        mapType = new TreeMap<>();
        mapType.put("0", "锯齿细绒棉");
        mapType.put("1", "锯齿机采棉");
        mapType.put("2", "皮辊细绒棉");
        type_group.addValues(mapType);*/

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
        color_group.addValues(mapColor);
    }

    private void initDrawerView() {
        bt_select = (LinearLayout) mRootView.findViewById(R.id.bt_select);
        bt_select.setOnClickListener(this);
        tv_location = (TextView) mRootView.findViewById(R.id.tv_Location);
        initProdata();
        Button btSelectRset = (Button) mRootView.findViewById(R.id.btSelectRset);
        btSelectRset.setOnClickListener(this);
        Button btSelectSure = (Button) mRootView.findViewById(R.id.btSelectSure);
        btSelectSure.setOnClickListener(this);
        relativy_origin = (RelativeLayout) mRootView.findViewById(R.id.relativy_origin);
        relativy_origin.setOnClickListener(this);
        image_origin = (ImageView) mRootView.findViewById(R.id.image_origin);
        origin_group = (MultiCheckGroupView) mRootView.findViewById(R.id.origin_group);

//        rl_type = (RelativeLayout) mRootView.findViewById(R.id.rl_type);
//        rl_type.setOnClickListener(this);
        iv_black_ty = (ImageView) mRootView.findViewById(R.id.iv_black_ty);
//        type_group = (MultiCheckGroupView) mRootView.findViewById(R.id.type_group);

        rl_color = (RelativeLayout) mRootView.findViewById(R.id.rl_color);
        rl_color.setOnClickListener(this);
        iv_black_color = (ImageView) mRootView.findViewById(R.id.iv_black_color);
        color_group = (MultiCheckGroupView) mRootView.findViewById(R.id.color_group);

 /*       rl_date = (RelativeLayout) mRootView.findViewById(R.id.rl_date);
        rl_date.setOnClickListener(this);*/
        iv_date = (ImageView) mRootView.findViewById(R.id.iv_date);
//        group_date = (MultiCheckGroupView) mRootView.findViewById(R.id.group_date);

        textView = (TextView) mRootView.findViewById(R.id.tv_elasticity);
        tv_length = (TextView) mRootView.findViewById(R.id.tv_length);
        //含杂率
        tv_trash = (TextView) mRootView.findViewById(R.id.tv_trash);
        //回潮率
        tv_moisture = (TextView) mRootView.findViewById(R.id.tv_moisture);

        doubleseekbar_elasticity = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_elasticity);
        doubleseekbar_length = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_length);
        doubleseekbar_trash = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_trash);
        doubleSeekBar_moisture = (DoubleSeekBar) mRootView.findViewById(R.id.doubleSeekBar_moisture);

        ll_showDrawer = (LinearLayout) mRootView.findViewById(R.id.ll_showDrawer);
        drawerLayout = (DrawerLayout) mRootView.findViewById(R.id.drawerLayout);
        ll_drawer1 = (RelativeLayout) mRootView.findViewById(R.id.ll_drawer);
        ll_showDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isKeyShow = false;
                isSortShow = false;
                isHorseShow = false;
                ((MainActivity) getActivity()).mTabLayout.setVisibility(View.GONE);
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                drawerLayout.setSystemUiVisibility(View.INVISIBLE);
                ((MainActivity) getActivity()).mTabLayout.setVisibility(View.GONE);
                String s = tv_house.getText().toString();
                if (s.equals("3.4及以下")) {
                    doubleseekbar_house.setValues(34, 34);
                } else if (s.equals("5.0及以上")) {
                    doubleseekbar_house.setValues(50, 50);
                } else {
                    String[] split = s.split("-");
                    String[] split1 = split[0].split("\\.");
                    String[] split2 = split[1].split("\\.");
                    String min = split1[0] + split1[1];
                    String max = split2[0] + split2[1];
                    doubleseekbar_house.setValues(Integer.valueOf(min), Integer.valueOf(max));
                }
                String elasticityStr = textView.getText().toString();
                if (elasticityStr.equals("24以下")) {
                    doubleseekbar_elasticity.setValues(24, 24);
                } else if (elasticityStr.equals("31以上")) {
                    doubleseekbar_elasticity.setValues(31, 31);
                } else {
                    String[] splitelasticity = textView.getText().toString().split("-");
                    doubleseekbar_elasticity.setValues(Integer.valueOf(splitelasticity[0]), Integer.valueOf(splitelasticity[1]));
                }
                String lengthStr = tv_length.getText().toString();
                if (lengthStr.equals("25以下")) {
                    doubleseekbar_length.setValues(25, 25);
                } else if (lengthStr.equals("32以上")) {
                    doubleseekbar_length.setValues(32, 32);
                } else {
                    String[] splitlength = tv_length.getText().toString().split("-");
                    doubleseekbar_length.setValues(Integer.valueOf(splitlength[0]), Integer.valueOf(splitlength[1]));
                }
                String[] split2 = tv_trash.getText().toString().split("-");
                String[] split3 = tv_moisture.getText().toString().split("-");
                doubleseekbar_trash.setValues(Integer.valueOf(split2[0]), Integer.valueOf(split2[1]));
                doubleSeekBar_moisture.setValues(Integer.valueOf(split3[0]), Integer.valueOf(split3[1]));
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                ((MainActivity) getActivity()).mTabLayout.setVisibility(View.VISIBLE);
                if (popWindowList!=null){
                    popWindowList.dismiss();
                }
                ishaveData();
                WindowManager.LayoutParams attr = getActivity().getWindow().getAttributes();
                attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getActivity().getWindow().setAttributes(attr);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void initProdata() {
        Map map = new HashMap();
        HttpManager.getServerApi().getAllFactorys(map).enqueue(new CallBack<AllFactoryBean>() {
            @Override
            public void success(AllFactoryBean data) {
                if (data.success) {
                    List<AllFactoryBean.ValueBean> value = data.value;
                    MORELISTTXT=new String[value.size()][value.get(0).factorys.size()];
                    List<String> listProvince = new ArrayList();
                    for (int i =0;i<value.size();i++){
                        listProvince.add(value.get(i).province);
                        List<String> storages = value.get(i).factorys;
                        String[] strings = storages.toArray(new String[storages.size()]);
                        MORELISTTXT[i]=strings;
                    }
                    LISTVIEWTXT = listProvince.toArray(new String[listProvince.size()]);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });

    }


    private void initView() {
        LinearLayout ll_search = (LinearLayout) mRootView.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isKeyShow = false;
                isSortShow = false;
                isHorseShow = false;
                startActivity(new Intent(BaseApplication.getContextObject(), SearchActivity.class));
            }
        });
        ImageView ivMeaage = (ImageView) mRootView.findViewById(R.id.iv_message);
        ivMeaage.setOnClickListener(this);
        mPtrrame = (PtrClassicFrameLayout) mRootView.findViewById(R.id.ptr_purchase_frame);
        tv_shaixuan = (TextView) mRootView.findViewById(R.id.tv_shaixuan);
        iv_shaixuan = (ImageView) mRootView.findViewById(R.id.iv_shaixuan);
        tv_horseSelect = (TextView) mRootView.findViewById(R.id.tv_horseSelect);
        iv_horseSelect = (ImageView) mRootView.findViewById(R.id.iv_horseSelect);
        tv_key = (TextView) mRootView.findViewById(R.id.tv_key);
        iv_key = (ImageView) mRootView.findViewById(R.id.iv_key);
        tv_quality_sort = (TextView) mRootView.findViewById(R.id.tv_quality_sort);
        iv_quality_sort = (ImageView) mRootView.findViewById(R.id.iv_quality_sort);
        inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ll_sortQuality = (LinearLayout) mRootView.findViewById(R.id.ll_sortQuality);
        ll_sortQuality.setOnClickListener(this);
        ll_keyQuality = (LinearLayout) mRootView.findViewById(R.id.ll_keyQuality);
        ll_keyQuality.setOnClickListener(this);
        ll_horseQuality = (LinearLayout) mRootView.findViewById(R.id.ll_horseQuality);
        ll_horseQuality.setOnClickListener(this);
        initPtr();
        upLoadBatch = (LinearLayout) mRootView.findViewById(R.id.upLoadBatch);
        upLoadBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpBatchAllActivity.class);
                intent.putExtra("qualityList", (Serializable) mDatas);
                final SerializableMap myMap = new SerializableMap();
                myMap.setMap(hashMap);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mapQuality", myMap);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initPtr() {
        mPtrrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo++;
                loadLazyData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                //下拉刷新
//                Toast.makeText(getActivity(), "下啦", Toast.LENGTH_SHORT).show();
                pageNo = 1;
                loadLazyData();
            }
        });
    }
    private void initListView(View conentView) {
        TextView textView = (TextView) conentView.findViewById(R.id.popDiss);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowList.dismiss();
            }
        });
        mainlist = (ListView) conentView.findViewById(R.id.classify_mainlist);
        morelist = (ListView) conentView.findViewById(R.id.classify_morelist);
        mainAdapter = new ClassifyMainAdapter(getActivity(), mainList);
        mainAdapter.setSelectItem(0);
        mainlist.setAdapter(mainAdapter);
        mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                initAdapter(MORELISTTXT[position]);
                mainAdapter.setSelectItem(position);
                mainAdapter.notifyDataSetChanged();
            }
        });
        mainlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 一定要设置这个属性，否则ListView不会刷新
        initAdapter(MORELISTTXT[0]);

        morelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                moreAdapter.setSelectItem(position);
                moreAdapter.notifyDataSetChanged();
                tv_location.setText(LISTVIEWTXT[mainAdapter.getSelectItem()]+" "+moreAdapter.getItem(position));
                popWindowList.dismiss();

            }
        });
    }

    private void initAdapter(String[] array) {
        moreAdapter = new ClassifyMoreAdapter(getActivity(), array);
        morelist.setAdapter(moreAdapter);
        moreAdapter.notifyDataSetChanged();
    }

    private void initModle(View conentView) {
        mainList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < LISTVIEWTXT.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("txt", LISTVIEWTXT[i]);
            mainList.add(map);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_c1:
                tv_house.setText("3.4及以下");
                doubleseekbar_house.setValues(34, 34);
                bt_c1.setTextColor(getResources().getColor(R.color.white));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));

                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_c2:
                tv_house.setText("5.0及以上");
                doubleseekbar_house.setValues(50, 50);
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.white));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));

                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_B1:
                tv_house.setText("3.5-3.6");
                doubleseekbar_house.setValues(35, 36);
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.white));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_A:
                tv_house.setText("3.7-4.2");
                doubleseekbar_house.setValues(37, 42);
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.white));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_B2:
                tv_house.setText("4.3-4.9");
                doubleseekbar_house.setValues(43, 49);
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.white));
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                break;
            case R.id.iv_message:
                isKeyShow = false;
                isSortShow = false;
                isHorseShow = false;
                if (!isLogin) {
                    startActivity(new Intent(BaseApplication.getContextObject(), LoginActivity.class));
                    return;
                }
                CallKefu.callOnLine(getActivity(), eMUserName, eMPassword);
                break;
            case R.id.btSelectRset:
                tv_house.setText("3.4-5.0");
                doubleseekbar_house.setValues(34, 50);
//                group_date.clearSelected();
//                type_group.clearSelected();
                color_group.clearSelected();
                origin_group.clearSelected();
                textView.setText("24-31");
                doubleseekbar_elasticity.setValues(24f, 31f);
                tv_length.setText("25-32");
                doubleseekbar_length.setValues(25f, 32f);
                tv_trash.setText("0-5");
                doubleseekbar_trash.setValues(0, 5f);
                tv_moisture.setText("0-10");
                doubleSeekBar_moisture.setValues(0, 10);
                tv_location.setText("请选择加工厂名称");
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_select:
                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View conentView = inflater.inflate(R.layout.popup_entrepot, null);
                TextView tv_select_fatory = (TextView) conentView.findViewById(R.id.tv_select_fatory);
                tv_select_fatory.setText("请选择加工厂");
                initModle(conentView);
                initListView(conentView);
                popWindowList = new PopupWindow();
                popWindowList.setContentView(conentView);
                // 设置SelectPicPopupWindow弹出窗体的宽
                popWindowList.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                // 设置SelectPicPopupWindow弹出窗体的高
                popWindowList.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popWindowList.setFocusable(true);//响应弹框以外的按钮的点击事件
                popWindowList.setOutsideTouchable(true);
                //防止虚拟软键盘被遮住
                popWindowList.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                // 刷新状态
                popWindowList.update();
                popWindowList.setBackgroundDrawable(new BitmapDrawable());
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                getActivity().getWindow().setAttributes(lp);
                popWindowList.showAtLocation(tv_house, Gravity.TOP, 0, 0);
                break;
            case R.id.btSelectSure:
//                List<String> dateList = group_date.getSelectedList();
//                group_date.commit();
                List<String> dateValue = new ArrayList<>();
         /*       List<String> typeList1 = type_group.getSelectedList();
                type_group.commit();*/
                List<String> typeValue = new ArrayList<>();
                List<String> colorList2 = color_group.getSelectedList();
                color_group.commit();
                List<String> colorValue = new ArrayList<>();
                List<String> originList3 = origin_group.getSelectedList();
                origin_group.commit();
                List<String> originValue = new ArrayList<>();
                if (colorList2.size() < 1) {
                    hashMap.put("colorGradeName", "");
                } else {
                    for (String temp : colorList2) {
                        colorValue.add(mapColor.get(temp));
                    }
                    hashMap.put("colorGradeName", ListToListString.getString(colorValue));
                }
                if (originList3.size() < 1) {
                    hashMap.put("origin", "");
                } else {
                    for (String temp : originList3) {
                        originValue.add(mapLocation.get(temp));
                    }
                    hashMap.put("origin", ListToListString.getString(originValue));
                }
                //textView强力 tv_length长度  tv_trash汗咋率 tv_moisture回潮率
                String elasticityStr = textView.getText().toString();
                if (elasticityStr.equals("24以下")) {
                    hashMap.put("breakLoadAverage", "[" + "24" + "," + "24" + "]");
                } else if (elasticityStr.equals("31以上")) {
                    hashMap.put("breakLoadAverage", "[" + "31" + "," + "31" + "]");
                } else {
                    String[] strong = elasticityStr.split("-");
                    hashMap.put("breakLoadAverage", "[" + strong[0] + "," + strong[1] + "]");
                }

                String length = tv_length.getText().toString();
                if (length.equals("25以下")) {
                    hashMap.put("lengthAverage", "[" + "25" + "," + "25" + "]");
                } else if (length.equals("32以上")) {
                    hashMap.put("lengthAverage", "[" + "32" + "," + "32" + "]");
                } else {
                    String[] split = length.split("-");
                    hashMap.put("lengthAverage", "[" + split[0] + "," + "32" + split[1]);
                }
                String[] trash = tv_trash.getText().toString().split("-");
                String[] moisture = tv_moisture.getText().toString().split("-");
//                hashMap.put("breakLoadAverage", "[" + strong[0] + "," + strong[1] + "]");
                hashMap.put("trash", "[" + trash[0] + "," + trash[1] + "]");
                hashMap.put("moisture", "[" + moisture[0] + "," + moisture[1] + "]");
                String s = tv_house.getText().toString();
                if (s.equals("3.4及以下")) {
                    hashMap.put("micronGrade", "C1");
                    hashMap.put("micronAverage", "[" + "3.4" + "," + "3.4" + "]");
                } else if (s.equals("5.0及以上")) {
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    hashMap.put("micronGrade", "C2");
                    hashMap.put("micronAverage", "[" + "5.0" + "," + "5.0" + "]");
                } else if (s.equals("3.5-3.6")) {
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    hashMap.put("micronGrade", "B1");
                    hashMap.put("micronAverage", "[" + "3.5" + "," + "3.6" + "]");
                } else if (s.equals("3.7-4.2")) {
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    hashMap.put("micronGrade", "A");
                    hashMap.put("micronAverage", "[" + "3.7" + "," + "4.2" + "]");
                } else if (s.equals("4.3-4.9")) {
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    hashMap.put("micronGrade", "B2");
                    hashMap.put("micronAverage", "[" + "4.3" + "," + "4.9" + "]");
                } else {
                    String[] split = s.split("-");
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    hashMap.put("micronAverage", "[" + split[0] + "," + split[1] + "]");
                }
                if (!tv_location.getText().toString().equals("请选择加工厂名称")) {
                    String[] split = tv_location.getText().toString().trim().split(" ");
                    hashMap.put("factory",split[1]);
                }
                loadLazyData();
                drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.relativy_origin:
                if (origin_group.isShowMyLayout()) {
                    origin_group.hideMyLayout();
                    image_origin.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    image_origin.setImageResource(R.mipmap.ic_sort_down);
                    origin_group.showMyLayout();
                }
                break;
/*            case R.id.rl_type:
                if (type_group.isShowMyLayout()) {
                    type_group.hideMyLayout();
                    iv_black_ty.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    iv_black_ty.setImageResource(R.mipmap.ic_sort_down);
                    type_group.showMyLayout();
                }
                break;*/
            case R.id.rl_color:
                if (color_group.isShowMyLayout()) {
                    color_group.hideMyLayout();
                    iv_black_color.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    iv_black_color.setImageResource(R.mipmap.ic_sort_down);
                    color_group.showMyLayout();
                }
                break;
/*            case R.id.rl_date:
                if (group_date.isShowMyLayout()) {
                    group_date.hideMyLayout();
                    iv_date.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    iv_date.setImageResource(R.mipmap.ic_sort_down);
                    group_date.showMyLayout();
                }
                break;*/
            case R.id.ll_sortQuality://排序
                isHorseShow = false;
                isKeyShow = false;
                if (!isSortShow) {
                    isSortShow = true;
                    iv_quality_sort.setImageResource(R.mipmap.ic_common_upred);
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                /*    if (sortType != 0) {
                        iv_quality_sort.setImageResource(R.mipmap.ic_common_upred);
                    } else {
                        iv_quality_sort.setImageResource(R.mipmap.ic_common_upblack);
                    }*/
                    showSort(sortType);
                } else {
                    isSortShow = false;
                    popWindowSort.dismiss();
                }

                break;
            case R.id.ll_keyQuality://关键词排序
                isHorseShow = false;
                isSortShow = false;
                if (!isKeyShow) {
                    isKeyShow = true;
                    iv_key.setImageResource(R.mipmap.ic_common_upred);
                    tv_key.setTextColor(getResources().getColor(R.color.tab_tv_selected));
               /*     if (keyType != 0) {
                        iv_key.setImageResource(R.mipmap.ic_common_upred);
                    } else {
                        iv_key.setImageResource(R.mipmap.ic_common_upblack);
                    }*/
                    showKey(keyType);
                } else {
                    isKeyShow = false;
                    popWindowKey.dismiss();
                }

                break;
            case R.id.ll_horseQuality://棉花年度排序
                isSortShow = false;
                isKeyShow = false;
                if (!isHorseShow) {
                    isHorseShow = true;
                    iv_horseSelect.setImageResource(R.mipmap.ic_common_upred);
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                  /*  if (horseType != 0) {
                        iv_horseSelect.setImageResource(R.mipmap.ic_common_upred);
                    } else {
                        iv_horseSelect.setImageResource(R.mipmap.ic_common_upblack);
                    }*/
                    showHorse(horseType);
                } else {
                    popWindowHors.dismiss();
                    isHorseShow = false;
                }

                break;
            case R.id.ll_Intelligence_quality://智能排序
                isKeyShow = false;
                if (sortType == 1) {
                    sortType = 0;
                    hashMap.remove("orderType");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_quality_sort.setText("排序");
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    hashMap.put("orderType", "1");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    tv_quality_sort.setText("智能排序");
                    sortType = 1;
                }
                popWindowSort.dismiss();
                isSortShow = false;
                break;
            case R.id.ll_StoB_quality://批号从小到大
                if (sortType == 2) {
                    sortType = 0;
                    hashMap.remove("orderType");
                    tv_quality_sort.setText("排序");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    hashMap.put("orderType", "3");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_quality_sort.setText("批号从小");
                    sortType = 2;
                }
                popWindowSort.dismiss();
                isSortShow = false;
                break;

            case R.id.ll_BtoS_quality://批号从大到小
                if (sortType == 3) {
                    sortType = 0;
                    hashMap.remove("orderType");
                    tv_quality_sort.setText("排序");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    hashMap.put("orderType", "4");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_quality_sort.setText("批号从大");
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    sortType = 3;
                }
                popWindowSort.dismiss();
                isSortShow = false;
                break;
            case R.id.ll_sort_type1:
                if (keyType == 1) {
                    keyType = 0;
                    hashMap.remove("keywords");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_key.setText("品类");
                    tv_key.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    hashMap.put("keywords", "[手摘棉]");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_key.setText("手摘棉");
                    tv_key.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    keyType = 1;
                }
                popWindowKey.dismiss();
                isKeyShow = false;
                break;
            case R.id.ll_sort_type2:
                if (keyType == 2) {
                    keyType = 0;
                    tv_key.setText("品类");
                    hashMap.remove("keywords");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_key.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    tv_key.setText("机采棉");
                    hashMap.put("keywords", "[机采棉]");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_key.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    keyType = 2;
                }
                popWindowKey.dismiss();
                isKeyShow = false;
                break;
            case R.id.ll_sort_type3:
                if (keyType == 3) {
                    tv_key.setText("品类");
                    hashMap.remove("keywords");
                    loadLazyData();
                    tv_key.setTextColor(getResources().getColor(R.color.drop_color));
                    keyType = 0;
                } else {
                    tv_key.setText("皮辊棉");
                    hashMap.put("keywords", "[皮辊棉]");
                    loadLazyData();
                    tv_key.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    keyType = 3;
                }
                popWindowKey.dismiss();
                isKeyShow = false;
                break;
            case R.id.ll_sort_type4:
                if (keyType == 4) {
                    tv_key.setText("品类");
                    hashMap.remove("keywords");
                    loadLazyData();
                    tv_key.setTextColor(getResources().getColor(R.color.drop_color));
                    keyType = 0;
                } else {
                    tv_key.setText("长绒棉");
                    hashMap.put("keywords", "[长绒棉]");
                    loadLazyData();
                    tv_key.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    keyType = 4;
                }
                popWindowKey.dismiss();
                isKeyShow = false;
                break;
      /*      case R.id.ll_sort_type5:
                if (keyType == 5) {
                    tv_key.setText("品类");
                    hashMap.remove("keywords");
                    loadLazyData();
                    tv_key.setTextColor(getResources().getColor(R.color.drop_color));
                    keyType = 0;
                } else {
                    tv_key.setText("双29");
                    hashMap.put("keywords", "双29");
                    loadLazyData();
                    tv_key.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    keyType = 5;
                }
                popWindowKey.dismiss();
                isKeyShow = false;
                break;*/
            case R.id.ll_horse_type1:
                if (horseType == 1) {
                    hashMap.remove("createYear");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setText("棉花年度");
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.drop_color));
                    horseType = 0;
                } else {
                    tv_horseSelect.setText("2017");
                    hashMap.put("createYear", "2017");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    horseType = 1;
                }
                popWindowHors.dismiss();
                isHorseShow = false;
                break;
  /*          case R.id.ll_horse_type2:
                if (horseType == 2) {
                    tv_horseSelect.setText("棉花年度");
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.drop_color));
                    horseType = 0;
                } else {
                    tv_horseSelect.setText("B1");
                    hashMap.put("micronGrade", "B1");
                    hashMap.put("micronAverage", "[3.5,3.6]");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    horseType = 2;
                }
                popWindowHors.dismiss();
                isHorseShow = false;
                break;
            case R.id.ll_horse_type3:
                if (horseType == 3) {
                    tv_horseSelect.setText("棉花年度");
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.drop_color));
                    horseType = 0;
                } else {
                    tv_horseSelect.setText("A");
                    hashMap.put("micronGrade", "A");
                    hashMap.put("micronAverage", "[3.7,4.2]");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    horseType = 3;
                }
                popWindowHors.dismiss();
                isHorseShow = false;
                break;
            case R.id.ll_horse_type4:
                if (horseType == 4) {
                    tv_horseSelect.setText("棉花年度");
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.drop_color));
                    horseType = 0;
                } else {
                    tv_horseSelect.setText("B2");
                    hashMap.put("micronGrade", "B2");
                    hashMap.put("micronAverage", "[4.3,4.9]");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    horseType = 4;
                }
                popWindowHors.dismiss();
                isHorseShow = false;
                break;
            case R.id.ll_horse_type5:
                if (horseType == 5) {
                    tv_horseSelect.setText("棉花年度");
                    hashMap.remove("micronGrade");
                    hashMap.remove("micronAverage");
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.drop_color));
                    horseType = 0;
                } else {
                    tv_horseSelect.setText("C2");
                    hashMap.put("micronGrade", "C2");
                    MicronAverage micronAverage = new MicronAverage("5.0", "5.0");
                    String jsonString = gson.toJson(micronAverage);
                    hashMap.put("micronAverage", jsonString);
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    loadLazyData();
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    horseType = 5;
                }
                popWindowHors.dismiss();
                isHorseShow = false;
                break;*/
        }
    }
    public static void showAsDropDown(PopupWindow pw, View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            if (Build.VERSION.SDK_INT == 25) {
                WindowManager wm = (WindowManager) pw.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                pw.setHeight(screenHeight - location[1] - anchor.getHeight() - yoff);
            }
            pw.showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, location[1] + anchor.getHeight() + yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }
    private void showSort(int sortType) {
        View conentView = inflater.inflate(R.layout.popup_drop_quality_sort, null);
        popWindowSort = new PopWindow(getActivity(), conentView);
        initSortView(conentView);
        popWindowSort.showAsDropDown(popWindowSort,ll_sortQuality,0,0);
        //7.0  24  7.1  25
      /*  if (Build.VERSION.SDK_INT < 24) {
            popWindowSort.showAsDropDown(ll_sortQuality, 0, 0);
        } else {
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            ll_sortQuality.getLocationOnScreen(location);
            popWindowSort.showAtLocation(ll_sortQuality, Gravity.NO_GRAVITY, 0, location[1] + ll_sortQuality.getHeight() + 0);
        }*/
        popWindowSort.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tv_quality_sort.getText().equals("排序")) {
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.drop_color));
                    iv_quality_sort.setImageResource(R.mipmap.ic_common_downblack);
                } else {
                    tv_quality_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    iv_quality_sort.setImageResource(R.mipmap.ic_common_downred);
                }
            }
        });
        switch (sortType) {
            case 1:
                tv_sort_quality.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                tv_stoBig_quality.setTextColor(getResources().getColor(R.color.drop_color));
                tv_btoS_quality.setTextColor(getResources().getColor(R.color.drop_color));

                iv_sort_quality.setVisibility(View.VISIBLE);
                iv_stoBig_quality.setVisibility(View.GONE);
                iv_btoS_quality.setVisibility(View.GONE);
                break;
            case 2:
                tv_sort_quality.setTextColor(getResources().getColor(R.color.drop_color));
                tv_stoBig_quality.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                tv_btoS_quality.setTextColor(getResources().getColor(R.color.drop_color));

                iv_sort_quality.setVisibility(View.GONE);
                iv_stoBig_quality.setVisibility(View.VISIBLE);
                iv_btoS_quality.setVisibility(View.GONE);
                break;
            case 3:
                tv_sort_quality.setTextColor(getResources().getColor(R.color.drop_color));
                tv_stoBig_quality.setTextColor(getResources().getColor(R.color.drop_color));
                tv_btoS_quality.setTextColor(getResources().getColor(R.color.tab_tv_selected));

                iv_sort_quality.setVisibility(View.GONE);
                iv_stoBig_quality.setVisibility(View.GONE);
                iv_btoS_quality.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initSortView(View conentView) {
        LinearLayout ll_Intelligence_quality = (LinearLayout) conentView.findViewById(R.id.ll_Intelligence_quality);
        ll_Intelligence_quality.setOnClickListener(this);
        LinearLayout ll_StoB_quality = (LinearLayout) conentView.findViewById(R.id.ll_StoB_quality);
        ll_StoB_quality.setOnClickListener(this);
        LinearLayout ll_BtoS_quality = (LinearLayout) conentView.findViewById(R.id.ll_BtoS_quality);
        ll_BtoS_quality.setOnClickListener(this);

        tv_sort_quality = (TextView) conentView.findViewById(R.id.tv_sort_quality);
        tv_btoS_quality = (TextView) conentView.findViewById(R.id.tv_BtoS_quality);
        tv_stoBig_quality = (TextView) conentView.findViewById(R.id.tv_StoBig_quality);

        iv_stoBig_quality = (ImageView) conentView.findViewById(R.id.iv_StoBig_quality);
        iv_btoS_quality = (ImageView) conentView.findViewById(R.id.iv_BtoS_quality);
        iv_sort_quality = (ImageView) conentView.findViewById(R.id.iv_sort_quality);

        FrameLayout fl_dismiss = (FrameLayout) conentView.findViewById(R.id.fl_dismiss);
        FrameLayout afl_dismiss = (FrameLayout) conentView.findViewById(R.id.afl_dismiss);
        afl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowSort.dismiss();
                isSortShow = false;
            }
        });
        fl_dismiss.setAlpha(0.3f);
        fl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowSort.dismiss();
                isSortShow = false;
            }
        });
    }

    private void showHorse(int horseType) {
        View conentView = inflater.inflate(R.layout.popup_drop_quality_horse, null);
        popWindowHors = new PopWindow(getActivity(), conentView);
        initHorseView(conentView);
        popWindowHors.showAsDropDown(popWindowHors,ll_horseQuality,0,0);
    /*    if (Build.VERSION.SDK_INT < 24) {
            popWindowHors.showAsDropDown(ll_horseQuality, 0, 0);
        } else {
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            ll_horseQuality.getLocationOnScreen(location);
            popWindowHors.showAtLocation(popWindowHors, Gravity.NO_GRAVITY, 0, location[1] + ll_horseQuality.getHeight() + 0);
        }*/
        popWindowHors.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tv_horseSelect.getText().equals("棉花年度")) {
                    iv_horseSelect.setImageResource(R.mipmap.ic_common_downblack);
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    tv_horseSelect.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    iv_horseSelect.setImageResource(R.mipmap.ic_common_downred);
                }
            }
        });
        switch (horseType) {
            case 1:
                tv_horse_type1.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_horse_type1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_horse_type1.setTextColor(getResources().getColor(R.color.drop_color));
                iv_horse_type1.setVisibility(View.GONE);
                break;
            case 3:
                tv_horse_type1.setTextColor(getResources().getColor(R.color.drop_color));
                iv_horse_type1.setVisibility(View.GONE);
                break;
            case 4:
                tv_horse_type1.setTextColor(getResources().getColor(R.color.drop_color));
                iv_horse_type1.setVisibility(View.GONE);
                break;
            case 5:
                tv_horse_type1.setTextColor(getResources().getColor(R.color.drop_color));
                iv_horse_type1.setVisibility(View.GONE);
                break;
        }
    }

    private void initHorseView(View conentView) {
        LinearLayout ll_horse_type1 = (LinearLayout) conentView.findViewById(R.id.ll_horse_type1);
        ll_horse_type1.setOnClickListener(this);
/*        LinearLayout ll_horse_type2 = (LinearLayout) conentView.findViewById(R.id.ll_horse_type2);
        ll_horse_type2.setOnClickListener(this);*//*
        LinearLayout ll_horse_type3 = (LinearLayout) conentView.findViewById(R.id.ll_horse_type3);
        ll_horse_type3.setOnClickListener(this);
        LinearLayout ll_horse_type4 = (LinearLayout) conentView.findViewById(R.id.ll_horse_type4);
        ll_horse_type4.setOnClickListener(this);
        LinearLayout ll_horse_type5 = (LinearLayout) conentView.findViewById(R.id.ll_horse_type5);
        ll_horse_type5.setOnClickListener(this);*/

        tv_horse_type1 = (TextView) conentView.findViewById(R.id.tv_horse_type1);
/*
        tv_horse_type2 = (TextView) conentView.findViewById(R.id.tv_horse_type2);
        tv_horse_type3 = (TextView) conentView.findViewById(R.id.tv_horse_type3);
        tv_horse_type4 = (TextView) conentView.findViewById(R.id.tv_horse_type4);
        tv_horse_type5 = (TextView) conentView.findViewById(R.id.tv_horse_type5);
*/

        iv_horse_type1 = (ImageView) conentView.findViewById(R.id.iv_horse_type1);
     /*   iv_horse_type2 = (ImageView) conentView.findViewById(R.id.iv_horse_type2);
        iv_horse_type3 = (ImageView) conentView.findViewById(R.id.iv_horse_type3);
        iv_horse_type4 = (ImageView) conentView.findViewById(R.id.iv_horse_type4);
        iv_horse_type5 = (ImageView) conentView.findViewById(R.id.iv_horse_type5);*/
        FrameLayout fl_dismiss = (FrameLayout) conentView.findViewById(R.id.fl_dismiss);
        fl_dismiss.setAlpha(0.3f);
        FrameLayout afl_dismiss = (FrameLayout) conentView.findViewById(R.id.afl_dismiss);
        afl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowHors.dismiss();
                isHorseShow = false;
            }
        });
        fl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowHors.dismiss();
                isHorseShow = false;
            }
        });
    }

    private void showKey(int keyType) {
        View conentView = inflater.inflate(R.layout.popup_drop_quality_keyword, null);
        popWindowKey = new PopWindow(getActivity(), conentView);
        initKeyView(conentView);
        popWindowKey.showAsDropDown(popWindowKey,ll_keyQuality,0,0);
     /*   if (Build.VERSION.SDK_INT < 24) {
            popWindowKey.showAsDropDown(ll_keyQuality, 0, 0);
        } else {
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            ll_keyQuality.getLocationOnScreen(location);
            popWindowKey.showAtLocation(ll_keyQuality, Gravity.NO_GRAVITY, 0, location[1] + ll_keyQuality.getHeight() + 0);
        }*/
        popWindowKey.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tv_key.getText().equals("品类")) {
                    iv_key.setImageResource(R.mipmap.ic_common_downblack);
                    tv_key.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    tv_key.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    iv_key.setImageResource(R.mipmap.ic_common_downred);
                }
            }
        });
        switch (keyType) {
            case 1:
                tv_sort_type1.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                tv_sort_type2.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type3.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type4.setTextColor(getResources().getColor(R.color.drop_color));
                iv_sort_type1.setVisibility(View.VISIBLE);
                iv_sort_type2.setVisibility(View.GONE);
                iv_sort_type3.setVisibility(View.GONE);
                iv_sort_type4.setVisibility(View.GONE);
                break;
            case 2:
                tv_sort_type1.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type2.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                tv_sort_type3.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type4.setTextColor(getResources().getColor(R.color.drop_color));
                iv_sort_type1.setVisibility(View.GONE);
                iv_sort_type2.setVisibility(View.VISIBLE);
                iv_sort_type3.setVisibility(View.GONE);
                iv_sort_type4.setVisibility(View.GONE);
                break;
            case 3:
                tv_sort_type1.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type2.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type3.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                tv_sort_type4.setTextColor(getResources().getColor(R.color.drop_color));
                iv_sort_type1.setVisibility(View.GONE);
                iv_sort_type2.setVisibility(View.GONE);
                iv_sort_type3.setVisibility(View.VISIBLE);
                iv_sort_type4.setVisibility(View.GONE);
                break;
            case 4:
                tv_sort_type1.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type2.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type3.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type4.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_sort_type1.setVisibility(View.GONE);
                iv_sort_type2.setVisibility(View.GONE);
                iv_sort_type3.setVisibility(View.GONE);
                iv_sort_type4.setVisibility(View.VISIBLE);
                break;
            case 5:
                tv_sort_type1.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type2.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type3.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort_type4.setTextColor(getResources().getColor(R.color.drop_color));
//                 .setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_sort_type1.setVisibility(View.GONE);
                iv_sort_type2.setVisibility(View.GONE);
                iv_sort_type3.setVisibility(View.GONE);
                iv_sort_type4.setVisibility(View.GONE);
                break;
        }
    }

    private void initKeyView(View conentView) {
        LinearLayout ll_sort_type1 = (LinearLayout) conentView.findViewById(R.id.ll_sort_type1);
        ll_sort_type1.setOnClickListener(this);
        LinearLayout ll_sort_type2 = (LinearLayout) conentView.findViewById(R.id.ll_sort_type2);
        ll_sort_type2.setOnClickListener(this);
        LinearLayout ll_sort_type3 = (LinearLayout) conentView.findViewById(R.id.ll_sort_type3);
        ll_sort_type3.setOnClickListener(this);
        LinearLayout ll_sort_type4 = (LinearLayout) conentView.findViewById(R.id.ll_sort_type4);
        ll_sort_type4.setOnClickListener(this);
       /* LinearLayout ll_sort_type5 = (LinearLayout) conentView.findViewById(R.id.ll_sort_type5);
        ll_sort_type5.setOnClickListener(this);*/
        tv_sort_type1 = (TextView) conentView.findViewById(R.id.tv_sort_type1);
        tv_sort_type2 = (TextView) conentView.findViewById(R.id.tv_sort_type2);
        tv_sort_type3 = (TextView) conentView.findViewById(R.id.tv_sort_type3);
        tv_sort_type4 = (TextView) conentView.findViewById(R.id.tv_sort_type4);
//          = (TextView) conentView.findViewById(R.id. );
        iv_sort_type1 = (ImageView) conentView.findViewById(R.id.iv_sort_type1);
        iv_sort_type2 = (ImageView) conentView.findViewById(R.id.iv_sort_type2);
        iv_sort_type3 = (ImageView) conentView.findViewById(R.id.iv_sort_type3);
        iv_sort_type4 = (ImageView) conentView.findViewById(R.id.iv_sort_type4);
//          = (ImageView) conentView.findViewById(R.id. );
        FrameLayout fl_dismiss = (FrameLayout) conentView.findViewById(R.id.fl_dismiss);
        FrameLayout afl_dismiss = (FrameLayout) conentView.findViewById(R.id.afl_dismiss);
        afl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowKey.dismiss();
                isKeyShow = false;
            }
        });
        fl_dismiss.setAlpha(0.3f);
        fl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowKey.dismiss();
                isKeyShow = false;
            }
        });
    }


    private void initRecylerView() {
        if (qualityRecylerAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            qualityRecyler = ((RecyclerView) mRootView.findViewById(R.id.qualityRecyler));
            qualityRecyler.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            qualityRecylerAdapter = new QualityRecylerAdapter(R.layout.fragment_quality_recyler_item, mDatas);
            qualityRecylerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    isUpDate = false;
                    String batchType = mDatas.get(position).batchType;
                    String code = mDatas.get(position).code;
                    String productId = mDatas.get(position).id;
                    addHistory(productId, batchType, code);
                    //1是捆 2是批 3捆批
                    if (batchType.equals("1")) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), QualityKunDetailsActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("productId", productId);
                        startActivity(intent);
                    } else if (batchType.equals("2")) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), QualityBatchDetailsActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("fromKun", "0");
                        intent.putExtra("productId", productId);
                        intent.putExtra("batchType", batchType);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(BaseApplication.getContextObject(), QualityBatchDetailsActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("fromKun", "1");
                        intent.putExtra("productId", productId);
                        intent.putExtra("batchType", batchType);
                        startActivity(intent);
                    }
                }
            });
            View getEmptyview = getLayoutInflater(getArguments()).inflate(R.layout.nodata_quality, (ViewGroup) qualityRecyler.getParent(), false);
            qualityRecylerAdapter.setEmptyView(getEmptyview);
            qualityRecyler.setAdapter(qualityRecylerAdapter);
        } else {
            qualityRecylerAdapter.notifyDataSetChanged();
        }
    }

    private void addHistory(String id, String batchType, String code) {
        Map<String, String> mapHistory = new HashMap<>();
        mapHistory.put("isProduct", "0");
        mapHistory.put("productId", id);
        mapHistory.put("batchType", batchType);
        mapHistory.put("code", code);
        mapHistory.put("mobile", mobileMy);
        HttpManager.getServerApi().addHistory(mapHistory).enqueue(new CallBack<QualityCountBean>() {
            @Override
            public void success(QualityCountBean data) {

            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });

    }

    protected boolean isVisible = false;//页面是否可见
    protected boolean isPrepared = false;//是否布局已创建

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        onVisible();
        isUpDate = true;
    }

    protected void onVisible() {
        if (!isVisible || !isPrepared) {
            return;
        }
        if (isUpDate) {
            upDataSort();
        }
        loadLazyData();
    }

    private void upDataSort() {
        tv_quality_sort.setText("排序");
        tv_house.setText("3.4-5.0");
        iv_quality_sort.setImageResource(R.mipmap.ic_common_downblack);
        tv_quality_sort.setTextColor(getResources().getColor(R.color.drop_color));
        sortType = 0;
        tv_key.setText("品类");
        iv_key.setImageResource(R.mipmap.ic_common_downblack);
        tv_key.setTextColor(getResources().getColor(R.color.drop_color));
        keyType = 0;
        tv_horseSelect.setText("棉花年度");
        iv_horseSelect.setImageResource(R.mipmap.ic_common_downblack);
        tv_horseSelect.setTextColor(getResources().getColor(R.color.drop_color));
        horseType = 0;
        tv_shaixuan.setTextColor(getResources().getColor(R.color.drop_color));
        iv_shaixuan.setImageResource(R.mipmap.ic_store_funnel);
//        group_date.clearSelected();
//        type_group.clearSelected();
        color_group.clearSelected();
        origin_group.clearSelected();
        textView.setText("24-31");
        tv_length.setText("25-32");
        tv_trash.setText("0-5");
        tv_moisture.setText("0-10");
        tv_location.setText("请选择加工厂名称");
        bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
        bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
        bt_a.setTextColor(getResources().getColor(R.color.drop_color));
        bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
        bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
        bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        hashMap.clear();
        hashMap.put("deviceNo", Common.deviceNo);
        hashMap.put("from", Common.from);
        hashMap.put("sourceTypeEnum", Common.sourceTypeEnum);
        hashMap.put("orderType", "1");
        hashMap.put("versionNo", Common.versionNo);
        hashMap.put("searchType", "3");
        loadLazyData();
/*        bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);*/
    }

    private void loadLazyData() {
        mPtrrame.refreshComplete();
        hashMap.put("pageNum", pageNo + "");
        showProgressBar("请求数据中.......", true);
        HttpManager.getServerApi().getQualityList(hashMap).enqueue(new CallBack<QualityBean>() {
            @Override
            public void success(QualityBean data) {
                dismissProgressBar();
                if (data.success) {
                    if (mDatas != null) {
                        if (pageNo == 1) {
                            mDatas.clear();
                            if (data.value.rows.size()==0){
                                upLoadBatch.setVisibility(View.GONE);
                            }else{
                                upLoadBatch.setVisibility(View.VISIBLE);
                            }
                        }
                        mDatas.addAll(data.value.rows);
                    } else {
                        mDatas = data.value.rows;
                    }
                    initRecylerView();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        //监听fragment的返回事件 如果drawerlayout没关闭 则关闭
        if (drawerLayout.isDrawerOpen(ll_drawer1)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
            return true;
        }
        if (popWindowSort != null && popWindowSort.isShowing()) {
            popWindowSort.dismiss();
            return true;
        }
        if (popWindowHors != null && popWindowHors.isShowing()) {
            popWindowHors.dismiss();
            return true;
        }
        if (popWindowKey != null && popWindowKey.isShowing()) {
            popWindowKey.dismiss();
            return true;
        }
        return BackHandlerHelper.handleBackPress(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (drawerLayout.isDrawerOpen(ll_drawer1)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        isVisible = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        mobileMy = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile");
        isLogin = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isLogin");
        eMUserName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMUserName");
        eMPassword = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMPassword");
    }

    public void ishaveData() {
//        List<String> selectedList = group_date.getSelectedList();
//        List<String> selectedList1 = type_group.getSelectedList();
        List<String> selectedList2 = color_group.getSelectedList();
        List<String> selectedList3 = origin_group.getSelectedList();
        if (tv_location.getText().toString().equals("请选择加工厂名称")&&tv_house.getText().equals("3.4-5.0") && selectedList2.size() < 1 && selectedList3.size() < 1 && textView.getText().equals("24-31") && tv_length.getText().equals("25-32") && tv_trash.getText().equals("0-5") && tv_moisture.getText().equals("0-10")) {
            tv_shaixuan.setTextColor(getResources().getColor(R.color.drop_color));
            iv_shaixuan.setImageResource(R.mipmap.ic_store_funnel);
        } else {
            tv_shaixuan.setTextColor(getResources().getColor(R.color.tab_tv_selected));
            iv_shaixuan.setImageResource(R.mipmap.ic_store_funnelred);
        }
    }
}
