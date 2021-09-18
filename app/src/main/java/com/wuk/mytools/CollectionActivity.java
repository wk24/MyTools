package com.wuk.mytools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wuk.mytools.adapter.CollectionAdapter;
import com.wuk.mytools.view.MyItemTouchHelperCallback;
import com.wuk.mytools.view.MyItemTouchListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配","美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点",  "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配",
            "美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点",  "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};
    private ArrayList<String> datas;
    private CollectionAdapter simpleAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

//        BarUtil.setStatusBarColor(this, getResources().getColor(R.color.white));
//        BarUtil.setStatusBarLightMode(this, true);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_collection);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(layoutManager);


        datas = new ArrayList();
        List<String> list1 = Arrays.asList(titles);
        datas.addAll(list1);
        simpleAdapter = new CollectionAdapter(this, datas);
        mRecyclerView.setAdapter(simpleAdapter);

        MyItemTouchHelperCallback myItemTouchHelperCallback = new MyItemTouchHelperCallback(simpleAdapter, datas);
        itemTouchHelper = new ItemTouchHelper(myItemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new MyItemTouchListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
//                Toast.makeText(mContext, datas.get(vh.getLayoutPosition()).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
//                if (vh.getLayoutPosition() != 0 && vh.getLayoutPosition() != 1) {
                itemTouchHelper.startDrag(vh);

                //获取系统震动服务
//                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
//                    vib.vibrate(70);

//                }
            }
        });
    }
}