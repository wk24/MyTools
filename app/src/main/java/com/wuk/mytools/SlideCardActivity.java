package com.wuk.mytools;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wuk.mytools.recycler.CardConfig;
import com.wuk.mytools.recycler.SlideCardBean;
import com.wuk.mytools.recycler.SlideCardCallback;
import com.wuk.mytools.recycler.SlideCardLayoutManager;
import com.wuk.mytools.recycler.adapter.UniversalAdapter;
import com.wuk.mytools.recycler.adapter.ViewHolder;

import java.util.List;

/**
 * 探探 左右滑动卡片效果
 */
public class SlideCardActivity extends AppCompatActivity {

    private RecyclerView rv;
    private UniversalAdapter<SlideCardBean> adapter;
    private List<SlideCardBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new SlideCardLayoutManager());

        mDatas = SlideCardBean.initDatas();
        adapter = new UniversalAdapter<SlideCardBean>(this, mDatas, R.layout.item_swipe_card) {
            @Override
            public void convert(ViewHolder viewHolder, SlideCardBean slideCardBean) {
//                viewHolder.setText(R.id.tvName, slideCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, slideCardBean.getPostition() + "/" + mDatas.size());
                Glide.with(SlideCardActivity.this)
                        .load(slideCardBean.getId())
                        .into((ImageView) viewHolder.getView(R.id.iv));
            }
        };
        rv.setAdapter(adapter);
        // 初始化数据
        CardConfig.initConfig(this);

        // 创建 ItemTouchHelper ，必须要使用 ItemTouchHelper.Callback
        ItemTouchHelper.Callback callback = new SlideCardCallback(rv, adapter, mDatas);
        ItemTouchHelper helper = new ItemTouchHelper(callback);

        // 绑定rv
        helper.attachToRecyclerView(rv);
    }
}
