package com.tianfu.cutton.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.adapter.HotAdapter;
import com.tianfu.cutton.adapter.ListAssociateAdapter;
import com.tianfu.cutton.adapter.SearchHisListAdapter;
import com.tianfu.cutton.adapter.base.OnItemClickListener;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.model.HotBean;
import com.tianfu.cutton.model.ListAssociatebean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.hot_recyclerView)
    RecyclerView hotRecyclerView;
    @BindView(R.id.tv_dismiss)
    TextView tvDismiss;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_search)
    AutoLinearLayout llSearch;
    @BindView(R.id.listRecyclerView)
    RecyclerView listRecyclerView;
    @BindView(R.id.delete_His)
    TextView deleteHis;
    @BindView(R.id.historyRecyclerView)
    RecyclerView historyRecyclerView;
    private Map<String, String> mapList;
    List<ListAssociatebean.ValueBean> mDatas;
    private ListAssociateAdapter listAssociateAdapter;
    private SearchHisListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        listRecyclerView.setVisibility(View.GONE);
        etSearch.addTextChangedListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent intent = new Intent(BaseApplication.getContextObject(), SearchContentActivity.class);
                    intent.putExtra("value", etSearch.getText().toString());
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        showList();
        showData();
    }

    private void showList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        historyRecyclerView.setLayoutManager(linearLayoutManager);
        Map<String, String> map = new HashMap<>();
        map.put("deviceNo", Common.deviceNo);
        map.put("type", "history");
        HttpManager.getServerApi().getHot(map).enqueue(new CallBack<HotBean>() {
            @Override
            public void success(final HotBean data) {
                if (data.success) {
                    listAdapter = new SearchHisListAdapter(BaseApplication.getContextObject(), data.value);
                    listAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int postion) {
                            Intent intent = new Intent(BaseApplication.getContextObject(),SearchContentActivity.class);
                            intent.putExtra("value",data.value.get(postion));
                            startActivity(intent);
                            finish();
                        }
                    });
                    historyRecyclerView.setAdapter(listAdapter);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void showData() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,3);
        hotRecyclerView.setLayoutManager(linearLayoutManager);
        Map<String, String> map = new HashMap<>();
        map.put("deviceNo", Common.deviceNo);
        map.put("type", "hot");
        HttpManager.getServerApi().getHot(map).enqueue(new CallBack<HotBean>() {
            @Override
            public void success(final HotBean data) {
                if (data.success) {
                    HotAdapter hotAdapter = new HotAdapter(BaseApplication.getContextObject(), data.value);
                    hotAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int postion) {
                            Intent intent = new Intent(BaseApplication.getContextObject(),SearchContentActivity.class);
                            intent.putExtra("keywords",data.value.get(postion));
                            startActivity(intent);
                            finish();
                        }
                    });
                    hotRecyclerView.setAdapter(hotAdapter);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        listRecyclerView.setVisibility(View.VISIBLE);
        String trim = etSearch.getText().toString().trim();
        llSearch.setVisibility(View.GONE);
        listRecyclerView.setVisibility(View.VISIBLE);
        mapList = new HashMap<>();
        mapList.put("keyword", trim);
        HttpManager.getServerApi().getlistAssociate(mapList).enqueue(new CallBack<ListAssociatebean>() {
            @Override
            public void success(ListAssociatebean data) {
                if (data.success) {
                    if (mDatas != null) {
                        mDatas.clear();
                        mDatas.addAll(data.value);
                    } else {
                        mDatas = data.value;
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
        if (listAssociateAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            listRecyclerView.setLayoutManager(linearLayoutManager);
            listAssociateAdapter = new ListAssociateAdapter(R.layout.list_text_item, mDatas);
            listAssociateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    int type = mDatas.get(position).type;
                    String value = mDatas.get(position).value;
                    Intent intent = new Intent(BaseApplication.getContextObject(), SearchContentActivity.class);
                    intent.putExtra("type", type + "");
                    intent.putExtra("value", value);
                    startActivity(intent);
                    finish();
                }
            });
            listRecyclerView.setAdapter(listAssociateAdapter);
        } else {
            listAssociateAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @OnClick({R.id.tv_dismiss, R.id.delete_His})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dismiss:
                onBackPressed();
                break;
            case R.id.delete_His:
                Map<String,String> map = new HashMap<>();
                map.put("deviceNo",Common.deviceNo);
                HttpManager.getServerApi().deleteSearchHis(map).enqueue(new CallBack<CodeValidate>() {
                    @Override
                    public void success(CodeValidate data) {
                        if (data.success) {
                            historyRecyclerView.setVisibility(View.GONE);
                            ToastUtil.show(BaseApplication.getContextObject(), "删除成功");
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {
                        ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
                    }
                });
                break;
        }
    }
}
