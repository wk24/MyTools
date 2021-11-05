package com.wuk.mytools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuk.mytools.view.GraticuleView;


/**
 *  拖拽
 */
public class DragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);

        TextView tv = findViewById(R.id.tv);
        GraticuleView graticule = findViewById(R.id.graticule);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("我是key", "我是value");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(tv);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv.startDragAndDrop(data, shadowBuilder, tv, View.DRAG_FLAG_GLOBAL_PREFIX_URI_PERMISSION);
                } else {
                    tv.startDrag(data, shadowBuilder, tv, View.DRAG_FLAG_GLOBAL_PREFIX_URI_PERMISSION);
                }
                tv.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);

                return true;
            }
        });

        graticule.setOnMyDragListener(new GraticuleView.OnMyDragListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onDrag(String s, float x, float y) {
                Log.e("TAG", "onDrag: "+ s );

                ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
                int width = layoutParams.width;
                int height = layoutParams.height;
                tv.setX(x - width/2);
                tv.setY(y - height/2);


            }
        });

    }
}