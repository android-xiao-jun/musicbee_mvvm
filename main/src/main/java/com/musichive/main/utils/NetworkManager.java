/*
 * Copyright (c) 2015 Everimaging Co., Ltd.
 */
package com.musichive.main.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-12-7 : 下午3:11
 */
public class NetworkManager {

    /**
     * We regard no active NetworkInfo
     * and any other NetworkInfo type except
     * TYPE_WIFI and TYPE_MOBILE as NetworkStatus.NONE
     */
    public enum NetworkStatus {
        NONE,
        WIFI,
        MOBILE;

        public static NetworkStatus parseFromSysType(int type) {
            switch (type) {
                case ConnectivityManager.TYPE_MOBILE:
                    return MOBILE;
                case ConnectivityManager.TYPE_WIFI:
                    return WIFI;
                default:
                    return NONE;
            }
        }

        public static boolean isMobileStatus(NetworkStatus status) {
            return MOBILE == status;
        }

        public static boolean isWifiStatus(NetworkStatus status) {
            return WIFI == status;
        }

        public static boolean isNoneStatus(NetworkStatus status) {
            return NONE == status;
        }
    }

    public interface OnNetworkStatusChangedListener {
        void onNetworkStatusChanged(NetworkManager manager, NetworkStatus currentStatus);
    }

    private static volatile NetworkManager instance;

    private Context mContext;
    private ConnectivityReceiver mConnectivityReceiver;
    private boolean mIsConnected;
    private NetworkStatus mCurrentStatus;
    private List<OnNetworkStatusChangedListener> mNetworkStatusChangedListeners;

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    private NetworkManager() {
        mIsConnected = false;
        mCurrentStatus = NetworkStatus.NONE;
        mNetworkStatusChangedListeners = new ArrayList<>();
    }

    public NetworkStatus getCurrentStatus() {
        return mCurrentStatus;
    }

    /**
     * Initializes NetworkManager instance with context     *
     *
     * @param context
     */
    public void init(Context context) {
        if (mContext == null) {
            mContext = context.getApplicationContext();
            updateCurrentStatus();
            registerReceiver();
        }
    }

    public synchronized void registerStatusListener(OnNetworkStatusChangedListener listener) {
        checkInit();
        if (!mNetworkStatusChangedListeners.contains(listener)) {
            mNetworkStatusChangedListeners.add(listener);
        }
    }

    public synchronized void unregisterStatusListener(OnNetworkStatusChangedListener listener) {
        checkInit();
        mNetworkStatusChangedListeners.remove(listener);
    }

    public boolean isConnected() {
        checkInit();
        return mIsConnected;
    }

    /**
     * Check is specific network status connected
     *
     * @param status
     * @return
     */
    public boolean isSpecificConnected(NetworkStatus status) {
        checkInit();
        return status == mCurrentStatus && mIsConnected;
    }

    public boolean isMobileStatus() {
        checkInit();
        return NetworkStatus.isMobileStatus(mCurrentStatus);
    }

    public boolean isWifiStatus() {
        checkInit();
        return NetworkStatus.isWifiStatus(mCurrentStatus);
    }

    public boolean isNoneStatus() {
        checkInit();
        return NetworkStatus.isNoneStatus(mCurrentStatus);
    }

    private void registerReceiver() {
        mConnectivityReceiver = new ConnectivityReceiver() {
            @Override
            public void onConnectivityChanged() {
                updateCurrentStatus();
            }
        };
        mConnectivityReceiver.register(mContext);
    }

    public void onDestory() {
        checkInit();
        mConnectivityReceiver.unregister(mContext);
    }

    private boolean setIsConnected(boolean isConnected) {
        boolean isChanged = false;
        if (mIsConnected != isConnected) {
            isChanged = true;
        }
        mIsConnected = isConnected;

        return isChanged;
    }

    private boolean setCurrentStatus(NetworkStatus networkStatus) {
        boolean isChanged = false;
        if (mCurrentStatus != networkStatus) {
            isChanged = true;
        }
        mCurrentStatus = networkStatus;

        return isChanged;
    }

    private void updateCurrentStatus() {
        ConnectivityManager connManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connManager != null) {

            NetworkInfo networkInfo = connManager
                    .getActiveNetworkInfo();

            if (networkInfo != null) {
                setIsConnected(networkInfo.isConnected());
                setCurrentStatus(NetworkStatus.parseFromSysType(networkInfo.getType()));

            } else {
                setIsConnected(false);
                setCurrentStatus(NetworkStatus.NONE);
            }
        }

        notifyNetworkStatusChanged();
    }

    private void notifyNetworkStatusChanged() {
        for (OnNetworkStatusChangedListener listener : mNetworkStatusChangedListeners) {
            listener.onNetworkStatusChanged(this, mCurrentStatus);
        }
    }

    private void checkInit() {
        if (mContext == null) {
            throw new IllegalStateException("NetworkManager must be init with context before using");
        }
    }
}