package com.wuk.mytools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.wuk.mytools.utils.FileUtil;

import java.io.File;

/**
 * 手写板 (加载图片,设置为背景)
 * @author wuk
 * @date 2021/8/30
 */
public class DrawView extends View {
    /*
     * 首先定义程序中所需的属性，然后添加构造方法
     * 重写onDraw（Canvas canvas）方法
     */
    private int view_width = 0;
    private int view_height = 0;
    //起始点的x坐标
    private float preX;
    //起始点的y坐标
    private float preY;
    //路径
    private Path path;
    //画笔
    public Paint paint = null;
    //定义一个内存中的图片，该图片作为缓冲区
    Bitmap cacheBitmap = null;
    //定义cacheBitmap上的Canvas对象
    Canvas cacheCanvas = null;

    public DrawView(Context context, AttributeSet attrs) {
        super(context,attrs);

        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    /**
     * 设置背景颜色，绘制cacheBitmap，绘制路径，保存当前绘图状态到栈中，调用resrore（）方法恢复所保存的状态
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint bmpPaint = new Paint();
        canvas.drawBitmap(cacheBitmap,0,0,bmpPaint);
        canvas.drawPath(path,paint);
        canvas.save();
        canvas.restore();


    }

    /**
     * 首先获取屏幕的宽度和高度，创建一个与该View相同大小的缓存区，
     * 创建一个新的画面，实例化一个路径，将内存中的位图会知道cacheCanvas中，最后实例化一个画笔，设置画笔的相关属性
     */
    public void init(int width, int height){
        view_width = width;
        view_height = height;

        //源文件路径
//        String filePath = PepApp.getAppFilePath() + ApiRoute.getPageAnswerPath(formDrawModel.bookId)+ formDrawModel.name+".png";
        String filePath = "";
        File file = new File(filePath);
        if (file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            cacheBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }else{
            cacheBitmap = Bitmap.createBitmap(view_width,view_height,Bitmap.Config.ARGB_8888);
            cacheBitmap.eraseColor(Color.parseColor("#ffffff"));
        }


        cacheCanvas = new Canvas();
        path = new Path();
        cacheCanvas.setBitmap(cacheBitmap);


    }

    /**
     * 保存
     * @param bookId
     */
    public void save(String bookId){
//        String path = PepApp.getAppFilePath() + ApiRoute.getPageAnswerPath(bookId)+ formDrawModel.name+".png";

        String path = "";
        FileUtil.saveBitmap(cacheBitmap,path);

//        FormDrawEvent formDrawEvent = new FormDrawEvent();
//        formDrawEvent.formDrawModel = formDrawModel;
//        formDrawEvent.isSave = true;
//        formDrawEvent.path = path;
//        EventBus.getDefault().post(formDrawEvent);

    }

    /**
     * clear（）方法，实现橡皮擦功能
     */
    public void clear() {
        //橡皮
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));       //设置图形重叠时的处理方式
//        paint.setStrokeWidth(50);       //设置笔触的宽度

        //清空
        cacheCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();

        cacheBitmap = Bitmap.createBitmap(view_width,view_height,Bitmap.Config.ARGB_8888);
        cacheBitmap.eraseColor(Color.parseColor("#ffffff"));
        cacheCanvas.setBitmap(cacheBitmap);
    }

    /**
     * 重写onTouchEvent（）方法，为该视图添加触摸事件监听器，
     * 首先获取触摸事件发生的位置，然后应用switch语句对事件的不同状态添加相应代码，最后调用inalidat（）方法更新视图
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //将绘图的起始点一道（x，y）坐标的位置
                path.moveTo(x,y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                //判断是否在允许的范围内
                if(dx>=5||dy>=5) {
                    path.quadTo(preX,preY,(x+preX)/2,(y+preY)/2);
                    preX = x;
                    preY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                //绘制路径
                cacheCanvas.drawPath(path,paint);
                path.reset();
                break;
            default:
                break;
        }
        invalidate();
        //返回true表明处理方法已经处理该事务
        return true;
    }
}
