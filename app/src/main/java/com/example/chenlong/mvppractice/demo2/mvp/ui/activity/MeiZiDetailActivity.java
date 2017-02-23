package com.example.chenlong.mvppractice.demo2.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chenlong.mvppractice.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MeiZiDetailActivity extends AppCompatActivity
{

    @BindView(R.id.detail)
    ImageView detail;
    private PhotoViewAttacher attacher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mei_zi_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Glide.with(this).load(url).centerCrop().into(detail);
        attacher = new PhotoViewAttacher(detail);

        attacher.update();
    }
}
