package com.ble.blescansdk.ble.enums;

import android.content.Context;

import com.ble.blescansdk.R;
import com.ble.blescansdk.ble.BleHolder;

import java.util.Objects;

public enum ErrorStatusEnum {

    CONTEXT_NULL(R.string.init_error_context_is_null, "context is null"),
    BLUETOOTH_NOT_OPEN(R.string.ble_error_bluetooth_not_open, "bluetooth is not open"),
    ACCESS_COARSE_LOCATION_NOT_EXIST(R.string.permission_error_access_coarse_location, "access coarse location not exist"),
    UNKNOWN_ERROR(R.string.unknown_error, "unknown error"),

    ;

    private final int code;
    private final String desc;

    ErrorStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getFailMessage(int code) {
        Context context = BleHolder.getContext();
        if (Objects.isNull(context)) {
            return CONTEXT_NULL.getDesc();
        }
        for (ErrorStatusEnum errorStatusEnum : ErrorStatusEnum.values()) {
            if (errorStatusEnum.getCode() == code) {
                return context.getString(code);
            }
        }
        return context.getString(UNKNOWN_ERROR.getCode());
    }
}
