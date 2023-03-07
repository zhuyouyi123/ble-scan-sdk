package com.ble.blescansdk.ble.callback;

import android.bluetooth.BluetoothDevice;

import com.ble.blescansdk.ble.entity.BleDevice;

public abstract class BleWrapperCallback<T extends BleDevice> extends BleScanCallback<T> {

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

    }

}
