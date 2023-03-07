package com.ble.blescansdk.ble.proxy;

import com.ble.blescansdk.ble.callback.BleScanCallback;
import com.ble.blescansdk.ble.entity.BleDevice;
import com.ble.blescansdk.ble.proxy.request.ScanRequest;

/**
 * 请求实现类
 *
 * @param <T>
 */

public class RequestImpl<T extends BleDevice> implements RequestListener<T> {


    public static <T extends BleDevice> RequestImpl<T> newRequestImpl() {
        return new RequestImpl<>();
    }


    @Override
    public void startScan(BleScanCallback<T> callback, long scanPeriod) {
        ScanRequest<T> request = Rproxy.getRequest(ScanRequest.class);
        request.startScan(callback, scanPeriod);
    }

}
