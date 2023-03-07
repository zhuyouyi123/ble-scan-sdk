package com.ble.blescansdk.ble.utils;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.ble.blescansdk.ble.BleHolder;

public class PermissionUtil {
    //判断某个权限是否打开
    public static boolean checkSelfPermission(Context context, String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED;
    }

    /**
     * 校验未知权限信息
     *
     * @return 是否存在权限
     */
    public static boolean checkLocationPermission() {
        LocationManager locationManager = (LocationManager) BleHolder.getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
