package com.tianfu.cutton.fragment.store;

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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.activity.store.SearchActivity;
import com.tianfu.cutton.activity.store.StoreBatchActivity;
import com.tianfu.cutton.activity.store.StoreKunActivity;
import com.tianfu.cutton.adapter.ClassifyMainAdapter;
import com.tianfu.cutton.adapter.ClassifyMoreAdapter;
import com.tianfu.cutton.adapter.StoreRecylerAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.fragment.adapter.BackHandlerHelper;
import com.tianfu.cutton.fragment.adapter.FragmentBackHandler;
import com.tianfu.cutton.model.AllStoragesBean;
import com.tianfu.cutton.model.QualityCountBean;
import com.tianfu.cutton.model.StoreBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CallKefu;
import com.tianfu.cutton.utils.ListToListString;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.widget.DoubleSeekBar;
import com.tianfu.cutton.widget.MultiCheckGroupView;
import com.tianfu.cutton.widget.PopWindow;

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
public class StoreFragment extends BaseFragment implements View.OnClickListener, FragmentBackHandler {
    private View mRootView;
    private RecyclerView recyler;
    private int pageNo = 1;
    private Map<String, String> hashMap;
    private List<StoreBean.ValueBean.RowsBean> mDatas;
    private StoreRecylerAdapter homeRecylerAdapter;
    private LayoutInflater inflater;
    private int sortType = 0;//记录排序规则
    private int typeType = 0;//记录排序规则
    private TextView tvZhineng;
    /*private TextView tvJtoY;
    private TextView tvYtoJ;*/
    private ImageView ivZhineng;
    /*    private ImageView ivJtoY;
        private ImageView ivYtoJ;*/
    private LinearLayout ll_zhineng;
    private LinearLayout ll_jtoY;
    private LinearLayout ll_ytoJ;
    private LinearLayout llDropDown;
    private LinearLayout llPrice;
    private LinearLayout llType;
    private LinearLayout llPopup;
    private PopWindow popDistance;
    private TextView tv_sort;
    private ImageView iv_sort;
    private ImageView iv_price_up;
    private ImageView iv_price_down;
    private PtrClassicFrameLayout mPtrrame;
    private TextView tv_price;
    private PopWindow popupType;
    private LinearLayout ll_myself;
    private LinearLayout ll_recevi;
    private TextView tv_myself;
    private TextView tv_recevi;
    private ImageView iv_myself;
    private ImageView iv_recevi;
    private TextView tv_store_type;
    //    private ImageView iv_store_type;
    private RelativeLayout ll_drawer;
    private ImageView iv_black_keyword;
    private MultiCheckGroupView keyword_group;
    private ImageView image_origin;
    private MultiCheckGroupView origin_group;
    private ImageView iv_black_ty;
    private MultiCheckGroupView type_group;
    private ImageView iv_black_color;
    private MultiCheckGroupView color_group;
    private ImageView iv_date;
    private MultiCheckGroupView date_group;
    private DoubleSeekBar doubleseekbar_house;
    private DoubleSeekBar doubleseekbar_elasticity;
    private DoubleSeekBar doubleseekbar_length;
    private DoubleSeekBar doubleseekbar_trash;
    private DoubleSeekBar doubleSeekBar_moisture;
    private Button bt_a;
    private Button bt_b1;
    private Button bt_b2;
    private Button bt_c1;
    private Button bt_c2;
    private TextView tv_house;
    private TextView tv_elasticity;
    private TextView tv_length;
    private TextView tv_trash;
    private TextView tv_moisture;
    private TextView tv_shaixuan;
    private ImageView imageView;
    private EditText et_search;
    private String mobileMy;
    private boolean isOn;
    private Map<String, String> mapKeyword;
    private Map<String, String> mapType;
    private Map<String, String> mapColor;
    private Map<String, String> mapDate;
    private Map<String, String> mapLocation;
    private TextView tv_3128B;
    private TextView tv_double28;
    private TextView tv_double29;
    private ImageView iv_3128B;
    private ImageView iv_double28;
    private ImageView iv_double29;
    private boolean isShowTypePop = false;
    private boolean isShowSort = false;
    private boolean data;
    private boolean isUpDate = true;
    private ListView mainlist;
    private ListView morelist;
    private List<Map<String, Object>> mainList;
    ClassifyMainAdapter mainAdapter;
    ClassifyMoreAdapter moreAdapter;
    private LinearLayout bt_select;
    private RelativeLayout rl_keyword;
    private RelativeLayout relativy_origin;
    private ImageView sts_price_up;
    private ImageView sts_price_down;
    private PopupWindow popWindowList;
    private boolean isShowContanc = true;
    private TextView tv_location;
    private EditText et_contact;
    private ImageView iv_contact;

    public StoreFragment() {
        // Required empty public constructor
    }

