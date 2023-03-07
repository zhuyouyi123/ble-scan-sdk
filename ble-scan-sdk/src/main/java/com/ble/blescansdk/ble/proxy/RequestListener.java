package com.ble.blescansdk.ble.proxy;

import com.ble.blescansdk.ble.callback.BleScanCallback;

public interface RequestListener <T>{

    void startScan(BleScanCallback<T> callback, long scanPeriod);

}
