package com.wuk.myrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookshelfRecyclerView rv = findViewById(R.id.rv);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);


//        for (int i = 0; i < 10; i++) {
//            bookList.add(i+"");
//        }

        rv.setLayoutManager(layoutManager);
        MyBookAdapter myBookAdapter = new MyBookAdapter(bookList);
        rv.setAdapter(myBookAdapter);
    }
}