    public static String[] LISTVIEWTXT;
    public static String[][] MORELISTTXT;
    private int count = 1;
    private int countsts = 1;
    private int num = 1;
    private int numsts = 1;
    int imageUp[] = new int[]{
            R.mipmap.ic_common_upred,
            R.mipmap.ic_common_upblack
    };
    int imageDown[] = new int[]{
            R.mipmap.ic_common_downblack,
            R.mipmap.ic_common_downred
    };
    int imageUpSts[] = new int[]{
            R.mipmap.ic_common_upred,
            R.mipmap.ic_common_upblack
    };
    int imageDownSts[] = new int[]{
            R.mipmap.ic_common_downblack,
            R.mipmap.ic_common_downred
    };
    private DrawerLayout drawerLayout;
    private Boolean isLogin;
    private String eMUserName;
    private String eMPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_store, container, false);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            hashMap = new HashMap<>();
            hashMap.put("deviceNo", Common.deviceNo);
            hashMap.put("from", Common.from);
            hashMap.put("sourceTypeEnum", Common.sourceTypeEnum);
            hashMap.put("versionNo", Common.versionNo);
            hashMap.put("searchType", "2");
            hashMap.put("orderType", "1");
            initView();
            initDrawerView();
            return mRootView;
        }
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
        tv_elasticity = (TextView) mRootView.findViewById(R.id.tv_elasticity);
        tv_length = (TextView) mRootView.findViewById(R.id.tv_length);
        tv_trash = (TextView) mRootView.findViewById(R.id.tv_trash);
        tv_moisture = (TextView) mRootView.findViewById(R.id.tv_moisture);
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
        doubleseekbar_elasticity = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_elasticity);
        doubleseekbar_length = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_length);
        doubleseekbar_trash = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_trash);
        doubleSeekBar_moisture = (DoubleSeekBar) mRootView.findViewById(R.id.doubleSeekBar_moisture);

        doubleseekbar_elasticity.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tv_elasticity.setText((int) lowValue + "-" + (int) highValue);
                if (((int) lowValue) == ((int) highValue)) {
                    if ((int) lowValue == 24) {
                        tv_elasticity.setText("24及以下");
                    } else if ((int) lowValue == 31) {
                        tv_elasticity.setText("31及以上");
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
        rl_keyword = (RelativeLayout) mRootView.findViewById(R.id.rl_keyword);
        rl_keyword.setOnClickListener(this);
        iv_black_keyword = (ImageView) mRootView.findViewById(R.id.iv_black_keyword);
        keyword_group = (MultiCheckGroupView) mRootView.findViewById(R.id.keyword_group);

        relativy_origin = (RelativeLayout) mRootView.findViewById(R.id.relativy_origin);
        relativy_origin.setOnClickListener(this);
        image_origin = (ImageView) mRootView.findViewById(R.id.image_origin);
        origin_group = (MultiCheckGroupView) mRootView.findViewById(R.id.origin_group);

    /*    RelativeLayout rl_type = (RelativeLayout) mRootView.findViewById(R.id.rl_type);
        rl_type.setOnClickListener(this);*/
        iv_black_ty = (ImageView) mRootView.findViewById(R.id.iv_black_ty);
//        type_group = (MultiCheckGroupView) mRootView.findViewById(R.id.type_group);

        RelativeLayout rl_color = (RelativeLayout) mRootView.findViewById(R.id.rl_color);
        rl_color.setOnClickListener(this);
        iv_black_color = (ImageView) mRootView.findViewById(R.id.iv_black_color);
        color_group = (MultiCheckGroupView) mRootView.findViewById(R.id.color_group);

        RelativeLayout rl_date = (RelativeLayout) mRootView.findViewById(R.id.rl_date);
        rl_date.setOnClickListener(this);
        RelativeLayout rl_contact = (RelativeLayout) mRootView.findViewById(R.id.rl_contact);
        rl_contact.setOnClickListener(this);
        iv_contact = (ImageView) mRootView.findViewById(R.id.iv_contact);
        iv_date = (ImageView) mRootView.findViewById(R.id.iv_date);
        date_group = (MultiCheckGroupView) mRootView.findViewById(R.id.date_group);

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
                String elasticityStr = tv_elasticity.getText().toString();
                if (elasticityStr.equals("24及以下")) {
                    doubleseekbar_elasticity.setValues(24, 24);
                } else if (elasticityStr.equals("31及以上")) {
                    doubleseekbar_elasticity.setValues(31, 31);
                } else {
                    String[] splitelasticity = tv_elasticity.getText().toString().split("-");
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
                String[] splittrash = tv_trash.getText().toString().split("-");
                String[] splitmoisture = tv_moisture.getText().toString().split("-");
                doubleseekbar_trash.setValues(Integer.valueOf(splittrash[0]), Integer.valueOf(splittrash[1]));
                doubleSeekBar_moisture.setValues(Integer.valueOf(splitmoisture[0]), Integer.valueOf(splitmoisture[1]));
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                ((MainActivity) getActivity()).mTabLayout.setVisibility(View.VISIBLE);
                ishaveData();
                if (popWindowList != null) {
                    popWindowList.dismiss();
                }
                WindowManager.LayoutParams attr = getActivity().getWindow().getAttributes();
                attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getActivity().getWindow().setAttributes(attr);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mapKeyword = new TreeMap<>();
        mapKeyword.put("0", "手摘棉");
        mapKeyword.put("1", "机采棉");
        mapKeyword.put("2", "皮辊棉");
        mapKeyword.put("3", "长绒棉");
        keyword_group.addValues(mapKeyword);
        keyword_group.setMultiCheck(false);


        mapType = new TreeMap<>();
        mapType.put("0", "锯齿细绒棉");
        mapType.put("1", "锯齿机采棉");
        mapType.put("2", "皮辊细绒棉");
//        type_group.addValues(mapType);

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
        mapDate = new TreeMap<>();
        mapDate.put("0", "2017");
        mapDate.put("1", "2016");
        date_group.addValues(mapDate);
        date_group.setMultiCheck(false);
        mapLocation = new TreeMap<>();
        mapLocation.put("0", "新疆棉");
        mapLocation.put("3", "地产棉");
        mapLocation.put("4", "进口棉");
        origin_group.addValues(mapLocation);
        origin_group.setMultiCheck(false);


        et_contact = (EditText) mRootView.findViewById(R.id.et_Contact);
        et_contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                    lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    getActivity().getWindow().setAttributes(lp);
                }
            }
        });
    }

    private void initProdata() {
        Map map = new HashMap();
        HttpManager.getServerApi().getAllStorages(map).enqueue(new CallBack<AllStoragesBean>() {
            @Override
            public void success(AllStoragesBean data) {
                if (data.success) {
                    List<AllStoragesBean.ValueBean> value = data.value;
                    MORELISTTXT = new String[value.size()][value.get(0).storages.size()];
                    List<String> listProvince = new ArrayList();
                    for (int i = 0; i < value.size(); i++) {
                        listProvince.add(value.get(i).province);
                        List<String> storages = value.get(i).storages;
                        String[] strings = storages.toArray(new String[storages.size()]);
                        MORELISTTXT[i] = strings;
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
        ImageView iv_message = (ImageView) mRootView.findViewById(R.id.iv_message);
        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowTypePop = false;
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                CallKefu.callOnLine(getActivity(), eMUserName, eMPassword);
            }
        });
        LinearLayout ll_search = (LinearLayout) mRootView.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        mPtrrame = (PtrClassicFrameLayout) mRootView.findViewById(R.id.ptr_purchase_frame);
        tv_shaixuan = (TextView) mRootView.findViewById(R.id.tv_shaixuan);
        imageView = (ImageView) mRootView.findViewById(R.id.iv_shaixuan);
        ll_drawer = (RelativeLayout) mRootView.findViewById(R.id.ll_drawer);
        drawerLayout = (DrawerLayout) mRootView.findViewById(R.id.drawerLayout);
        inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        llDropDown = (LinearLayout) mRootView.findViewById(R.id.ll_drop_down);
        llDropDown.setOnClickListener(this);
        llPrice = (LinearLayout) mRootView.findViewById(R.id.ll_price);
        llPrice.setOnClickListener(this);
        llType = (LinearLayout) mRootView.findViewById(R.id.ll_type);
        llType.setOnClickListener(this);
        llPopup = (LinearLayout) mRootView.findViewById(R.id.ll_popup);
        llPopup.setOnClickListener(this);
        tv_sort = (TextView) mRootView.findViewById(R.id.tv_sort);
        iv_sort = (ImageView) mRootView.findViewById(R.id.iv_sort);
        iv_price_up = (ImageView) mRootView.findViewById(R.id.iv_price_up);
        iv_price_down = (ImageView) mRootView.findViewById(R.id.iv_price_down);
        tv_price = (TextView) mRootView.findViewById(R.id.tv_price);
        tv_store_type = (TextView) mRootView.findViewById(R.id.tv_store_type);
//        iv_store_type = (ImageView) mRootView.findViewById(R.id.iv_store_type);
        sts_price_up = (ImageView) mRootView.findViewById(R.id.sts_price_up);
        sts_price_down = (ImageView) mRootView.findViewById(R.id.sts_price_down);
        initPtr();
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
                pageNo = 1;
                loadLazyData();
            }
        });
    }


    private void initRecylerView() {
        if (homeRecylerAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyler = ((RecyclerView) mRootView.findViewById(R.id.storeRecyler));
            layoutManager.setAutoMeasureEnabled(true);
            recyler.setNestedScrollingEnabled(false);
            recyler.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            homeRecylerAdapter = new StoreRecylerAdapter(R.layout.fragment_store_recyler_item, mDatas);
            homeRecylerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    isUpDate = false;
                    String batchType = mDatas.get(position).batchType;
                    String productId = mDatas.get(position).productId;
                    String mobileBaojia = mDatas.get(position).mobile;
                    String code = mDatas.get(position).code;
                    addHistory(productId, batchType, code, mobileMy);
                    if (batchType.equals("1")) {
                        Intent intent = new Intent(getActivity(), StoreKunActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("productId", productId);
                        startActivity(intent);
                    } else if (batchType.equals("2")) {
                        Intent intent = new Intent(getActivity(), StoreBatchActivity.class);
                        intent.putExtra("productId", productId);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("fromKun", "0");
                        intent.putExtra("code", code);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), StoreBatchActivity.class);
                        intent.putExtra("productId", productId);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("fromKun", "1");
                        intent.putExtra("code", code);
                        startActivity(intent);
                    }
                }
            });
            View getEmptyview = getLayoutInflater(getArguments()).inflate(R.layout.nodata_quality, (ViewGroup) recyler.getParent(), false);
            homeRecylerAdapter.setEmptyView(getEmptyview);
            recyler.setAdapter(homeRecylerAdapter);
        } else {
            homeRecylerAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mobileMy = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile");
    }

    private void upDataSort() {
        tv_sort.setText("排序");
        iv_sort.setImageResource(R.mipmap.ic_common_downblack);
        tv_sort.setTextColor(getResources().getColor(R.color.drop_color));

        sts_price_down.setImageResource(R.mipmap.ic_common_downblack);
        sts_price_up.setImageResource(R.mipmap.ic_common_upblack);
        tv_store_type.setTextColor(getResources().getColor(R.color.drop_color));

        tv_price.setTextColor(getResources().getColor(R.color.drop_color));
        iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
        iv_price_up.setImageResource(R.mipmap.ic_common_upblack);

        tv_shaixuan.setTextColor(getResources().getColor(R.color.drop_color));
        imageView.setImageResource(R.mipmap.ic_store_funnel);
        sortType = 0;
        typeType = 0;
        bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
        keyword_group.clearSelected();
        origin_group.clearSelected();
        color_group.clearSelected();
        date_group.clearSelected();
//        type_group.clearSelected();
        bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
        bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
        bt_a.setTextColor(getResources().getColor(R.color.drop_color));
        bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
        bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
        tv_house.setText("3.4-5.0");
        tv_elasticity.setText("24-31");
        tv_length.setText("25-32");
        tv_trash.setText("0-5");
        tv_moisture.setText("0-10");
        tv_location.setText("请选择仓库名称");
        et_contact.getText().clear();
        hashMap.clear();
        hashMap.put("deviceNo", Common.deviceNo);
        hashMap.put("from", Common.from);
        hashMap.put("sourceTypeEnum", Common.sourceTypeEnum);
        hashMap.put("versionNo", Common.versionNo);
        hashMap.put("searchType", "2");
        hashMap.put("orderType", "1");
        loadLazyData();
    }

    private void addHistory(String productId, String batchType, String code, String mobileMy) {
        Map<String, String> mapHistory = new HashMap<>();
        mapHistory.put("isProduct", "1");
        mapHistory.put("productId", productId);
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
        isLogin = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isLogin");
        eMUserName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMUserName");
        eMPassword = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMPassword");
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

    private void loadLazyData() {
        mPtrrame.refreshComplete();
        hashMap.put("pageNum", pageNo + "");
        showProgressBar("请求数据中.......", true);
        HttpManager.getServerApi().getStoreList(hashMap).enqueue(new CallBack<StoreBean>() {
            @Override
            public void success(StoreBean data) {
                dismissProgressBar();
                if (data.success) {
                    if (mDatas != null) {
                        if (pageNo == 1) {
                            mDatas.clear();
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

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        isVisible = false;
    }

    private void showDistance(final int i) {
        View conentView = inflater.inflate(R.layout.popup_store_down, null);
        initPopview(conentView);
        popDistance = new PopWindow(getActivity(), conentView);
        popDistance.showAsDropDown(popDistance, llDropDown, 0, 0);
  /*      if (Build.VERSION.SDK_INT < 24) {
            popDistance.showAsDropDown(llDropDown, 0, 0);
        } else {
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            llDropDown.getLocationOnScreen(location);
            popDistance.showAtLocation(llDropDown, Gravity.NO_GRAVITY, 0, location[1] + llDropDown.getHeight() + 0);
        }*/
        popDistance.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tv_sort.getText().toString().equals("排序")) {
                    iv_sort.setImageResource(R.mipmap.ic_common_downblack);
                    tv_sort.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    iv_sort.setImageResource(R.mipmap.ic_common_downred);
                    tv_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                }
            }
        });
        switch (sortType) {
            case 1:
                tvZhineng.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                ivZhineng.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvZhineng.setTextColor(getResources().getColor(R.color.drop_color));
                ivZhineng.setVisibility(View.GONE);
                break;
            case 3:
                tvZhineng.setTextColor(getResources().getColor(R.color.drop_color));
                ivZhineng.setVisibility(View.GONE);
                break;
        }
    }

    private void initPopview(View conentView) {
        FrameLayout afl_dismiss = (FrameLayout) conentView.findViewById(R.id.afl_dismiss);
        afl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDistance.dismiss();
                isShowSort = false;
            }
        });
        FrameLayout fl_dismiss = (FrameLayout) conentView.findViewById(R.id.fl_dismiss);
        fl_dismiss.setAlpha(0.3f);
        fl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDistance.dismiss();
                isShowSort = false;
            }
        });
        tvZhineng = (TextView) conentView.findViewById(R.id.tv_zhineng);

        ivZhineng = (ImageView) conentView.findViewById(R.id.iv_zhineng);

        ll_zhineng = (LinearLayout) conentView.findViewById(R.id.ll_zhineng);
        ll_zhineng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select:
                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View conentView = inflater.inflate(R.layout.popup_entrepot, null);
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
                popWindowList.showAtLocation(rl_keyword, Gravity.TOP, 0, 0);
                break;
            case R.id.ll_search:
                isShowTypePop = false;
                isShowSort = false;
                startActivity(new Intent(BaseApplication.getContextObject(), SearchActivity.class));
                break;
            case R.id.btSelectRset://重置
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
                keyword_group.clearSelected();
                origin_group.clearSelected();
                color_group.clearSelected();
                date_group.clearSelected();
