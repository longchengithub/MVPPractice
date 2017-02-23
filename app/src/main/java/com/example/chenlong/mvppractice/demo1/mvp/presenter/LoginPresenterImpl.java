package com.example.chenlong.mvppractice.demo1.mvp.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.chenlong.mvppractice.demo1.mvp.view.ILoginView;

/**
 * Created by ChenLong on 2017/2/5.
 */

public class LoginPresenterImpl implements ILoginPresenter
{
    private ILoginView mLoginView;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            mLoginView.showProgress();
        }
    };

    public LoginPresenterImpl(ILoginView loginView)
    {
        mLoginView = loginView;
    }

    @Override
    public void OnLogin()
    {
        new Thread(() -> {

            handler.sendEmptyMessage(0);

            if ("admin".equals(mLoginView.getAccount()) && "123456".equals(mLoginView.getPassword()))
            {
                handler.postDelayed(() -> {
                    mLoginView.loginSuccess();
                    mLoginView.hideProgress();
                }, 2000);
            } else
            {
                handler.postDelayed(() -> {
                    mLoginView.loginFailed();
                    mLoginView.hideProgress();
                }, 2000);
            }
        }).start();
    }
}
