package com.ble.blescansdk.ble;

import android.content.Context;

import com.ble.blescansdk.ble.callback.BleWrapperCallback;
import com.ble.blescansdk.ble.entity.BleDevice;
import com.ble.blescansdk.ble.enums.ManufacturerEnum;

public class BleOptions<T extends BleDevice> {

    /**
     * 蓝牙扫描周期时长
     */
    public long scanPeriod = 10 * 1000L;

    public int manufacturer = ManufacturerEnum.THIRD_PARTY.getCode();

    /**
     * 连续扫描
     */
    public boolean continuousScanning = false;

    /**
     * 设置是否过滤扫描到的设备(已扫描到的不会再次扫描)
     */
    public boolean ignoreRepeat = false;


//    UUID[] uuid_services_extra = new UUID[]{};
//    UUID uuid_service = UUID.fromString("0000fee9-0000-1000-8000-00805f9b34fb");
//    UUID uuid_write_cha = UUID.fromString("d44bc439-abfd-45a2-b575-925416129600");
//    UUID uuid_read_cha = UUID.fromString("d44bc439-abfd-45a2-b575-925416129600");
//    UUID uuid_notify_cha = UUID.fromString("d44bc439-abfd-45a2-b575-925416129601");
//    UUID uuid_notify_desc = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
//
//    UUID uuid_ota_service = UUID.fromString("0000fee8-0000-1000-8000-00805f9b34fb");
//    UUID uuid_ota_notify_cha = UUID.fromString("003784cf-f7e3-55b4-6c4c-9fd140100a16");
//    UUID uuid_ota_write_cha = UUID.fromString("013784cf-f7e3-55b4-6c4c-9fd140100a16");
//
//    public UUID getUuidService() {
//        return uuid_service;
//    }

//    private BleWrapperCallback<T> bleWrapperCallback;
//
//    public BleWrapperCallback<T> getBleWrapperCallback() {
//        return bleWrapperCallback;
//    }
//
//    public BleOptions<T> setBleWrapperCallback(BleWrapperCallback<T> bleWrapperCallback) {
//        this.bleWrapperCallback = bleWrapperCallback;
//        return this;
//    }


    /**
     * 设置连续扫描
     *
     * @param continuousScanning 是否是连续扫描
     * @return {@link BleOptions}
     */
    public BleOptions<T> setContinuousScanning(boolean continuousScanning) {
        this.continuousScanning = continuousScanning;
        return this;
    }

    public boolean isContinuousScanning() {
        return continuousScanning;
    }

    public BleOptions<T> setScanPeriod(long scanPeriod) {
        this.scanPeriod = scanPeriod;
        return this;
    }

    public void setManufacturer(ManufacturerEnum manufacturer) {
        this.manufacturer = manufacturer.getCode();
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public BleOptions<T> setIgnoreRepeat(boolean ignoreRepeat) {
        this.ignoreRepeat = ignoreRepeat;
        return this;
    }

    public boolean isIgnoreRepeat() {
        return ignoreRepeat;
    }
}
