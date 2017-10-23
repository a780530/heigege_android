package com.tianfu.cutton.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.quality.QualityBatchDetailsActivity;
import com.tianfu.cutton.activity.quality.QualityKunDetailsActivity;
import com.tianfu.cutton.activity.store.StoreBatchActivity;
import com.tianfu.cutton.activity.store.StoreKunActivity;
import com.tianfu.cutton.adapter.DiffTypeViewAdapter;
import com.tianfu.cutton.adapter.OnItemListener;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.model.MyfavoritesBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
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

import static com.tianfu.cutton.R.id.bt_delete;

public class MyCollectionActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.recycler_Mycollection)
    RecyclerView recyclerMycollection;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    @BindView(R.id.ll_selectAll)
    AutoLinearLayout llSelectAll;
    @BindView(bt_delete)
    Button btDelete;
    @BindView(R.id.go_store)
    Button goStore;
    @BindView(R.id.ll_nodata)
    AutoLinearLayout llNodata;
    @BindView(R.id.cb_checked)
    CheckBox cbChecked;
    @BindView(R.id.ptr_purchase_frame)
    PtrClassicFrameLayout ptrPurchaseFrame;

    //记录选择的Item
    private HashSet<Integer> positionSet;
    List<MyfavoritesBean.ValueBean.RowsBean> mDataList;
    private boolean isSelectAll;
    DiffTypeViewAdapter adapter;
    private PtrClassicFrameLayout mPtrrame;
    private boolean isShow;
    private int pageNo = 1;
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        positionSet = new HashSet<>();
        mPtrrame = (PtrClassicFrameLayout) findViewById(R.id.ptr_purchase_frame);
        initPtr();
        map = new HashMap<>();
        map.put("deviceNo", Common.deviceNo);
        map.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
        showData();
    }

    private void initPtr() {
        mPtrrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
            /*    tvEdit.setText("编辑");
                llSelectAll.setVisibility(View.GONE);*/
                pageNo++;
//                adapter.showSelect(false);
                showData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
             /*   tvEdit.setText("编辑");
                llSelectAll.setVisibility(View.GONE);*/
                pageNo = 1;
                showData();
            }
        });
    }

    private void showData() {
        mPtrrame.refreshComplete();
        map.put("pageNum", "" + pageNo);
        //MyfavoritesBean 这是模型
        showProgressBar("", true);
        HttpManager.getServerApi().myCollection(map).enqueue(new CallBack<MyfavoritesBean>() {
            @Override
            public void success(MyfavoritesBean data) {
                //这是数据
                dismissProgressBar();
                if (data.success) {
                    if (data.value.rows.size() < 1 && pageNo == 1) {
                        llNodata.setVisibility(View.VISIBLE);
                        llSelectAll.setVisibility(View.GONE);
                        tvEdit.setVisibility(View.GONE);
                        tvEdit.setEnabled(false);
                        mPtrrame.setVisibility(View.GONE);
                    } else {
                        tvEdit.setVisibility(View.VISIBLE);
//                        tvEdit.setText("编辑");
//                        llSelectAll.setVisibility(View.GONE);
                        mPtrrame.setVisibility(View.VISIBLE);
                        llNodata.setVisibility(View.GONE);
                        if (mDataList != null) {
                            if (pageNo == 1) {
                                mDataList.clear();
                            }
                            mDataList.addAll(data.value.rows);
                            if (tvEdit.getText().equals("完成")) {
                                adapter.showSelect(true);
                            }
                        } else {
                            mDataList = data.value.rows;
                        }
                        initView();
                    }
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initView() {
        if (adapter == null) {
            recyclerMycollection.setLayoutManager(new LinearLayoutManager(this));
            adapter = new DiffTypeViewAdapter(this, mDataList);
            setListener();
            recyclerMycollection.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    private void setListener() {
        adapter.setOncheckedListener(new DiffTypeViewAdapter.OncheckedListener() {
            @Override
            public void isChecked(boolean hasChecked) {

            }
        });
        adapter.setOnItemListener(new OnItemListener() {

            @Override
            public void checkBoxClick(int position) {
                addOrRemove(position);
            }

            @Override
            public void onItemClick(View view, int position) {
                if (tvEdit.getText().toString().equals("完成")) {
                    if (!mDataList.get(position).isSelect) {
                        mDataList.get(position).isSelect = true;
                        positionSet.add(position);
                        adapter.notifyDataSetChanged();
                        if (positionSet.size()==mDataList.size()){
                            cbChecked.setChecked(true);
                            isSelectAll = true;
                        }
                    } else {
                        isSelectAll = false;
                        cbChecked.setChecked(false);
                        mDataList.get(position).isSelect = false;
                        positionSet.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                    setDiffBg();
                } else {
                    String isProduct = mDataList.get(position).isProduct;
                    String batchType = mDataList.get(position).batchType;
                    String code = mDataList.get(position).code;
                    int productId = mDataList.get(position).goodsId;
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

                //触发Item的单击事件
            }

            @Override
            public void onItemLongClick(View view, final int position, boolean isShowSelecter) {
                AlertDialog dialog = new AlertDialog.Builder(MyCollectionActivity.this)
                        .setMessage("确定要取消收藏吗")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
                                map.put("deviceNo", Common.deviceNo);
                                int ids = mDataList.get(position).id;
                                map.put("ids", ids + "");
                                HttpManager.getServerApi().deleteCollectionS(map).enqueue(new CallBack<CodeValidate>() {
                                    @Override
                                    public void success(CodeValidate data) {
                                        if (data.success) {
                                            addOrRemove(position);
                                            adapter.notifyDataSetChanged();
                                            delete();
                                            ToastUtil.showImage(BaseApplication.getContextObject(), "删除成功");
                                        } else {
                                            ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                                        }
                                    }

                                    @Override
                                    public void failure(ErrorType type, int httpCode) {

                                    }
                                });

                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.tv_select_all, bt_delete, R.id.cb_checked, R.id.go_store})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_store:
                Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fragmentid", "1");
                startActivity(intent);
                finish();
                break;
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
                    for (int i = 0; i < mDataList.size(); i++) {
                        mDataList.get(i).isSelect = false;
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
            case bt_delete:
                String ids = "";
                Boolean isNext = false;
                Iterator<Integer> iterator = positionSet.iterator();
                while (iterator.hasNext()) {
                    if (!isNext) {
                        Integer next = iterator.next();
                        ids += mDataList.get(next).id;
                        isNext = true;
                    } else {
                        ids += ",";
                        Integer next = iterator.next();
                        ids += mDataList.get(next).id;
                    }
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
                map.put("deviceNo", Common.deviceNo);
                map.put("ids", ids);
                HttpManager.getServerApi().deleteCollectionS(map).enqueue(new CallBack<CodeValidate>() {
                    @Override
                    public void success(CodeValidate data) {
                        if (data.success) {
                            delete();
                            if (mDataList.size()<1){
                                adapter.showSelect(false);
                                tvEdit.setText("编辑");
                                pageNo = 1;
                                llSelectAll.setVisibility(View.GONE);
                            }else{
                                adapter.showSelect(true);
                                llSelectAll.setVisibility(View.VISIBLE);
                            }
                            isSelectAll = false;
                            cbChecked.setChecked(false);
                            ToastUtil.showImage(BaseApplication.getContextObject(), "删除成功");
                            showData();
                        } else {

                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {
                        ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
                    }
                });
                break;
            case R.id.cb_checked:
                selectAll();
                break;
        }
    }

    /**
     * 操作Item记录集合
     */
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
            if (positionSet.size()==mDataList.size()){
                cbChecked.setChecked(true);
                isSelectAll = true;
            }
        }

        if (positionSet.size() == 0) {
            isShow = false;
        }
        setDiffBg();
    }

    /**
     * 删除
     */
    private void delete() {
        HashSet<MyfavoritesBean.ValueBean.RowsBean> valueSet = new HashSet<>();
        for (Integer integer : positionSet) {
            valueSet.add(adapter.getItem(integer));
        }
        for (MyfavoritesBean.ValueBean.RowsBean itemModel : valueSet) {
            adapter.removeData(itemModel);
        }
        adapter.notifyDataSetChanged();
        positionSet.clear();
        setDiffBg();
    }

    /**
     * 全选
     */
    private void selectAll() {
        if (!isSelectAll) {
            isSelectAll = true;
            for (int i = 0; i < mDataList.size(); i++) {
                mDataList.get(i).isSelect = true;
                positionSet.add(i);
            }
            adapter.notifyDataSetChanged();
        } else {
            isSelectAll = false;
            for (int i = 0; i < mDataList.size(); i++) {
                mDataList.get(i).isSelect = false;
                positionSet.remove(i);
            }
            adapter.notifyDataSetChanged();
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

}