package com.example.chenlong.mvppractice.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.chenlong.mvppractice.BaseApplication;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * 网络相关工具类
 *
 * @HotBitmapGG
 */
public class NetWorkUtil {

  private NetWorkUtil() {

  }


  public static boolean isNetworkConnected() {

    if (BaseApplication.newInstance() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) BaseApplication.newInstance()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null) {
        return mNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  public static boolean isWifiConnected() {

    if (BaseApplication.newInstance() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) BaseApplication.newInstance()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(
          ConnectivityManager.TYPE_WIFI);
      if (mWiFiNetworkInfo != null) {
        return mWiFiNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  public static boolean isMobileConnected() {

    if (BaseApplication.newInstance() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) BaseApplication.newInstance()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mMobileNetworkInfo = mConnectivityManager
          .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      if (mMobileNetworkInfo != null) {
        return mMobileNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  public static int getConnectedType() {

    if (BaseApplication.newInstance() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) BaseApplication.newInstance()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
        return mNetworkInfo.getType();
      }
    }
    return -1;
  }
}
