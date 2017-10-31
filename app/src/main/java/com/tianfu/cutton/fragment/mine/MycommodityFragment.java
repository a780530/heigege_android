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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.mine.MyCommodityBatchDetailsActivity;
import com.tianfu.cutton.activity.mine.MyCommodityKunActivity;
import com.tianfu.cutton.adapter.MycommodityRecylerAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.MyStoreBean;
import com.tianfu.cutton.model.SelfOutBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoRelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MycommodityFragment extends BaseFragment {
    private static final String EXTRA_CONTENT_COMMODITY = "key";
    private int value;
    private View view;
    private PtrClassicFrameLayout mPtrrame;
    private RecyclerView recyclerCommodity;
    private HashMap<String, String> mapAll;
    private List<MyStoreBean.ValueBean.RowsBean> mdatas;
    private MycommodityRecylerAdapter mycommodityRecylerAdapter;
    private HashMap<String, String> oFFMap;
    private HashMap<String, String> onMap;
    private HashMap<String, String> outMap;
    private HashMap<String, String> depleteMap;
    private int pageNo = 1;
    private TextView tvSelfOut;
    private AutoRelativeLayout commodityLayout;
    private String[] splitOffTime;
    private String[] splitNowTime;
    private Boolean isTrueSave;
    private String pushOffTime;

    public static MycommodityFragment newInstance(int type) {
        MycommodityFragment mFragment = new MycommodityFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(EXTRA_CONTENT_COMMODITY, type);
        mFragment.setArguments(arguments);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_mycommodity, container, false);
            pushOffTime = SharedPreferencesUtil.getStringValue(getActivity(),"pushOffTime");
            isTrueSave = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isTrueSave");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(System.currentTimeMillis());
            Date date = new Date(lt);
            String res = simpleDateFormat.format(date);
            splitNowTime = res.split(" ");
            initView();
            return view;
        }
    }
