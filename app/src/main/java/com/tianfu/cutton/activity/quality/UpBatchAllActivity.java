package com.tianfu.cutton.activity.quality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.home.UploadingResourceActivity;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.adapter.OnItemListener;
import com.tianfu.cutton.adapter.QualityTypeViewAdapter;
import com.tianfu.cutton.model.QualityBean;
import com.tianfu.cutton.model.SerializableMap;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class UpBatchAllActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.cb_checked)
    CheckBox cbChecked;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.ll_selectAll)
    AutoLinearLayout llSelectAll;
    @BindView(R.id.recycler_Mycollection)
    RecyclerView recyclerMycollection;
    @BindView(R.id.ptr_purchase_frame)
    PtrClassicFrameLayout ptrPurchaseFrame;
    @BindView(R.id.ll_nodata)
    AutoLinearLayout llNodata;
    QualityTypeViewAdapter adapter;
    private int pageNo = 1;
    private boolean isSelectAll;
    private boolean isShow;
    //记录选择的Item
    private HashSet<Integer> positionSet;
    private List<QualityBean.ValueBean.RowsBean> mDatas;
    private Map<String, String> map;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_batch_all);
        ButterKnife.bind(this);
        positionSet = new HashSet<>();
        initPtr();
        tvTitle.setText("上传资源");
        mDatas = (List<QualityBean.ValueBean.RowsBean>) getIntent().getSerializableExtra("qualityList");
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("mapQuality");
        map = serializableMap.getMap();
        pageNo = Integer.parseInt(map.get("pageNum"));
        initAdapter();
        llSelectAll.setVisibility(View.VISIBLE);
        if (adapter != null) {
            adapter.showSelect(true);
        }
        tvEdit.setText("完成");
    }


    private void initAdapter() {
        if (adapter == null) {
            recyclerMycollection.setLayoutManager(new LinearLayoutManager(this));
            adapter = new QualityTypeViewAdapter(this, mDatas);
            setListener();
            recyclerMycollection.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void setListener() {
        adapter.setOnItemListener(new OnItemListener() {
            @Override
            public void checkBoxClick(int position) {
                addOrRemove(position);
            }

            @Override
            public void onItemClick(View view, int position) {
                if (tvEdit.getText().toString().equals("完成")) {
                    if (!mDatas.get(position).isSelect) {
                        mDatas.get(position).isSelect = true;
                        positionSet.add(position);
                        adapter.notifyDataSetChanged();
                        if (positionSet.size() == mDatas.size()) {
                            cbChecked.setChecked(true);
                            isSelectAll = true;
                        }
                    } else {
                        isSelectAll = false;
                        cbChecked.setChecked(false);
                        mDatas.get(position).isSelect = false;
                        positionSet.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                    setDiffBg();
                } else {
                    String isProduct = mDatas.get(position).isProduct;
                    String batchType = mDatas.get(position).batchType;
                    String code = mDatas.get(position).code;
                    String productId = mDatas.get(position).productId;
                    String mobileBaojia = mDatas.get(position).mobile;
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
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position, boolean isPress) {

            }
        });
    }

    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            Log.e("----", "remove");
            cbChecked.setChecked(false);
            isSelectAll = false;
            positionSet.remove(position);
        } else {
            // 如果不包含，则添加
            Log.e("----", "add");
            positionSet.add(position);
            if (positionSet.size() == mDatas.size()) {
                cbChecked.setChecked(true);
                isSelectAll = true;
            }
        }

        if (positionSet.size() == 0) {
            isShow = false;
        }
        setDiffBg();
    }

    private void setDiffBg() {
        if (positionSet != null && positionSet.size() > 0) {
            btDelete.setBackgroundResource(R.mipmap.ic_back_supply);
            btDelete.setEnabled(true);
        } else {
            btDelete.setEnabled(true);
            btDelete.setBackgroundColor(getResources().getColor(R.color.bt_back));
        }
    }

    private void initPtr() {
        ptrPurchaseFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo++;
                showData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrPurchaseFrame.refreshComplete();
            }
        });
    }

    private void showData() {
        ptrPurchaseFrame.refreshComplete();
        map.put("pageNum", pageNo + "");
        HttpManager.getServerApi().getQualityList(map).enqueue(new CallBack<QualityBean>() {
            @Override
            public void success(QualityBean data) {
                if (data.success) {
                    mDatas.addAll(data.value.rows);
                    if (tvEdit.getText().equals("完成")) {
                        adapter.showSelect(true);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.cb_checked, R.id.bt_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_edit:
                if (adapter == null) {
                    return;
                }
                String s = tvEdit.getText().toString();
                if ("编辑".equals(s)) {
                    llSelectAll.setVisibility(View.VISIBLE);
                    adapter.showSelect(true);
                    tvEdit.setText("完成");
                } else if ("完成".equals(s)) {
                    isSelectAll = false;
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).isSelect = false;
                        positionSet.remove(i);
                    }
                    adapter.notifyDataSetChanged();
                    setDiffBg();
                    cbChecked.setChecked(false);
                    llSelectAll.setVisibility(View.GONE);
                    adapter.showSelect(false);
                    tvEdit.setText("编辑");
                }
                break;
            case R.id.cb_checked:
                selectAll();
                break;
            case R.id.bt_delete:
                String ids = "";
                Boolean isNext = false;
                Iterator<Integer> iterator = positionSet.iterator();
                while (iterator.hasNext()) {
                    if (!isNext) {
                        Integer next = iterator.next();
                        ids += mDatas.get(next).code;
                        isNext = true;
                    } else {
                        ids += ",";
                        Integer next = iterator.next();
                        ids += mDatas.get(next).code;
                    }
                }
//                startActivity(new Intent(this,UploadingResourceActivity.class));
                if (!isLogin) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(this, UploadingResourceActivity.class);
                intent.putExtra("codes", ids);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isLogin = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isLogin");
    }

    private void selectAll() {
        if (!isSelectAll) {
            isSelectAll = true;
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isSelect = true;
                positionSet.add(i);
            }
            adapter.notifyDataSetChanged();
        } else {
            isSelectAll = false;
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isSelect = false;
                positionSet.remove(i);
            }
            adapter.notifyDataSetChanged();
        }
        setDiffBg();
    }
}
