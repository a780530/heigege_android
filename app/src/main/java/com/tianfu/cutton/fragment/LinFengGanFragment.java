package com.tianfu.cutton.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianfu.cutton.R;

/**
 * A simple {@link Fragment} subclass.
 * 页面可见的时候加载数据
 *
 */
public class LinFengGanFragment extends Fragment {


    private View mRootView;

    public LinFengGanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_lin_feng_gan, container, false);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            return mRootView;
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
//        请求数据 展示
    /*    HttpManager.getServerApi().isLogin(new HashMap<String, String>()).enqueue(new CallBack<IsLogin>() {
            @Override
            public void success(IsLogin data) {
                //请求成功的回调
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
 //请求失败的回调  缺省页面
            }
        });*/
    }
}
