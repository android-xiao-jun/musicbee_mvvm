/*
 * Copyright (c) 2015 Everimaging Co., Ltd.
 */

package com.musichive.main.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-12-7 : 下午3:11
 */
public abstract class ConnectivityReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.d("ConnectivityReceiver->onReceive: " + intent);

        onConnectivityChanged();
    }

    public void register(Context context){
        IntentFilter connectivityFilter = new IntentFilter();
        connectivityFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this,connectivityFilter);
    }

    public void unregister(Context context){
        context.unregisterReceiver(this);
    }

    public abstract void onConnectivityChanged();
}
