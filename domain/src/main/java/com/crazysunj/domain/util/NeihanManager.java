/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.crazysunj.domain.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.crazysunj.domain.BuildConfig;

import net.grandcentrix.tray.core.ItemNotFoundException;

import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

/**
 * @author: sunjian
 * created on: 2017/9/23 下午9:41
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NeihanManager {

    private static final String KEY_MIN_TIME = "minTime";//上次更新时间的 Unix 时间戳，秒为单位
    private static final String KEY_SCREEN_WIDTH = "screenWidth";//屏幕宽度，px为单位
    private static final String KEY_IID = "iid";//一个长度为10的纯数字字符串，用于标识用户唯一性
    private static final String KEY_DEVICE_ID = "deviceId";//设备 id，一个长度为11的纯数字字符串
    private static final String KEY_UUID = "uuid";//用户 id，一个长度为15的纯数字字符串
    private static final String KEY_OPENUDID = "openudid";//一个长度为16的数字和小写字母混合字符串
    private static final String KEY_RESOLUTION = "resolution";//屏幕宽高，例如 1920*1080

    private NeihanPreference mNeihanPreference;

    private int sceenWidth;
    private String iid;
    private String deviceId;
    private String uuid;
    private String openudid;
    private String resolution;
    private String ac;
    private String versionCode;
    private String manifestVersionCode;
    private String updateVersionCode;
    private String versionName;

    @Inject
    public NeihanManager(NeihanPreference neihanPreference) {
        mNeihanPreference = neihanPreference;
        try {
            sceenWidth = mNeihanPreference.getInt(KEY_SCREEN_WIDTH);
        } catch (ItemNotFoundException e) {
            Context context = mNeihanPreference.getContext();
            sceenWidth = context.getResources().getDisplayMetrics().widthPixels;
            mNeihanPreference.put(KEY_SCREEN_WIDTH, sceenWidth);
        }

        try {
            iid = mNeihanPreference.getString(KEY_IID);
        } catch (ItemNotFoundException e) {
            iid = createNumberString(10);
            mNeihanPreference.put(KEY_IID, iid);
        }

        try {
            deviceId = mNeihanPreference.getString(KEY_DEVICE_ID);
        } catch (ItemNotFoundException e) {
            deviceId = createNumberString(11);
            mNeihanPreference.put(KEY_DEVICE_ID, deviceId);
        }

        try {
            uuid = mNeihanPreference.getString(KEY_UUID);
        } catch (ItemNotFoundException e) {
            uuid = createNumberString(15);
            mNeihanPreference.put(KEY_UUID, uuid);
        }

        try {
            openudid = mNeihanPreference.getString(KEY_OPENUDID);
        } catch (ItemNotFoundException e) {
            openudid = createNumberAndLowercaseString(16);
            mNeihanPreference.put(KEY_OPENUDID, openudid);
        }

        try {
            resolution = mNeihanPreference.getString(KEY_RESOLUTION);
        } catch (ItemNotFoundException e) {
            DisplayMetrics displayMetrics = mNeihanPreference.getContext().getResources().getDisplayMetrics();
            resolution = String.format(Locale.getDefault(), "%d*%d", displayMetrics.widthPixels, displayMetrics.heightPixels);
            mNeihanPreference.put(KEY_RESOLUTION, resolution);
        }

        ac = getNetworkType();
        versionName = BuildConfig.VERSION_NAME;
        versionCode = versionName.replaceAll("\\.", "");
        manifestVersionCode = versionName;
        updateVersionCode = manifestVersionCode + "0";
    }

    public long getAmLocTime() {
        long amLocTime = System.currentTimeMillis();
        mNeihanPreference.put(KEY_MIN_TIME, (amLocTime / 1000L));
        return amLocTime;
    }

    //先调getMinTime再调getAmLocTime
    public long getMinTime() {
        return mNeihanPreference.getLong(KEY_MIN_TIME, (System.currentTimeMillis() / 1000L));
    }

    public int getSceenWidth() {
        return sceenWidth;
    }

    public String getIid() {
        return iid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getOpenudid() {
        return openudid;
    }

    public String getResolution() {
        return resolution;
    }

    public String getAc() {
        return ac;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getManifestVersionCode() {
        return manifestVersionCode;
    }

    public String getUpdateVersionCode() {
        return updateVersionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    private String createNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String createNumberAndLowercaseString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int charOrNum = random.nextInt(2);
            if (charOrNum == 0) {
                sb.append((char) (random.nextInt(26) + 97));
            } else if (charOrNum == 1) {
                sb.append(random.nextInt(10));
            }
        }
        return sb.toString();
    }

    private String getNetworkType() {

        NetworkInfo info = ((ConnectivityManager) mNeihanPreference.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "wifi";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return "2g";
                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return "3g";
                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return "4g";
                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            return "3g";
                        } else {
                            return "wifi";
                        }
                }
            } else {
                return "wifi";
            }
        }
        return "wifi";
    }
}
