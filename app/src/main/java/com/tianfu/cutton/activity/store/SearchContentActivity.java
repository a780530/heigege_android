package com.tianfu.cutton.activity.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.quality.QualityBatchDetailsActivity;
import com.tianfu.cutton.activity.quality.QualityKunDetailsActivity;
import com.tianfu.cutton.adapter.ClassifyMainAdapter;
import com.tianfu.cutton.adapter.ClassifyMoreAdapter;
import com.tianfu.cutton.adapter.OnItemListener;
import com.tianfu.cutton.adapter.SearchDiffTypeViewAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.AllStoragesBean;
import com.tianfu.cutton.model.SearchTestbean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ListToListString;
import com.tianfu.cutton.widget.DoubleSeekBar;
import com.tianfu.cutton.widget.MultiCheckGroupView;
import com.tianfu.cutton.widget.PopWindow;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.tianfu.cutton.R.id.bt_c1;
import static com.tianfu.cutton.R.id.bt_c2;
import static com.tianfu.cutton.R.id.date_group;
import static com.tianfu.cutton.R.id.doubleseekbar_elasticity;
import static com.tianfu.cutton.R.id.doubleseekbar_house;
import static com.tianfu.cutton.R.id.doubleseekbar_length;
import static com.tianfu.cutton.R.id.iv_price_down;
import static com.tianfu.cutton.R.id.iv_sort;
import static com.tianfu.cutton.R.id.sts_price_down;
import static com.tianfu.cutton.R.id.sts_price_up;
import static com.tianfu.cutton.R.id.tv_elasticity;
import static com.tianfu.cutton.R.id.tv_house;
import static com.tianfu.cutton.R.id.tv_price;
import static com.tianfu.cutton.R.id.tv_shaixuan;
import static com.tianfu.cutton.R.id.tv_sort;
import static com.tianfu.cutton.R.id.tv_store_type;
import static com.tianfu.cutton.R.id.type_group;

//import static com.tianfu.cutton.R.id.iv_store_type;


