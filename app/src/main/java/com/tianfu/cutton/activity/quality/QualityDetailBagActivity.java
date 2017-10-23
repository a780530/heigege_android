package com.tianfu.cutton.activity.quality;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.model.QualityBagBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.widget.vhtableview.VHTableAdapter;
import com.tianfu.cutton.widget.vhtableview.VHTableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QualityDetailBagActivity extends BaseActivity {
    List<QualityBagBean.ValueBean.RowsBean> mDatas;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ArrayList<ArrayList<String>> contentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_detail_bag);
        ButterKnife.bind(this);
        //设置数据源
        final VHTableView vht_table = (VHTableView) findViewById(R.id.vht_table);
        final ArrayList<String> titleData = new ArrayList<>();
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        tvTitle.setText(code);
        titleData.add("序号");
        titleData.add("   颜色级   ");
        titleData.add("轧工质量");
        titleData.add("长度级");
        titleData.add("马克隆值");
        titleData.add("马克隆档次");
        titleData.add("马克隆值级");
        titleData.add("Rd%");
        titleData.add("+b");
        titleData.add("长度(mm)");
        titleData.add("长度整齐度(%)");
        titleData.add("强力（cN/tex）");
        titleData.add("异性纤维");
        titleData.add("         包号         ");
        contentData = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        System.out.println("code:" + code);
        map.put("code", code);
        showProgressBar("",true);
        HttpManager.getServerApi().getBagMessage(map).enqueue(new CallBack<QualityBagBean>() {
            @Override
            public void success(QualityBagBean data) {
                if (data.success) {
                    mDatas = data.value.rows;
                    initData(contentData);
                    dismissProgressBar();
                    VHTableAdapter tableAdapter = new VHTableAdapter(QualityDetailBagActivity.this, titleData, contentData);
                    //vht_table.setFirstColumnIsMove(true);//设置第一列是否可移动,默认不可移动
                    //vht_table.setShowTitle(false);//设置是否显示标题行,默认显示
                    //一般表格都只是展示用的，所以这里没做刷新，真要刷新数据的话，重新setadaper一次吧
                    vht_table.setAdapter(tableAdapter);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });

    }

    private void initData(ArrayList<ArrayList<String>> contentData) {
        for (int i = 0; i < mDatas.size(); i++) {
            ArrayList<String> contentRowData = new ArrayList<>();
            if (i < 9) {
                contentRowData.add("" + (i + 1));
            } else {
                contentRowData.add((i + 1) + "");
            }
            contentRowData.add(mDatas.get(i).colorGrade != null ? mDatas.get(i).colorGrade : "--");//颜色及
            contentRowData.add(mDatas.get(i).yg != null ? mDatas.get(i).yg : "--");//轧工
            contentRowData.add(mDatas.get(i).lengthGrade != null ? mDatas.get(i).lengthGrade : "--");//长度及
            contentRowData.add(mDatas.get(i).micron != null ? mDatas.get(i).micron : "--");//马克隆值
            contentRowData.add(mDatas.get(i).micronLevel != null ? mDatas.get(i).micronLevel : "--");//马克隆档次
            contentRowData.add(mDatas.get(i).micronGrade != null ? mDatas.get(i).micronGrade : "--");//马克隆值级
            contentRowData.add(mDatas.get(i).rd != null ? mDatas.get(i).rd : "--");//Rd%
            contentRowData.add(mDatas.get(i).plusB != null ? mDatas.get(i).plusB : "--");//+b
            contentRowData.add(mDatas.get(i).length != null ? mDatas.get(i).length : "--");//长度
            contentRowData.add(mDatas.get(i).uniformityIndex != null ? mDatas.get(i).uniformityIndex : "--");//长度整齐度
            contentRowData.add(mDatas.get(i).breakLoad != null ? mDatas.get(i).breakLoad : "--");//强力（cN/tex）
            if (TextUtils.isEmpty(mDatas.get(i).hasForegionFiber)||mDatas.get(i).hasForegionFiber.equals("0")) {
                contentRowData.add("无");
            }else{
                contentRowData.add("有");
            }
            if (!TextUtils.isEmpty(mDatas.get(i).barCode)) {
                String barCode = mDatas.get(i).barCode;
                contentRowData.add("..."+barCode.substring(barCode.length() - 10));
            }else{
                contentRowData.add("--");
            }
            contentData.add(contentRowData);
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
