package com.wuk.banner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.util.BannerUtils;
import com.youth.banner.util.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Banner banner = findViewById(R.id.banner);

        ImageNetAdapter adapter = new ImageNetAdapter(DataBean.getTestData3());

        banner.setAdapter(adapter)
//              .setCurrentItem(0,false)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this))//设置指示器
                .setLoopTime(5000)
                .setOnBannerListener((data, position) -> {
                    LogUtils.d("position：" + position);
                });



//        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData3()) {
//            @Override
//            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
//                //图片加载自己实现
//                Glide.with(holder.itemView)
//                        .load(data.imageUrl)
//                        .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                        .into(holder.imageView);
//            }
//        });
//        banner.setIndicator(new RoundLinesIndicator(this));
//        banner.setIndicatorSelectedWidth(BannerUtils.dp2px(15));

    }
}