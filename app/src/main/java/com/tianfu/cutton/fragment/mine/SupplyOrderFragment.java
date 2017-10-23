package com.tianfu.cutton.fragment.mine;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.mine.SupplyOrderDetailsActivity;
import com.tianfu.cutton.adapter.SupplyOrderAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.ListSupplyOrderBySelfBean;
import com.tianfu.cutton.model.PurchaseOrder;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.tianfu.cutton.common.Common.versionNo;


/**
 * A simple {@link Fragment} subclass.
 */
public class SupplyOrderFragment extends BaseFragment {
    private static final String EXTRA_CONTENT_SUPPLY = "key";
    private int value;
    private RecyclerView recyclerSupply;
    private PtrClassicFrameLayout mPtrrame;
    private int pageNo = 1;
    private List<ListSupplyOrderBySelfBean.ValueBean.RowsBean> mdatas;
    private View mRootView;
    private HashMap<String, String> hashMap;
    private HashMap<String, String> hashMapNot;
    private HashMap<String, String> hashMapSupply;
    private LinearLayout llNotNet;

    public static SupplyOrderFragment newInstance(int type) {
        SupplyOrderFragment mFragment = new SupplyOrderFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(EXTRA_CONTENT_SUPPLY, type);
        mFragment.setArguments(arguments);
        return mFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_supply_order, container, false);
            initView();
            netOk();//判断网络是否连接
            return mRootView;
        }
    }
/*
    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        if (netMobile == NetUtils.NETWORK_NONE) {
            llNotNet.setVisibility(View.VISIBLE);
            mPtrrame.setVisibility(View.GONE);
        } else {
            llNotNet.setVisibility(View.GONE);
            mPtrrame.setVisibility(View.VISIBLE);
        }
    }*/


    private void netOk() {
        boolean netConnect = this.isNetConnect();
        if (netConnect) {
            llNotNet.setVisibility(View.GONE);
            mPtrrame.setVisibility(View.VISIBLE);
        } else {
            llNotNet.setVisibility(View.VISIBLE);
            mPtrrame.setVisibility(View.GONE);
        }
    }


    private SupplyOrderAdapter adapterSupply;

    private void initRecyclerview() {
        if (adapterSupply == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerSupply.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            adapterSupply = new SupplyOrderAdapter(R.layout.fragment_supply_order_item, mdatas);
            adapterSupply.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    ListSupplyOrderBySelfBean.ValueBean.RowsBean rowsBean = mdatas.get(position);
                    intent.putExtra("RowsBeanSupply", rowsBean);
                    intent.setClass(getActivity(), SupplyOrderDetailsActivity.class);
                    startActivity(intent);
                }
            });
            adapterSupply.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                    final String id = mdatas.get(position).supplyId;
                    switch (view.getId()) {
                        case R.id.sure_supply:
                            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                    .setMessage("确定供货吗")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            Map<String,String> map = new HashMap<String, String>();
                                            map.put("supplyId",id);
                                            HttpManager.getServerApi().sureSupply(map).enqueue(new CallBack<PurchaseOrder>() {
                                                @Override
                                                public void success(PurchaseOrder data) {
                                                    if (data.success) {
                                                        value = getArguments().getInt(EXTRA_CONTENT_SUPPLY, -1);
                                                        mdatas.clear();
                                                        switch (value) {
                                                            case 1:
                                                                shouData(hashMap);
                                                                break;
                                                            case 2:
                                                                shouData(hashMapNot);
                                                                break;
                                                            case 3:
                                                                shouData(hashMapSupply);
                                                                break;
                                                        }
                                                        dialog.dismiss();
                                                    } else {
                                                        ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                                                    }
                                                }

                                                @Override
                                                public void failure(ErrorType type, int httpCode) {
                                                    ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
                                                }
                                            });
                                        }
                                    })
                                    .create();
                            dialog.show();
                            break;
                    }
                }
            });
            value = getArguments().getInt(EXTRA_CONTENT_SUPPLY, -1);
            View getEmptyview = getLayoutInflater(getArguments()).inflate(R.layout.nodata_supply, (ViewGroup) recyclerSupply.getParent(), false);
            TextView tv_supply = (TextView) getEmptyview.findViewById(R.id.tv_supply);
            if (value!=1){
                tv_supply.setText("您还没有相关的供货单");
            }
            adapterSupply.setEmptyView(getEmptyview);
            recyclerSupply.setAdapter(adapterSupply);
        } else {
            adapterSupply.notifyDataSetChanged();
        }
    }

    private void initView() {
        llNotNet = (LinearLayout) mRootView.findViewById(R.id.ll_notNet);
        recyclerSupply = (RecyclerView) mRootView.findViewById(R.id.supplyOrder_recycler);
        mPtrrame = (PtrClassicFrameLayout) mRootView.findViewById(R.id.ptr_frame);
        initPtr();
    }

    private void initPtr() {
        mPtrrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo++;
//                shouData();
                value = getArguments().getInt(EXTRA_CONTENT_SUPPLY, -1);
                switch (value) {
                    case 1:
                        shouData(hashMap);
                        break;
                    case 2:
                        shouData(hashMapNot);
                        break;
                    case 3:
                        shouData(hashMapSupply);
                        break;
                }
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                value = getArguments().getInt(EXTRA_CONTENT_SUPPLY, -1);
                switch (value) {
                    case 1:
                        shouData(hashMap);
                        break;
                    case 2:
                        shouData(hashMapNot);
                        break;
                    case 3:
                        shouData(hashMapSupply);
                        break;
                }
            }
        });
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
    }

    protected void onVisible() {
        if (!isVisible || !isPrepared) {
            return;
        }
        value = getArguments().getInt(EXTRA_CONTENT_SUPPLY, -1);
        switch (value) {
            case 1:
                hashMap = new HashMap<>();
                hashMap.put("deviceNo", Common.deviceNo);
                hashMap.put("from", Common.from);
                hashMap.put("version", versionNo);
                shouData(hashMap);
                break;
            case 2:
                hashMapNot = new HashMap<>();
                hashMapNot.put("deviceNo", Common.deviceNo);
                hashMapNot.put("from", Common.from);
                hashMapNot.put("version", versionNo);
                hashMapNot.put("supplyStatus", "NotSupply");
                shouData(hashMapNot);
//                shouData();
                break;
            case 3:
                hashMapSupply = new HashMap<>();
                hashMapSupply.put("deviceNo", Common.deviceNo);
                hashMapSupply.put("from", Common.from);
                hashMapSupply.put("version", versionNo);
                hashMapSupply.put("supplyStatus", "Supply");
                shouData(hashMapSupply);
                break;
        }
    }

    private void shouData(final HashMap<String, String> params) {
        showProgressBar("", true);
        mPtrrame.refreshComplete();
        params.put("pageNum", pageNo + "");
        HttpManager.getServerApi().listSupplyOrderBySelf(params).enqueue(new CallBack<ListSupplyOrderBySelfBean>() {
            @Override
            public void success(ListSupplyOrderBySelfBean data) {
                if (data.success) {
                    dismissProgressBar();
                    if (mdatas != null) {
                        if (pageNo == 1) {
                            mdatas.clear();
                        }
                        mdatas.addAll(data.value.rows);
                    } else {
                        mdatas = data.value.rows;
                    }
                    initRecyclerview();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }
}
