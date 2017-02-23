package com.example.chenlong.mvppractice.demo2.mvp.view;

import com.example.chenlong.mvppractice.demo2.mvp.bean.MeiZiBean;

import java.util.List;

/**
 * Created by ChenLong on 2017/2/5.
 */

public interface IMeiziView
{
    void showProgress();

    void hideProgress();

    void addAdapterData(int pageSize, int page, List<MeiZiBean.ResultsBean> resultsBeen);

    void setAdapterData(List<MeiZiBean.ResultsBean> resultsBeen);

    void loadFailed();
}