public class SearchContentActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search)
    AutoLinearLayout llSearch;
    @BindView(tv_sort)
    TextView tvSort;
    @BindView(iv_sort)
    ImageView ivSort;
    @BindView(R.id.ll_drop_down)
    AutoLinearLayout llDropDown;
    @BindView(tv_price)
    TextView tvPrice;
    @BindView(R.id.ll_price)
    AutoLinearLayout llPrice;
    @BindView(tv_store_type)
    TextView tvStoreType;
    /*    @BindView(iv_store_type)
        ImageView ivStoreType;*/
    @BindView(R.id.ll_type)
    AutoLinearLayout llType;
    @BindView(tv_shaixuan)
    TextView tvShaixuan;
    @BindView(R.id.iv_shaixuan)
    ImageView ivShaixuan;
    @BindView(R.id.ll_popup)
    AutoLinearLayout llPopup;
    @BindView(R.id.storeRecyler)
    RecyclerView storeRecyler;
    @BindView(R.id.ptr_purchase_frame)
    PtrClassicFrameLayout ptrPurchaseFrame;
    @BindView(R.id.iv_black_keyword)
    ImageView ivBlackKeyword;
    @BindView(R.id.rl_keyword)
    AutoRelativeLayout rlKeyword;
    @BindView(R.id.keyword_group)
    MultiCheckGroupView keywordGroup;
    @BindView(R.id.image_origin)
    ImageView imageOrigin;
    @BindView(R.id.relativy_origin)
    AutoRelativeLayout relativyOrigin;
    @BindView(R.id.iv_black_ty)
    ImageView ivBlackTy;
    @BindView(R.id.rl_type)
    AutoRelativeLayout rlType;
    @BindView(type_group)
    MultiCheckGroupView typeGroup;
    @BindView(R.id.iv_black_color)
    ImageView ivBlackColor;
    @BindView(R.id.rl_color)
    AutoRelativeLayout rlColor;
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.rl_date)
    AutoRelativeLayout rlDate;
    @BindView(date_group)
    MultiCheckGroupView dateGroup;
    @BindView(tv_house)
    TextView tvHouse;
    @BindView(bt_c1)
    Button btC1;
    @BindView(bt_c2)
    Button btC2;
    @BindView(doubleseekbar_house)
    DoubleSeekBar doubleseekbarHouse;
    @BindView(R.id.bt_B1)
    Button btB1;
    @BindView(R.id.bt_A)
    Button btA;
    @BindView(R.id.bt_B2)
    Button btB2;
    @BindView(tv_elasticity)
    TextView tvElasticity;
    @BindView(doubleseekbar_elasticity)
    DoubleSeekBar doubleseekbarElasticity;
    @BindView(R.id.tv_length)
    TextView tvLength;
    @BindView(doubleseekbar_length)
    DoubleSeekBar doubleseekbarLength;

    @BindView(R.id.btSelectRset)
    Button btSelectRset;
    @BindView(R.id.btSelectSure)
    Button btSelectSure;
    @BindView(R.id.ll_button)
    AutoLinearLayout llButton;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.ll_data)
    AutoLinearLayout llData;
    @BindView(R.id.nodata_search)
    AutoLinearLayout nodataSearch;
    @BindView(R.id.iv_price_up)
    ImageView ivPriceUp;
    @BindView(iv_price_down)
    ImageView ivPriceDown;
    @BindView(sts_price_up)
    ImageView stsPriceUp;
    @BindView(sts_price_down)
    ImageView stsPriceDown;
    @BindView(R.id.tv_Location)
    TextView tvLocation;
    @BindView(R.id.bt_select)
    AutoLinearLayout btSelect;
    @BindView(R.id.tv_trash)
    TextView tvTrash;
    @BindView(R.id.doubleseekbar_trash)
    DoubleSeekBar doubleseekbarTrash;
    @BindView(R.id.tv_moisture)
    TextView tvMoisture;
    @BindView(R.id.doubleSeekBar_moisture)
    DoubleSeekBar doubleSeekBarMoisture;
    @BindView(R.id.origin_group)
    MultiCheckGroupView originGroup;
    @BindView(R.id.color_group)
    MultiCheckGroupView colorGroup;
    @BindView(R.id.ll_drawer)
    AutoRelativeLayout llDrawer;
    private int sortType = 0;//记录排序规则
    private int typeType = 0;//记录排序规则
    private int count = 1;
    private int num = 1;
    private int pageNo = 1;
    private PopWindow popDistance;
    private LayoutInflater inflater;
    int imageUp[] = new int[]{
            R.mipmap.ic_common_upred,
            R.mipmap.ic_common_upblack
    };
    int imageDown[] = new int[]{
            R.mipmap.ic_common_downblack,
            R.mipmap.ic_common_downred
    };
    private TextView tvZhineng;
    private int countsts = 1;
    private ImageView ivZhineng;
    private ListView mainlist;
    private ListView morelist;
    ClassifyMainAdapter mainAdapter;
    ClassifyMoreAdapter moreAdapter;
    private List<Map<String, Object>> mainList;
    private PopWindow popupType;
    private TextView tv_myself;
    private TextView tv_recevi;
    private ImageView iv_myself;
    private ImageView iv_recevi;
    private String value;
    private String type;
    private int numsts = 1;
    List<SearchTestbean.ValueBean.RowsBean> mDataList;
    private SearchDiffTypeViewAdapter adapter;
    private String keywords1;
    private Map<String, String> hashMap;
    private boolean isShowTypePop = false;
    private boolean isShowSort = false;
    private boolean isUpDate = true;
    private TextView tv_3128B;
    private TextView tv_double28;
    private TextView tv_double29;
    private ImageView iv_3128B;
    private ImageView iv_double28;
    private ImageView iv_double29;
    private Map<String, String> mapType;
    private Map<String, String> mapColor;
    private Map<String, String> mapDate;
    private Map<String, String> mapLocation;
    private Map<String, String> mapKeyword;
    public static String[] LISTVIEWTXT;
    public static String[][] MORELISTTXT;
    int imageUpSts[] = new int[]{
            R.mipmap.ic_common_upred,
            R.mipmap.ic_common_upblack
    };
    int imageDownSts[] = new int[]{
            R.mipmap.ic_common_downblack,
            R.mipmap.ic_common_downred
    };
    private PopupWindow popWindowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_content);
        ButterKnife.bind(this);
        initDrawerView();
        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        value = intent.getStringExtra("value");
        String contact = intent.getStringExtra("contact");
        keywords1 = intent.getStringExtra("keywords");
        if (value != null) {
            tvSearch.setText(value);
        } else if (keywords1 != null) {
            tvSearch.setText(keywords1);
        }
        hashMap = new HashMap<>();
        if (keywords1 != null) {
            hashMap.put("keywords", keywords1);
        }
        if (contact != null) {
            hashMap.put("contact", contact);
        }
        hashMap.put("deviceNo", Common.deviceNo);
        hashMap.put("from", Common.from);
        hashMap.put("sourceTypeEnum", Common.sourceTypeEnum);
        hashMap.put("versionNo", Common.versionNo);
        hashMap.put("searchType", "1");
        initPtr();
        initData();
    }

    private void initPtr() {
        ptrPurchaseFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo++;
                initData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                initData();
            }
        });
    }

    private void initData() {
        ptrPurchaseFrame.refreshComplete();
        if (value != null) {
            hashMap.put("keyword", value);
        }
        if (type != null) {
            hashMap.put("keywordType", type);
        }
        hashMap.put("pageNum", pageNo + "");
        showProgressBar("");
        HttpManager.getServerApi().getSearch(hashMap).enqueue(new CallBack<SearchTestbean>() {
            @Override
            public void success(SearchTestbean data) {
                if (data.success) {
                    if (data.value.rows.size() > 0) {
                        llData.setVisibility(View.VISIBLE);
                        nodataSearch.setVisibility(View.GONE);
                        if (mDataList != null) {
                            if (pageNo == 1) {
                                mDataList.clear();
                            }
                            mDataList.addAll(data.value.rows);
                        } else {
                            mDataList = data.value.rows;
                        }
                        initRecyclerView();
                    } else {
                        if (pageNo==1){
                            llData.setVisibility(View.GONE);
                            nodataSearch.setVisibility(View.VISIBLE);
                        }
                    }
                }
                dismissProgressBar();
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initRecyclerView() {
        if (adapter == null) {
            storeRecyler.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SearchDiffTypeViewAdapter(this, mDataList);
            setListener();
            storeRecyler.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void setListener() {
        adapter.setOnItemListener(new OnItemListener() {
            @Override
            public void checkBoxClick(int position) {

            }

            @Override
            public void onItemClick(View view, int position) {
                String isProduct = mDataList.get(position).isProduct;
                String batchType = mDataList.get(position).batchType;
                String code = mDataList.get(position).code;
                String productId = mDataList.get(position).productId;
                String mobileBaojia = mDataList.get(position).mobile;
                if (isProduct.equals("0")) {
                    if (batchType.equals("1")) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), QualityKunDetailsActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("productId", productId + "");
                        startActivity(intent);
                    } else if (batchType.equals("2")) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), QualityBatchDetailsActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("productId", productId + "");
                        intent.putExtra("fromKun", "0");
                        intent.putExtra("batchType", batchType);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(BaseApplication.getContextObject(), QualityBatchDetailsActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("productId", productId + "");
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("fromKun", "1");
                        startActivity(intent);
                    }
                } else {
                    if (batchType.equals("1")) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), StoreKunActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("productId", productId + "");
                        startActivity(intent);
                    } else if (batchType.equals("2")) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), StoreBatchActivity.class);
                        intent.putExtra("productId", productId + "");
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("fromKun", "0");
                        intent.putExtra("code", code);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(BaseApplication.getContextObject(), StoreBatchActivity.class);
                        intent.putExtra("productId", productId + "");
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("fromKun", "1");
                        intent.putExtra("code", code);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position, boolean isPress) {

            }
        });
    }

    private void initDrawerView() {
        initProdata();
        doubleseekbarHouse.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
         /*       btC1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btC2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btA.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btC1.setTextColor(getResources().getColor(R.color.drop_color));
                btC2.setTextColor(getResources().getColor(R.color.drop_color));
                btA.setTextColor(getResources().getColor(R.color.drop_color));
                btB1.setTextColor(getResources().getColor(R.color.drop_color));
                btB2.setTextColor(getResources().getColor(R.color.drop_color));
                tvHouse.setText(lowValue + "-" + highValue);*/
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
                        tvLength.setText("25以下");
                    } else if ((int) lowValue == 32) {
                        tvLength.setText("32以上");
                    }
                }
            }
        });
        doubleseekbarTrash.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tvTrash.setText((int) lowValue + "-" + (int) highValue);
            }
        });
        doubleSeekBarMoisture.setOnDoubleValueChangeListener(new DoubleSeekBar.DoubleSeekBarValueChangeListener() {
            @Override
            public void onDoubleValueChange(float lowValue, float highValue) {
                tvMoisture.setText((int) lowValue + "-" + (int) highValue);
            }
        });
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                drawerLayout.setSystemUiVisibility(View.INVISIBLE);
                String s = tvHouse.getText().toString();
                if (s.equals("3.4及以下")) {
                    doubleseekbarHouse.setValues(34, 34);
                } else if (s.equals("5.0及以上")) {
                    doubleseekbarHouse.setValues(50, 50);
                } else {
                    String[] split = s.split("-");
                    String[] split1 = split[0].split("\\.");
                    String[] split2 = split[1].split("\\.");
                    String min = split1[0] + split1[1];
                    String max = split2[0] + split2[1];
                    doubleseekbarHouse.setValues(Integer.valueOf(min), Integer.valueOf(max));
                }
                String elasticityStr = tvElasticity.getText().toString();
                if (elasticityStr.equals("24及以下")) {
                    doubleseekbarElasticity.setValues(24, 24);
                } else if (elasticityStr.equals("31及以上")) {
                    doubleseekbarElasticity.setValues(31, 31);
                } else {
                    String[] splitelasticity = tvElasticity.getText().toString().split("-");
                    doubleseekbarElasticity.setValues(Integer.valueOf(splitelasticity[0]), Integer.valueOf(splitelasticity[1]));
                }
                String lengthStr = tvLength.getText().toString();
                if (lengthStr.equals("25以下")) {
                    doubleseekbarLength.setValues(25, 25);
                } else if (lengthStr.equals("32以上")) {
                    doubleseekbarLength.setValues(32, 32);
                } else {
                    String[] splitlength = tvLength.getText().toString().split("-");
                    doubleseekbarLength.setValues(Integer.valueOf(splitlength[0]), Integer.valueOf(splitlength[1]));
                }
            /*    String[] splitelasticity = tvElasticity.getText().toString().split("-");
                String[] splitlength = tvLength.getText().toString().split("-");*/
                String[] splittrash = tvTrash.getText().toString().split("-");
                String[] splitmoisture = tvMoisture.getText().toString().split("-");
            /*    doubleseekbarElasticity.setValues(Integer.valueOf(splitelasticity[0]), Integer.valueOf(splitelasticity[1]));
                doubleseekbarLength.setValues(Integer.valueOf(splitlength[0]), Integer.valueOf(splitlength[1]));*/
                doubleseekbarTrash.setValues(Integer.valueOf(splittrash[0]), Integer.valueOf(splittrash[1]));
                doubleSeekBarMoisture.setValues(Integer.valueOf(splitmoisture[0]), Integer.valueOf(splitmoisture[1]));
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                if (popWindowList!=null){
                    popWindowList.dismiss();
                }
                isHavaData();
                WindowManager.LayoutParams attr = SearchContentActivity.this.getWindow().getAttributes();
                attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                SearchContentActivity.this.getWindow().setAttributes(attr);
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
        keywordGroup.addValues(mapKeyword);
        keywordGroup.setMultiCheck(false);


        mapType = new TreeMap<>();
        mapType.put("0", "锯齿细绒棉");
        mapType.put("1", "锯齿机绒棉");
        mapType.put("2", "皮辊细绒棉");
        typeGroup.addValues(mapType);

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
        mapDate = new TreeMap<>();
        mapDate.put("0", "2017");
        mapDate.put("1", "2016");
        dateGroup.addValues(mapDate);
        dateGroup.setMultiCheck(false);
        mapLocation = new TreeMap<>();
        mapLocation.put("0", "新疆棉");
        mapLocation.put("3", "地产棉");
        mapLocation.put("4", "进口棉");
        originGroup.addValues(mapLocation);
        originGroup.setMultiCheck(false);
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

    private void isHavaData() {
        List<String> selectedkeyword = keywordGroup.getSelectedList();
        List<String> selectedtype = typeGroup.getSelectedList();
        List<String> selectedcolor = colorGroup.getSelectedList();
        List<String> selectedorigin = originGroup.getSelectedList();
        List<String> selecteddate = dateGroup.getSelectedList();
        if (selectedkeyword.size() < 1 && selectedtype.size() < 1 && selectedcolor.size() < 1 && selectedorigin.size() < 1 && selecteddate.size() < 1 && tvHouse.getText().equals("3.4-5.0") && tvElasticity.getText().equals("24-31") && tvLength.getText().equals("25-32") && tvTrash.getText().equals("0-5") && tvMoisture.getText().equals("0-10")) {
            tvShaixuan.setTextColor(getResources().getColor(R.color.drop_color));
            ivShaixuan.setImageResource(R.mipmap.ic_store_funnel);
        } else {
            tvShaixuan.setTextColor(getResources().getColor(R.color.tab_tv_selected));
            ivShaixuan.setImageResource(R.mipmap.ic_store_funnelred);
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_search, R.id.ll_drop_down, R.id.ll_price, R.id.ll_type, R.id.ll_popup, R.id.rl_keyword, R.id.relativy_origin, R.id.rl_type, R.id.rl_color, R.id.rl_date, bt_c1, bt_c2, R.id.bt_B1, R.id.bt_A, R.id.bt_B2, R.id.btSelectRset, R.id.btSelectSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                isShowTypePop = false;
                onBackPressed();
                isShowSort = false;
                break;
            case R.id.ll_search:
                isShowTypePop = false;
                isShowSort = false;
                startActivity(new Intent(BaseApplication.getContextObject(), SearchActivity.class));
                finish();
                break;
            case R.id.ll_drop_down:
                isShowTypePop = false;
                if (isShowSort == false) {
                    isShowSort = true;
                    showDistance(sortType);
                    ivSort.setImageResource(R.mipmap.ic_common_upred);
                    tvSort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
            /*        if (sortType == 0) {
                        ivSort.setImageResource(R.mipmap.ic_common_upblack);
                    } else {
                        ivSort.setImageResource(R.mipmap.ic_common_upred);
                    }*/
                } else {
                    isShowSort = false;
                    popDistance.dismiss();
                }
                break;
            case R.id.ll_price:
                pageNo=1;
                stsPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                stsPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));
                isShowTypePop = false;
                isShowSort = false;
                sortType = 0;
         /*       typeType = 0;
                ivStoreType.setImageResource(R.mipmap.ic_common_downblack);
                tvStoreType.setText("关键字");
                tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));*/
                ivSort.setImageResource(R.mipmap.ic_common_downblack);
                tvSort.setText("排序");
                tvSort.setTextColor(getResources().getColor(R.color.drop_color));
                ivPriceUp.setImageResource(imageUp[++count % imageUp.length]);
                ivPriceDown.setImageResource(imageDown[++num % imageDown.length]);
                tvPrice.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                if (count % 2 == 0) {
                    hashMap.remove("orderType");
                    hashMap.put("orderType", "19");

                    initData();
                } else {
                    hashMap.remove("orderType");
                    hashMap.put("orderType", "18");

                    initData();
                }
                break;
            case R.id.ll_type:
                pageNo = 1;
                isShowSort = false;
                isShowTypePop = false;
                sortType = 0;
                ivSort.setImageResource(R.mipmap.ic_common_downblack);
                tvSort.setText("排序");
                tvSort.setTextColor(getResources().getColor(R.color.drop_color));
                stsPriceUp.setImageResource(imageUpSts[++countsts % imageUp.length]);
                stsPriceDown.setImageResource(imageDownSts[++numsts % imageDown.length]);
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                if (countsts % 2 == 0) {
                    hashMap.put("orderType", "21");
                } else {
                    hashMap.put("orderType", "20");
                }
                initData();
                break;
            case R.id.ll_popup:
                drawerLayout.openDrawer(Gravity.RIGHT);
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
            case R.id.relativy_origin:
                if (originGroup.isShowMyLayout()) {
                    originGroup.hideMyLayout();
                    imageOrigin.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    imageOrigin.setImageResource(R.mipmap.ic_sort_down);
                    originGroup.showMyLayout();
                }
                break;
            case R.id.rl_type:
                if (typeGroup.isShowMyLayout()) {
                    typeGroup.hideMyLayout();
                    ivBlackTy.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    ivBlackTy.setImageResource(R.mipmap.ic_sort_down);
                    typeGroup.showMyLayout();
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
            case R.id.rl_date:
                if (dateGroup.isShowMyLayout()) {
                    dateGroup.hideMyLayout();
                    ivDate.setImageResource(R.mipmap.ic_sort_up);
                } else {
                    ivDate.setImageResource(R.mipmap.ic_sort_down);
                    dateGroup.showMyLayout();
                }
                break;
            case bt_c1:
                tvHouse.setText("3.4及以下");
                doubleseekbarHouse.setValues(34, 34);
                btC1.setTextColor(getResources().getColor(R.color.white));
                btC2.setTextColor(getResources().getColor(R.color.drop_color));
                btA.setTextColor(getResources().getColor(R.color.drop_color));
                btB1.setTextColor(getResources().getColor(R.color.drop_color));
                btB2.setTextColor(getResources().getColor(R.color.drop_color));

                btC1.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                btC2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btA.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                break;
            case bt_c2:
                tvHouse.setText("5.0及以上");
                doubleseekbarHouse.setValues(50, 50);
                btC1.setTextColor(getResources().getColor(R.color.drop_color));
                btC2.setTextColor(getResources().getColor(R.color.white));
                btA.setTextColor(getResources().getColor(R.color.drop_color));
                btB1.setTextColor(getResources().getColor(R.color.drop_color));
                btB2.setTextColor(getResources().getColor(R.color.drop_color));

                btC1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btC2.setBackgroundResource(R.drawable.btn_bottom_background_left_press);
                btA.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB1.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
                btB2.setBackgroundResource(R.drawable.btn_bottom_background_right_press);
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
            case R.id.btSelectRset:
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
                keywordGroup.clearSelected();
                originGroup.clearSelected();
                colorGroup.clearSelected();
                dateGroup.clearSelected();
                typeGroup.clearSelected();
                tvHouse.setText("3.4-5.0");
                doubleseekbarHouse.setValues(34, 50);
                tvLength.setText("25-32");
                doubleseekbarLength.setValues(25, 32);
                tvElasticity.setText("24-31");
                doubleseekbarElasticity.setValues(24, 31);
                tvTrash.setText("0-5");
                doubleseekbarTrash.setValues(0, 5);
                tvMoisture.setText("0-10");
                doubleSeekBarMoisture.setValues(0, 10);
                break;
            case R.id.btSelectSure:
                List<String> selectedkeyword = keywordGroup.getSelectedList();
                keywordGroup.commit();
                List<String> keyValue = new ArrayList<>();

                List<String> selectedtype = typeGroup.getSelectedList();
                typeGroup.commit();
                List<String> typeValue = new ArrayList<>();

                List<String> selectedcolor = colorGroup.getSelectedList();
                colorGroup.commit();
                List<String> colorValue = new ArrayList<>();

                List<String> selectedorigin = originGroup.getSelectedList();
                originGroup.commit();
                List<String> originValue = new ArrayList<>();

                List<String> selecteddate = dateGroup.getSelectedList();
                dateGroup.commit();
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
                if (selectedtype.size() < 1) {
                    hashMap.put("type", "");
                } else {
                    for (String temp : selectedtype) {
                        String s = mapType.get(temp);
                        typeValue.add(s);
                    }
                    hashMap.put("type", ListToListString.getString(typeValue));
                }
                if (selectedcolor.size() < 1) {
                    hashMap.put("colorGradeName", "");
                } else {
                    for (String temp : selectedcolor) {
                        String s = mapColor.get(temp);
                        colorValue.add(s);
           /*             if (s.equals("白棉1级")) {
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
                String[] trash = tvTrash.getText().toString().split("-");
                String[] moisture = tvMoisture.getText().toString().split("-");
                String s = tvHouse.getText().toString().trim();
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
                String elasticityStr = tvElasticity.getText().toString();
                if (elasticityStr.equals("24及以下")) {
                    hashMap.put("breakLoadAverage", "[" + "24" + "," + "24" + "]");
                } else if (elasticityStr.equals("31及以上")) {
                    hashMap.put("breakLoadAverage", "[" + "31" + "," + "31" + "]");
                } else {
                    String[] strong = elasticityStr.split("-");
                    hashMap.put("breakLoadAverage", "[" + strong[0] + "," + strong[1] + "]");
                }
                String length = tvLength.getText().toString();
                if (length.equals("25以下")) {
                    hashMap.put("lengthAverage", "[" + "25" + "," + "25" + "]");
                } else if (length.equals("32以上")) {
                    hashMap.put("lengthAverage", "[" + "32" + "," + "32" + "]");
                } else {
                    String[] split = length.split("-");
                    hashMap.put("lengthAverage", "[" + split[0] + "," + split[1]+"]");
                }
     /*           hashMap.put("breakLoadAverage", "[" + strong[0] + "," + strong[1] + "]");
                hashMap.put("lengthAverage", "[" + length[0] + "," + length[1] + "]");*/
                hashMap.put("trash", "[" + trash[0] + "," + trash[1] + "]");
                hashMap.put("moisture", "[" + moisture[0] + "," + moisture[1] + "]");
                if (!tvLocation.getText().toString().equals("请选择仓库名称")) {
                    String[] split = tvLocation.getText().toString().split("");
                    hashMap.put("location", split[0]);
                    hashMap.put("storage", split[1]);
                }
                initData();
                drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
        }
    }

    private void showPopType(int typeType) {
        View conentView = inflater.inflate(R.layout.popup_store_type1, null);
        popupType = new PopWindow(this, conentView);
        initType(conentView);
        if (Build.VERSION.SDK_INT < 24) {
            popupType.showAsDropDown(llDropDown, 0, 0);
        } else {
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            llDropDown.getLocationOnScreen(location);
            popupType.showAtLocation(llDropDown, Gravity.NO_GRAVITY, 0, location[1] + llDropDown.getHeight() + 0);
        }
        popupType.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tvStoreType.getText().toString().equals("关键字")) {
                    tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    tvStoreType.setTextColor(getResources().getColor(R.color.tab_tv_selected));
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
        LinearLayout ll_myself = (LinearLayout) conentView.findViewById(R.id.ll_myself);
        ll_myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    ivSort.setImageResource(R.mipmap.ic_common_downblack);
                tvSort.setText("排序");
                tvSort.setTextColor(getResources().getColor(R.color.drop_color));
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);*/
                tvStoreType.setText("2017新棉");
                tvStoreType.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 1;
                popupType.dismiss();
                isShowTypePop = false;
                hashMap.remove("orderType");
                hashMap.remove("keywords");
                hashMap.put("keywords", "2017新棉");

                initData();
            }
        });
        LinearLayout ll_recevi = (LinearLayout) conentView.findViewById(R.id.ll_recevi);
        ll_recevi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      ivSort.setImageResource(R.mipmap.ic_common_downblack);
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));
                tvSort.setText("排序");
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvSort.setTextColor(getResources().getColor(R.color.drop_color));*/
                tvStoreType.setText("国储棉");
                tvStoreType.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 2;
                popupType.dismiss();
                isShowTypePop = false;
                hashMap.remove("orderType");
                hashMap.remove("keywords");
                hashMap.put("keywords", "国储棉");
                initData();
            }
        });
        tv_myself = (TextView) conentView.findViewById(R.id.tv_myself);
        tv_recevi = (TextView) conentView.findViewById(R.id.tv_recevi);
        iv_myself = (ImageView) conentView.findViewById(R.id.iv_myself);
        iv_recevi = (ImageView) conentView.findViewById(R.id.iv_recevi);
        LinearLayout ll_3128B = (LinearLayout) conentView.findViewById(R.id.ll_3128B);
        ll_3128B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     ivSort.setImageResource(R.mipmap.ic_common_downblack);
                tvSort.setText("排序");
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvSort.setTextColor(getResources().getColor(R.color.drop_color));
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));*/
                tvStoreType.setText("3128B");
                typeType = 3;
                tvStoreType.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                popupType.dismiss();
                isShowTypePop = false;
                hashMap.remove("orderType");
                hashMap.remove("keywords");
                hashMap.put("keywords", "3128B");
                initData();
            }
        });
        LinearLayout ll_double28 = (LinearLayout) conentView.findViewById(R.id.ll_double28);
        ll_double28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         /*       ivSort.setImageResource(R.mipmap.ic_common_downblack);
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));
                tvSort.setText("排序");
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvSort.setTextColor(getResources().getColor(R.color.drop_color));
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));*/
                tvStoreType.setText("双28");
                tvStoreType.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 4;
                popupType.dismiss();
                isShowTypePop = false;
                hashMap.remove("orderType");
                hashMap.remove("keywords");
                hashMap.put("keywords", "双28");
                initData();
            }
        });
        LinearLayout ll_double29 = (LinearLayout) conentView.findViewById(R.id.ll_double29);
        ll_double29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    ivSort.setImageResource(R.mipmap.ic_common_downblack);
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));
                tvSort.setText("排序");
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvSort.setTextColor(getResources().getColor(R.color.drop_color));
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));*/
                tvStoreType.setText("双29");
                tvStoreType.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                typeType = 5;
                popupType.dismiss();
                isShowTypePop = false;
                hashMap.remove("orderType");
                hashMap.remove("keywords");
                hashMap.put("keywords", "双29");
                initData();
            }
        });
        tv_3128B = (TextView) conentView.findViewById(R.id.tv_3128B);
        tv_double28 = (TextView) conentView.findViewById(R.id.tv_double28);
        tv_double29 = (TextView) conentView.findViewById(R.id.tv_double29);
        iv_3128B = (ImageView) conentView.findViewById(R.id.iv_3128B);
        iv_double28 = (ImageView) conentView.findViewById(R.id.iv_double28);
        iv_double29 = (ImageView) conentView.findViewById(R.id.iv_double29);
        FrameLayout fl_dismiss = (FrameLayout) conentView.findViewById(R.id.fl_dismiss);
        fl_dismiss.setAlpha(0.3f);
        fl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupType.dismiss();
                isShowTypePop = false;
            }
        });
    }

    private void showDistance(int sortType) {
        View conentView = inflater.inflate(R.layout.popup_store_down1, null);
        initPopview(conentView);
        popDistance = new PopWindow(this, conentView);
        popDistance.showAsDropDown(popDistance,llDropDown,0,0);
   /*     if (Build.VERSION.SDK_INT < 24) {
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
                if (tvSort.getText().toString().equals("排序")) {
                    ivSort.setImageResource(R.mipmap.ic_common_downblack);
                    tvSort.setTextColor(getResources().getColor(R.color.drop_color));
                } else {
                    ivSort.setImageResource(R.mipmap.ic_common_downred);
                    tvSort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
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
        tvZhineng = (TextView) conentView.findViewById(R.id.tv_zhineng);

        ivZhineng = (ImageView) conentView.findViewById(R.id.iv_zhineng);

        LinearLayout ll_zhineng = (LinearLayout) conentView.findViewById(R.id.ll_zhineng);
        ll_zhineng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNo = 1;
                sortType = 1;
               /* typeType = 0;

                ivStoreType.setImageResource(R.mipmap.ic_common_downblack);
                tvStoreType.setText("关键字");
                tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));*/
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvSort.setText("智能排序");
                tvSort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                ivSort.setImageResource(R.mipmap.ic_common_downred);
                popDistance.dismiss();
                isShowSort = false;
                hashMap.remove("orderType");
                hashMap.put("orderType", "1");
                stsPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                stsPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));
                initData();
            }
        });
    /*    LinearLayout ll_jtoY = (LinearLayout) conentView.findViewById(R.id.ll_JtoY);
        ll_jtoY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        *//*        typeType = 0;
                ivStoreType.setImageResource(R.mipmap.ic_common_downblack);
                tvStoreType.setText("关键字");
                tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));*//*
                tvSort.setText("距离从近");
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvSort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                ivSort.setImageResource(R.mipmap.ic_common_downred);
                sortType = 2;
                popDistance.dismiss();
                isShowSort = false;
                stsPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                stsPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));
                hashMap.remove("orderType");
                hashMap.put("orderType", "2");
                initData();
            }
        });*/
   /*     LinearLayout ll_ytoJ = (LinearLayout) conentView.findViewById(R.id.ll_YtoJ);
        ll_ytoJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 *//*               typeType = 0;
                ivStoreType.setImageResource(R.mipmap.ic_common_downblack);
                tvStoreType.setText("关键字");
                tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));*//*
                tvPrice.setTextColor(getResources().getColor(R.color.drop_color));
                ivPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                ivPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvSort.setText("距离从远");
                tvSort.setTextColor(getResources().getColor(R.color.tab_tv_selected));
                ivSort.setImageResource(R.mipmap.ic_common_downred);
                sortType = 3;
                popDistance.dismiss();
                stsPriceDown.setImageResource(R.mipmap.ic_common_downblack);
                stsPriceUp.setImageResource(R.mipmap.ic_common_upblack);
                tvStoreType.setTextColor(getResources().getColor(R.color.drop_color));
                isShowSort = false;
                hashMap.remove("orderType");
                hashMap.put("orderType", "7");
                initData();
            }
        });*/
        FrameLayout fl_dismiss = (FrameLayout) conentView.findViewById(R.id.fl_dismiss);
        fl_dismiss.setAlpha(0.3f);
        fl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDistance.dismiss();
                isShowSort = false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(llDrawer)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
            return;
        } else {
            super.onBackPressed();
        }
        if (popupType != null && popupType.isShowing()) {
            popupType.dismiss();
            return;
        } else {
            super.onBackPressed();
        }
        if (popDistance != null && popDistance.isShowing()) {
            popDistance.dismiss();
            return;
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.bt_select)
    public void onViewClicked() {
        LayoutInflater inflater = (LayoutInflater) this
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
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        this.getWindow().setAttributes(lp);
        popWindowList.showAtLocation(rlKeyword, Gravity.TOP, 0, 0);
        popWindowList.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

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
        mainAdapter = new ClassifyMainAdapter(this, mainList);
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
                tvLocation.setText(LISTVIEWTXT[mainAdapter.getSelectItem()] + " " + moreAdapter.getItem(position));
                popWindowList.dismiss();

            }
        });
    }

    private void initAdapter(String[] strings) {
        moreAdapter = new ClassifyMoreAdapter(this, strings);
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
}
