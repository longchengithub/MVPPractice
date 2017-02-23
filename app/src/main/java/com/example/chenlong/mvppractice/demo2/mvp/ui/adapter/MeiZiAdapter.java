package com.example.chenlong.mvppractice.demo2.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.chenlong.mvppractice.R;
import com.example.chenlong.mvppractice.demo2.mvp.bean.MeiZiBean;
import com.example.chenlong.mvppractice.demo2.mvp.ui.activity.MeiZiDetailActivity;

import java.util.List;

/**
 * Created by ChenLong on 2017/2/5.
 */

public class MeiZiAdapter extends RecyclerView.Adapter<MeiZiAdapter.MeiZiViewHolder>
{

    private List<MeiZiBean.ResultsBean> meiziInfos;
    private Context mContext;


    public void setMeiziInfos(List<MeiZiBean.ResultsBean> meiziInfos)
    {
        this.meiziInfos = meiziInfos;
    }

    @Override
    public MeiZiViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_meizi, parent, false);

        return new MeiZiViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MeiZiViewHolder holder, int position)
    {

        MeiZiBean.ResultsBean resultsBean = meiziInfos.get(position);

        Glide.with(mContext)
                .load(resultsBean.getUrl())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.textView.setText(resultsBean.getPublishedAt() + ":" + resultsBean.getWho());

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MeiZiDetailActivity.class);
            intent.putExtra("url", resultsBean.getUrl());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount()
    {
        return meiziInfos == null ? 0 : meiziInfos.size();
    }

    static class MeiZiViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public MeiZiViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.meizi_card);
            imageView = (ImageView) itemView.findViewById(R.id.meizi_image);
            textView = (TextView) itemView.findViewById(R.id.auther_name);
        }
    }
}
