package com.wuk.mytools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

import com.wuk.mytools.R;


/**
 * 使用GridView实现书架
 * @author wuk
 * @date 2021/9/7
 */
public class BookGridView extends GridView {
    private Bitmap background;
    Context context;

    public BookGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        background = null;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int count = getChildCount();
        int top = count > 0 ? getChildAt(0).getTop() : 0;
        int width = getWidth();
        int height = getHeight();
        int totalCount = this.getCount();        //如果总数为0自然没必要计算高度并画图
        if (totalCount > 0) {						//获取列数
            int numColumns=3;			// 计算行高，通过行数和控件高度计算
            int rowCount = totalCount / numColumns;
            if (totalCount % numColumns != 0) {
                rowCount++;
            }
            int rowHeight = height / rowCount*2/3;
            if (background == null) {
                background = BitmapFactory.decodeResource(getResources(),
                        R.drawable.bookshelf_layer_center);
                background = Bitmap.createScaledBitmap(background, width-200,
                        rowHeight, true);
            }
            int backgroundWidth = background.getWidth()-200;
            int backgroundHeight = background.getHeight()*2;
            for (int y = top; y < height; y += backgroundHeight) {
//                for (int x = 0; x < width; x += backgroundWidth) {
//                    canvas.drawBitmap(background, x, y+backgroundHeight/2, null);
//                }
//                for (int x = 0; x < width; x += backgroundWidth) {
                    canvas.drawBitmap(background, 100, y+backgroundHeight/2, null);
//                }
            }
        }
        super.dispatchDraw(canvas);
    }
}

