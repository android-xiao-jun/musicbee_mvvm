package com.musichive.common.bean;

import android.text.TextUtils;


import com.musichive.common.utils.ToastUtils;

import io.reactivex.observers.DisposableObserver;

/**
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-10-23 : 下午3:09
 */
public abstract class ModelSubscriber<T> extends DisposableObserver<BaseResponseBean<T>> {

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        resultMsg("网络错误或服务器异常!");//e.getMessage()
        onFailure(ResponseCode.NETWORK_ERROR);
    }

    @Override
    public final void onNext(BaseResponseBean<T> responseData) {
        if (responseData.isSuccess()) {
            onSuccess(responseData.getData());
        } else if (responseData.isInValidToken()) {
//            SessionHelper.clearToken();
            onFailure(responseData.getCode());
        } else {
            if (ResponseCode.CODE_10000.equals(responseData.getCode())) {
                onFailure(responseData.getCode());
                showMsg(responseData.getMsg());
            } else {
                if(TextUtils.isEmpty(responseData.getMessage())){
                    resultMsg(responseData.getMsg());
                }else{
                    resultMsg(responseData.getMessage());
                }
                onFailure(responseData.getCode());
            }
        }
    }

    @Override
    public void onComplete() {
    }

    protected void resultMsg(String msg) {

    }

    protected void showMsg(String msg) {
        ToastUtils.showShort(msg);
    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(String errorCode);
}