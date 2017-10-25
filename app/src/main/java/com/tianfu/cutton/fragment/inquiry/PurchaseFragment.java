package com.tianfu.cutton.fragment.inquiry;


import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.activity.purchase.PurchaseDetailsActivity;
import com.tianfu.cutton.activity.purchase.ReleasePurchaseActivity;
import com.tianfu.cutton.adapter.PurchaseRecylerAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.fragment.adapter.BackHandlerHelper;
import com.tianfu.cutton.fragment.adapter.FragmentBackHandler;
import com.tianfu.cutton.model.ListPurchaseOrder;
import com.tianfu.cutton.model.MicronAverage;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
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

import static com.tianfu.cutton.R.id.aflDissmiss;
import static com.tianfu.cutton.common.Common.versionNo;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseFragment extends BaseFragment implements View.OnClickListener, FragmentBackHandler {
    private View mRootView;
    private int pageNo = 1;
    private RecyclerView purchaseRecyler;
    private PurchaseRecylerAdapter adapter;
    private List<ListPurchaseOrder.ValueBean.RowsBean> mDatas;
    private PurchaseRecylerAdapter adapterPurchase;
    private LinearLayout ll_purchase_sort;
    private TextView tv_zonghe;
    private ImageView iv_zonghe;
    private TextView tv_purchase_sort;
    private PopWindow popWindow;
    private boolean isSeletct = false;
    private LinearLayout ll_purchase_price;
    private ImageView iv_price_up;
    private ImageView iv_price_down;
    private TextView tv_purchase_price;
    private ImageView iv_purchase_sort;
    private LinearLayout ll_purchase_origin;
    private MultiCheckGroupView originGroup;
    private PopWindow popOriginWindow;
    private ImageView iv_origin;
    private TextView tvOrigin;
    private boolean isOriginSsletct = false;
    private LinearLayout ll_showDrawer;
    private RelativeLayout ll_drawer;
    private DrawerLayout drawerLayout;
    private MultiCheckGroupView keyword_group;
    private MultiCheckGroupView type_group;
    private MultiCheckGroupView color_group;
    private ImageView iv_black_keyword;
    private ImageView iv_black_origin;
    private ImageView iv_black_color;
    private DoubleSeekBar doubleseekbar_house;
    private DoubleSeekBar doubleseekbar_elasticity;
    private DoubleSeekBar doubleseekbar_length;
    private TextView tv_house;
    private TextView tv_elasticity;
    private TextView tv_length;
    private Button bt_c1;
    private Button bt_c2;
    private Button bt_b1;
    private Button bt_a;
    private Button bt_b2;
    private HashMap<String, String> hashMap;
    private TextView tv_shaixuan;
    private ImageView iv_shaixuan;
    private PtrClassicFrameLayout mPtrrame;
    private Boolean isLogin;
    private Map<String, String> mapType;
    private Map<String, String> mapKeyword;
    private Map<String, String> mapColor;
    private TreeMap<String, String> mapLocation;
    private boolean isUpData = true;
    private boolean isShowSort = false;
    private boolean isShowType = false;

    public PurchaseFragment() {
        // Required empty public constructor
    }

    private int count = 1;
    private int num = 1;
    int imageUp[] = new int[]{
            R.mipmap.ic_common_upred,
            R.mipmap.ic_common_upblack
    };
    int imageDown[] = new int[]{
            R.mipmap.ic_common_downblack,
            R.mipmap.ic_common_downred
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_purchase, container, false);
            //默认排序
            hashMap = new HashMap<>();
            hashMap.put("deviceNo", Common.deviceNo);
            hashMap.put("from", Common.from);
            hashMap.put("version", versionNo);
            initView();
            initDrawerView();
            isUpData = false;
            return mRootView;
        }
    }

    private void initDrawerView() {
        Button btSelectRset = (Button) mRootView.findViewById(R.id.btSelectRset);
        btSelectRset.setOnClickListener(this);
        Button btSelectSure = (Button) mRootView.findViewById(R.id.btSelectSure);
        btSelectSure.setOnClickListener(this);

        RelativeLayout rl_keyword = (RelativeLayout) mRootView.findViewById(R.id.rl_keyword);
        rl_keyword.setOnClickListener(this);
        iv_black_keyword = (ImageView) mRootView.findViewById(R.id.iv_black_keyword);
        keyword_group = (MultiCheckGroupView) mRootView.findViewById(R.id.keyword_group);

        RelativeLayout rl_type = (RelativeLayout) mRootView.findViewById(R.id.rl_type);
        rl_type.setOnClickListener(this);
        iv_black_origin = (ImageView) mRootView.findViewById(R.id.iv_black_origin);
        type_group = (MultiCheckGroupView) mRootView.findViewById(R.id.type_group);

        RelativeLayout rl_color = (RelativeLayout) mRootView.findViewById(R.id.rl_color);
        rl_color.setOnClickListener(this);
        iv_black_color = (ImageView) mRootView.findViewById(R.id.iv_black_color);
        color_group = (MultiCheckGroupView) mRootView.findViewById(R.id.color_group);

        doubleseekbar_house = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_house);
        doubleseekbar_elasticity = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_elasticity);
        doubleseekbar_length = (DoubleSeekBar) mRootView.findViewById(R.id.doubleseekbar_length);

        tv_house = (TextView) mRootView.findViewById(R.id.tv_house);
        tv_elasticity = (TextView) mRootView.findViewById(R.id.tv_elasticity);
        tv_length = (TextView) mRootView.findViewById(R.id.tv_length);

        bt_c1 = (Button) mRootView.findViewById(R.id.bt_c1);
        bt_c1.setOnClickListener(this);
        bt_c2 = (Button) mRootView.findViewById(R.id.bt_c2);
        bt_c2.setOnClickListener(this);
        bt_b1 = (Button) mRootView.findViewById(R.id.bt_B1);
        bt_b1.setOnClickListener(this);
        bt_a = (Button) mRootView.findViewById(R.id.bt_A);
        bt_a.setOnClickListener(this);
        bt_b2 = (Button) mRootView.findViewById(R.id.bt_B2);
        bt_b2.setOnClickListener(this);

        doubleseekbar_house.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
          /*      bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                tv_house.setText(lowVal "-" + highValue);*/
                tv_house.setText(lowValue + "-" + highValue);
                if ((lowValue+"").equals("3.5")&&(highValue+"").equals("3.6")){
                    bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    bt_b1.setTextColor(getResources().getColor(R.color.white));
                }else if ((lowValue+"").equals("3.7")&&(highValue+"").equals("4.2")){
                    bt_a.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    bt_a.setTextColor(getResources().getColor(R.color.white));
                }else if ((lowValue+"").equals("4.3")&&(highValue+"").equals("4.9")){
                    bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                    bt_b2.setTextColor(getResources().getColor(R.color.white));
                }else if (lowValue == highValue) {
                    if ((highValue+"").equals("3.4")) {
                        bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                        bt_c1.setTextColor(getResources().getColor(R.color.white));
                        tv_house.setText("3.4及以下");
                    } else if (lowValue == 5.0) {
                        tv_house.setText("5.0及以上");
                        bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                        bt_c2.setTextColor(getResources().getColor(R.color.white));
                    }
                }else {
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
                if ((int) lowValue== (int) highValue){
                    if ((int) lowValue==25){
                        tv_length.setText("25以下");
                    }else if ((int) lowValue==32){
                        tv_length.setText("32以上");
                    }
                }
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
                    String min = split1[0]+split1[1];
                    String max = split2[0]+split2[1];
                    doubleseekbar_house.setValues(Integer.valueOf(min),Integer.valueOf(max));
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
         /*       String[] split = tv_elasticity.getText().toString().split("-");
                String[] split1 = tv_length.getText().toString().split("-");
                doubleseekbar_elasticity.setValues(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
                doubleseekbar_length.setValues(Integer.valueOf(split1[0]), Integer.valueOf(split1[1]));*/

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                ((MainActivity) getActivity()).mTabLayout.setVisibility(View.VISIBLE);
                ishaveData();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mapKeyword = new TreeMap<>();
        mapKeyword.put("0", "双28");
        mapKeyword.put("1", "双29");
        mapKeyword.put("2", "超低价");
        mapKeyword.put("3", "3128B");
        mapKeyword.put("4", "兵团棉");
        mapKeyword.put("5", "无三丝");
        mapKeyword.put("6", "手摘棉");
        mapKeyword.put("7", "机采棉");
        keyword_group.addValues(mapKeyword);


        mapType = new TreeMap<>();
        mapType.put("0", "锯齿细绒棉");
        mapType.put("1", "锯齿机采棉");
        mapType.put("2", "皮辊细绒棉");
        type_group.addValues(mapType);

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

    private void ishaveData() {
        List<String> selectedList = keyword_group.getSelectedList();
        List<String> selectedList1 = type_group.getSelectedList();
        List<String> selectedList2 = color_group.getSelectedList();
        if (selectedList.size() < 1 && selectedList1.size() < 1 && selectedList2.size() < 1 && tv_house.getText().toString().equals("3.4-5.0") && tv_elasticity.getText().toString().equals("24-31") && tv_length.getText().toString().equals("25-32")) {
            iv_shaixuan.setImageResource(R.mipmap.ic_store_funnel);
            tv_shaixuan.setTextColor(getResources().getColor(R.color.drop_color));
        } else {
            iv_shaixuan.setImageResource(R.mipmap.ic_store_funnelred);
            tv_shaixuan.setTextColor(getResources().getColor(R.color.tab_tv_selected));
        }
    }

    //todo 如果需要请求网络 建议采用懒加载模式，只有页面可见时，才去请求网络。防止每次切换tab时，都去请求2-3次网络。这里用toast模拟请求网络
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
        isUpData = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        isLogin = SharedPreferencesUtil.getBooleanValue(getActivity(), "isLogin");
    }

    protected void onVisible() {
        if (!isVisible || !isPrepared) {
            return;
        }
        if (isUpData) {
            upDataSort();
        }
        showData(hashMap);
    }

    private void upDataSort() {
        tv_purchase_sort.setText("排序");
        isOriginSsletct = false;
        isSeletct = false;
        tv_purchase_sort.setTextColor(getResources().getColor(R.color.drop_color));
        iv_purchase_sort.setImageResource(R.mipmap.ic_common_downblack);
        tv_purchase_price.setTextColor(getResources().getColor(R.color.drop_color));
        iv_price_up.setImageResource(imageUp[1]);
        iv_price_down.setImageResource(imageDown[0]);
        tvOrigin.setTextColor(getResources().getColor(R.color.drop_color));
        iv_origin.setImageResource(R.mipmap.ic_common_downblack);
        if (originGroup != null) {
            originGroup.clearFixed();
        }
        if (keyword_group != null) {
            keyword_group.clearSelected();
        }
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
        type_group.clearSelected();
        color_group.clearSelected();
        iv_shaixuan.setImageResource(R.mipmap.ic_store_funnel);
        tv_shaixuan.setTextColor(getResources().getColor(R.color.drop_color));

        tv_house.setText("3.4-5.0");
        tv_elasticity.setText("24-31");
        tv_length.setText("25-32");
        hashMap.clear();
        hashMap.put("deviceNo", Common.deviceNo);
        hashMap.put("from", Common.from);
        hashMap.put("version", versionNo);
        showData(hashMap);
    }

    private void initView() {
        mPtrrame = (PtrClassicFrameLayout) mRootView.findViewById(R.id.ptr_purchase_frame);
        initPtr();
        tv_shaixuan = (TextView) mRootView.findViewById(R.id.tv_shaixuan);
        iv_shaixuan = (ImageView) mRootView.findViewById(R.id.iv_shaixuan);
        drawerLayout = (DrawerLayout) mRootView.findViewById(R.id.drawerLayout);
        ll_showDrawer = (LinearLayout) mRootView.findViewById(R.id.ll_showDrawer);
        ll_drawer = (RelativeLayout) mRootView.findViewById(R.id.ll_drawer);
        ll_showDrawer.setOnClickListener(this);
        TextView releasePurase = (TextView) mRootView.findViewById(R.id.releasePurase);
        releasePurase.setOnClickListener(this);
        ll_purchase_sort = (LinearLayout) mRootView.findViewById(R.id.ll_purchase_sort);
        ll_purchase_sort.setOnClickListener(this);
        iv_purchase_sort = (ImageView) mRootView.findViewById(R.id.iv_purchase_sort);
        tv_purchase_sort = (TextView) mRootView.findViewById(R.id.tv_purchase_sort);
        ll_purchase_price = (LinearLayout) mRootView.findViewById(R.id.ll_purchase_price);
        ll_purchase_price.setOnClickListener(this);
        iv_price_up = (ImageView) mRootView.findViewById(R.id.iv_price_up);
        iv_price_down = (ImageView) mRootView.findViewById(R.id.iv_price_down);
        iv_price_up.setImageResource(imageUp[1]);
        iv_price_down.setImageResource(imageDown[0]);
        tv_purchase_price = (TextView) mRootView.findViewById(R.id.tv_purchase_price);
        ll_purchase_origin = (LinearLayout) mRootView.findViewById(R.id.ll_purchase_origin);
        ll_purchase_origin.setOnClickListener(this);
        iv_origin = (ImageView) mRootView.findViewById(R.id.iv_origin);
        tvOrigin = (TextView) mRootView.findViewById(R.id.tvOrigin);
        RelativeLayout close = (RelativeLayout) mRootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowType = false;
                isShowSort = false;
            }
        });
    }

    private void initPtr() {
        mPtrrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo++;
                showData(hashMap);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //下拉刷新
//                Toast.makeText(getActivity(), "下啦", Toast.LENGTH_SHORT).show();
                pageNo = 1;
                showData(hashMap);
            }
        });
    }

    private void showData(HashMap<String, String> hashMap) {
        mPtrrame.refreshComplete();
        hashMap.put("pageNum", pageNo + "");
        showProgressBar("请求数据中.......", true);
        HttpManager.getServerApi().listPurchaseOrder(hashMap).enqueue(new CallBack<ListPurchaseOrder>() {
            @Override
            public void success(ListPurchaseOrder data) {
                dismissProgressBar();
                if (data.success) {
                    if (mDatas != null) {
                        if (pageNo == 1) {
                            mDatas.clear();
                            System.out.println(" mDatas.clear();");
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
//                ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
            }
        });


    }


    private void initRecylerView() {
        if (adapterPurchase == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            purchaseRecyler = ((RecyclerView) mRootView.findViewById(R.id.purchaseRecyler));
            purchaseRecyler.setLayoutManager(layoutManager);
            adapterPurchase = new PurchaseRecylerAdapter(R.layout.fragment_purchase_recyler_item, mDatas);
            adapterPurchase.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    isUpData = false;
                    Intent intent = new Intent();
                    ListPurchaseOrder.ValueBean.RowsBean rowsBean = mDatas.get(position);
                    intent.putExtra("RowsBeanPurchase", rowsBean);
                    intent.setClass(getActivity(), PurchaseDetailsActivity.class);
                    startActivity(intent);
                }
            });
            View getEmptyview = getLayoutInflater(getArguments()).inflate(R.layout.nodata_main_purchase, (ViewGroup) purchaseRecyler.getParent(), false);
            adapterPurchase.setEmptyView(getEmptyview);
            purchaseRecyler.setAdapter(adapterPurchase);
        } else {
            adapterPurchase.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_purchase_sort:
                if (!isShowSort) {
                    isShowSort = true;
                    iv_purchase_sort.setImageResource(R.mipmap.ic_common_upred);
                    tv_purchase_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                    showPopup();
                } else {
                    isShowSort = false;
                }
                isShowType = false;
                break;
            case R.id.ll_sort:
           /*     if (originGroup != null) {
                    originGroup.clearFixed();
                }
                isOriginSsletct = false;
                tvOrigin.setTextColor(getResources().getColor(R.color.drop_color));
                iv_origin.setImageResource(R.mipmap.ic_common_downblack);*/
                if (isSeletct == false) {
                    tv_purchase_sort.setText("综合排序");
                    tv_purchase_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
//                    hashMap.remove("origin");
                    hashMap.remove("sort");
                    hashMap.put("sort", "IdDesc");
                    showData(hashMap);
                    tv_purchase_price.setTextColor(getResources().getColor(R.color.drop_color));
                    iv_price_up.setImageResource(imageUp[1]);
                    iv_price_down.setImageResource(imageDown[0]);
                }
                popWindow.dismiss();
                isShowSort = false;
                iv_purchase_sort.setImageResource(R.mipmap.ic_common_downred);
                isSeletct = true;
                break;
            case R.id.ll_purchase_price:
                isShowSort = false;
                isShowType = false;
              /*  if (originGroup != null) {
                    originGroup.clearFixed();
                }
                isOriginSsletct = false;
                tvOrigin.setTextColor(getResources().getColor(R.color.drop_color));
                iv_origin.setImageResource(R.mipmap.ic_common_downblack);*/
                tv_purchase_price.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                iv_price_up.setImageResource(imageUp[++count % imageUp.length]);
                iv_price_down.setImageResource(imageDown[++num % imageDown.length]);
                iv_purchase_sort.setImageResource(R.mipmap.ic_common_downblack);
                tv_purchase_sort.setText("排序");
                tv_purchase_sort.setTextColor(getResources().getColor(R.color.drop_color));
                hashMap.remove("sort");
                if (count % 2 == 0) {
                    hashMap.remove("origin");
                    hashMap.remove("sort");
                    hashMap.put("sort", "PriceAsc");

                } else {
                    hashMap.remove("origin");
                    hashMap.remove("sort");
                    hashMap.put("sort", "PriceDesc");
                }

                showData(hashMap);
                isSeletct = false;
                break;
            case R.id.ll_purchase_origin:
                isShowSort = false;
                if (!isShowType) {
                    isShowType = true;
                    iv_origin.setImageResource(R.mipmap.ic_common_upred);
                    tvOrigin.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                 /*   if (isOriginSsletct) {
                        iv_origin.setImageResource(R.mipmap.ic_common_upred);
                    } else {
                        iv_origin.setImageResource(R.mipmap.ic_common_upblack);
                    }*/
                    showOrigin();
                } else {
                    isShowType = false;
                }

                break;
            case R.id.btRset:
                originGroup.clearSelected();
                originGroup.clearFixed();
                break;
            case R.id.btSure:
                popOriginWindow.dismiss();
                isShowType = false;
                originGroup.commitFixed();
                originGroup.commit();
                List<String> selectedList = originGroup.getSelectedList();
                List<String> value = new ArrayList<>();
                for (String temp : selectedList) {
                    String s = mapLocation.get(temp);
                    value.add(s);
                }
                String str = ListToListString.getString(value);
                if (selectedList.size() < 1) {
                    isOriginSsletct = false;
                    showData(hashMap);
                    break;
                } else {
                    isSeletct = false;
//                    iv_purchase_sort.setImageResource(R.mipmap.ic_common_downblack);
//                    tv_purchase_sort.setText("排序");
//                    tv_purchase_sort.setTextColor(getResources().getColor(R.color.drop_color));
//                    tv_purchase_price.setTextColor(getResources().getColor(R.color.drop_color));
//                    iv_price_up.setImageResource(imageUp[1]);
//                    iv_price_down.setImageResource(imageDown[0]);
//                    hashMap.remove("sort");
                    hashMap.remove("origin");
                    hashMap.put("origin", str);
                    showData(hashMap);
                    isOriginSsletct = true;
                    tvOrigin.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                }
                break;
            case R.id.releasePurase:
                isShowSort = false;
                isShowType = false;
                if (isLogin == true) {
                    startActivity(new Intent(getActivity(), ReleasePurchaseActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
            case R.id.fl_dismiss:
                popWindow.dismiss();
                isShowSort = false;
                break;
            case R.id.afl_dismiss:
                popWindow.dismiss();
                isShowSort = false;
                break;
            case aflDissmiss:
                popOriginWindow.dismiss();
                isShowType = false;
                break;
            case R.id.ll_showDrawer:
                isShowSort = false;
                isShowType = false;
                ((MainActivity) getActivity()).mTabLayout.setVisibility(View.GONE);
                drawerLayout.openDrawer(Gravity.RIGHT);
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
            case R.id.rl_type:
                if (type_group.isShowMyLayout()) {
                    type_group.hideMyLayout();
                    iv_black_origin.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    iv_black_origin.setImageResource(R.mipmap.ic_sort_down);
                    type_group.showMyLayout();
                }
                break;
            case R.id.rl_color:
                if (color_group.isShowMyLayout()) {
                    color_group.hideMyLayout();
                    iv_black_color.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    iv_black_color.setImageResource(R.mipmap.ic_sort_down);
                    color_group.showMyLayout();
                }
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
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.white));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
                tv_house.setText("5.0及以上");
                doubleseekbar_house.setValues(50, 50);
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_B1:
                tv_house.setText("3.5-3.6");
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.white));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
                doubleseekbar_house.setValues(35, 36);
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_A:
                tv_house.setText("3.7-4.2");
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.white));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.drop_color));
                doubleseekbar_house.setValues(37, 42);
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case R.id.bt_B2:
                tv_house.setText("4.3-4.9");
                bt_c1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_c2.setTextColor(getResources().getColor(R.color.drop_color));
                bt_a.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b1.setTextColor(getResources().getColor(R.color.drop_color));
                bt_b2.setTextColor(getResources().getColor(R.color.white));
                doubleseekbar_house.setValues(43, 49);
                bt_c1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_c2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_a.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                bt_b2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                break;
            case R.id.btSelectRset:
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
                type_group.clearSelected();
                color_group.clearSelected();
                tv_house.setText("3.4-5.0");
                tv_length.setText("25-32");
                tv_elasticity.setText("24-31");
                doubleseekbar_house.setValues(34, 50);
                doubleseekbar_elasticity.setValues(24, 31);
                doubleseekbar_length.setValues(25, 32);
                break;
            case R.id.btSelectSure:
                List<String> selectedkeyword = keyword_group.getSelectedList();
                keyword_group.commit();
                List<String> keyValue = new ArrayList<>();
                List<String> selectedtype = type_group.getSelectedList();
                type_group.commit();
                List<String> originValue = new ArrayList<>();
                List<String> selectedcolor = color_group.getSelectedList();
                color_group.commit();
                List<String> colorValue = new ArrayList<>();
                if (selectedkeyword.size() > 0) {
                    for (String temp : selectedkeyword) {
                        String s = mapKeyword.get(temp);
                        keyValue.add(s);
                    }
                    hashMap.put("keyword", ListToListString.getString(keyValue));
                } else {
                    hashMap.remove("keyword");
                }
                if (selectedcolor.size() > 0) {
                    for (String temp : selectedcolor) {
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
                        }
                        System.out.println("list:----------" + colorValue.toString());
                        hashMap.put("colorGrade", ListToListString.getString(colorValue));
                    }
                } else {
                    hashMap.remove("colorGrade");
                }
                if (selectedtype.size() > 0) {
                    for (String temp : selectedtype) {
                        String s = mapType.get(temp);
                        originValue.add(s);
                        System.out.println(ListToListString.getString(originValue).toString());
                    }
                    hashMap.put("type", ListToListString.getString(originValue));
                } else {
                    hashMap.remove("type");
                }
                Gson gson = new Gson();
                String strHorse = tv_house.getText().toString();
                if (strHorse.equals("4.3-4.9")) {
                    String[] split = strHorse.split("-");
                    MicronAverage micronAverage = new MicronAverage(split[0].toString(), split[1].toString());
                    String jsonString = gson.toJson(micronAverage);
                    hashMap.put("micronAverage", jsonString);
                } else if (strHorse.equals("3.7-4.2")) {
                    String[] split = strHorse.split("-");
                    MicronAverage micronAverage = new MicronAverage(split[0].toString(), split[1].toString());
                    String jsonString = gson.toJson(micronAverage);
                    hashMap.put("micronAverage", jsonString);
                } else if (strHorse.equals("3.5-3.6")) {
                    String[] split = strHorse.split("-");
                    MicronAverage micronAverage = new MicronAverage(split[0].toString(), split[1].toString());
                    String jsonString = gson.toJson(micronAverage);
                    hashMap.put("micronAverage", jsonString);
                } else if (strHorse.equals("5.0及以上")) {
                    MicronAverage micronAverage = new MicronAverage("5.0", "5.0");
                    String jsonString = gson.toJson(micronAverage);
                    hashMap.put("micronAverage", jsonString);
                } else if (strHorse.equals("3.4及以下")) {
                    MicronAverage micronAverage = new MicronAverage("3.4", "3.4");
                    String jsonString = gson.toJson(micronAverage);
                    hashMap.put("micronAverage", jsonString);
                } else {
                    String[] split = strHorse.split("-");
                    MicronAverage micronAverage = new MicronAverage(split[0].toString(), split[1].toString());
                    String jsonString = gson.toJson(micronAverage);
                    hashMap.put("micronAverage", jsonString);
                    hashMap.put("micronGrade", "");
                }

                String strLength = tv_length.getText().toString();
                if (strLength.equals("25以下")){
                    MicronAverage micronAverage2 = new MicronAverage("25", "25");
                    String jsonString2 = gson.toJson(micronAverage2);
                    hashMap.put("lengthAverage", jsonString2);
                }else if (strLength.equals("32以上")){
                    MicronAverage micronAverage2 = new MicronAverage("32", "32");
                    String jsonString2 = gson.toJson(micronAverage2);
                    hashMap.put("lengthAverage", jsonString2);
                }else{
                    String[] split2 = strLength.split("-");
                    MicronAverage micronAverage2 = new MicronAverage(split2[0].toString(), split2[1].toString());
                    String jsonString2 = gson.toJson(micronAverage2);
                    hashMap.put("lengthAverage", jsonString2);
                }


                String strElasticity = tv_elasticity.getText().toString();
                if (strElasticity.equals("24及以下")){
                    MicronAverage micronAverage3 = new MicronAverage("24", "24");
                    String jsonString3 = gson.toJson(micronAverage3);
                    hashMap.put("breakLoadAverage", jsonString3);
                }else if (strElasticity.equals("31及以上")){
                    MicronAverage micronAverage3 = new MicronAverage("31", "31");
                    String jsonString3 = gson.toJson(micronAverage3);
                    hashMap.put("breakLoadAverage", jsonString3);
                }else{
                    String[] split3 = strElasticity.split("-");
                    MicronAverage micronAverage3 = new MicronAverage(split3[0].toString(), split3[1].toString());
                    String jsonString3 = gson.toJson(micronAverage3);
                    hashMap.put("breakLoadAverage", jsonString3);
                }
                showData(hashMap);
                drawerLayout.closeDrawer(Gravity.RIGHT);

                break;
        }
    }

    private void showOrigin() {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popup_drop_origin, null);
        popOriginWindow = new PopWindow(getActivity(), conentView);
        FrameLayout popBackground = (FrameLayout) conentView.findViewById(R.id.popBackground);
        popBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popOriginWindow.dismiss();
                isShowType = false;
            }
        });
        popBackground.setAlpha(0.3f);
        FrameLayout aflDissmiss = (FrameLayout) conentView.findViewById(R.id.aflDissmiss);
        aflDissmiss.setOnClickListener(this);
        originGroup = (MultiCheckGroupView) conentView.findViewById(R.id.originGroup);
        Button btRset = (Button) conentView.findViewById(R.id.btRset);
        btRset.setOnClickListener(this);
        Button btSure = (Button) conentView.findViewById(R.id.btSure);
        btSure.setOnClickListener(this);
        mapLocation = new TreeMap<>();
        mapLocation.put("0", "南疆");
        mapLocation.put("1", "北疆");
        mapLocation.put("2", "东疆");
        mapLocation.put("3", "地产棉");
        mapLocation.put("4", "进口棉");
        originGroup.addValues(mapLocation);
        popOriginWindow.showAsDropDown(popOriginWindow,ll_purchase_sort,0,0);
      /*  if (Build.VERSION.SDK_INT < 24) {
            popOriginWindow.showAsDropDown(ll_purchase_sort, 0, 0);
        } else {
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            ll_purchase_sort.getLocationOnScreen(location);
            popOriginWindow.showAtLocation(ll_purchase_sort, Gravity.NO_GRAVITY, 0, location[1] + ll_purchase_sort.getHeight() + 0);
        }*/
