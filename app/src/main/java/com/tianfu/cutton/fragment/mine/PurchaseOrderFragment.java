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
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.mine.EditPurchaseActivity;
import com.tianfu.cutton.activity.mine.PurchaseOrderDetailsActivity;
import com.tianfu.cutton.activity.purchase.ReleasePurchaseActivity;
import com.tianfu.cutton.adapter.PurchaseOrderRecylerAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.PurchaseOrder;
import com.tianfu.cutton.model.PurchaseOrderBySelfBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;

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
public class PurchaseOrderFragment extends BaseFragment {

    private static final String EXTRA_CONTENT_PURCHASE = "key";
    private int value;
    private RecyclerView purchaseOrderRecycler;
    private PtrClassicFrameLayout mPtrrame;
    private List<PurchaseOrderBySelfBean.ValueBean.RowsBean> mDatas;
    private int pageNo = 1;
    private int requestCode;
    private View mRootView;
    private HashMap<String, String> params;
    private HashMap<String, String> paramsIng;
    private HashMap<String, String> paramsFinsh;
    private HashMap<String, String> paramsOver;
    private HashMap<String, String> paramsClose;


    public PurchaseOrderFragment() {

    }

    public static PurchaseOrderFragment newInstance(int type) {
        PurchaseOrderFragment mFragment = new PurchaseOrderFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(EXTRA_CONTENT_PURCHASE, type);
        mFragment.setArguments(arguments);
        return mFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_purchase_order, container, false);
            initView();
            return mRootView;
        }
    }

    private void initView() {
        purchaseOrderRecycler = (RecyclerView) mRootView.findViewById(R.id.purchase_Order_recycler);
        mPtrrame = (PtrClassicFrameLayout) mRootView.findViewById(R.id.ptr_purchase_frame);
        initPtr();
    }

    private void initPtr() {
        mPtrrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo++;
                //上啦加载
//                Toast.makeText(getActivity(), "上啦", Toast.LENGTH_SHORT).show();
                value = getArguments().getInt(EXTRA_CONTENT_PURCHASE, -1);
                switch (value) {
                    case 1:
                        shouData(params);
                        break;
                    case 2:
                        shouData(paramsIng);
                        break;
                    case 3:
                        shouData(paramsFinsh);
                        break;
                    case 4:
                        shouData(paramsOver);
                        break;
                    case 5:
                        shouData(paramsClose);
                        break;
                }
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //下拉刷新
//                Toast.makeText(getActivity(), "下啦", Toast.LENGTH_SHORT).show();
                pageNo = 1;
                value = getArguments().getInt(EXTRA_CONTENT_PURCHASE, -1);
                switch (value) {
                    case 1:
                        shouData(params);
                        break;
                    case 2:
                        shouData(paramsIng);
                        break;
                    case 3:
                        shouData(paramsFinsh);
                        break;
                    case 4:
                        shouData(paramsOver);
                        break;
                    case 5:
                        shouData(paramsClose);
                        break;
                }
            }
        });
    }

    private PurchaseOrderRecylerAdapter adapterRecycler;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1://采购单详情的回掉
                if (data != null) {
                    String stringExtra = data.getStringExtra("1");
                    if (stringExtra != null) {
                        if (stringExtra.equals("close") || stringExtra.equals("finish")) {
                            mDatas.clear();
                            switch (value) {
                                case 1:
                                    shouData(params);
                                    break;
                                case 2:
                                    shouData(paramsIng);
                                    break;
                                case 3:
                                    shouData(paramsFinsh);
                                    break;
                                case 4:
                                    shouData(paramsOver);
                                    break;
                                case 5:
                                    shouData(paramsClose);
                                    break;

                            }
                        }
                    }
                }
                break;
            case 2://编辑采购单的回调
                if (data != null) {
                    String stringExtra1 = data.getStringExtra("2");
                    if (stringExtra1 != null) {
                        if (stringExtra1.equals("success")) {
                            mDatas.clear();
                            switch (value) {
                                case 1:
                                    shouData(params);
                                    break;
                                case 2:
                                    shouData(paramsIng);
                                    break;
                                case 3:
                                    shouData(paramsFinsh);
                                    break;
                                case 4:
                                    shouData(paramsOver);
                                    break;
                                case 5:
                                    shouData(paramsClose);
                                    break;

                            }
                        }
                    }
                }
                break;
        }
    }

    private void initRecyclerView() {
        if (adapterRecycler == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            purchaseOrderRecycler.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            adapterRecycler = new PurchaseOrderRecylerAdapter(R.layout.fragment_purchase_order_item, mDatas);
            adapterRecycler.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    TextView textView = (TextView) view.findViewById(R.id.tv_Numbers);
                    intent.putExtra("purchaseSn", textView.getText());
                    intent.setClass(getActivity(), PurchaseOrderDetailsActivity.class);
//                    startActivity(intent);
                    requestCode = 1;
                    startActivityForResult(intent, requestCode);
                }
            });
            adapterRecycler.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                    final String id = mDatas.get(position).id;
                    switch (view.getId()) {
                        case R.id.bt_edit:
                            Intent intent = new Intent();
                            PurchaseOrderBySelfBean.ValueBean.RowsBean rowsBean = mDatas.get(position);
                            intent.putExtra("RowsBean", rowsBean);
                            intent.setClass(getActivity(), EditPurchaseActivity.class);
//                            startActivity(intent);
                            requestCode = 2;
                            startActivityForResult(intent, requestCode);
                            break;
                        case R.id.bt_finish:
                            finshPurchase(id, position);
                            break;
                        case R.id.bt_close:
                            closePurchase(id, position);
                            break;
                    }
                }

                private void closePurchase(final String id, final int position) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setMessage("确定要关闭采购单吗")
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
                                    map.put("id",id);
                                    HttpManager.getServerApi().closePurchaseOrder(map).enqueue(new CallBack<PurchaseOrder>() {
                                        @Override
                                        public void success(PurchaseOrder data) {
                                            mDatas.clear();
                                            if (data.success) {
                                                switch (value) {
                                                    case 1:
                                                        shouData(params);
                                                        break;
                                                    case 2:
                                                        shouData(paramsIng);
                                                        break;
                                                    case 3:
                                                        shouData(paramsFinsh);
                                                        break;
                                                    case 4:
                                                        shouData(paramsOver);
                                                        break;
                                                    case 5:
                                                        shouData(paramsClose);
                                                        break;
                                                }
                                                dialog.dismiss();
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

                private void finshPurchase(final String id, final int position) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setMessage("确定要完成采购单吗")
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
                                    map.put("id",id);
                                    HttpManager.getServerApi().completePurchaseOrder(map).enqueue(new CallBack<PurchaseOrder>() {
                                        @Override
                                        public void success(PurchaseOrder data) {
                                            mDatas.clear();
                                            if (data.success) {
                                                switch (value) {
                                                    case 1:
                                                        shouData(params);
                                                        break;

                                                    case 2:
                                                        shouData(paramsIng);
                                                        break;
                                                    case 3:
                                                        shouData(paramsFinsh);
                                                        break;
                                                    case 4:
                                                        shouData(paramsOver);
                                                        break;
                                                    case 5:
                                                        shouData(paramsClose);
                                                        break;
                                                }
                                                dialog.dismiss();
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
            View getEmptyview = getLayoutInflater(getArguments()).inflate(R.layout.nodata_purchase, (ViewGroup) purchaseOrderRecycler.getParent(), false);
            Button button = (Button) getEmptyview.findViewById(R.id.go_purchase);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BaseApplication.getContextObject(), ReleasePurchaseActivity.class));
                }
            });
            TextView tv_purchse = (TextView) getEmptyview.findViewById(R.id.tv_purchse);
            value = getArguments().getInt(EXTRA_CONTENT_PURCHASE, -1);
            if (value!=1){
                tv_purchse.setText("您还没有相关的采购单");
                button.setVisibility(View.GONE);
            }
            adapterRecycler.setEmptyView(getEmptyview);
            purchaseOrderRecycler.setAdapter(adapterRecycler);
        } else {
            adapterRecycler.notifyDataSetChanged();
        }

    }

    private void shouData(final HashMap<String, String> params) {
        showProgressBar("",true);
        mPtrrame.refreshComplete();
        params.put("pageNum", pageNo + "");
        HttpManager.getServerApi().getPurchaseOrder(params).enqueue(new CallBack<PurchaseOrderBySelfBean>() {
            @Override
            public void success(final PurchaseOrderBySelfBean data) {
                if (data.success) {
                    dismissProgressBar();
                    if (mDatas != null) {
                        if (pageNo == 1) {
                            mDatas.clear();
                        }
                        mDatas.addAll(data.value.rows);
                    } else {
                        mDatas = data.value.rows;
                    }
                    initRecyclerView();
                }

            }

            @Override
            public void failure(ErrorType type, int httpCode) {

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
        value = getArguments().getInt(EXTRA_CONTENT_PURCHASE, -1);
        switch (value) {
            case 1:
                params = new HashMap<>();
                params.put("deviceNo", Common.deviceNo);
                params.put("from", Common.from);
                params.put("version", versionNo);
                shouData(params);
                break;
            case 2:
                paramsIng = new HashMap<>();
                paramsIng.put("deviceNo", Common.deviceNo);
                paramsIng.put("from", Common.from);
                paramsIng.put("version", versionNo);
                paramsIng.put("purchaseStatus", "Purchasing");
                shouData(paramsIng);
                break;
            case 3:
                paramsFinsh = new HashMap<>();
                paramsFinsh.put("deviceNo", Common.deviceNo);
                paramsFinsh.put("from", Common.from);
                paramsFinsh.put("version", versionNo);
                paramsFinsh.put("purchaseStatus", "Complete");
                shouData(paramsFinsh);
                break;
            case 4:
                paramsOver = new HashMap<>();
                paramsOver.put("deviceNo", Common.deviceNo);
                paramsOver.put("from", Common.from);
                paramsOver.put("version", versionNo);
                paramsOver.put("purchaseStatus", "Invalid");
                shouData(paramsOver);
                break;
            case 5:
                paramsClose = new HashMap<>();
                paramsClose.put("deviceNo", Common.deviceNo);
                paramsClose.put("from", Common.from);
                paramsClose.put("version", versionNo);
                paramsClose.put("purchaseStatus", "Close");
                shouData(paramsClose);
                break;
        }
    }
}
