package com.tianfu.cutton.fragment.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.quality.QualityBatchDetailsActivity;
import com.tianfu.cutton.adapter.KunGetBatchAdapter;
import com.tianfu.cutton.model.KunGetBatch;
import com.tianfu.cutton.model.StoreKunMessage;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.tianfu.cutton.R.id.inquiryRecyler;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorekunBatchFragment extends BaseFragment {
    private View mRootView;
    private List<KunGetBatch.ValueBean.RowsBean> mDatas;
    private RecyclerView storeBatchRecyler;
    private KunGetBatchAdapter kunAdapter;
    private int pageNo = 1;
    private PtrClassicFrameLayout mPtrrame;
    private LinearLayout nodata;
    private String productId;

    public StorekunBatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_store_detail_batch, container, false);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            mPtrrame = (PtrClassicFrameLayout) mRootView.findViewById(R.id.ptr_purchase_frame);
            nodata = (LinearLayout) mRootView.findViewById(R.id.nodata);
            initPtr();
            productId = getActivity().getIntent().getStringExtra("productId");
            initIsOn();
            return mRootView;
        }
    }
    private void initIsOn() {
        Map mapIsOn = new HashMap();
        mapIsOn.put("productIds",productId);
        HttpManager.getServerApi().getStoreKunMessage(mapIsOn).enqueue(new CallBack<StoreKunMessage>() {
            @Override
            public void success(StoreKunMessage data) {
                if (data.success) {

                    mPtrrame.setVisibility(View.VISIBLE);
                    nodata.setVisibility(View.GONE);
                } else {
                    mPtrrame.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }
    private void initPtr() {
        mPtrrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo=1;
                loadLazyData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {


                //下拉刷新
//                Toast.makeText(getActivity(), "下啦", Toast.LENGTH_SHORT).show();
                pageNo = 1;
                loadLazyData();
            }
        });
    }

    private void initRecylerView() {
        if (kunAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            storeBatchRecyler = ((RecyclerView) mRootView.findViewById(inquiryRecyler));
            storeBatchRecyler.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            kunAdapter = new KunGetBatchAdapter(R.layout.fragment_quality_recyler_item, mDatas);
            kunAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(BaseApplication.getContextObject(), QualityBatchDetailsActivity.class);
                    String batchCode = mDatas.get(position).batchCode;
                    String id = mDatas.get(position).id;
                    intent.putExtra("batchType", "3");
                    intent.putExtra("productId", id);
                    intent.putExtra("code", batchCode);
                    intent.putExtra("fromKun","1");
                    startActivity(intent);
                }
            });
            View getEmptyview = getLayoutInflater(getArguments()).inflate(R.layout.nodata_quality, (ViewGroup) storeBatchRecyler.getParent(), false);
            kunAdapter.setEmptyView(getEmptyview);
            storeBatchRecyler.setAdapter(kunAdapter);
        } else {
            kunAdapter.notifyDataSetChanged();
        }
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
        loadLazyData();
    }

    private void loadLazyData() {
        mPtrrame.refreshComplete();
        Map<String, String> map = new HashMap<>();
        String code = getActivity().getIntent().getStringExtra("code");
        map.put("code", code);
        map.put("pageNum", pageNo + "");
        showProgressBar("请求数据中.......", true);
        HttpManager.getServerApi().kunGetBatch(map).enqueue(new CallBack<KunGetBatch>() {
            @Override
            public void success(KunGetBatch data) {
                dismissProgressBar();
                if (data.success) {
                    if (mDatas != null) {
                        if (pageNo==1){
                            mDatas.clear();
                        }
                        mDatas.addAll(data.value.rows);
                    } else {
                        mDatas = data.value.rows;
                    }
                    initRecylerView();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                dismissProgressBar();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        isVisible = false;
    }
}
