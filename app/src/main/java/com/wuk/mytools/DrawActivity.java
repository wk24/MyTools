package com.wuk.mytools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.wuk.mytools.view.DrawView;

/**
 * 自定义画板
 */
public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        DrawView drawView = findViewById(R.id.draw_view);
        ViewGroup.LayoutParams layoutParams = drawView.getLayoutParams();
        layoutParams.width = 2000;
        layoutParams.height = 2000;
        drawView.setLayoutParams(layoutParams);
        drawView.init(2000,2000);
    }
}