package com.wuk.mytools;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookshelfByGridActivity extends AppCompatActivity {

    private GridView mGridView;
    private String[] provinceNames = new String[]{"北京", "上海", "广东", "广西", "天津", "重庆", "湖北", "湖南", "河北", "河南", "山东"};


    private GridView bookShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bookshelf_by_grid);
        bookShelf = (GridView) findViewById(R.id.bookShelf);
        List<String> provinceBeanList = new ArrayList<>();
        for (int i = 0; i < provinceNames.length; i++) {
            provinceBeanList.add(provinceNames[i]);
        }
        ProvinceAdapter mProvinceAdapter = new ProvinceAdapter(this, provinceBeanList);
        bookShelf.setAdapter(mProvinceAdapter);

    }

    class  ProvinceAdapter extends BaseAdapter {

        private final Activity mainActivity3;
        private final List<String> provinceBeanList;

        public ProvinceAdapter(Activity mainActivity3, List<String> provinceBeanList) {

            this.mainActivity3 = mainActivity3;
            this.provinceBeanList = provinceBeanList;
        }

        @Override
        public int getCount() {
            return provinceBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return provinceBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.province_grid_view_item_layout, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.text.setText(provinceBeanList.get(position));
            return convertView;
        }
    }

    class ViewHolder {

        TextView text;

    }
}