private boolean isTrue = false;
    private void initView() {
        tvSelfOut = (TextView) view.findViewById(R.id.tv_selfOut);
        final ImageView ivTrue = (ImageView) view.findViewById(R.id.ivTrue);
        LinearLayout commodityTrue = (LinearLayout) view.findViewById(R.id.commodityTrue);
        commodityTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTrue){
                    isTrue = true;
                    ivTrue.setImageResource(R.mipmap.ic_commodity_truewhite);
                }else{
                    isTrue = false;
                    ivTrue.setImageResource(R.mipmap.ic_commodity_true);
                }
                SharedPreferencesUtil.saveBooleanValue(BaseApplication.getContextObject(),"isTrueSave",isTrue);
            }
        });
        final Map map = new HashMap();
        map.put("deviceNo",Common.deviceNo);
        map.put("version",Common.versionNo);
        map.put("from",Common.from);

        commodityLayout = (AutoRelativeLayout) view.findViewById(R.id.commodityLayout);
        LinearLayout commodityClose = (LinearLayout) view.findViewById(R.id.commodityClose);
        commodityClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭提醒
                commodityLayout.setVisibility(View.GONE);
                HttpManager.getServerApi().pushOff(map).enqueue(new CallBack<SelfOutBean>() {
                    @Override
                    public void success(SelfOutBean data) {
                        if (data != null) {
                            if (data.success){
                                if (data.value!=null){

                                }
                            }
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {

                    }
                });
            }
        });
        mPtrrame = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_purchase_frame);
        recyclerCommodity = (RecyclerView) view.findViewById(R.id.recyclerCommodity);
        initSelfData();
        initPtr();
    }

    private void initSelfData() {
        Map map  = new HashMap();
        map.put("deviceNo",Common.deviceNo);
        map.put("version",Common.versionNo);
        map.put("from",Common.from);
        HttpManager.getServerApi().outoExpiringCount(map).enqueue(new CallBack<SelfOutBean>() {
            @Override
            public void success(SelfOutBean data) {
                if (data != null) {
                    if (data.success){
                        if (data.value!=null){
                            if (pushOffTime!=null){
                                splitOffTime = pushOffTime.split(" ");
                                if (splitOffTime[0].equals(splitNowTime[0])){
                                    if (!isTrueSave){
                                        if (Float.parseFloat(data.value)>0){
                                            tvSelfOut.setText("今天有"+data.value+"批商品自动下架");
                                        }else{
                                            commodityLayout.setVisibility(View.GONE);
                                        }
                                    }else{
                                        commodityLayout.setVisibility(View.GONE);
                                    }
                                }else{
                                    if (Float.parseFloat(data.value)>0){
                                        tvSelfOut.setText("今天有"+data.value+"批商品自动下架");
                                    }else{
                                        commodityLayout.setVisibility(View.GONE);
                                    }
                                }
                            }else{
                                    if (Float.parseFloat(data.value)>0){
                                        tvSelfOut.setText("今天有"+data.value+"批商品自动下架");
                                    }else{
                                        commodityLayout.setVisibility(View.GONE);
                                    }
                            }


                         /*   */
                        }
                    }
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
                pageNo++;
                value = getArguments().getInt(EXTRA_CONTENT_COMMODITY, -1);
                switch (value) {
                    case 1:
                        shouData(mapAll);
                        break;
                    case 2:
                        shouData(oFFMap);
                        break;
                    case 3:
                        shouData(onMap);
                        break;
                    case 4:
                        shouData(outMap);
                        break;
                    case 5:
                        shouData(depleteMap);
                        break;
                }
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //下拉刷新
//                Toast.makeText(getActivity(), "下啦", Toast.LENGTH_SHORT).show();
                pageNo = 1;
                value = getArguments().getInt(EXTRA_CONTENT_COMMODITY, -1);
                switch (value) {
                    case 1:
                        shouData(mapAll);
                        break;
                    case 2:
                        shouData(oFFMap);
                        break;
                    case 3:
                        shouData(onMap);
                        break;
                    case 4:
                        shouData(outMap);
                        break;
                    case 5:
                        shouData(depleteMap);
                        break;
                }
            }
        });
    }



    private void shouData(final HashMap<String, String> params) {
        showProgressBar("", true);
        mPtrrame.refreshComplete();
        params.put("pageNum", "" + pageNo);
        HttpManager.getServerApi().myCommodity(params).enqueue(new CallBack<MyStoreBean>() {
            @Override
            public void success(MyStoreBean data) {
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
                    initRecyclerView();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
            }
        });
    }

    private void initRecyclerView() {
        if (mycommodityRecylerAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerCommodity.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            mycommodityRecylerAdapter = new MycommodityRecylerAdapter(R.layout.fragment_mycommodity_item, mdatas);
            mycommodityRecylerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    int id = mdatas.get(position).id;
                    String batchType = mdatas.get(position).batchType;
                    String code = mdatas.get(position).code;
                    String state = mdatas.get(position).state;
                    if (batchType.equals("1")) {
                        Intent intent = new Intent(getActivity(), MyCommodityKunActivity.class);
                        intent.putExtra("code", code);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("state", state);
                        intent.putExtra("productId", id + "");
                        startActivity(intent);
                    } else if (batchType.equals("2")) {
                        Intent intent = new Intent(getActivity(), MyCommodityBatchDetailsActivity.class);
                        intent.putExtra("productId", id + "");
                        intent.putExtra("state", state);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("fromKun","0");
                        intent.putExtra("code", code);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), MyCommodityBatchDetailsActivity.class);
                        intent.putExtra("productId", id + "");
                        intent.putExtra("state", state);
                        intent.putExtra("batchType", batchType);
                        intent.putExtra("code", code);
                        intent.putExtra("fromKun","1");
                        startActivity(intent);
                    }

                }
            });
            mycommodityRecylerAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
                    int id = mdatas.get(position).id;
                    value = getArguments().getInt(EXTRA_CONTENT_COMMODITY, -1);
                    final Map<String, String> chageMap = new HashMap<String, String>();
                    chageMap.put("id", id + "");
                    switch (view.getId()) {
                        case R.id.commodiy_chushou:
                            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                    .setMessage("是否将商品状态改为已出售？")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            soldOut(position, chageMap);
                                        }
                                    })
                                    .create();
                            dialog.show();
                            break;
                        case R.id.commodiy_xiajia:
                            TextView commodiy_xiajia = (TextView) view.findViewById(R.id.commodiy_xiajia);
                            final String stateX = commodiy_xiajia.getText().toString();
                            AlertDialog dialog1 = new AlertDialog.Builder(getActivity())
                                    .setMessage("是否"+stateX+"此商品")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            offCommodity(stateX, position, chageMap);
                                        }
                                    })
                                    .create();
                            dialog1.show();
                            break;
                        case R.id.commodiy_xiaohao:
                            AlertDialog dialog2 = new AlertDialog.Builder(getActivity())
                                    .setMessage("是否将商品状态改为已消耗")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            deleteCommodity(position, chageMap);
                                        }
                                    })
                                    .create();
                            dialog2.show();
                            break;
                    }
                }
            });
            View getEmptyview = getLayoutInflater(getArguments()).inflate(R.layout.nodata_commodity, (ViewGroup) recyclerCommodity.getParent(), false);
            TextView tv_Commmodity = (TextView) getEmptyview.findViewById(R.id.tv_Commmodity);
            value = getArguments().getInt(EXTRA_CONTENT_COMMODITY, -1);
            if (value != 1) {
                tv_Commmodity.setText("老板，您还没有相关的商品");
            }
            mycommodityRecylerAdapter.setEmptyView(getEmptyview);
            recyclerCommodity.setAdapter(mycommodityRecylerAdapter);
        } else {
            mycommodityRecylerAdapter.notifyDataSetChanged();
        }
    }

    private void deleteCommodity(final int position, Map<String, String> chageMap) {
        chageMap.remove("state");
        chageMap.put("state", "DEPLETE");
        HttpManager.getServerApi().changeState(chageMap).enqueue(new CallBack<MyStoreBean>() {
            @Override
            public void success(MyStoreBean data) {
                if (data.success) {
                    mdatas.get(position).state = "DEPLETE";
                    mycommodityRecylerAdapter.notifyItemChanged(position);
                    ToastUtil.show(BaseApplication.getContextObject(), "消耗成功");
                    switch (value) {
                        case 2:
                            mycommodityRecylerAdapter.remove(position);
                            break;
                        case 3:
                            mycommodityRecylerAdapter.remove(position);
                            break;

                    }
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void offCommodity(String state, final int position, Map<String, String> chageMap) {
        chageMap.remove("state");

        if (state.equals("下架")) {
            chageMap.put("state", "OFF");
            HttpManager.getServerApi().changeState(chageMap).enqueue(new CallBack<MyStoreBean>() {
                @Override
                public void success(MyStoreBean data) {
                    if (data.success) {
                        mdatas.get(position).state = "OFF";
                        mycommodityRecylerAdapter.notifyItemChanged(position);
                        ToastUtil.show(BaseApplication.getContextObject(), "下架成功");
                        switch (value) {
                            case 3:
                                mycommodityRecylerAdapter.remove(position);
                                break;
                        }
                    }
                }

                @Override
                public void failure(ErrorType type, int httpCode) {

                }
            });
        } else {
            chageMap.put("state", "ON");
            HttpManager.getServerApi().changeState(chageMap).enqueue(new CallBack<MyStoreBean>() {
                @Override
                public void success(MyStoreBean data) {
                    if (data.success) {
                        mdatas.get(position).state = "ON";
                        mycommodityRecylerAdapter.notifyItemChanged(position);
                        ToastUtil.show(BaseApplication.getContextObject(), "上架成功");
                        switch (value) {
                            case 2:
                                mycommodityRecylerAdapter.remove(position);
                                break;
                        }
                    }
                }

                @Override
                public void failure(ErrorType type, int httpCode) {

                }
            });
        }
    }

    private void soldOut(final int position, Map<String, String> chageMap) {
        chageMap.put("state", "SOLD_OUT");
        HttpManager.getServerApi().changeState(chageMap).enqueue(new CallBack<MyStoreBean>() {
            @Override
            public void success(MyStoreBean data) {
                if (data.success) {
                    mdatas.get(position).state = "SOLD_OUT";
                    mycommodityRecylerAdapter.notifyItemChanged(position);
                    ToastUtil.show(BaseApplication.getContextObject(), "出售成功");
                    switch (value) {
                        case 2:
                            mycommodityRecylerAdapter.remove(position);
                            break;
                        case 3:
                            mycommodityRecylerAdapter.remove(position);
                            break;
                    }
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
        value = getArguments().getInt(EXTRA_CONTENT_COMMODITY, -1);
        switch (value) {
            case 1:
                mapAll = new HashMap<>();
                mapAll.put("deviceNo", Common.deviceNo);
                mapAll.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
                mapAll.put("state", "");
                shouData(mapAll);
                break;
            case 2:
                oFFMap = new HashMap<>();
                oFFMap.put("deviceNo", Common.deviceNo);
                oFFMap.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
                oFFMap.put("state", "OFF");
                shouData(oFFMap);
                break;
            case 3:
                onMap = new HashMap<>();
                onMap.put("deviceNo", Common.deviceNo);
                onMap.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
                onMap.put("state", "ON");
                shouData(onMap);
                break;
            case 4:
                outMap = new HashMap<>();
                outMap.put("deviceNo", Common.deviceNo);
                outMap.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
                outMap.put("state", "SOLD_OUT");
                shouData(outMap);
                break;
            case 5:
                depleteMap = new HashMap<>();
                depleteMap.put("deviceNo", Common.deviceNo);
                depleteMap.put("mobile", SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile"));
                depleteMap.put("state", "DEPLETE");
                shouData(depleteMap);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
