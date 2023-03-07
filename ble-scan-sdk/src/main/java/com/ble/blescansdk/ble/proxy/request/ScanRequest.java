package com.ble.blescansdk.ble.proxy.request;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;

import androidx.core.os.HandlerCompat;

import com.ble.blescansdk.ble.BleHolder;
import com.ble.blescansdk.ble.annotation.Implement;
import com.ble.blescansdk.ble.callback.BleScanCallback;
import com.ble.blescansdk.ble.callback.IScanWrapperCallback;
import com.ble.blescansdk.ble.entity.BleDevice;
import com.ble.blescansdk.ble.entity.bo.BlueToothDeviceBO;
import com.ble.blescansdk.ble.enums.ErrorStatusEnum;
import com.ble.blescansdk.ble.scan.BleScannerCompat;
import com.ble.blescansdk.ble.scan.handle.BleHandler;
import com.ble.blescansdk.ble.utils.PermissionUtil;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Implement(ScanRequest.class)
public class ScanRequest<T extends BleDevice> implements IScanWrapperCallback {

    private static final String TAG = ScanRequest.class.getSimpleName();

    private BleScanCallback<T> bleScanCallback;

    private static boolean scanning = false;

    /**
     * 扫描到的设备
     */
    private final ConcurrentMap<String, BlueToothDeviceBO> scanDevices = new ConcurrentHashMap<>();

    private final Handler handler = BleHandler.of();

    /**
     * 是否忽略重复的设备
     */
    private static boolean isIgnoreRepeat = false;

    public void startScan(BleScanCallback<T> callback, long scanPeriod) {
        if (callback == null) {
            throw new IllegalArgumentException("BleScanCallback can not be null!");
        }
        bleScanCallback = callback;
        if (!PermissionUtil.checkSelfPermission(BleHolder.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            bleScanCallback.onScanFailed(ErrorStatusEnum.ACCESS_COARSE_LOCATION_NOT_EXIST.getCode());
            return;
        }


        if (!BleHolder.getInstance().getBluetoothAdapter().isEnabled()) {
            bleScanCallback.onScanFailed(ErrorStatusEnum.BLUETOOTH_NOT_OPEN.getCode());
            return;
        }

        // 扫描间隔大于0 且未开启连续扫描
        if (scanPeriod >= 0 && !BleHolder.getBleOptions().isContinuousScanning()) {
            HandlerCompat.postDelayed(handler, () -> {
                if (scanning) {
                    stopScan();
                }
            }, "stop_token", scanPeriod);
        }
        BleScannerCompat.getScanner().startScan(this);
    }


    public void stopScan() {
        if (!BleHolder.getInstance().getBluetoothAdapter().isEnabled()) {
            bleScanCallback.onScanFailed(ErrorStatusEnum.BLUETOOTH_NOT_OPEN.getCode());
            return;
        }
        if (!scanning) {
            return;
        }
        handler.removeCallbacksAndMessages("stop_token");
        BleScannerCompat.getScanner().stopScan();
    }


    @Override
    public void onStart() {
        scanning = true;
        if (bleScanCallback != null) {
            bleScanCallback.onStart();
            isIgnoreRepeat = BleHolder.getBleOptions().isIgnoreRepeat();
        }
    }

    @Override
    public void onStop() {
        scanning = false;
        if (bleScanCallback != null) {
            bleScanCallback.onStop();
            bleScanCallback = null;
        }
        scanDevices.clear();
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (Objects.isNull(device)) {
            return;
        }

        String address = device.getAddress().replaceAll(":", "");
        boolean macExist = scanDevices.containsKey(address);
        // mac不存在 或者 不忽略重复
        if (!macExist || !isIgnoreRepeat) {
            scanDevices.put(address, new BlueToothDeviceBO(device, rssi, scanRecord));
            if (bleScanCallback != null) {
                bleScanCallback.onLeScan(device, rssi, scanRecord);
            }
        }
    }

    @Override
    public void onScanFailed(int errorCode) {

    }
}
