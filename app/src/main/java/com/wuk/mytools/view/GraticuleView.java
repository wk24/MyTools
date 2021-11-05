package com.wuk.mytools.view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * 接收/控制 拖拽
 */
public class GraticuleView extends View {
    public float currentX = -10;
    public float currentY = -10;
    private boolean tag = false;
    Paint paint = new Paint();

    public GraticuleView(Context context) {
        super(context);
    }

    public GraticuleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStrokeWidth((float) 2);
        if (!tag) {
            currentX = -10;
            currentY = -10;
        }
        canvas.drawLine(currentX, currentY, 0, currentY, paint);
        canvas.drawLine(currentX, currentY, 3000, currentY, paint);
        canvas.drawLine(currentX, currentY, currentX, 3000, paint);
        canvas.drawLine(currentX, currentY, currentX, 0, paint);
    }

    public void setLocation(float f1, float f2) {
        currentX = f1;
        currentY = f2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                currentY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                setStratDrag(true);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                Log.e("TAG", "onDragEvent: " + "ACTION_DRAG_LOCATION");

                float x = event.getX();
                float y = event.getY();
//                int currentPage = viewPepFoxit.getCurrentPage();
//                int pageCount = viewPepFoxit.getPageCount();
//                int pageLayoutMode = viewPepFoxit.getPageLayoutMode();
//                Float radius = viewPepFoxit.getRadius(currentPage);
//                RectF rectPDF_location = viewPepFoxit.getPageRect(currentPage + 1);
//                if (pageCount != currentPage + 1 || PDFViewCtrl.PAGELAYOUTMODE_FACING == pageLayoutMode) { //单页状态的最后一页不校验
//                    if (x > rectPDF_location.right - radius) {
//                        x = rectPDF_location.right - radius;
//                    }
//                }
//                rectPDF_location = viewPepFoxit.getPageRect(currentPage);
//                if (PDFViewCtrl.PAGELAYOUTMODE_SINGLE == pageLayoutMode) {
//                    if (x > rectPDF_location.right - radius) {
//                        x = rectPDF_location.right - radius;
//                    }
//                }
//                if (x < rectPDF_location.left + radius) {
//                    x = rectPDF_location.left + radius;
//                }
//                if (y < rectPDF_location.top + radius) {
//                    y = rectPDF_location.top + radius;
//                }
//                if (y > rectPDF_location.bottom - radius) {
//                    y = rectPDF_location.bottom - radius;
//                }
                setLocation(x, y);
                invalidate();
                break;
            case DragEvent.ACTION_DROP:

                Log.e("TAG", "onDragEvent: " + "ACTION_DROP");
//                PointF point = new PointF(event.getX(), event.getY());
//                int currentPage_drop = viewPepFoxit.getCurrentPage();
//                RectF rectPDF_drop = viewPepFoxit.getPageRect(currentPage_drop + 1);
//                int pageCount_drop = viewPepFoxit.getPageCount();
//                int pageLayoutMode11 = viewPepFoxit.getPageLayoutMode();
//                float x1 = event.getX();
//                float y1 = event.getY();
//                float radius_drop = viewPepFoxit.getRadius(currentPage_drop);
//                if (pageCount_drop != currentPage_drop + 1 || PDFViewCtrl.PAGELAYOUTMODE_FACING == pageLayoutMode11) { //单页状态的最后一页不校验
//                    if (x1 > rectPDF_drop.right - radius_drop) {
//                        x1 = rectPDF_drop.right - radius_drop;
//                    }
//                }
//                rectPDF_drop = viewPepFoxit.getPageRect(currentPage_drop);
//                if (PDFViewCtrl.PAGELAYOUTMODE_SINGLE == pageLayoutMode11) {
//                    if (x1 > rectPDF_drop.right - radius_drop) {
//                        x1 = rectPDF_drop.right - radius_drop;
//                    }
//                }
//                if (x1 < rectPDF_drop.left + radius_drop) {
//                    x1 = rectPDF_drop.left + radius_drop;
//                }
//                if (y1 < rectPDF_drop.top + radius_drop) {
//                    y1 = rectPDF_drop.top + radius_drop;
//                }
//                if (y1 > rectPDF_drop.bottom - radius_drop) {
//                    y1 = rectPDF_drop.bottom - radius_drop;
//                }
//                point.set(x1, y1);
//                int pageIndex = viewPepFoxit.getPageIndex(point);
//                if (pageIndex == -1) {
//                    if (listener != null) {
//                        listener.onCancel();
//                    }
//                } else if (pageIndex == 0 || pageIndex == viewPepFoxit.getPageCount() - 1) {
//                    if (listener != null) {
//                        listener.onCancel();
//                    }
//                    Toast.makeText(getContext(), "该页面不能添加资源", Toast.LENGTH_SHORT).show();
//                } else {
//
//
//                    PointF positionPointF = viewPepFoxit.getPositionRectF(x1, y1, pageIndex);
//
                float x1 = event.getX();
                float y1 = event.getY();
                if (listener != null) {
                    ClipData clipData = event.getClipData();
                    String string = clipData.getDescription().getLabel().toString();
                    String string1 = clipData.getItemAt(0).getText().toString();
                    listener.onDrag(string + ":" + string1,x1,y1);
                }
//                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                if (listener != null) {
                    listener.onCancel();
                }
                break;
            default:
        }

        return true;
    }

    /**
     * 设置开始拖拽
     *
     * @param b
     */
    public void setStratDrag(boolean b) {
        tag = b;
    }


    public interface OnMyDragListener {
        void onCancel();

        void onDrag(String s, float x, float y);
    }

    private OnMyDragListener listener;

    public void setOnMyDragListener(OnMyDragListener listener) {
        this.listener = listener;
    }

}
