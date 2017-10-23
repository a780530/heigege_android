package com.tianfu.cutton.fragment.store;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.quality.ChartUtils;
import com.tianfu.cutton.model.QualityBagBean;
import com.tianfu.cutton.model.StoreKunMessage;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreBatchMapFragment extends BaseFragment {

    @BindView(R.id.mPieChart)
    PieChart mPieChart;
    private View mRootView;
    private ScatterChart mScatterChart;
    private ScatterChart mBlScatterChart;
    private PieChart mYgPieChart;
    private Map map;
    private String productId;
    private ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_quality_batch_map, container, false);
            ButterKnife.bind(mRootView);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            //initView();
            String code = getActivity().getIntent().getStringExtra("code");
            productId = getActivity().getIntent().getStringExtra("productId");
            imageView = (ImageView) mRootView.findViewById(R.id.imageView);
            initIsOn();
            map = new HashMap();
            map.put("code", code);
            initData();
            return mRootView;
        }
    }

    private void initIsOn() {
        final ScrollView ssMap = (ScrollView) mRootView.findViewById(R.id.ss_map);
        final LinearLayout nodata = (LinearLayout) mRootView.findViewById(R.id.nodata);
        Map mapIsOn = new HashMap();
        mapIsOn.put("productIds", productId);
        HttpManager.getServerApi().getStoreKunMessage(mapIsOn).enqueue(new CallBack<StoreKunMessage>() {
            @Override
            public void success(StoreKunMessage data) {
                if (data.success) {
                    ssMap.setVisibility(View.VISIBLE);
                    nodata.setVisibility(View.GONE);
                } else {
                    ssMap.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initData() {
        HttpManager.getServerApi().getBagMessage(map).enqueue(new CallBack<QualityBagBean>() {
            @Override
            public void success(QualityBagBean data) {
                showProgressBar("", true);
                if (data.success && data != null && data.value.rows.size() > 0) {
                    // 绘制长度分布饼状图
                    mPieChart = (PieChart) mRootView.findViewById(R.id.mPieChart);
                    initPieChart(data, mPieChart);
                    // 绘制马值散点图
                    mScatterChart = (ScatterChart) mRootView.findViewById(R.id.mScatterChart);
                    //画平均线
                    int size = data.value.rows.size();
                    float aFloat = 0f;
                    for (int i = 0; i < size; i++) {
                        aFloat += Float.valueOf(data.value.rows.get(i).micron);
                    }
                    float v = aFloat / data.value.total;
                    initScatterChart(data, mScatterChart);
                    LimitLine limitLine = new LimitLine(v);
                    limitLine.setLineColor(Color.BLUE);
                    mScatterChart.getAxisLeft().addLimitLine(limitLine);

                    // 轧工质量饼状图
                    mYgPieChart = (PieChart) mRootView.findViewById(R.id.mYgPieChart1);
                    if (!TextUtils.isEmpty(data.value.rows.get(0).yg)) {
                        initPieChart(data, mYgPieChart);
                    }else{
                        mYgPieChart.setVisibility(View.INVISIBLE);
                    }

                    // 强力散点图
                    mBlScatterChart = (ScatterChart) mRootView.findViewById(R.id.mBlScatterChart);
                    initScatterChart(data, mBlScatterChart);

                    //画平均线
                    float aFloatS = 0f;
                    for (int i = 0; i < size; i++) {
                        aFloatS += Float.valueOf(data.value.rows.get(i).breakLoad);
                    }
                    float vBreakLoad = aFloatS / data.value.total;
                    System.out.println("vBreakLoad:" + vBreakLoad);
                    LimitLine limitLineS = new LimitLine(vBreakLoad);
                    limitLineS.setLineColor(Color.BLUE);
                    mBlScatterChart.getAxisLeft().addLimitLine(limitLineS);
                    dismissProgressBar();
                    imageView.setVisibility(View.GONE);
                } else {
                    ToastUtil.show(BaseApplication.getContextObject(), "暂无数据");
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initYgPieChart(QualityBagBean data) {
        mYgPieChart = mPieChart;
        //填充数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (Map.Entry<String, Float> entry : ChartUtils.getYg(data).entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey() + "mm "));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setFormLineWidth(5f);
        dataSet.setValueLineVariableLength(true);
        dataSet.setSelectionShift(5f);
        // 折线
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(1f);
        dataSet.setValueLineColor(Color.DKGRAY);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
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
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data1 = new PieData(dataSet);
        data1.setValueFormatter(new PercentFormatter());
        data1.setValueTextSize(11f);
        data1.setValueTextColor(Color.BLACK);
        mYgPieChart.setData(data1);
        mYgPieChart.highlightValues(null);
        //刷新
        mYgPieChart.invalidate();
    }

    /**
     * 绘制散点图
     *
     * @param data
     * @param mScatterChart
     */
    private void initScatterChart(QualityBagBean data, ScatterChart mScatterChart) {
        mScatterChart.getDescription().setEnabled(false);
        mScatterChart.setDrawGridBackground(false);
        mScatterChart.setTouchEnabled(true);
//        mScatterChart.leftaxis
        mScatterChart.setMaxHighlightDistance(10f);

        // 支持缩放和拖动
        mScatterChart.setDragEnabled(false);
        mScatterChart.setScaleEnabled(false);
        mScatterChart.setMaxVisibleValueCount(6);
        mScatterChart.setVisibleXRangeMinimum(3);
        mScatterChart.setPinchZoom(false);


        Legend l = mScatterChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.set
        l.setDrawInside(false);
        l.setXOffset(5f);

        setScatterChartData(data, mScatterChart);

    }

    private void setScatterChartData(QualityBagBean data, ScatterChart mScatterChart) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        int count = 1;
        int size = data.value.rows.size();

        if ((size % 10) != 0) {
            size = 10 - (size % 10);
        } else {
            size = 0;
        }
        for (QualityBagBean.ValueBean.RowsBean rowsBean : data.value.rows) {
            float micron = Float.parseFloat(rowsBean.micron);
            if (micron < 5)
                yVals1.add(new Entry(count, micron));
            else
                yVals2.add(new Entry(count, micron));
            count++;
        }
        if (mScatterChart.getId() == R.id.mBlScatterChart) {
            count = 1;
            yVals1.clear();
            yVals2.clear();
            for (QualityBagBean.ValueBean.RowsBean rowsBean : data.value.rows) {
                char asc = rowsBean.breakLoad.toCharArray()[0];
                if (asc >= 48 && asc <= 57) { // 数字开头
                    float breakLoad = Float.parseFloat(rowsBean.breakLoad);
                    yVals1.add(new Entry(count, breakLoad));
                    count++;
                } else {
                    yVals1.add(new Entry(count, 0));
                    count++;
                }

            }
        }
        for (int i = 0; i < size; i++) {
            yVals1.add(new Entry(count, 0));
        }
        YAxis yl = mScatterChart.getAxisLeft();

        if (mScatterChart.getId() == R.id.mBlScatterChart) {
            yl.resetAxisMinimum();
            yl.setLabelCount(5, true);
            yl.setAxisMinimum(25f);
            yl.setAxisMaximum(35f);
        } else {
            yl.setAxisMinimum(3);
            yl.setAxisMaximum(6);
        }
        mScatterChart.getAxisRight().setEnabled(false);
        XAxis xl = mScatterChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAxisMinimum(0);
        xl.setAxisMaximum(data.value.rows.size() + size);
        xl.setLabelCount((data.value.rows.size() + size) / 10 + 1);
        xl.setLabelCount((data.value.rows.size() + size) / 10 + 1, true);
        if (((data.value.rows.size() + size) / 10) > 12) {
            xl.setTextSize(7);
        }
        xl.setDrawGridLines(true);
        //创建一个数据集,并给它一个类型
        //设置颜色
        ScatterDataSet set1 = new ScatterDataSet(yVals1, "");
        ScatterDataSet set2 = new ScatterDataSet(yVals2, "");
        set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set2.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[1]);
        set2.setScatterShapeHoleRadius(2.5f);
        set2.setColor(Color.rgb(255, 255, 255));
        set2.setScatterShapeSize(4f);
        set1.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set1.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[3]);
        set1.setScatterShapeHoleRadius(2f);
        set1.setColor(Color.rgb(255, 255, 255));
        set1.setScatterShapeSize(4f);
        set1.setFormSize(2);
        ArrayList<IScatterDataSet> dataSets = new ArrayList<IScatterDataSet>();
        dataSets.add(set2);
        dataSets.add(set1);

        //创建一个数据集的数据对象
        ScatterData data1 = new ScatterData(dataSets);
//        mScatterChart.set
        mScatterChart.setData(data1);
        mScatterChart.invalidate();
    }

    private void initPieChart(QualityBagBean data, PieChart mPieChart) {
        //饼状图
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setEntryLabelTextSize(10);
        mPieChart.setCenterTextSize(10);

        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);
        mPieChart.setDrawCenterText(true);
        mPieChart.setRotationAngle(0);
        //填充数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (Map.Entry<String, Float> entry : ChartUtils.getLenght(data).entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey() + "mm "));
        }
        if (mPieChart.getId() == R.id.mYgPieChart1) {
            entries.clear();
            for (Map.Entry<String, Float> entry : ChartUtils.getYg(data).entrySet()) {
                entries.add(new PieEntry(entry.getValue(), entry.getKey()));
            }
        }
        //设置数据
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = mPieChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setFormLineWidth(12f);
        l.setTextSize(10f);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(10f);
        l.setDrawInside(false);
        l.setXEntrySpace(10f);
        l.setYEntrySpace(5f);
        // 输入标签样式
        mPieChart.setDrawEntryLabels(false);
        setData(entries, mPieChart);

    }

    private void initView() {
        //mScatterChart = (ScatterChart) mRootView.findViewById(R.id.mScatterChart);
        mScatterChart.getDescription().setEnabled(false);
        mScatterChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        mScatterChart.setDrawGridBackground(false);
        mScatterChart.setTouchEnabled(true);
        mScatterChart.setMaxHighlightDistance(10f);

        // 支持缩放和拖动
        mScatterChart.setDragEnabled(true);
        mScatterChart.setScaleEnabled(true);

        mScatterChart.setMaxVisibleValueCount(10);
        mScatterChart.setPinchZoom(true);

        Legend l = mScatterChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXOffset(5f);

        YAxis yl = mScatterChart.getAxisLeft();
        yl.setAxisMinimum(0f);
        mScatterChart.getAxisRight().setEnabled(false);

        XAxis xl = mScatterChart.getXAxis();
        xl.setDrawGridLines(false);

    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries, PieChart mPieChart) {

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setFormLineWidth(5f);
        dataSet.setValueLineVariableLength(true);
        dataSet.setSelectionShift(5f);
        // 折线
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(1f);
        dataSet.setValueLineColor(Color.DKGRAY);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
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
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

}
