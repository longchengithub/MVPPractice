package com.example.chenlong.mvppractice.demo2.mvp.presenter;

import com.example.chenlong.mvppractice.demo2.mvp.bean.MeiZiBean;
import com.example.chenlong.mvppractice.demo2.mvp.view.IMeiziView;
import com.example.chenlong.mvppractice.http.RetrofitHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ChenLong on 2017/2/5.
 */

public class MeiZiPresenterImpl implements IMeiziPresenter
{
    private IMeiziView meiziView;

    public MeiZiPresenterImpl(IMeiziView meiziView)
    {
        this.meiziView = meiziView;
    }

    //默认访问第一页,每次访问20行数据
    private int page = 1;
    private int pageSize = 20;

    @Override
    public void loadData()
    {
        RetrofitHelper.getMeiziApi()
                .getMeiZiInfos(pageSize, page)
                .doOnSubscribe(subscription -> meiziView.showProgress())
                .delay(1, TimeUnit.SECONDS)
                .filter(meiZiBean -> !meiZiBean.isError())
                .map(MeiZiBean::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultsBeanList -> {
                    meiziView.setAdapterData(resultsBeanList);
                    meiziView.hideProgress();
                }, throwable -> {
                    meiziView.hideProgress();
                    meiziView.loadFailed();
                });
    }

    @Override
    public void loadMore()
    {
        page++;
        RetrofitHelper.getMeiziApi()
                .getMeiZiInfos(pageSize, page)
                //这里应该有上拉加载更多的footer的加载提示 这里简化了不写
                .filter(meiZiBean -> !meiZiBean.isError())
                .map(MeiZiBean::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultsBeanList -> meiziView.addAdapterData(pageSize, page, resultsBeanList), throwable -> meiziView.loadFailed());
    }
}
