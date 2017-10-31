package com.tianfu.cutton.zxing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanNodataActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.ll_title)
    AutoLinearLayout llTitle;
    @BindView(R.id.tv_scan)
    TextView tvScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_nodata);
        ButterKnife.bind(this);
        String rawText = getIntent().getStringExtra("rawText");
        if (!TextUtils.isEmpty(rawText)){
            tvScan.setText(rawText);
        }else{
            tvScan.setText("--");
        }
        tvTitle.setText("扫码结果");
        tvEdit.setText("完成");
        tvEdit.setTextColor(getResources().getColor(R.color.white));
    }

    @OnClick({R.id.iv_back, R.id.tv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_edit:
                onBackPressed();
                break;
        }
    }
}
