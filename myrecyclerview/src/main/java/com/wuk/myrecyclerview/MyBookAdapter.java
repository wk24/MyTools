package com.wuk.myrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author wuk
 * @date 2022/6/21
 */
public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.MyViewHolder> {

    private List<String> bookList;

    public MyBookAdapter(List<String> bookList) {

        this.bookList = bookList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mybook, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(bookList.get(position));
    }

    @Override
    public int getItemCount() {
        if (bookList != null && bookList.size()>0){
            return bookList.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
