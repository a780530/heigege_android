package com.tianfu.cutton.fragment.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hyphenate.chat.ChatClient;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.home.SortActivity;
import com.tianfu.cutton.activity.home.UploadingResourceActivity;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.activity.purchase.PurchaseDetailsActivity;
import com.tianfu.cutton.activity.purchase.ReleasePurchaseActivity;
import com.tianfu.cutton.activity.store.SearchActivity;
import com.tianfu.cutton.activity.store.SearchContentActivity;
import com.tianfu.cutton.activity.store.StoreBatchActivity;
import com.tianfu.cutton.activity.store.StoreKunActivity;
import com.tianfu.cutton.adapter.HomeNewPriceAdapter;
import com.tianfu.cutton.adapter.HomeRecylerAdapter;
import com.tianfu.cutton.adapter.ResourceAdapter;
import com.tianfu.cutton.adapter.SellerAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.fragment.adapter.AlphaPageTransformer;
import com.tianfu.cutton.model.AreaLintPriceByLastBean;
import com.tianfu.cutton.model.HomeNewPriceBean;
import com.tianfu.cutton.model.HomeSortBean;
import com.tianfu.cutton.model.NewPriceBean;
import com.tianfu.cutton.model.PurchaseDynamicsBean;
import com.tianfu.cutton.model.QualityCountBean;
import com.tianfu.cutton.model.ResourcesBean;
import com.tianfu.cutton.model.SellerBean;
import com.tianfu.cutton.model.VersionBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CallKefu;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.UltraViewPagerPro;
import com.tianfu.cutton.zxing.android.CaptureActivity;
import com.tmall.ultraviewpager.UltraViewPager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import util.UpdateAppUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, OnChartValueSelectedListener {
    private View mRootView;
    private List<PurchaseDynamicsBean.ValueBean> mDatas;
    private List<ResourcesBean.ValueBean> mResourcesData;
    private List<SellerBean.ValueBean> mSellerDatas;
    private List<HomeNewPriceBean> mNewDatas;
    private RollPagerView mRollViewPager;
    private RecyclerView recyler;
    private Map<String, String> hashMap;
    private ArrayList<Entry> pointValues;
    private HomeRecylerAdapter homeRecylerAdapter;
    private LinearLayout go_purchase;
    private String mobileMy;
    private Boolean isLogin;
    private String eMUserName;
    private String eMPassword;
    private UltraViewPager.Orientation gravity_indicator;
    private boolean isDownLoad = true;
    private UltraViewPagerPro ultraViewPager;
    private ImageView iv_message;
    private HomeSortBean.ValueBean sortData;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private static final int FIRST_PAGE = 1;
    private HomeSortBean.ValueBean mData;
    private AlertDialog.Builder mDialog;
    private String appName;
    private String versionLocation;
    private HomeNewPriceAdapter homeNewPriceAdapter;
    private RecyclerView homeNewPrice;
    private Handler handler;
    private ViewPager mapViewPager;
    private TextView tvIndex;
    private LineChart mChart;
    private RecyclerView resourceRecycler;
    private ResourceAdapter resourceAdapter;
    private RecyclerView sellerRecycler;
    private SellerAdapter sellerAdapter;
    private String[] values;
    private String[] valueDate;
    private int pageNumZi = 1;//当前周籽棉
    private int pageNumPi = 1;//当前周皮棉
    private int pageNumMian = 1;//当前周面纱
    private int ziState = 4;
    private int piState = 4;
    private int mianState = 4;
    private int datachange = 1;//
    private PagerAdapter mPagerAdapter;
    private List<List<String>> valueZiCotton;
    private List<String> date1;
    private List<List<String>> valuePiCotton;
    private List<String> date2;
    private List<List<String>> valueCotton;
    private List<String> date3;
    private TextView beforeWeek;
    private TextView nextWeek;
    private ArrayList<Integer> colors;
    private PtrClassicFrameLayout mPtrrame;
    private String max1;
    private String min1;
    private String max2;
    private String min2;
    private String max3;
    private String min3;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_home, container, false);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            ButterKnife.bind(this, mRootView);
            hashMap = new HashMap<>();
            hashMap.put("deviceNo", Common.deviceNo);
            hashMap.put("from", Common.from);
            hashMap.put("sourceTypeEnum", Common.sourceTypeEnum);
            hashMap.put("versionNo", Common.versionNo);
            hashMap.put("searchType", "2");
            hashMap.put("pageNum", "1");
            hashMap.put("orderType", "0");
            initViewpager();
            initView();
            handler = new Handler();
            handler.postDelayed(new TimerRunnable(), 60000);
            return mRootView;
        }
    }

    private void initNewPriceData() {
        Map map = new HashMap();
        HttpManager.getServerApi().getAreaLintPriceByLast(map).enqueue(new CallBack<AreaLintPriceByLastBean>() {
            @Override
            public void success(AreaLintPriceByLastBean data) {
                if (data.success) {
                    List<AreaLintPriceByLastBean.ValueBean> value = data.value;
                    int size = value.size() / 8;
                    if (value.size() % 8 > 0) {
                        size++;
                    }
                    initNewPriceView(size, value);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initSellerData() {
        Map map = new HashMap();
        HttpManager.getServerApi().listAllRecommendSeller(map).enqueue(new CallBack<SellerBean>() {
            @Override
            public void success(SellerBean data) {
                if (data.success) {
                    if (mSellerDatas != null) {
                        mSellerDatas.clear();
                        mSellerDatas.addAll(data.value);
                    } else {
                        mSellerDatas = data.value;
                    }
                    initSellerRecyclerView();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initSellerRecyclerView() {
        if (sellerAdapter == null) {
            sellerRecycler = (RecyclerView) mRootView.findViewById(R.id.sellerRecycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            sellerRecycler.setLayoutManager(layoutManager);
            sellerAdapter = new SellerAdapter(R.layout.home_recommended_seller_item, mSellerDatas);
            sellerRecycler.setAdapter(sellerAdapter);
            sellerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(getActivity(), SearchContentActivity.class);
                    intent.putExtra("contact", mSellerDatas.get(position).userName);
                    startActivity(intent);
                }
            });
        } else {
            sellerAdapter.notifyDataSetChanged();
        }

    }

    private void initMapViewPager(final List<List<String>> valueZiCotton, final List<List<String>> valuePiCotton, final List<List<String>> valueCotton, final List<String> date1, final List<String> date2, final List<String> date3) {
        mapViewPager = (ViewPager) mRootView.findViewById(R.id.mapViewPager);
        mapViewPager.setPageMargin(dip2px(BaseApplication.getContextObject(), 10));
        mapViewPager.setOffscreenPageLimit(3);
        mapViewPager.addOnPageChangeListener(this);
        mapViewPager.setAdapter(mPagerAdapter = new PagerAdapter() {
            @Override
            public void notifyDataSetChanged() {
                datachange = getCount();
                super.notifyDataSetChanged();
            }

            @Override
            public int getCount() {
                return 3 * 1000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.home_map_item, null);
                linearLayout.setId(R.id.item_id);
                mChart = (LineChart) linearLayout.findViewById(R.id.lineChat);
                inttChart(valueZiCotton, valuePiCotton, valueCotton, position, date1, date2, date3);
                container.addView(linearLayout);
                return linearLayout;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                LinearLayout view = (LinearLayout) object;
                container.removeView(view);
            }

            @Override
            public int getItemPosition(Object object) {
                if (datachange > 0) {
                    datachange--;
                    return POSITION_NONE;
                } else {

                }
                return super.getItemPosition(object);
            }

        });
        mapViewPager.setCurrentItem(1500 + currentPosition, false);
        mapViewPager.setPageTransformer(true, new AlphaPageTransformer());
    }


    private void inttChart(List<List<String>> valueZiCotton, List<List<String>> valuePiCotton, List<List<String>> valueCotton, int position, List<String> date1, List<String> date2, List<String> date3) {
        mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setHighlightPerDragEnabled(true);
        mChart.setPinchZoom(false);
        mChart.zoomIn();  //默认视图放大1.4倍，
        mChart.zoomOut();  //默认视图缩小到0.7倍，
        mChart.getAxisRight().setEnabled(false);
        mChart.setDrawGridBackground(false);
//        mChart.setBackgroundColor(Color.argb(200, 173, 215, 210));
        colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        setData(valueZiCotton, valuePiCotton, valueCotton, position, date1, date2, date3);
//        mChart.animateX(2500);
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.home_map_text);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);//是否自动换行
        l.setForm(Legend.LegendForm.LINE);
//        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        MyXFormatter formatter = new MyXFormatter(valueDate);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        if (position % 3 == 0) {
            int size = date1.size();
            xAxis.setLabelCount(size - 1);
        } else if (position % 3 == 1) {
            int size = date2.size();
            xAxis.setLabelCount(size - 1);
        } else if (position % 3 == 2) {
            int size = date3.size();
            xAxis.setLabelCount(size - 1);
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(formatter);
        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.isDrawAxisLineEnabled()
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextColor(Color.BLACK);
        if (position % 3 == 0) {
            if (!TextUtils.isEmpty(max1)) {
                leftAxis.setAxisMaximum(Float.parseFloat(max1.substring(0, 1)) + 1f);
            }
            if (!TextUtils.isEmpty(min1)) {
                leftAxis.setAxisMinimum(Float.parseFloat(min1.substring(0, 1)));
            }
        } else if (position % 3 == 1) {
            if (!TextUtils.isEmpty(max2)) {
                leftAxis.setAxisMaximum(Float.parseFloat(max2));
            }
            if (!TextUtils.isEmpty(min2)) {
                leftAxis.setAxisMinimum(Float.parseFloat(min2));
            }
        } else if (position % 3 == 2) {
            if (!TextUtils.isEmpty(max3)) {
                leftAxis.setAxisMaximum(Float.parseFloat(max3));
            }
            if (!TextUtils.isEmpty(min3)) {
                leftAxis.setAxisMinimum(Float.parseFloat(min3));
            }
        }
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
    }

    private void setData(List<List<String>> valueZiCotton, List<List<String>> valuePiCotton, List<List<String>> valueCotton, int position, List<String> date1, List<String> date2, List<String> date3) {
        if (position % 3 == 0) {
            values = date1.toArray(new String[date1.size()]);
            changeDate(values);
            List<String> vals1 = valueZiCotton.get(0);
            List<String> vals2 = valueZiCotton.get(1);
            List<String> vals3 = valueZiCotton.get(2);
            List<String> vals4 = valueZiCotton.get(3);
            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            for (int i = 0; i < vals1.size(); i++) {
                yVals1.add(new Entry(i, Float.parseFloat(vals1.get(i))));
            }
            ArrayList<Entry> yVals2 = new ArrayList<Entry>();
            for (int i = 0; i < vals2.size(); i++) {
                yVals2.add(new Entry(i, Float.parseFloat(vals2.get(i))));
            }
            ArrayList<Entry> yVals3 = new ArrayList<Entry>();
            for (int i = 0; i < vals3.size(); i++) {
                yVals3.add(new Entry(i, Float.parseFloat(vals3.get(i))));
            }
            ArrayList<Entry> yVals4 = new ArrayList<Entry>();
            for (int i = 0; i < vals4.size(); i++) {
                yVals4.add(new Entry(i, Float.parseFloat(vals4.get(i))));
            }
            init4Map(yVals1, yVals2, yVals3, yVals4);
        } else if (position % 3 == 1) {
            values = date2.toArray(new String[date2.size()]);
            changeDate(values);
            List<String> vals1 = valuePiCotton.get(0);
            List<String> vals2 = valuePiCotton.get(1);
            List<String> vals3 = valuePiCotton.get(2);
            List<String> vals4 = valuePiCotton.get(3);
            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            for (int i = 0; i < vals1.size(); i++) {
                yVals1.add(new Entry(i, Float.parseFloat(vals1.get(i))));
            }

            ArrayList<Entry> yVals2 = new ArrayList<Entry>();
            for (int i = 0; i < vals2.size(); i++) {
                yVals2.add(new Entry(i, Float.parseFloat(vals2.get(i))));
            }

            ArrayList<Entry> yVals3 = new ArrayList<Entry>();
            for (int i = 0; i < vals3.size(); i++) {
                yVals3.add(new Entry(i, Float.parseFloat(vals3.get(i))));
            }

            ArrayList<Entry> yVals4 = new ArrayList<Entry>();
            for (int i = 0; i < vals4.size(); i++) {
                yVals4.add(new Entry(i, Float.parseFloat(vals4.get(i))));
            }
            init4Map(yVals1, yVals2, yVals3, yVals4);
        } else if (position % 3 == 2) {
            changeDate(values);
            values = date3.toArray(new String[date3.size()]);
            List<String> vals1 = valueCotton.get(0);
            List<String> vals2 = valueCotton.get(1);
            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            for (int i = 0; i < vals1.size(); i++) {
                yVals1.add(new Entry(i, Float.parseFloat(vals1.get(i))));
            }
            ArrayList<Entry> yVals2 = new ArrayList<Entry>();
            for (int i = 0; i < vals2.size(); i++) {
                yVals2.add(new Entry(i, Float.parseFloat(vals2.get(i))));
            }
            init2Map(yVals1, yVals2);
        }


    }

    private void changeDate(String[] values) {
        List<String> list = new ArrayList();
        for (int i = 0; i < values.length; i++) {
            if (values[i].contains("月")) {
                String s = values[i].replace("月", "/");
                if (s.contains("日")) {
                    s = s.replace("日", "");
                }
                list.add(s);
            }
        }
        valueDate = list.toArray(new String[list.size()]);
    }

    private void init2Map(ArrayList<Entry> yVals1, ArrayList<Entry> yVals2) {
        LineDataSet set1, set2;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "精梳32支");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(colors.get(1));
            set1.setCircleColor(ColorTemplate.getHoloBlue());
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
//            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            set1.setDrawValues(false);

            set2 = new LineDataSet(yVals2, "普梳32支");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(colors.get(3));
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
//            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setDrawValues(false);
//            set2.setHighLightColor(Color.rgb(244, 117, 117));
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
            mChart.setData(data);
        }
    }

    private void init4Map(ArrayList<Entry> yVals1, ArrayList<Entry> yVals2, ArrayList<Entry> yVals3, ArrayList<Entry> yVals4) {
        LineDataSet set1, set2, set3, set4;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set4 = (LineDataSet) mChart.getData().getDataSetByIndex(3);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            set4.setValues(yVals4);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "北疆机采棉");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//            set1.setColors(colors);
            set1.setColor(colors.get(0));
            set1.setCircleColor(ColorTemplate.getHoloBlue());
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
//            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            set1.setDrawValues(false);

            set2 = new LineDataSet(yVals2, "阿克苏手摘棉");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(colors.get(1));
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
//            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setDrawValues(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));

            set3 = new LineDataSet(yVals3, "喀什手摘棉");
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            set3.setColor(colors.get(2));
            set3.setCircleColor(Color.YELLOW);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setDrawValues(false);
//            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            set4 = new LineDataSet(yVals4, "库尔勒手摘棉");
            set4.setAxisDependency(YAxis.AxisDependency.LEFT);
            set4.setColor(colors.get(3));
            set4.setCircleColor(Color.GREEN);
            set4.setLineWidth(2f);
            set4.setCircleRadius(3f);
            set4.setDrawValues(false);
            set4.setFillAlpha(65);
//            set4.setFillColor(ColorTemplate.colorWithAlpha(Color.GREEN, 200));
            set4.setDrawCircleHole(false);
            set4.setHighLightColor(Color.rgb(244, 117, 117));

            LineData data = new LineData(set1, set2, set3, set4);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
            mChart.setData(data);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler = null; //此处在Activity退出时及时 回收
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    class TimerRunnable implements Runnable {

        @Override
        public void run() {
            if (mViewPager != null) {
                int curItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(curItem + 1);
                if (handler != null) {
                    handler.postDelayed(this, 60000);
                }
            }
        }
    }

    private void initNewPriceView(final int size, final List<AreaLintPriceByLastBean.ValueBean> value) {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.id_viewpager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return size * 500;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
       /*     LinearLayout view = (LinearLayout) object;
            container.removeView(view);*/
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.home_item_new_price, null);
                TextView new_Location1 = (TextView) linearLayout.findViewById(R.id.new_Location1);
                TextView new_price1 = (TextView) linearLayout.findViewById(R.id.new_price1);
                TextView new_Location2 = (TextView) linearLayout.findViewById(R.id.new_Location2);
                TextView new_price2 = (TextView) linearLayout.findViewById(R.id.new_price2);
                TextView new_Location3 = (TextView) linearLayout.findViewById(R.id.new_Location3);
                TextView new_price3 = (TextView) linearLayout.findViewById(R.id.new_price3);
                TextView new_Location4 = (TextView) linearLayout.findViewById(R.id.new_Location4);
                TextView new_price4 = (TextView) linearLayout.findViewById(R.id.new_price4);
                TextView new_Location5 = (TextView) linearLayout.findViewById(R.id.new_Location5);
                TextView new_price5 = (TextView) linearLayout.findViewById(R.id.new_price5);
                TextView new_Location6 = (TextView) linearLayout.findViewById(R.id.new_Location6);
                TextView new_price6 = (TextView) linearLayout.findViewById(R.id.new_price6);
                TextView new_Location7 = (TextView) linearLayout.findViewById(R.id.new_Location7);
                TextView new_price7 = (TextView) linearLayout.findViewById(R.id.new_price7);
                TextView new_Location8 = (TextView) linearLayout.findViewById(R.id.new_Location8);
                TextView new_price8 = (TextView) linearLayout.findViewById(R.id.new_price8);
                new_price1.setText(position % size + "000");
                int count = (position % size) * 8;
                for (int i = count; i < count + 8; i++) {
                    if (i >= value.size()) {
                        break;
                    }
                    if (i == count) {
                        new_Location1.setText(value.get(i).name);
                        new_price1.setText(value.get(i).value);
                    } else if (i == count + 1) {
                        new_Location2.setText(value.get(i).name);
                        new_price2.setText(value.get(i).value);
                    } else if (i == count + 2) {
                        new_Location3.setText(value.get(i).name);
                        new_price3.setText(value.get(i).value);
                    } else if (i == count + 3) {
                        new_Location4.setText(value.get(i).name);
                        new_price4.setText(value.get(i).value);
                    } else if (i == count + 4) {
                        new_Location5.setText(value.get(i).name);
                        new_price5.setText(value.get(i).value);
                    } else if (i == count + 5) {
                        new_Location6.setText(value.get(i).name);
                        new_price6.setText(value.get(i).value);
                    } else if (i == count + 6) {
                        new_Location7.setText(value.get(i).name);
                        new_price7.setText(value.get(i).value);
                    } else if (i == count + 7) {
                        new_Location8.setText(value.get(i).name);
                        new_price8.setText(value.get(i).value);
                    }
                }

                container.addView(linearLayout);
                return linearLayout;

            }
        });
        mViewPager.setCurrentItem(size * 250);
    }


    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private static final int REQUEST_CODE_SCAN = 0x0000;
    private int currentPosition = 1;

    /**
     * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直被调用。
     *
     * @param position             索引值 当前页面，及你点击滑动的页面
     * @param positionOffset       0-1 当前页面偏移的百分比
     * @param positionOffsetPixels 当前页面偏移的像素位置
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 滑动状态改变的方法 state :draaging 拖拽 idle 静止 settling 惯性过程
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position % 3;
        if (position % 3 == 0) {
            showState(ziState);
            tvIndex.setText("籽棉价格指数");
        } else if (position % 3 == 1) {
            showState(piState);
            tvIndex.setText("皮棉价格指数");
        } else if (position % 3 == 2) {
            showState(mianState);
            tvIndex.setText("棉纱价格指数");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    private void initSortMapData() {
        Map map = new HashMap();
        map.put("type", "4");
        HttpManager.getServerApi().getPriceIndexByType(map).enqueue(new CallBack<NewPriceBean>() {
            @Override
            public void success(NewPriceBean data) {
                if (data.success && data.value.size() > 0) {
                    if (data.value.get(0).currPage == data.value.get(0).totalPage) {
                        piState = 5;
                        showState(5);
                    }
                    if (data.value.get(1).currPage == data.value.get(1).totalPage) {
                        ziState = 5;
                        showState(5);
                    }
                    if (data.value.get(2).currPage == data.value.get(2).totalPage) {
                        mianState = 5;
                        showState(5);
                    }
                    List<NewPriceBean.ValueBean> valueData = data.value;
                    valueZiCotton = valueData.get(1).value;
                    date1 = valueData.get(1).date;
                    max1 = valueData.get(1).max;
                    System.out.println("0...." + valueData.get(1).max);
                    min1 = valueData.get(1).min;
                    valuePiCotton = valueData.get(0).value;
                    date2 = valueData.get(0).date;
                    max2 = valueData.get(0).max;
                    min2 = valueData.get(0).min;
                    valueCotton = valueData.get(2).value;
                    date3 = valueData.get(2).date;
                    max3 = valueData.get(2).max;
                    min3 = valueData.get(2).min;
                    initMapViewPager(valueZiCotton, valuePiCotton, valueCotton, date1, date2, date3);
                } else {
                    beforeWeek.setEnabled(false);
                    nextWeek.setEnabled(false);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }


    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            Intent intent = new Intent(getActivity(),
                    CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(getActivity(), deniedPermissions)) {
                // 第一种：用AndPermission默认的提示语。
                AndPermission.defaultSettingDialog(getActivity(), 400).show();
            } else {
                return;
            }
        }
    };

    private void initView() {
        LinearLayout go_sort = (LinearLayout) mRootView.findViewById(R.id.go_sort);
        go_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SortActivity.class));
            }
        });
        LinearLayout home_zixing = (LinearLayout) mRootView.findViewById(R.id.home_zixing);
        home_zixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndPermission.with(getActivity())
                        .permission(Manifest
                                .permission.CAMERA)
                        .callback(listener)
                        .start();
            }
        });
        iv_message = (ImageView) mRootView.findViewById(R.id.iv_message);
        tvIndex = (TextView) mRootView.findViewById(R.id.tvIndex);
        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                iv_message.setImageResource(R.mipmap.ic_mine_message);
                CallKefu.callOnLine(getActivity(), eMUserName, eMPassword);
            }
        });
        LinearLayout ll_search = (LinearLayout) mRootView.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseApplication.getContextObject(), SearchActivity.class));
            }
        });
    /*    mRootView.findViewById(R.id.ll_quality).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity) getActivity()).mViewPager.setCurrentItem(2);
            }
        });*/
        mRootView.findViewById(R.id.go_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                //查资源
                startActivity(new Intent(getActivity(), UploadingResourceActivity.class));
            }
        });
        go_purchase = (LinearLayout) mRootView.findViewById(R.id.go_purchase);
        go_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), ReleasePurchaseActivity.class));
            }
        });
        beforeWeek = (TextView) mRootView.findViewById(R.id.beforeWeek);
        nextWeek = (TextView) mRootView.findViewById(R.id.nextWeek);
        beforeWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    /*            beforeWeek.setTextColor(getResources().getColor(R.color.white));
                beforeWeek.setBackgroundResource(R.mipmap.ic_left_red);
                nextWeek.setEnabled(true);
                nextWeek.setTextColor(getResources().getColor(R.color.tabColor));
                nextWeek.setBackgroundResource(R.drawable.ic_right_white);*/
                Map map = new HashMap();
                if (currentPosition == 0) {
                    pageNumZi++;
                    ziState = 2;
                    showState(ziState);
                    map.put("type", "2");
                    map.put("pageNum", pageNumZi + "");
                } else if (currentPosition == 1) {
                    pageNumPi++;
                    piState = 2;
                    showState(ziState);
                    map.put("type", "1");
                    map.put("pageNum", pageNumPi + "");
                } else if (currentPosition == 2) {
                    pageNumMian++;
                    mianState = 2;
                    showState(mianState);
                    map.put("type", "3");
                    map.put("pageNum", pageNumMian + "");
                }
                initoneMapData(map);
            }
        });
        nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                if (currentPosition == 0) {
                    map.put("type", "2");
                    pageNumZi--;
                    ziState = 3;
                    showState(ziState);
                    map.put("pageNum", pageNumZi + "");
                } else if (currentPosition == 1) {
                    map.put("type", "1");
                    pageNumPi--;
                    piState = 3;
                    showState(piState);
                    map.put("pageNum", pageNumPi + "");
                } else if (currentPosition == 2) {
                    pageNumMian--;
                    mianState = 3;
                    showState(mianState);
                    map.put("type", "3");
                    map.put("pageNum", pageNumMian + "");
                }
                initoneMapData(map);
            }
        });
        mPtrrame = (PtrClassicFrameLayout) mRootView.findViewById(R.id.ptr_purchase_frame);
        mPtrrame.disableWhenHorizontalMove(true);
        initPtr();
    }

    private void initPtr() {
        mPtrrame.setPtrHandler(new PtrDefaultHandler() {


            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadLazyData();
                initSortMapData();
                initSellerData();
                initResourcesData();
                initNewPriceData();
                mPtrrame.refreshComplete();
            }
        });
    }


    private void initoneMapData(Map map) {
        HttpManager.getServerApi().getPriceIndexByType(map).enqueue(new CallBack<NewPriceBean>() {
            @Override
            public void success(NewPriceBean data) {
                if (data.success) {
                    if (currentPosition == 0) {
                        if (data.value.get(0).currPage == data.value.get(0).totalPage) {
                            ziState = 1;
                            showState(ziState);
                        } else if (pageNumZi == 1) {
                            ziState = 4;
                            showState(ziState);
                        }
                        valueZiCotton = data.value.get(0).value;
                        date1 = data.value.get(0).date;
                        max1 = data.value.get(0).max;
                        System.out.println("............" + data.value.get(0).max);
                        min1 = data.value.get(0).min;
                        initMapViewPager(valueZiCotton, valuePiCotton, valueCotton, date1, date2, date3);
                        mPagerAdapter.notifyDataSetChanged();
                    } else if (currentPosition == 1) {
                        if (data.value.get(0).currPage == data.value.get(0).totalPage) {
                            piState = 1;
                            showState(piState);
                        } else if (pageNumPi == 1) {
                            piState = 4;
                            showState(piState);
                        }
                        valuePiCotton = data.value.get(0).value;
                        date2 = data.value.get(0).date;
                        max2 = data.value.get(0).max;
                        min2 = data.value.get(0).min;
                        initMapViewPager(valueZiCotton, valuePiCotton, valueCotton, date1, date2, date3);
                        mPagerAdapter.notifyDataSetChanged();
                    } else if (currentPosition == 2) {
                        if (data.value.get(0).currPage == data.value.get(0).totalPage) {
                            mianState = 1;
                            showState(mianState);
                        } else if (pageNumMian == 1) {
                            mianState = 4;
                            showState(mianState);
                        }
                        valueCotton = data.value.get(0).value;
                        date3 = data.value.get(0).date;
                        max3 = data.value.get(0).max;
                        min3 = data.value.get(0).min;
                        initMapViewPager(valueZiCotton, valuePiCotton, valueCotton, date1, date2, date3);
                        mPagerAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
            }
        });
    }

    private void showState(int pageNumMian) {
        switch (pageNumMian) {
            case 1:
                //左边滑到头
                nextWeek.setTextColor(getResources().getColor(R.color.tabColor));
                nextWeek.setBackgroundResource(R.mipmap.ic_right_white);
                nextWeek.setEnabled(true);
                beforeWeek.setEnabled(false);
                beforeWeek.setTextColor(getResources().getColor(R.color.tabColor));
                beforeWeek.setBackgroundResource(R.mipmap.ic_left_white);
                break;
            case 2:
                //中间位置 左边红
                nextWeek.setTextColor(getResources().getColor(R.color.tabColor));
                nextWeek.setBackgroundResource(R.mipmap.ic_right_white);
                nextWeek.setEnabled(true);
                beforeWeek.setEnabled(true);
                beforeWeek.setTextColor(getResources().getColor(R.color.white));
                beforeWeek.setBackgroundResource(R.mipmap.ic_left_red);
                break;
            case 3:
                //中间位置 右边红
                nextWeek.setTextColor(getResources().getColor(R.color.white));
                nextWeek.setBackgroundResource(R.mipmap.ic_right_red);
                nextWeek.setEnabled(true);
                beforeWeek.setEnabled(true);
                beforeWeek.setTextColor(getResources().getColor(R.color.tabColor));
                beforeWeek.setBackgroundResource(R.mipmap.ic_left_white);
                break;
            case 4:
                //右边滑到头
                nextWeek.setTextColor(getResources().getColor(R.color.tabColor));
                nextWeek.setBackgroundResource(R.mipmap.ic_right_white);
                nextWeek.setEnabled(false);
                beforeWeek.setEnabled(true);
                beforeWeek.setTextColor(getResources().getColor(R.color.tabColor));
                beforeWeek.setBackgroundResource(R.mipmap.ic_left_white);
                break;
            case 5:
                //只有一周
                nextWeek.setTextColor(getResources().getColor(R.color.tabColor));
                nextWeek.setBackgroundResource(R.mipmap.ic_right_white);
                nextWeek.setEnabled(false);
                beforeWeek.setEnabled(false);
                beforeWeek.setTextColor(getResources().getColor(R.color.tabColor));
                beforeWeek.setBackgroundResource(R.mipmap.ic_left_white);
                break;
        }
    }

    private void initRecylerView() {
        if (homeRecylerAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyler = ((RecyclerView) mRootView.findViewById(R.id.homeRecyler));
            //自动测量
            layoutManager.setAutoMeasureEnabled(true);
            //禁用滑动
            recyler.setNestedScrollingEnabled(false);
            recyler.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            homeRecylerAdapter = new HomeRecylerAdapter(R.layout.fragment_home_recyler_item, mDatas);
            recyler.setAdapter(homeRecylerAdapter);
            homeRecylerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    PurchaseDynamicsBean.ValueBean valueBean = mDatas.get(position);
                    intent.putExtra("PurchaseDynamicsBean", valueBean);
                    intent.setClass(getActivity(), PurchaseDetailsActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            homeRecylerAdapter.notifyDataSetChanged();
        }
    }

    private void addHistory(String id, String batchType, String code, String mobileMy) {
        Map<String, String> mapHistory = new HashMap<>();
        mapHistory.put("isProduct", "1");
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

    private void initViewpager() {
        mRollViewPager = (RollPagerView) mRootView.findViewById(R.id.roll_view_pager);
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter(mRollViewPager));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), getResources().getColor(R.color.black_33), Color.WHITE));
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
        isDownLoad = true;
        isLogin = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isLogin");
        eMUserName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMUserName");
        eMPassword = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMPassword");
    }

    protected void onVisible() {
        if (!isVisible || !isPrepared) {
            return;
        }
        if (isDownLoad) {
            loadLazyData();
            initSortMapData();
            initSellerData();
            initResourcesData();
            initNewPriceData();
        }
    }

    private void initResourcesData() {
        Map map = new HashMap();
        map.put("devieNo", Common.deviceNo);
        HttpManager.getServerApi().cheapProduct(map).enqueue(new CallBack<ResourcesBean>() {
            @Override
            public void success(ResourcesBean data) {
                if (data.success) {
                    if (mResourcesData != null) {
                        mResourcesData.clear();
                        mResourcesData.addAll(data.value);
                    } else {
                        mResourcesData = data.value;
                    }
                    initResourceRecycler();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initResourceRecycler() {
        if (resourceAdapter == null) {
            resourceRecycler = (RecyclerView) mRootView.findViewById(R.id.resourceRecycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            resourceRecycler.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            resourceAdapter = new ResourceAdapter(R.layout.fragment_home_special_resources_item, mResourcesData);
            resourceRecycler.setAdapter(resourceAdapter);
            resourceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String batchType = mResourcesData.get(position).batchType;
                    int productId = mResourcesData.get(position).id;
                    String mobileBaojia = mResourcesData.get(position).mobile;
                    String code = mResourcesData.get(position).code;
                    addHistory(productId + "", batchType, code, mobileMy);
                    if (batchType.equals("1")) {
                        Intent intent = new Intent(getActivity(), StoreKunActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("productId", productId + "");
                        startActivity(intent);
                    } else if (batchType.equals("2")) {
                        Intent intent = new Intent(getActivity(), StoreBatchActivity.class);
                        intent.putExtra("productId", productId + "");
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("fromKun", "0");
                        intent.putExtra("code", code);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), StoreBatchActivity.class);
                        intent.putExtra("productId", productId + "");
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("mobileBaojia", mobileBaojia);
                        intent.putExtra("fromKun", "1");
                        intent.putExtra("code", code);
                        startActivity(intent);
                    }
                }
            });
        } else {
            resourceAdapter.notifyDataSetChanged();
        }
    }


    private void loadLazyData() {
        Map map = new HashMap();
        map.put("deviceNo", Common.deviceNo);
        HttpManager.getServerApi().purchaseDynamics(map).enqueue(new CallBack<PurchaseDynamicsBean>() {
            @Override
            public void success(PurchaseDynamicsBean data) {
                if (data.success) {
                    if (mDatas != null) {
                        mDatas.clear();
                        mDatas.addAll(data.value);
                    } else {
                        mDatas = data.value;
                    }
                    initRecylerView();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private class TestNormalAdapter extends LoopPagerAdapter {

        private int[] imgs = {R.mipmap.ic_banner1, R.mipmap.ic_banner2};  // 本地图片
        private int count = imgs.length;

        public TestNormalAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            final int picNo = position + 1;
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setOnClickListener(new View.OnClickListener()      // 点击事件
            {
                @Override
                public void onClick(View v) {

                }

            });

            return view;
        }

        @Override
        public int getRealCount() {
            return count;
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
        iv_message.setImageResource(R.mipmap.ic_mine_message);
        mobileMy = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile");
        if (ChatClient.getInstance().isLoggedInBefore()) {
            CallKefu.addMessageListener(iv_message);
        }
        isUpdate();
    }

    private void isUpdate() {
        Map<String, String> map = new HashMap<>();
        map.put("from", Common.from);
        HttpManager.getServerApi().getAppVersion(map).enqueue(new CallBack<VersionBean>() {
            @Override
            public void success(VersionBean data) {
                if (data.success) {
                    String appCode = data.value.appCode;
                    int forceUpdate = data.value.forceUpdate;//是否强制更新 1强制更新,0不强制更新
                    String downloadUrl = data.value.downloadUrl;//app下载地址
//                    String downloadUrl = "http://issuecdn.baidupcs.com/issue/netdisk/apk/BaiduNetdisk_7.15.1.apk";
                    String appVersion = data.value.appVersion;//appVersion
                    String appName = data.value.appName;//app名称
                    String versionNo = Common.deviceNo;
                    if (forceUpdate == 1) {
                        //强制更新
                        if (appVersion != null && downloadUrl != null && appCode != null) {
                            UpdateAppUtils.from(getActivity())
                                    .serverVersionCode(Integer.valueOf(appCode))
                                    .serverVersionName(appVersion)
                                    .apkPath(downloadUrl)
                                    .isForce(true)
                                    .update();
                        }
                    } else {
                        if (!SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isUpdate")) {
                            if (appVersion != null && downloadUrl != null && appCode != null) {
                                UpdateAppUtils.from(getActivity())
                                        .serverVersionCode(Integer.valueOf(appCode))
                                        .serverVersionName(appVersion)
                                        .apkPath(downloadUrl)
                                        .update();
                                boolean isUpdate = true;
                                SharedPreferencesUtil.saveBooleanValue(BaseApplication.getContextObject(), "isUpdate", isUpdate);
                            }
                        }
                    }
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }
}
