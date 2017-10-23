package com.tianfu.cutton.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.HomeSortBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SortActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.textTop1)
    TextView textTop1;
    @BindView(R.id.textTop2)
    TextView textTop2;
    @BindView(R.id.textTop3)
    TextView textTop3;
    @BindView(R.id.textTop4)
    TextView textTop4;
    @BindView(R.id.textTop5)
    TextView textTop5;
    @BindView(R.id.purchaseTop1)
    TextView purchaseTop1;
    @BindView(R.id.purchaseTop2)
    TextView purchaseTop2;
    @BindView(R.id.purchaseTop3)
    TextView purchaseTop3;
    @BindView(R.id.purchaseTop4)
    TextView purchaseTop4;
    @BindView(R.id.purchaseTop5)
    TextView purchaseTop5;
    @BindView(R.id.resourceTop1)
    TextView resourceTop1;
    @BindView(R.id.resourceTop2)
    TextView resourceTop2;
    @BindView(R.id.resourceTop3)
    TextView resourceTop3;
    @BindView(R.id.resourceTop4)
    TextView resourceTop4;
    @BindView(R.id.resourceTop5)
    TextView resourceTop5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);
        tvTitle.setText("排行榜");
        initData();
    }

    private void initData() {
        Map map = new HashMap();
        map.put("versionNo", Common.versionNo);
        HttpManager.getServerApi().getSort(map).enqueue(new CallBack<HomeSortBean>() {
            @Override
            public void success(HomeSortBean data) {
                if (data.success){
                    List<String> active = data.value.active;
                    List<String> purchase = data.value.purchase;
                    List<String> product = data.value.product;
                    initView(active,textTop1,textTop2,textTop3,textTop4,textTop5);
                    initView(purchase,purchaseTop1,purchaseTop2,purchaseTop3,purchaseTop4,purchaseTop5);
                    initView(product,resourceTop1,resourceTop2,resourceTop3,resourceTop4,resourceTop5);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initView(List<String> active, TextView textView1,TextView textView2,TextView textView3,TextView textView4,TextView textView5) {
        if (active.size()==0){
            return;
        }else if (active.size()==1){
            textView1.setText(active.get(0));
        }else if (active.size()==2){
            textView1.setText(active.get(0));
            textView2.setText(active.get(1));
        }else if (active.size()==3){
            textView1.setText(active.get(0));
            textView2.setText(active.get(1));
            textView3.setText(active.get(2));
        }else if (active.size()==4){
            textView1.setText(active.get(0));
            textView2.setText(active.get(1));
            textView3.setText(active.get(2));
            textView4.setText(active.get(3));
        }else if (active.size()==5){
            textView1.setText(active.get(0));
            textView2.setText(active.get(1));
            textView3.setText(active.get(2));
            textView4.setText(active.get(3));
            textView5.setText(active.get(4));
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
