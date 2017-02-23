package com.example.chenlong.mvppractice.demo2.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.chenlong.mvppractice.R;
import com.example.chenlong.mvppractice.demo2.mvp.bean.MeiZiBean;
import com.example.chenlong.mvppractice.demo2.mvp.presenter.MeiZiPresenterImpl;
import com.example.chenlong.mvppractice.demo2.mvp.ui.adapter.EndlessRecyclerOnScrollListener;
import com.example.chenlong.mvppractice.demo2.mvp.ui.adapter.MeiZiAdapter;
import com.example.chenlong.mvppractice.demo2.mvp.view.IMeiziView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiZiActivity extends AppCompatActivity implements IMeiziView
{

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    private MeiZiPresenterImpl meiZiPresenter;
    private List<MeiZiBean.ResultsBean> resultsBeanList = new ArrayList<>();
    private MeiZiAdapter meiZiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mei_zi);
        ButterKnife.bind(this);
        meiZiPresenter = new MeiZiPresenterImpl(this);

        mToolBar.setTitle("Mei Zi");

        initSwipeRefresh();

        initRecycler();

        meiZiPresenter.loadData();
    }

    private void initRecycler()
    {
        mRecycler.setHasFixedSize(true);        //设置item保持高度不变
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);
        meiZiAdapter = new MeiZiAdapter();
        mRecycler.setAdapter(meiZiAdapter);
        mRecycler.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager)
        {
            @Override
            public void onLoadMore(int currentPage)
            {
                meiZiPresenter.loadMore();
            }
        });
    }

    private void initSwipeRefresh()
    {
        mSwipeRefresh.setColorSchemeColors(Color.CYAN, Color.GREEN, Color.BLUE, Color.BLACK);
        mSwipeRefresh.setOnRefreshListener(() -> {
            meiZiPresenter.loadData();
        });
    }

    @Override
    public void showProgress()
    {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress()
    {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void addAdapterData(int pageSize, int page, List<MeiZiBean.ResultsBean> resultsBean)
    {
        resultsBeanList.addAll(resultsBean);
//判断当前是否还有更多数据，进行recyclerView的局部item更新
        if (page * pageSize - pageSize - 1 > 0)
        {
            resultsBeanList.addAll(resultsBean);
            meiZiAdapter.setMeiziInfos(resultsBeanList);
            meiZiAdapter.notifyDataSetChanged();
        } else
        {
            meiZiAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setAdapterData(List<MeiZiBean.ResultsBean> resultsBean)
    {
        resultsBeanList.clear();
        resultsBeanList.addAll(resultsBean);
        meiZiAdapter.setMeiziInfos(resultsBeanList);
        meiZiAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFailed()
    {
        Toast.makeText(this, "网络加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }
}
