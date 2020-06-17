package com.mnn.mydream.cosmetology.view;

import java.util.ArrayList;
import java.util.List;

import com.mnn.mydream.cosmetology.utils.BitmapUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class DrawView extends View {
    Path mPath;
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint;
    Paint nPaint;
    int width;
    int height;
    int color;
    int downState;
    float mX = 0.0f;
    float mY = 0.0f;
    private Bitmap cachebBitmap;
    private Context context;
    public DrawType drawType;
    public Paint paint;// 声明画笔
    private Point cenPoint;// 声明线段的中间点，拖动直线需要用到次点

    public int moveState;
    private List<DrawPath> paths;
    private Point pointa, pointb, pointc;
    private Rect pointaRect, pointbRect, pointcRect;
    private DrawPath dp;
    // 矩形的4个顶点和中心点（用来移动）
    private Point point1, point2, point3, point4;
    private Rect point1Rect, point2Rect, point3Rect, point4Rect;
    private Rect rect;

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        this.context = context;
        paths = new ArrayList<DrawPath>();
        initCanvas();
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
        paths = new ArrayList<DrawPath>();
        initCanvas();
        init();
    }

    public DrawView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        paths = new ArrayList<DrawPath>();
        initCanvas();
        init();
    }

    public Point downPoint, movePoint, upPoint;
    private Point rDotsPoint;// 圆点
    private int radius = 0;// 半径

    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        nPaint = new Paint();
        nPaint.setAntiAlias(true);
        nPaint.setColor(Color.WHITE);
        nPaint.setStyle(Paint.Style.STROKE);
        nPaint.setStrokeWidth(20);

        downPoint = new Point();
        movePoint = new Point();
        rDotsPoint = new Point();
        upPoint = new Point();

        cenPoint = new Point();

        point1 = new Point();
        point2 = new Point();
        point3 = new Point();
        point4 = new Point();

        rect = new Rect();
        point1Rect = null;
        point2Rect = null;
        point3Rect = null;
        point4Rect = null;

        pointa = new Point();
        pointb = new Point();
        pointc = new Point();

        pointaRect = null;
        pointbRect = null;
        pointcRect = null;
    }


    /**
     * 撤销
     */
    public void revoke() {
        initCanvas();
        invalidate();
        if (!paths.isEmpty() && paths.size() > 0) {
            Log.v("paths.size()", paths.size() + "");
            int location = paths.size() - 1;

            paths.remove(location);
            Log.v("paths.size()", paths.size() + "");
            for (int i = 0; i < paths.size(); i++) {
                DrawPath dp2 = paths.get(i);

                dp.paint.setColor(dp.color);
                dp.paint.setMaskFilter(dp.mf);
                dp.paint.setStrokeWidth(dp.strokeWidth);
                mCanvas.drawPath(dp2.path, dp2.paint);
            }
            invalidate();
        }
    }

    /**
     * 清空
     */
    public void clearAll() {
        paths.clear();
        initCanvas();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int act = event.getAction();
        float x = event.getX();
        float y = event.getY();
        if (drawType == DrawType.brush) {
            switch (act) {
                case MotionEvent.ACTION_DOWN:
                    dp = new DrawPath();
                    mPath = new Path();
                    dp.paint = mPaint;
                    dp.path = mPath;
                    dp.color = mPaint.getColor();
                    dp.mf = mPaint.getMaskFilter();
                    dp.strokeWidth = mPaint.getStrokeWidth();
                    mPath.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.quadTo(mX, mY, x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    mCanvas.drawPath(dp.path, dp.paint);
                    paths.add(dp);
                    mPath = null;
                    //	mPath = null;
                    Log.v("paths", paths.size() + "-----------");
                    break;
            }
            invalidate();
            mX = x;
            mY = y;
            return true;
        } else if (drawType == DrawType.eraser) {

            switch (act) {
                case MotionEvent.ACTION_DOWN:
                    dp = new DrawPath();
                    mPath = new Path();
                    dp.paint = nPaint;
                    dp.path = mPath;
                    dp.color = nPaint.getColor();
                    dp.mf = nPaint.getMaskFilter();
                    dp.strokeWidth = nPaint.getStrokeWidth();
                    mPath.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.quadTo(mX, mY, x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    mCanvas.drawPath(dp.path, dp.paint);
                    paths.add(dp);
                    mPath = null;
                    break;
            }
            invalidate();
            mX = x;
            mY = y;
            return true;
        } else if (drawType == DrawType.line) {
            switch (act) {
                case MotionEvent.ACTION_DOWN:
                    dp = new DrawPath();
                    mPath = new Path();
                    dp.paint = mPaint;
                    dp.path = mPath;
                    dp.color = mPaint.getColor();
                    dp.mf = mPaint.getMaskFilter();
                    dp.strokeWidth = mPaint.getStrokeWidth();
                    // 获取起点坐标
                    downPoint.set((int) event.getX(), (int) event.getY());
                    // mPath.reset();
                    mPath.moveTo(x, y);
                case MotionEvent.ACTION_MOVE:
                    // 获取当前拖动点坐标
                    movePoint.set((int) event.getX(), (int) event.getY());
                    mPath.setLastPoint(downPoint.x, downPoint.y);
                    mPath.lineTo(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    // 重新设定以线段两个端点为中心的两个矩形
                    mCanvas.drawPath(dp.path, dp.paint);
                    paths.add(dp);
                    mPath = null;
                    break;
                default:
                    break;
            }
            return true;
        } else if (drawType == DrawType.rectangle) {
            switch (act) {
                case MotionEvent.ACTION_DOWN:
                    dp = new DrawPath();
                    mPath = new Path();
                    dp.paint = mPaint;
                    dp.path = mPath;
                    dp.color = mPaint.getColor();
                    dp.mf = mPaint.getMaskFilter();
                    dp.strokeWidth = mPaint.getStrokeWidth();
                    // 获取起点坐标
                    downPoint.set((int) event.getX(), (int) event.getY());
                    if (point2Rect != null) {
                        // 判断用户所点击的点是否在矩形顶点point1为中心的矩形point1Rect内，
                        // 如果在这个矩形内，则我们认为用户点击了该点
                        if (point1Rect.contains(downPoint.x, downPoint.y)) {
                            downState = 1;
                            Log.i("onTouchDown", "downState = 1");
                        } else if (point2Rect.contains(downPoint.x, downPoint.y)) {
                            downState = 2;
                            Log.i("onTouchDown", "downState = 2");
                        } else if (point3Rect.contains(downPoint.x, downPoint.y)) {
                            downState = 3;
                            Log.i("onTouchDown", "downState = 3");
                        } else if (point4Rect.contains(downPoint.x, downPoint.y)) {
                            downState = 4;
                            Log.i("onTouchDown", "downState = 4");
                        } else if (rect.contains(downPoint.x, downPoint.y)) {
                            downState = 5;
                            Log.i("onTouchDown", "downState = 5");
                        } else {
                            downState = 0;
                            Log.i("onTouchDown", "downState = 0");
                        }
                    }
                    break;
                // 拖动
                case MotionEvent.ACTION_MOVE:
                    // 获取当前拖动点坐标
                    movePoint.set((int) event.getX(), (int) event.getY());
                    mPath.reset();
                    if (movePoint.x > downPoint.x && movePoint.y > downPoint.y) {
                        mPath.addRect(downPoint.x, downPoint.y, movePoint.x,
                                movePoint.y, Path.Direction.CCW);

                    } else if (movePoint.x < downPoint.x && movePoint.y > downPoint.y) {
                        mPath.addRect(movePoint.x, downPoint.y, downPoint.x,
                                movePoint.y, Path.Direction.CCW);
                    } else if (movePoint.x > downPoint.x && movePoint.y < downPoint.y) {
                        mPath.addRect(downPoint.x, movePoint.y, movePoint.x,
                                downPoint.y, Path.Direction.CCW);
                    } else if (movePoint.x < downPoint.x && movePoint.y < downPoint.y) {
                        mPath.addRect(movePoint.x, movePoint.y, downPoint.x,
                                downPoint.y, Path.Direction.CCW);
                    }
                    // 根据用户所点击的坐标点画相应的矩形
                    switch (downState) {
                        case 1:
                            // 点击点point1，则point2不变；根据point1、point2重新设置点point3,point4
                            point1.set(movePoint.x, movePoint.y);
                            point3.set(point1.x, point2.y);
                            point4.set(point2.x, point1.y);
                            break;
                        case 2:
                            // 点击点point2，则point1不变；根据point1、point2重新设置点point3,point4
                            point2.set(movePoint.x, movePoint.y);
                            point3.set(point1.x, point2.y);
                            point4.set(point2.x, point1.y);
                            break;
                        case 3:
                            // 点击点point3，则重新设置矩形点point3、1、2
                            point3.set(movePoint.x, movePoint.y);
                            point1.set(point3.x, point4.y);
                            point2.set(point4.x, point3.y);
                            break;
                        case 4:
                            // 点击点point4，则重新设置矩形点point4、1、2
                            point4.set(movePoint.x, movePoint.y);
                            point1.set(point3.x, point4.y);
                            point2.set(point4.x, point3.y);
                            break;
                        case 5:
                            // 计算矩形中间点
                            cenPoint.x = (point1.x + point2.x) / 2;
                            cenPoint.y = (point1.y + point2.y) / 2;
                            // 移动距离
                            int movedX = movePoint.x - cenPoint.x;
                            int movedY = movePoint.y - cenPoint.y;
                            // 重新设置矩形4个顶点的坐标
                            point1.x = point1.x + movedX;
                            point1.y = point1.y + movedY;
                            point2.x = point2.x + movedX;
                            point2.y = point2.y + movedY;
                            point3.set(point1.x, point2.y);
                            point4.set(point2.x, point1.y);
                            break;
                        default:
                            getStartPoint();
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mCanvas.drawPath(mPath, mPaint);
                    paths.add(dp);
                    mPath = null;
                    break;
            }
            // 每次拖动完之后需要重新设定4个顶点小矩形。
            invalidate();
            return true;
        } else if (drawType == DrawType.triangle) {
            switch (act) {
                case MotionEvent.ACTION_DOWN:
                    dp = new DrawPath();
                    mPath = new Path();
                    dp.paint = mPaint;
                    dp.path = mPath;
                    dp.color = mPaint.getColor();
                    dp.mf = mPaint.getMaskFilter();
                    dp.strokeWidth = mPaint.getStrokeWidth();
                    downPoint.set((int) event.getX(), (int) event.getY());
                    if (pointaRect == null) {
                        pointa.set(downPoint.x, downPoint.y);
                        pointaRect = new Rect(pointa.x - 20, pointa.y - 20,
                                pointa.x + 20, pointa.y + 20);
                        mCanvas.drawPoint(pointa.x, pointa.y, mPaint);
                        invalidate();
                    } else if (pointbRect == null) {
                        pointb.set(downPoint.x, downPoint.y);
                        pointbRect = new Rect(pointb.x - 20, pointb.y - 20,
                                pointb.x + 20, pointb.y + 20);
                        mCanvas.drawLine(pointa.x, pointa.y, pointb.x, pointb.y,
                                mPaint);
                        invalidate();
                    } else if (pointcRect == null) {
                        pointc.set(downPoint.x, downPoint.y);
                        pointcRect = new Rect(pointc.x - 20, pointc.y - 20,
                                pointc.x + 20, pointc.y + 20);
                        mCanvas.drawPoint(pointc.x, pointc.y, mPaint);
                        //	mPath = new Path();
                        mPath.moveTo(pointa.x, pointa.y);
                        mPath.lineTo(pointb.x, pointb.y);
                        mPath.lineTo(pointc.x, pointc.y);
                        mPath.close();
                        mCanvas.drawPath(dp.path, dp.paint);
                        mCanvas.save();
                        paths.add(dp);
                        mPath = null;
                        //mCanvas.save();
                        pointaRect = null;
                        pointbRect = null;
                        pointcRect = null;
                        invalidate();
                    }
                    break;
                default:
                    break;
            }
            return true;
        } else if (drawType == DrawType.circle) {
            switch (act) {
                case MotionEvent.ACTION_DOWN:
                    // 获取起点坐标
                    dp = new DrawPath();
                    mPath = new Path();
                    dp.paint = mPaint;
                    dp.path = mPath;
                    dp.color = mPaint.getColor();
                    dp.mf = mPaint.getMaskFilter();
                    dp.strokeWidth = mPaint.getStrokeWidth();
                    downPoint.set((int) event.getX(), (int) event.getY());
                case MotionEvent.ACTION_MOVE:
                    movePoint.set((int) event.getX(), (int) event.getY());
                    mPath.reset();
                    mPath.addCircle(rDotsPoint.x, rDotsPoint.y, radius,
                            Path.Direction.CCW);
                    switch (downState) {
                        case 1:
                            // 在圆上，圆心不变，重新计算半径
                            radius = (int) Math.sqrt((movePoint.x - rDotsPoint.x)
                                    * (movePoint.x - rDotsPoint.x)
                                    + (movePoint.y - rDotsPoint.y)
                                    * (movePoint.y - rDotsPoint.y));
                            break;
                        case -1:
                            // 在园内。半径不变，改变其圆心坐标
                            rDotsPoint.x = movePoint.x;
                            rDotsPoint.y = movePoint.y;
                            break;
                        default:
                            // 在园外。重新设置圆心坐标、半径。画圆
                            rDotsPoint.set(downPoint.x, downPoint.y);
                            radius = (int) Math.sqrt((movePoint.x - rDotsPoint.x)
                                    * (movePoint.x - rDotsPoint.x)
                                    + (movePoint.y - rDotsPoint.y)
                                    * (movePoint.y - rDotsPoint.y));
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mCanvas.drawPath(mPath, mPaint);
                    paths.add(dp);
                    //mPath.reset();
                    mPath = null;
                    break;
            }
            invalidate();
            mX = x;
            mY = y;
            return true;
        }
        return false;
    }

    // 判断用户开始画矩形点
    public void getStartPoint() {
        if (downPoint.x < movePoint.x && downPoint.y < movePoint.y) {
            point1.set(downPoint.x, downPoint.y);
            point2.set(movePoint.x, movePoint.y);
            point3.set(point1.x, point2.y);
            point4.set(point2.x, point1.y);
        } else if (downPoint.x < movePoint.x && downPoint.y > movePoint.y) {
            point3.set(downPoint.x, downPoint.y);
            point4.set(movePoint.x, movePoint.y);
            point1.set(point3.x, point4.y);
            point2.set(point4.x, point3.y);
        } else if (downPoint.x > movePoint.x && downPoint.y > movePoint.y) {
            point2.set(downPoint.x, downPoint.y);
            point1.set(movePoint.x, movePoint.y);
            point3.set(point1.x, point2.y);
            point4.set(point2.x, point1.y);
        } else if (downPoint.x > movePoint.x && downPoint.y < movePoint.y) {
            point4.set(downPoint.x, downPoint.y);
            point3.set(movePoint.x, movePoint.y);
            point1.set(point3.x, point4.y);
            point2.set(point4.x, point3.y);
        }
        setRect();
    }

    public void setRect() {
        // 设置以矩形的4个顶点为中心的小矩形
        point1Rect = new Rect(point1.x - 30, point1.y - 30, point1.x + 30,
                point1.y + 30);
        point2Rect = new Rect(point2.x - 30, point2.y - 30, point2.x + 30,
                point2.y + 30);
        point3Rect = new Rect(point3.x - 30, point3.y - 30, point3.x + 30,
                point3.y + 30);
        point4Rect = new Rect(point4.x - 30, point4.y - 30, point4.x + 30,
                point4.y + 30);
        rect.set(point1.x, point1.y, point2.x, point2.y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//		canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(cachebBitmap, 0, 0, null);
        if (mPath != null) {
            if (drawType == DrawType.eraser) {
                canvas.drawPath(mPath, nPaint);
            } else {
                canvas.drawPath(mPath, mPaint);
            }
        }

    }

    public void setColor(int c) {
        mPaint.setColor(c);
    }

    public void setBlock(float size) {
        mPaint.setStrokeWidth(size);
    }

    public void initCanvas() {
        // 创建一个新画布,画布宽度为屏幕宽度，高度为屏幕高度
        DisplayMetrics metrics = new DisplayMetrics();
        @SuppressLint("WrongConstant")
        WindowManager wm = (WindowManager) this.getContext().getSystemService(
                "window");
        wm.getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        cachebBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        cachebBitmap.eraseColor(Color.TRANSPARENT);
        // 将bitmap放在canvas上，在bitmap上画图
        mCanvas = new Canvas();
        mCanvas.setBitmap(cachebBitmap);
    }

    public Bitmap getCanvasSnapshot() {
        setDrawingCacheEnabled(true);
        buildDrawingCache(true);
        Bitmap bmp = getDrawingCache(true);
        if (null == bmp) {
            Log.d("leehong2",
                    "getCanvasSnapshot getDrawingCache == null");
        }
        return BitmapUtil.duplicateBitmap(bmp);
    }

    public void cleanDrawingCache() {
        setDrawingCacheEnabled(false);
    }

    public class DrawPath {
        private Path path;// 路径
        private Paint paint;// 画笔
        private int color; // 颜色
        private MaskFilter mf; // 效果
        private float strokeWidth;// 大小
    }
}
