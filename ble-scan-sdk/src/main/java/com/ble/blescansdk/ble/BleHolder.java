package com.ble.blescansdk.ble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.ble.blescansdk.ble.callback.BleScanCallback;
import com.ble.blescansdk.ble.callback.BluetoothChangedObserver;
import com.ble.blescansdk.ble.callback.IBleStatusCallback;
import com.ble.blescansdk.ble.entity.BleDevice;
import com.ble.blescansdk.ble.enums.ErrorStatusEnum;
import com.ble.blescansdk.ble.proxy.RequestImpl;
import com.ble.blescansdk.ble.proxy.RequestListener;
import com.ble.blescansdk.ble.proxy.RequestProxy;

import java.util.Objects;

public final class BleHolder<T extends BleDevice> {

    @SuppressLint("StaticFieldLeak")
    public static volatile BleHolder instance = new BleHolder<>();

    @SuppressLint("StaticFieldLeak")
    private static Context context = null;

    private RequestListener<T> request;

    private static BleOptions bleOptions;


    private final Object locker = new Object();

    private BluetoothAdapter bluetoothAdapter;

    // 监听蓝牙状态改变
    private BluetoothChangedObserver bleObserver;

    public static <T extends BleDevice> BleHolder<T> getInstance() {
        synchronized (BleHolder.class) {
            if (Objects.isNull(instance)) {
                return new BleHolder<>();
            }
        }
        return instance;
    }

    public void init(Context ctx) {
        context = ctx;
        // 初始化 bleOptions
        bleOptions = Objects.isNull(bleOptions) ? getBleOptions() : bleOptions;
        request = (RequestListener<T>) RequestProxy.newProxy().bindProxy(RequestImpl.newRequestImpl());
    }

    public static Context getContext() {
        return context;
    }

    /**
     * start scanning
     */
    public void startScan(BleScanCallback<T> callback) {
        if (!checkContext()) {
            callback.onScanFailed(ErrorStatusEnum.CONTEXT_NULL.getCode());
            return;
        }
        request.startScan(callback, getBleOptions().scanPeriod);
    }

    private void initBleObserver() {
        if (Objects.isNull(bleObserver)) {
            bleObserver = new BluetoothChangedObserver();
            bleObserver.registerReceiver();
        }
    }

    public void setBluetoothChangedObserver(IBleStatusCallback callback) {
        if (Objects.nonNull(bleObserver)) {
            bleObserver.setBleStatusCallback(callback);
        }
    }

    public static BleOptions getBleOptions() {
        if (bleOptions == null) {
            bleOptions = new BleOptions<>();
        }
        return bleOptions;
    }

    private boolean checkContext() {
        return !Objects.isNull(context);
    }

    public BluetoothAdapter getBluetoothAdapter() {
        if (Objects.isNull(bluetoothAdapter)) {
            return BluetoothAdapter.getDefaultAdapter();
        }
        return bluetoothAdapter;
    }
}
