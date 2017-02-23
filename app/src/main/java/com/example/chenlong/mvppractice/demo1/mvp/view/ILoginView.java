package com.example.chenlong.mvppractice.demo1.mvp.view;

/**
 * Created by ChenLong on 2017/2/5.
 */

/**
 * 登录页面包含的方法有
 * 1.显示或隐藏进度条
 * 2.登录成功或失败
 * 3.获取登录时输入的account,password
 */
public interface ILoginView
{
    void showProgress();

    void hideProgress();

    void loginSuccess();

    void loginFailed();

    String getAccount();

    String getPassword();
}
