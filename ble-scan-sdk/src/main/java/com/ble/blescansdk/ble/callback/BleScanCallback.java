package com.ble.blescansdk.ble.callback;

import android.bluetooth.BluetoothDevice;

/**
 * 蓝牙连接回调
 * Created by Administrator on 2016/12/21.
 *
 * @param <T>
 */
public abstract class BleScanCallback<T> {

    /**
     * Start the scan
     */
    public void onStart() {
    }


    public abstract void onScanFailed(int errorCode);

    /**
     * Stop scanning
     */
    public void onStop() {
    }

    /**
     * Scan to device
     *
     * @param device     ble device object
     * @param rssi       rssi
     * @param scanRecord Bluetooth radio package
     */
    public abstract void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord);

}
