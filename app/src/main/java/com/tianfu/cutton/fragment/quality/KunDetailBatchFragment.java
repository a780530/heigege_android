package com.tianfu.cutton.fragment.quality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.quality.QualityBatchDetailsActivity;
import com.tianfu.cutton.adapter.KunGetBatchAdapter;
import com.tianfu.cutton.model.KunGetBatch;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

//捆信息的批次页面

/**
 * A simple {@link Fragment} subclass.
 */
public class KunDetailBatchFragment extends BaseFragment {
    private View mRootView;
    private List<KunGetBatch.ValueBean.RowsBean> mDatas;
    private RecyclerView inquiryRecyler;
    private KunGetBatchAdapter kunAdapter;
    private int pageNo = 1;
    private PtrClassicFrameLayout mPtrrame;

    public KunDetailBatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_inquiry_detail_batch, container, false);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            mPtrrame = (PtrClassicFrameLayout) mRootView.findViewById(R.id.ptr_purchase_frame);
            initPtr();
            return mRootView;
        }
    }

    private void initPtr() {
        mPtrrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo = 1;
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
            inquiryRecyler = ((RecyclerView) mRootView.findViewById(R.id.inquiryRecyler));
            inquiryRecyler.setLayoutManager(layoutManager);
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
                    intent.putExtra("fromKun", "1");
                    intent.putExtra("code", batchCode);
                    startActivity(intent);
                }
            });
            View getEmptyview = getLayoutInflater(getArguments()).inflate(R.layout.nodata_quality, (ViewGroup) inquiryRecyler.getParent(), false);
            kunAdapter.setEmptyView(getEmptyview);
            inquiryRecyler.setAdapter(kunAdapter);
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
        showProgressBar("数据请求中", true);
        HttpManager.getServerApi().kunGetBatch(map).enqueue(new CallBack<KunGetBatch>() {
            @Override
            public void success(KunGetBatch data) {
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
                    initRecylerView();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

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
