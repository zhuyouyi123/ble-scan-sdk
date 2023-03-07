package com.ble.blescansdk.ble.scan;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;

import com.ble.blescansdk.ble.BleHolder;
import com.ble.blescansdk.ble.callback.IScanWrapperCallback;
import com.ble.blescansdk.ble.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

class BluetoothScannerImplLollipop extends BleScannerCompat {
    private static final String TAG = "BluetoothScannerImplLol";

    private BluetoothLeScanner scanner;
    private ScanSettings scanSettings;
    private List<ScanFilter> filters = new ArrayList<>();

    @Override
    public void startScan(IScanWrapperCallback scanWrapperCallback) {
        super.startScan(scanWrapperCallback);
        if (scanner == null) {
            scanner = bluetoothAdapter.getBluetoothLeScanner();
        }
        setScanSettings();
        scanner.startScan(filters, scanSettings, scannerCallback);
    }

    @Override
    public void stopScan() {
        if (scanner == null) {
            scanner = bluetoothAdapter.getBluetoothLeScanner();
        }
        scanner.stopScan(scannerCallback);
        super.stopScan();
    }

    private final ScanCallback scannerCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (null == result.getScanRecord()) {
                return;
            }
            byte[] scanRecord = result.getScanRecord().getBytes();
            if (scanWrapperCallback != null) {
                scanWrapperCallback.onLeScan(device, result.getRssi(), scanRecord);
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult sr : results) {
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            if (scanWrapperCallback != null) {
                scanWrapperCallback.onScanFailed(errorCode);
            }
        }
    };

    private void setScanSettings() {
        boolean background = SystemUtil.isBackground(BleHolder.getInstance().getContext());
//        BleLog.d(TAG, "currently in the background:>>>>>" + background);
//
//        ScanFilter filter = Ble.options().getScanFilter();
//        if (filter != null) {
//            filters.add(filter);
//        }
        if (background) {
            scanSettings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                    .build();
        } else {
            scanSettings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();
        }
    }
}