//                type_group.clearSelected();
                tv_house.setText("3.4-5.0");
                doubleseekbar_house.setValues(34, 50);
                tv_length.setText("25-32");
                doubleseekbar_length.setValues(25, 32);
                tv_elasticity.setText("24-31");
                doubleseekbar_elasticity.setValues(24, 31);
                tv_trash.setText("0-5");
                doubleseekbar_trash.setValues(0, 5);
                tv_moisture.setText("0-10");
                doubleSeekBar_moisture.setValues(0, 10);
                tv_location.setText("请选择仓库名称");
                et_contact.getText().clear();
                break;
            case R.id.btSelectSure://确定
                String etContact = et_contact.getText().toString().trim();
                if (!TextUtils.isEmpty(etContact)){
                    hashMap.put("contact",etContact);
                }
                List<String> selectedkeyword = keyword_group.getSelectedList();
                keyword_group.commit();
                List<String> keyValue = new ArrayList<>();
          /*      List<String> selectedtype = type_group.getSelectedList();
                type_group.commit();*/
                List<String> typeValue = new ArrayList<>();
                List<String> selectedcolor = color_group.getSelectedList();
                color_group.commit();
                List<String> colorValue = new ArrayList<>();
                List<String> selectedorigin = origin_group.getSelectedList();
                origin_group.commit();
                List<String> originValue = new ArrayList<>();
                List<String> selecteddate = date_group.getSelectedList();
                date_group.commit();
                List<String> dateValue = new ArrayList<>();
                if (selectedkeyword.size() < 1) {
                    hashMap.put("keywords", "");
                } else {
                    for (String temp : selectedkeyword) {
                        String s = mapKeyword.get(temp);
                        keyValue.add(s);
                    }
                    hashMap.put("keywords", ListToListString.getString(keyValue));
                }
           /*     if (selectedtype.size() < 1) {
                    hashMap.put("type", "");
                } else {
                    for (String temp : selectedtype) {
                        String s = mapType.get(temp);
                        typeValue.add(s);
                    }
                    hashMap.put("type", ListToListString.getString(typeValue));
                }*/
                if (selectedcolor.size() < 1) {
                    hashMap.put("colorGradeName", "");
                } else {
                    for (String temp : selectedcolor) {
                        String s = mapColor.get(temp);
                       /* if (s.equals("白棉1级")) {
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
                        }*/
                        colorValue.add(s);
                    }
                    hashMap.put("colorGradeName", ListToListString.getString(colorValue));
                }
                if (selecteddate.size() < 1) {
                    hashMap.put("createYear", "");
                } else {
                    for (String temp : selecteddate) {
                        String s = mapDate.get(temp);
                        dateValue.add(s);
                    }
                    hashMap.put("createYear", ListToListString.getString(dateValue));
                }
                if (selectedorigin.size() < 1) {
                    hashMap.put("origin", "");
                } else {
                    for (String temp : selectedorigin) {
                        String s = mapLocation.get(temp);
                        originValue.add(s);
                    }
                    hashMap.put("origin", ListToListString.getString(originValue));
                }
                String elasticityStr = tv_elasticity.getText().toString();
                if (elasticityStr.equals("24及以下")) {
                    hashMap.put("breakLoadAverage", "[" + "24" + "," + "24" + "]");
                } else if (elasticityStr.equals("31及以上")) {
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
                hashMap.put("trash", "[" + trash[0] + "," + trash[1] + "]");
                hashMap.put("moisture", "[" + moisture[0] + moisture[1] + "]");
                if (!tv_location.getText().toString().equals("请选择仓库名称")) {
                    String[] split = tv_location.getText().toString().split("");
                    hashMap.put("location", split[0]);
                    hashMap.put("storage", split[1]);
                }
                loadLazyData();
                drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
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
            case R.id.rl_keyword:
                if (keyword_group.isShowMyLayout()) {
                    keyword_group.hideMyLayout();
                    iv_black_keyword.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    iv_black_keyword.setImageResource(R.mipmap.ic_sort_down);
                    keyword_group.showMyLayout();
                }
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
     /*       case R.id.rl_type:
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
            case R.id.rl_date:
                if (date_group.isShowMyLayout()) {
                    date_group.hideMyLayout();
                    iv_date.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    iv_date.setImageResource(R.mipmap.ic_sort_down);
                    date_group.showMyLayout();
                }
                break;
            case R.id.rl_contact:
                if (isShowContanc) {
                    isShowContanc = false;
                    et_contact.setVisibility(View.GONE);
                    iv_contact.setImageResource(R.mipmap.ic_sort_up);
                }else{
                    isShowContanc  = true;
                    et_contact.setVisibility(View.VISIBLE);
                    iv_contact.setImageResource(R.mipmap.ic_sort_down);
                }
                break;
            case R.id.ll_drop_down:
                isShowTypePop = false;
                if (isShowSort == false) {
                    isShowSort = true;
                    showDistance(sortType);
                    tv_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    iv_sort.setImageResource(R.mipmap.ic_common_upred);
                   /* if (sortType == 0) {
                        iv_sort.setImageResource(R.mipmap.ic_common_upblack);
                    } else {
                        iv_sort.setImageResource(R.mipmap.ic_common_upred);
                    }*/
                } else {
                    isShowSort = false;
                    popDistance.dismiss();
                }

                break;
            case R.id.ll_price:
                sts_price_down.setImageResource(R.mipmap.ic_common_downblack);
                sts_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_store_type.setTextColor(getResources().getColor(R.color.drop_color));
                pageNo = 1;
                isShowSort = false;
                isShowTypePop = false;
                sortType = 0;
                iv_sort.setImageResource(R.mipmap.ic_common_downblack);
                tv_sort.setText("排序");
                tv_sort.setTextColor(getResources().getColor(R.color.drop_color));
                iv_price_up.setImageResource(imageUp[++count % imageUp.length]);
                iv_price_down.setImageResource(imageDown[++num % imageDown.length]);
                tv_price.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                if (count % 2 == 0) {
                    hashMap.put("orderType", "19");
                    loadLazyData();
                } else {
                    hashMap.put("orderType", "18");
                    loadLazyData();
                }
                break;
            case R.id.ll_type:
                pageNo = 1;
                isShowSort = false;
                isShowTypePop = false;
                sortType = 0;
                iv_sort.setImageResource(R.mipmap.ic_common_downblack);
                tv_sort.setText("排序");
                tv_sort.setTextColor(getResources().getColor(R.color.drop_color));
                sts_price_up.setImageResource(imageUpSts[++countsts % imageUp.length]);
                sts_price_down.setImageResource(imageDownSts[++numsts % imageDown.length]);
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);
                if (countsts % 2 == 0) {
                    hashMap.put("orderType", "21");
                    loadLazyData();
                } else {
                    hashMap.put("orderType", "20");
                    loadLazyData();
                }
                break;
    /*        case R.id.ll_type:
                isShowSort = false;
                if (isShowTypePop == false) {
                    isShowTypePop = true;
                    showPopType(typeType);
                    iv_store_type.setImageResource(R.mipmap.ic_common_upred);
                    tv_store_type.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                *//*    if (typeType == 0) {
                        iv_store_type.setImageResource(R.mipmap.ic_common_upblack);
                    } else {
                        iv_store_type.setImageResource(R.mipmap.ic_common_upred);
                    }*//*
                } else {
                    popupType.dismiss();
                    isShowTypePop = false;
                }

                break;*/
            case R.id.ll_popup:
                isShowSort = false;
                isShowTypePop = false;
                ((MainActivity) getActivity()).mTabLayout.setVisibility(View.GONE);
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.ll_zhineng:
                sts_price_down.setImageResource(R.mipmap.ic_common_downblack);
                sts_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_store_type.setTextColor(getResources().getColor(R.color.drop_color));
                isShowSort = false;
                pageNo = 1;
                sortType = 1;
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_sort.setText("智能排序");
                tv_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_sort.setImageResource(R.mipmap.ic_common_downred);
                hashMap.put("orderType", "1");
                loadLazyData();
                popDistance.dismiss();
                break;
      /*      case R.id.ll_JtoY:
        *//*        pageNo = 1;
                isShowSort = false;
                tv_sort.setText("距离从近");
                sts_price_down.setImageResource(R.mipmap.ic_common_downblack);
                sts_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_store_type.setTextColor(getResources().getColor(R.color.drop_color));
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_sort.setImageResource(R.mipmap.ic_common_downred);
                sortType = 2;
                hashMap.put("orderType", "2");
                loadLazyData();
                popDistance.dismiss();*//*
                break;*/
      /*      case R.id.ll_YtoJ:
                pageNo = 1;
                isShowSort = false;
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);
                sts_price_down.setImageResource(R.mipmap.ic_common_downblack);
                sts_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_store_type.setTextColor(getResources().getColor(R.color.drop_color));
                tv_sort.setText("距离从远");
                tv_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_sort.setImageResource(R.mipmap.ic_common_downred);
                sortType = 3;
                hashMap.put("orderType", "7");
                loadLazyData();
                popDistance.dismiss();
                break;*/
            case R.id.ll_myself:
                pageNo = 1;
                sortType = 0;
                isShowTypePop = false;
             /*   iv_sort.setImageResource(R.mipmap.ic_common_downblack);
                tv_sort.setText("排序");
                tv_sort.setTextColor(getResources().getColor(R.color.drop_color));
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);*/
                tv_store_type.setText("2017新棉");