//        popOriginWindow.showPopupWindow(ll_purchase_sort);
        popOriginWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (originGroup.getSelectedList().size() < 1) {
                    iv_origin.setImageResource(R.mipmap.ic_common_downblack);
                    tvOrigin.setTextColor(getResources().getColor(R.color.drop_color));
                    hashMap.remove("origin");
                } else {
                    iv_origin.setImageResource(R.mipmap.ic_common_downred);
                }
            }
        });
    }

    private void showPopup() {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popup_drop_down, null);
        popWindow = new PopWindow(getActivity(), conentView);
        LinearLayout ll_sort = (LinearLayout) conentView.findViewById(R.id.ll_sort);
        FrameLayout fl_dismiss = (FrameLayout) conentView.findViewById(R.id.fl_dismiss);
        fl_dismiss.setAlpha(0.3f);
        FrameLayout afl_dismiss = (FrameLayout) conentView.findViewById(R.id.afl_dismiss);
        afl_dismiss.setOnClickListener(this);
        fl_dismiss.setOnClickListener(this);
        tv_zonghe = (TextView) conentView.findViewById(R.id.tv_zonghe);
        iv_zonghe = (ImageView) conentView.findViewById(R.id.iv_zonghe);
        ll_sort.setOnClickListener(this);
        popWindow.showAsDropDown(popWindow,ll_purchase_sort,0,0);
     /*   if (Build.VERSION.SDK_INT < 24) {
            popWindow.showAsDropDown(ll_purchase_sort, 0, 0);
        } else {
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            ll_purchase_sort.getLocationOnScreen(location);
            popWindow.showAtLocation(ll_purchase_sort, Gravity.NO_GRAVITY, 0, location[1] + ll_purchase_sort.getHeight() + 0);
        }*/
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tv_purchase_sort.getText().toString().equals("综合排序")) {
                    iv_purchase_sort.setImageResource(R.mipmap.ic_common_downred);
                    tv_purchase_sort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                } else {
                    iv_purchase_sort.setImageResource(R.mipmap.ic_common_downblack);
                    tv_purchase_sort.setTextColor(getResources().getColor(R.color.drop_color));
                }
            }
        });
        if (isSeletct) {
            iv_purchase_sort.setImageResource(R.mipmap.ic_common_upred);
            tv_zonghe.setTextColor(getResources().getColor(R.color.tab_tv_selected));
            iv_zonghe.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public boolean onBackPressed() {
        //监听fragment的返回事件 如果drawerlayout没关闭 则关闭
        if (drawerLayout.isDrawerOpen(ll_drawer)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
            ;
            return true;
        }
        if (popWindow!=null&&popWindow.isShowing()){
            popWindow.dismiss();
            return true;
        }
        if (popOriginWindow!=null&&popOriginWindow.isShowing()){
            popOriginWindow.dismiss();
            return true;
        }
        return BackHandlerHelper.handleBackPress(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (drawerLayout.isDrawerOpen(ll_drawer)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
            ;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}


