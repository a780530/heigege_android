package com.tianfu.cutton.fragment.store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.model.BagDetailsMessageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BagMessageFragment extends Fragment {

    @BindView(R.id.tv_barCode)
    TextView tvBarCode;
    @BindView(R.id.tv_color)
    TextView tvColor;
    @BindView(R.id.tv_quality)
    TextView tvQuality;
    @BindView(R.id.tv_lengthGrade)
    TextView tvLengthGrade;
    @BindView(R.id.tv_horse)
    TextView tvHorse;
    @BindView(R.id.tv_micronLevel)
    TextView tvMicronLevel;
    @BindView(R.id.tv_micronGrade)
    TextView tvMicronGrade;
    @BindView(R.id.tv_rd)
    TextView tvRd;
    @BindView(R.id.tv_plusB)
    TextView tvPlusB;
    @BindView(R.id.tv_length)
    TextView tvLength;
    @BindView(R.id.tv_uniformityIndex)
    TextView tvUniformityIndex;
    @BindView(R.id.tv_breakLoad)
    TextView tvBreakLoad;
    private View mRootview;
    private BagDetailsMessageBean.ValueBean valueBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootview != null) {
            return mRootview;
        } else {
            mRootview = inflater.inflate(R.layout.fragment_bag_message, container, false);
            ViewGroup parent = (ViewGroup) mRootview.getParent();
            if (parent != null) {
                parent.removeView(mRootview);
            }
//            ButterKnife.bind(this, mRootview);
        }
        ButterKnife.bind(this, mRootview);
        valueBean = (BagDetailsMessageBean.ValueBean) getActivity().getIntent().getSerializableExtra("valueBean");
        initData();
        return mRootview;
    }

    private void initData() {
        isNull(valueBean.barCode, tvBarCode);
        isNull(valueBean.colorGrade, tvColor);
        isNull(valueBean.yg, tvQuality);
        isNull(valueBean.lengthGrade, tvLengthGrade);
        isNull(valueBean.micron, tvHorse);
        isNull(valueBean.micronLevel, tvMicronLevel);
        isNull(valueBean.micronGrade, tvMicronGrade);
        isNull(valueBean.rd, tvRd);
        isNull(valueBean.plusB, tvPlusB);
        isNull(valueBean.length, tvLength);
        isNull(valueBean.uniformityIndex, tvUniformityIndex);
        isNull(valueBean.breakLoad, tvBreakLoad);
    }

    private void isNull(String barCode, TextView tv) {
        if (barCode != null && !"".equals(barCode)) {
            tv.setText(barCode);
        } else {
            tv.setText("--");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