//                iv_store_type.setImageResource(R.mipmap.ic_common_downred);
                tv_store_type.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 1;
                hashMap.remove("keywords");
                hashMap.put("keywords", "2017新棉");
                loadLazyData();
                popupType.dismiss();
                break;
            case R.id.ll_recevi:
                isShowTypePop = false;
                pageNo = 1;
                sortType = 0;
       /*         iv_sort.setImageResource(R.mipmap.ic_common_downblack);
                tv_sort.setText("排序");
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_sort.setTextColor(getResources().getColor(R.color.drop_color));
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));*/
                tv_store_type.setText("国储棉");
//                iv_store_type.setImageResource(R.mipmap.ic_common_downred);
                tv_store_type.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 2;
                hashMap.remove("keywords");
                hashMap.put("keywords", "国储棉");
                loadLazyData();
                popupType.dismiss();
                break;
            case R.id.ll_3128B:
                sortType = 0;
                pageNo = 1;
                isShowTypePop = false;
           /*     iv_sort.setImageResource(R.mipmap.ic_common_downblack);
                tv_sort.setText("排序");
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_sort.setTextColor(getResources().getColor(R.color.drop_color));
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));*/
                tv_store_type.setText("3128B");
//                iv_store_type.setImageResource(R.mipmap.ic_common_downred);
                tv_store_type.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 3;
                hashMap.remove("keywords");
                hashMap.put("keywords", "3128B");
                loadLazyData();
                popupType.dismiss();
                break;
            case R.id.ll_double28:
                sortType = 0;
                pageNo = 1;
                isShowTypePop = false;
           /*     iv_sort.setImageResource(R.mipmap.ic_common_downblack);
                tv_sort.setText("排序");
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_sort.setTextColor(getResources().getColor(R.color.drop_color));
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));*/
                tv_store_type.setText("双28");
