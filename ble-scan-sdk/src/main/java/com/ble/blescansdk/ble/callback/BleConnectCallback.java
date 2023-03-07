package com.ble.blescansdk.ble.callback;

/**
 * 蓝牙扫描回调
 * Created by Administrator on 2016/12/21.
 *
 * @param <T>
 */
public abstract class BleConnectCallback<T> {

    /**
     * 描结果回调
     *
     * @param device     设备
     * @param rssi       信号值
     * @param scanRecord 扫描原始数据
     */
    public abstract void onLeScan(T device, int rssi, byte[] scanRecord);
}
