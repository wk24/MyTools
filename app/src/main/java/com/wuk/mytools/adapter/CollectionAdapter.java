package com.wuk.mytools.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wuk.mytools.CollectionActivity;
import com.wuk.mytools.R;

import java.util.ArrayList;

/**
 * @author wuk
 * @date 2021/9/17
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyViewHolder> {


    private CollectionActivity activity;
    private ArrayList<String> datas;

    public CollectionAdapter(CollectionActivity activity, ArrayList<String> datas) {

        this.activity = activity;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_textview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (datas !=null){
            return datas.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(datas.get(position));
    }





    static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.id_text);
        }
    }
}
