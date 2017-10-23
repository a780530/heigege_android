package com.tianfu.cutton.net;


import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by xiaohei on 2017/3/28.
 */

public  abstract class CallBack<T> implements retrofit2.Callback<T> {
    @Override
    public final void onResponse(Call<T> call, Response<T> response) {


        T data =  response.body();
        if(data != null){
            success(data);
        }else {
            failure(ErrorType.ERROR_API, response.code());
        }

        if(response.code() == 402){
            // UserClient.getDefault().userReLogin();
        }

    }




    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        //TODO 统一处理
        failure(ErrorType.ERROR_NETCONNECT, -1);

    }


    public abstract void success(T data);

    public abstract void failure(ErrorType type, int httpCode);

    public void onProgress(int progress){
        // TODO  监听进度
    }

    public enum ErrorType{
        ERROR_NETCONNECT,
        ERROR_API,
        ERROR_FORMAT,
    }
}
