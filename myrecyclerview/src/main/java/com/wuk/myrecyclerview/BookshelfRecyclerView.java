package com.wuk.myrecyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 自定义书架recyclerview
 *
 * @author wuk
 * @date 2021/9/7
 */
public class BookshelfRecyclerView extends RecyclerView {

    private Bitmap background;
    Context context;

    public BookshelfRecyclerView(@NonNull Context context) {
        super(context);
    }

    public BookshelfRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        background = null;
    }

    public BookshelfRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int count = getChildCount();
        int top;
        int itemHeight;
        if (count > 0) {
            View childAt = getChildAt(0);
            top = childAt.getTop();
            itemHeight = childAt.getHeight();
        } else {
            top = getTop();
            itemHeight = 300;
        }

        int width  = getWidth();
        int height = getHeight();


        int rowHeight = width / 20;
        if (background == null) {
            background = BitmapFactory.decodeResource(getResources(),
                    R.drawable.bookshelf_layer_center);
            background = Bitmap.createScaledBitmap(background, width,
                    rowHeight, true);
        }
        for (int y = top; y < height; y += itemHeight) {
            canvas.drawBitmap(background, 0, y + (itemHeight - rowHeight), null);
        }
        super.dispatchDraw(canvas);
    }
}
