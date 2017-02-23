package com.example.chenlong.mvppractice;

import android.app.Application;
import android.content.Context;

/**
 * Created by ChenLong on 2017/2/5.
 */

public class BaseApplication extends Application
{

    private static Context mContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext = this;
    }

    public static Context newInstance()
    {
        return mContext;
    }
}
