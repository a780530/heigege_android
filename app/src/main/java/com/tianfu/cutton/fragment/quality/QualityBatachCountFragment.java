package com.tianfu.cutton.fragment.quality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.quality.QualityDetailBagActivity;
import com.tianfu.cutton.model.QualityCountBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.StringJudgeUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QualityBatachCountFragment extends Fragment {

    @BindView(R.id.count_code)
    TextView countCode;
    @BindView(R.id.count_type)
    TextView countType;
    @BindView(R.id.count_totalBag)
    TextView countTotalBag;
    @BindView(R.id.count_moisture)
    TextView countMoisture;
    @BindView(R.id.count_grossweight)
    TextView countGrossweight;
    @BindView(R.id.count_trash)
    TextView countTrash;
    @BindView(R.id.count_tareweight)
    TextView countTareweight;
    @BindView(R.id.count_stdweight)
    TextView countStdweight;
    @BindView(R.id.count_netweight)
    TextView countNetweight;
    @BindView(R.id.count_ygP1)
    TextView countYgP1;
    @BindView(R.id.count_ygP2)
    TextView countYgP2;
    @BindView(R.id.count_ygP3)
    TextView countYgP3;
    @BindView(R.id.count_lengthgrade)
    TextView countLengthgrade;
    @BindView(R.id.count_colorGrade)
    TextView countColorGrade;
    @BindView(R.id.count_lengthAverage)
    TextView countLengthAverage;
    @BindView(R.id.count_white1)
    TextView countWhite1;
    @BindView(R.id.count_white2)
    TextView countWhite2;
    @BindView(R.id.count_white3)
    TextView countWhite3;
    @BindView(R.id.count_white4)
    TextView countWhite4;
    @BindView(R.id.count_white5)
    TextView countWhite5;
    @BindView(R.id.count_lightSpotted1)
    TextView countLightSpotted1;
    @BindView(R.id.count_lightSpotted2)
    TextView countLightSpotted2;
    @BindView(R.id.count_lightSpotted3)
    TextView countLightSpotted3;
    @BindView(R.id.count_yellowish1)
    TextView countYellowish1;
    @BindView(R.id.count_yellowish2)
    TextView countYellowish2;
    @BindView(R.id.count_yellowish3)
    TextView countYellowish3;
    @BindView(R.id.count_yellow1)
    TextView countYellow1;
    @BindView(R.id.count_yellow2)
    TextView countYellow2;
    @BindView(R.id.count_uniformityIndexMin)
    TextView countUniformityIndexMin;
    @BindView(R.id.count_uniformityIndexMax)
    TextView countUniformityIndexMax;
    @BindView(R.id.count_uniformityIndexAverage)
    TextView countUniformityIndexAverage;
    @BindView(R.id.count_uniformityIndex1Rate)
    TextView countUniformityIndex1Rate;
    @BindView(R.id.count_uniformityIndex2Rate)
    TextView countUniformityIndex2Rate;
    @BindView(R.id.count_uniformityIndex3Rate)
    TextView countUniformityIndex3Rate;
    @BindView(R.id.count_uniformityIndex4Rate)
    TextView countUniformityIndex4Rate;
    @BindView(R.id.count_uniformityIndex5Rate)
    TextView countUniformityIndex5Rate;
    @BindView(R.id.count_plusBAverage)
    TextView countPlusBAverage;
    @BindView(R.id.count_rdAverage)
    TextView countRdAverage;
    @BindView(R.id.count_length32)
    TextView countLength32;
    @BindView(R.id.count_length31)
    TextView countLength31;
    @BindView(R.id.count_length30)
    TextView countLength30;
    @BindView(R.id.count_length29)
    TextView countLength29;
    @BindView(R.id.count_length28)
    TextView countLength28;
    @BindView(R.id.count_length27)
    TextView countLength27;
    @BindView(R.id.count_length26)
    TextView countLength26;
    @BindView(R.id.count_length25)
    TextView countLength25;
    @BindView(R.id.count_micronGrade)
    TextView countMicronGrade;
    @BindView(R.id.count_micronAverage)
    TextView countMicronAverage;
    @BindView(R.id.count_micronGradeC1Rate)
    TextView countMicronGradeC1Rate;
    @BindView(R.id.count_micronGradeB1Rate)
    TextView countMicronGradeB1Rate;
    @BindView(R.id.count_micronGradeA1Rate)
    TextView countMicronGradeA1Rate;
    @BindView(R.id.count_micronGradeB2Rate)
    TextView countMicronGradeB2Rate;
    @BindView(R.id.count_micronGradeC2Rate)
    TextView countMicronGradeC2Rate;
    @BindView(R.id.count_breakLoadMin)
    TextView countBreakLoadMin;
    @BindView(R.id.count_breakLoadMax)
    TextView countBreakLoadMax;
    @BindView(R.id.count_breakLoadAverage)
    TextView countBreakLoadAverage;
    @BindView(R.id.count_breakLoad1Rate)
    TextView countBreakLoad1Rate;
    @BindView(R.id.count_breakLoad2Rate)
    TextView countBreakLoad2Rate;
    @BindView(R.id.count_breakLoad3Rate)
    TextView countBreakLoad3Rate;
    @BindView(R.id.count_breakLoad4Rate)
    TextView countBreakLoad4Rate;
    @BindView(R.id.count_breakLoad5Rate)
    TextView countBreakLoad5Rate;
    @BindView(R.id.bt_count_bag)
    Button btCountBag;
    private View mRootview;
    private Map<String, String> map;
    private String code;
    private Map<String, Float> mapTomap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootview != null) {
            return mRootview;
        } else {
            mRootview = inflater.inflate(R.layout.fragment_quality_batach_count, container, false);
            ViewGroup parent = (ViewGroup) mRootview.getParent();
            if (parent != null) {
                parent.removeView(mRootview);
            }
            ButterKnife.bind(this, mRootview);
            Intent intent = getActivity().getIntent();
            code = intent.getStringExtra("code");
            String fromKun = intent.getStringExtra("fromKun");
            map = new HashMap<>();
            map.put("code", code);
            map.put("fromKun", fromKun);
            mapTomap = new HashMap<>();
            initData();
            return mRootview;
        }
    }

    private void initData() {
        HttpManager.getServerApi().getQualityCount(map).enqueue(new CallBack<QualityCountBean>() {
            @Override
            public void success(QualityCountBean data) {
                if (data.value != null) {
                    if (data.success) {
                        QualityCountBean.ValueBean value = data.value;
                        if (!TextUtils.isEmpty(value.batchCode)) {
                            countCode.setText("批号：" + value.batchCode);
                        }
                        if (!TextUtils.isEmpty(value.totalBag+"")) {
                            countTotalBag.setText(value.totalBag + "/" + value.totalBag);
                        } else {
                            countTotalBag.setText("--");
                        }
                        if (!TextUtils.isEmpty(value.trash+"")) {
                            countTrash.setText(value.trash + "%");
                        } else {
                            countTrash.setText("--");
                        }
                        if (!TextUtils.isEmpty(value.moisture+"")) {
                            countMoisture.setText(value.moisture + "%");
                        } else {
                            countMoisture.setText("--");
                        }

                        StringJudgeUtils.judgeStringT(value.grossweight, countGrossweight);
                        StringJudgeUtils.judgeStringKg(value.tareweight, countTareweight);
                        StringJudgeUtils.judgeStringT(value.stdweight, countStdweight);
                        StringJudgeUtils.judgeStringT(value.netweight, countNetweight);
                        if (!TextUtils.isEmpty(value.ygP1)) {
                            countYgP1.setText("P1:" + value.ygP1 + "%");
                        } else {
                            countYgP1.setText("P1:--");
                        }
                        if (!TextUtils.isEmpty(value.ygP2)) {
                            countYgP2.setText("P2:" + value.ygP2 + "%");
                        } else {
                            countYgP2.setText("P2:--");
                        }
                        if (!TextUtils.isEmpty(value.ygP3)) {
                            countYgP3.setText("P3:" + value.ygP3 + "%");
                        } else {
                            countYgP3.setText("P3:--");
                        }

                        StringJudgeUtils.judgeStringT(value.type, countType);
                        StringJudgeUtils.judgeStringNumber(value.lengthGrade+"", countLengthgrade);
                        StringJudgeUtils.judgeStringT(value.colorGrade, countColorGrade);
                        StringJudgeUtils.judgeStringNumber(value.lengthAverage+"", countLengthAverage);
                        StringJudgeUtils.judgeStringNumber(value.white1, countWhite1);
                        StringJudgeUtils.judgeStringNumber(value.white2, countWhite2);
                        StringJudgeUtils.judgeStringNumber(value.white3, countWhite3);
                        StringJudgeUtils.judgeStringNumber(value.white4, countWhite4);
                        StringJudgeUtils.judgeStringNumber(value.white5, countWhite5);
                        StringJudgeUtils.judgeStringNumber(value.lightSpotted1, countLightSpotted1);
                        StringJudgeUtils.judgeStringNumber(value.lightSpotted2, countLightSpotted2);
                        StringJudgeUtils.judgeStringNumber(value.lightSpotted3, countLightSpotted3);
                        StringJudgeUtils.judgeStringNumber(value.yellowish1, countYellowish1);
                        StringJudgeUtils.judgeStringNumber(value.yellowish2, countYellowish2);
                        StringJudgeUtils.judgeStringNumber(value.yellowish3, countYellowish3);
                        StringJudgeUtils.judgeStringNumber(value.yellow1, countYellow1);
                        StringJudgeUtils.judgeStringNumber(value.yellow2, countYellow2);
                        StringJudgeUtils.judgeStringNumber(value.uniformityIndexMin+"", countUniformityIndexMin);
                        StringJudgeUtils.judgeStringNumber(value.uniformityIndexAverage+"", countUniformityIndexAverage);
                        StringJudgeUtils.judgeStringNumber(value.uniformityIndexMax+"", countUniformityIndexMax);
                        StringJudgeUtils.judgeStringNumber(value.uniformityIndex1Rate, countUniformityIndex1Rate);
                         StringJudgeUtils.judgeStringNumber(value.uniformityIndex2Rate, countUniformityIndex2Rate);
                        StringJudgeUtils.judgeStringNumber(value.uniformityIndex3Rate, countUniformityIndex3Rate);
                        StringJudgeUtils.judgeStringNumber(value.uniformityIndex4Rate, countUniformityIndex4Rate);
                        StringJudgeUtils.judgeStringNumber(value.uniformityIndex5Rate, countUniformityIndex5Rate);
                        StringJudgeUtils.judgeStringNumber(value.plusBAverage+"", countPlusBAverage);
                        StringJudgeUtils.judgeStringNumber(value.rdAverage+"", countRdAverage);
                        StringJudgeUtils.judgeStringNumber(value.length25, countLength25);
                        StringJudgeUtils.judgeStringNumber(value.length26, countLength26);
                        StringJudgeUtils.judgeStringNumber(value.length27, countLength27);
                        StringJudgeUtils.judgeStringNumber(value.length28, countLength28);
                        StringJudgeUtils.judgeStringNumber(value.length29, countLength29);
                        StringJudgeUtils.judgeStringNumber(value.length30, countLength30);
                        StringJudgeUtils.judgeStringNumber(value.length31, countLength31);
                        StringJudgeUtils.judgeStringNumber(value.length32, countLength32);
                        StringJudgeUtils.judgeStringNumber(value.micronGrade, countMicronGrade);
                        StringJudgeUtils.judgeStringNumber(value.micronAverage+"", countMicronAverage);
                        StringJudgeUtils.judgeStringNumber(value.micronGradeC1Rate, countMicronGradeC1Rate);
                        StringJudgeUtils.judgeStringNumber(value.micronGradeB1Rate, countMicronGradeB1Rate);
                        StringJudgeUtils.judgeStringNumber(value.micronGradeA1Rate, countMicronGradeA1Rate);
                        StringJudgeUtils.judgeStringNumber(value.micronGradeC2Rate, countMicronGradeC2Rate);
                        StringJudgeUtils.judgeStringNumber(value.micronGradeB2Rate, countMicronGradeB2Rate);
                        StringJudgeUtils.judgeStringNumber(value.breakLoadMin+"", countBreakLoadMin);
                        StringJudgeUtils.judgeStringNumber(value.breakLoadMax+"", countBreakLoadMax);
                        StringJudgeUtils.judgeStringNumber(value.breakLoadAverage+"", countBreakLoadAverage);
                        StringJudgeUtils.judgeStringNumber(value.breakLoad1Rate, countBreakLoad1Rate);
                        StringJudgeUtils.judgeStringNumber(value.breakLoad2Rate, countBreakLoad2Rate);
                        StringJudgeUtils.judgeStringNumber(value.breakLoad3Rate, countBreakLoad3Rate);
                        StringJudgeUtils.judgeStringNumber(value.breakLoad4Rate, countBreakLoad4Rate);
                        StringJudgeUtils.judgeStringNumber(value.breakLoad5Rate, countBreakLoad5Rate);
                        if (!TextUtils.isEmpty(value.length25)) {
                            mapTomap.put("25mm ", Float.parseFloat(value.length25));
                        }
                        if (!TextUtils.isEmpty(value.length26)) {
                            mapTomap.put("26mm ", Float.parseFloat(value.length26));
                        }
                        if (!TextUtils.isEmpty(value.length27)) {
                            mapTomap.put("27mm ", Float.parseFloat(value.length27));
                        }
                        if (!TextUtils.isEmpty(value.length28)) {
                            mapTomap.put("28mm ", Float.parseFloat(value.length28));
                        }
                        if (!TextUtils.isEmpty(value.length29)) {
                            mapTomap.put("29mm ", Float.parseFloat(value.length29));
                        }
                        if (!TextUtils.isEmpty(value.length30)) {
                            mapTomap.put("30mm ", Float.parseFloat(value.length30));
                        }
                        if (!TextUtils.isEmpty(value.length31)) {
                            mapTomap.put("31mm ", Float.parseFloat(value.length31));
                        }
                        if (!TextUtils.isEmpty(value.length32)) {
                            mapTomap.put("32mm ", Float.parseFloat(value.length32));
                        }
                    }
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }


    @OnClick(R.id.bt_count_bag)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), QualityDetailBagActivity.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }

}
