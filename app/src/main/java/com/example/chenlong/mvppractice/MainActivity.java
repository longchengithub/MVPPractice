package com.example.chenlong.mvppractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chenlong.mvppractice.demo2.mvp.ui.activity.MeiZiActivity;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MeiZiActivity.class);
        startActivity(intent);

        Observable.fromArray("url1", "url2", "url3")
                .subscribe(s -> {
                    System.out.println(s);
                });
//
//        IjkVideoView videoView= (IjkVideoView) findViewById(R.id.ijk_player);
//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//        File file=new File(Environment.getExternalStorageDirectory(),"movie.mp4");
//        videoView.setVideoPath(file.getPath());
//        videoView.start();

        int[] a = {1, 2, 4, 5, 6, 8, 9, 3, 56};
        for (int i = 0; i < a.length-1; i++)
        {
            for (int j = 0; j < a.length - i - 1; j++)
            {
                if(a[j]>a[j+1]){
                    int temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
    }
}
