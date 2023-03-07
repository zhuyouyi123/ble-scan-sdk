package com.ble.blescansdk.ble.callback;

import android.bluetooth.BluetoothDevice;

import com.ble.blescansdk.ble.analysis.AnalysisSeekDeviceHandle;
import com.ble.blescansdk.ble.entity.BleDevice;
import com.ble.blescansdk.ble.entity.seek.SeekBleDevice;

public abstract class SeekBleWrapperCallback<T extends BleDevice> extends BleScanCallback<T> {

    private final AnalysisSeekDeviceHandle analysisSeekDeviceHandle = AnalysisSeekDeviceHandle.getInstance();

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        SeekBleDevice seekBleDevice = analysisSeekDeviceHandle.handle(scanRecord, device, rssi);
        onRangedScan(seekBleDevice, rssi);
    }

    public abstract void onRangedScan(SeekBleDevice device, int rssi);

}