//                iv_store_type.setImageResource(R.mipmap.ic_common_downred);
                tv_store_type.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 4;
                hashMap.remove("keywords");
                hashMap.put("keywords", "双28");
                loadLazyData();
                popupType.dismiss();
                break;
            case R.id.ll_double29:
                pageNo = 1;
                sortType = 0;
                isShowTypePop = false;
          /*      iv_sort.setImageResource(R.mipmap.ic_common_downblack);
                tv_sort.setText("排序");
                iv_price_down.setImageResource(R.mipmap.ic_common_downblack);
                iv_price_up.setImageResource(R.mipmap.ic_common_upblack);
                tv_sort.setTextColor(getResources().getColor(R.color.drop_color));
                tv_price.setTextColor(getResources().getColor(R.color.drop_color));*/
                tv_store_type.setText("双29");
//                iv_store_type.setImageResource(R.mipmap.ic_common_downred);
                tv_store_type.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 5;
                hashMap.remove("keywords");
                hashMap.put("keywords", "双29");
                loadLazyData();
                popupType.dismiss();
                break;
        }
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
                tv_location.setText(LISTVIEWTXT[mainAdapter.getSelectItem()] + " " + moreAdapter.getItem(position));
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

    private void showPopType(int typeType) {
        View conentView = inflater.inflate(R.layout.popup_store_type, null);
        popupType = new PopWindow(getActivity(), conentView);
        initType(conentView);
        if (Build.VERSION.SDK_INT < 24) {
            popupType.showAsDropDown(llType, 0, 0);
        } else {
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            llType.getLocationOnScreen(location);
            popupType.showAtLocation(llType, Gravity.NO_GRAVITY, 0, location[1] + llType.getHeight() + 0);
        }
        popupType.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tv_store_type.getText().toString().equals("关键字")) {
//                    iv_store_type.setImageResource(R.mipmap.ic_common_downblack);
                    tv_store_type.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
//                    iv_store_type.setImageResource(R.mipmap.ic_common_downred);
                    tv_store_type.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                }
            }
        });
        switch (typeType) {
            case 1:
                tv_myself.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_myself.setVisibility(View.VISIBLE);

                tv_recevi.setTextColor(getResources().getColor(R.color.drop_color));
                iv_recevi.setVisibility(View.GONE);
                tv_3128B.setTextColor(getResources().getColor(R.color.drop_color));
                iv_3128B.setVisibility(View.GONE);
                tv_double28.setTextColor(getResources().getColor(R.color.drop_color));
                iv_double28.setVisibility(View.GONE);
                tv_double29.setTextColor(getResources().getColor(R.color.drop_color));
                iv_double29.setVisibility(View.GONE);
                break;
            case 2:
                tv_myself.setTextColor(getResources().getColor(R.color.drop_color));
                iv_myself.setVisibility(View.GONE);

                tv_recevi.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_recevi.setVisibility(View.VISIBLE);
                tv_3128B.setTextColor(getResources().getColor(R.color.drop_color));
                iv_3128B.setVisibility(View.GONE);
                tv_double28.setTextColor(getResources().getColor(R.color.drop_color));
                iv_double28.setVisibility(View.GONE);
                tv_double29.setTextColor(getResources().getColor(R.color.drop_color));
                iv_double29.setVisibility(View.GONE);
                break;
            case 3:
                tv_myself.setTextColor(getResources().getColor(R.color.drop_color));
                iv_myself.setVisibility(View.GONE);

                tv_recevi.setTextColor(getResources().getColor(R.color.drop_color));
                iv_recevi.setVisibility(View.GONE);
                tv_3128B.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_3128B.setVisibility(View.VISIBLE);
                tv_double28.setTextColor(getResources().getColor(R.color.drop_color));
                iv_double28.setVisibility(View.GONE);
                tv_double29.setTextColor(getResources().getColor(R.color.drop_color));
                iv_double29.setVisibility(View.GONE);
                break;
            case 4:
                tv_myself.setTextColor(getResources().getColor(R.color.drop_color));
                iv_myself.setVisibility(View.GONE);
                tv_recevi.setTextColor(getResources().getColor(R.color.drop_color));
                iv_recevi.setVisibility(View.GONE);
                tv_3128B.setTextColor(getResources().getColor(R.color.drop_color));
                iv_3128B.setVisibility(View.GONE);
                tv_double28.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_double28.setVisibility(View.VISIBLE);
                tv_double29.setTextColor(getResources().getColor(R.color.drop_color));
                iv_double29.setVisibility(View.GONE);
                break;
            case 5:
                tv_myself.setTextColor(getResources().getColor(R.color.drop_color));
                iv_myself.setVisibility(View.GONE);
                tv_recevi.setTextColor(getResources().getColor(R.color.drop_color));
                iv_recevi.setVisibility(View.GONE);
                tv_3128B.setTextColor(getResources().getColor(R.color.drop_color));
                iv_3128B.setVisibility(View.GONE);
                tv_double28.setTextColor(getResources().getColor(R.color.drop_color));
                iv_double28.setVisibility(View.GONE);
                tv_double29.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_double29.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initType(View conentView) {
        FrameLayout afl_dismiss = (FrameLayout) conentView.findViewById(R.id.afl_dismiss);
        afl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupType.dismiss();
                isShowTypePop = false;
            }
        });
        FrameLayout fl_dismiss = (FrameLayout) conentView.findViewById(R.id.fl_dismiss);
        fl_dismiss.setAlpha(0.3f);
        fl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupType.dismiss();
                isShowTypePop = false;
            }
        });
        ll_myself = (LinearLayout) conentView.findViewById(R.id.ll_myself);
        ll_myself.setOnClickListener(this);
        ll_recevi = (LinearLayout) conentView.findViewById(R.id.ll_recevi);
        ll_recevi.setOnClickListener(this);
        tv_myself = (TextView) conentView.findViewById(R.id.tv_myself);
        tv_recevi = (TextView) conentView.findViewById(R.id.tv_recevi);
        iv_myself = (ImageView) conentView.findViewById(R.id.iv_myself);
        iv_recevi = (ImageView) conentView.findViewById(R.id.iv_recevi);
        LinearLayout ll_3128B = (LinearLayout) conentView.findViewById(R.id.ll_3128B);
        ll_3128B.setOnClickListener(this);
        LinearLayout ll_double28 = (LinearLayout) conentView.findViewById(R.id.ll_double28);
        ll_double28.setOnClickListener(this);
        LinearLayout ll_double29 = (LinearLayout) conentView.findViewById(R.id.ll_double29);
        ll_double29.setOnClickListener(this);
        tv_3128B = (TextView) conentView.findViewById(R.id.tv_3128B);
        tv_double28 = (TextView) conentView.findViewById(R.id.tv_double28);
        tv_double29 = (TextView) conentView.findViewById(R.id.tv_double29);
        iv_3128B = (ImageView) conentView.findViewById(R.id.iv_3128B);
        iv_double28 = (ImageView) conentView.findViewById(R.id.iv_double28);
        iv_double29 = (ImageView) conentView.findViewById(R.id.iv_double29);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (drawerLayout.isDrawerOpen(ll_drawer)) {
//            drawerLayout.closeDrawer(Gravity.RIGHT);
        }
    }

    @Override
    public boolean onBackPressed() {
        //监听fragment的返回事件 如果drawerlayout没关闭 则关闭
        if (drawerLayout.isDrawerOpen(ll_drawer)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
            ;
            return true;
        }
        if (popupType != null && popupType.isShowing()) {
            popupType.dismiss();
            return true;
        }
        if (popDistance != null && popDistance.isShowing()) {
            popDistance.dismiss();
            return true;
        }
        return BackHandlerHelper.handleBackPress(this);
    }

    public void ishaveData() {
        List<String> selectedkeyword = keyword_group.getSelectedList();
//        List<String> selectedtype = type_group.getSelectedList();
        List<String> selectedcolor = color_group.getSelectedList();
        List<String> selectedorigin = origin_group.getSelectedList();
        List<String> selecteddate = date_group.getSelectedList();
        if (TextUtils.isEmpty(tv_location.getText().toString().trim())&&TextUtils.isEmpty(et_contact.getText().toString().trim())&&selectedkeyword.size() < 1 && selectedcolor.size() < 1 && selectedorigin.size() < 1 && selecteddate.size() < 1 && tv_house.getText().equals("3.4-5.0") && tv_elasticity.getText().equals("24-31") && tv_length.getText().equals("25-32") && tv_trash.getText().equals("0-5") && tv_moisture.getText().equals("0-10")) {
            tv_shaixuan.setTextColor(getResources().getColor(R.color.drop_color));
            imageView.setImageResource(R.mipmap.ic_store_funnel);
        } else {
            tv_shaixuan.setTextColor(getResources().getColor(R.color.tab_tv_selected));
            imageView.setImageResource(R.mipmap.ic_store_funnelred);
        }
    }
}