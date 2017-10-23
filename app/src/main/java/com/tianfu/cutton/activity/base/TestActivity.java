package com.tianfu.cutton.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tianfu.cutton.R;
import com.tianfu.cutton.model.TestBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TestActivity extends AppCompatActivity {


    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        Map<String,String> map = new HashMap();
        map.put("code","65079161001");
        map.put("fromKun","0");
      HttpManager.getServerApi().getTest(map).enqueue(new CallBack<TestBean>() {
          @Override
          public void success(TestBean data) {
              if (data.success){
                  ToastUtil.show(BaseApplication.getContextObject(),"成功");
              }else{
                  ToastUtil.show(BaseApplication.getContextObject(),"失败");
              }

          }

          @Override
          public void failure(ErrorType type, int httpCode) {
              ToastUtil.show(BaseApplication.getContextObject(),"网络错误");
          }
      });
    }
}






