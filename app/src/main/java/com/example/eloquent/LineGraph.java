package com.example.eloquent;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


//===========================================================================
public class LineGraph extends View{

    private float mPaddingTop = 130.0F;
    private float mPaddingRight = 45.0F;
    private float mPaddingLeft = 130.5F; //done
    private float mPaddingBottom = 265.5F; //done195.5F-------
    private long maxValue = 600000;

    private int lineColor = -7480884;
    private int bgColor = -7480884;
    private Typeface typeFace;//

    private int xLength =490;
    private int yLength=420;//---------

    private int chartXLength =832;
    private int chartYLength =120;

    private Paint p = new Paint();
    private Paint pCircle = new Paint();
    private Paint pCircleBG = new Paint();
    private Paint pLine = new Paint();
    private Paint pBaseLine = new Paint();
    private Paint pBaseLineX = new Paint();
    private Paint pMarkText = new Paint();

    private String[] legendArray = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};

    private List chartEntities;


    public LineGraph(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.initialize(attrs);
    }


    public final float getMPaddingRight() {
        return this.mPaddingRight;
    }

    public final void setMPaddingRight(float var1) {
        this.mPaddingRight = var1;
    }

    public final float getMPaddingLeft() {
        return this.mPaddingLeft;
    }

    public final void setMPaddingLeft(float var1) {
        this.mPaddingLeft = var1;
    }

    public final float getMPaddingBottom() {
        return this.mPaddingBottom;
    }

    public final void setMPaddingBottom(float var1) {
        this.mPaddingBottom = var1;
    }

    public final long getMaxValue() {
        return this.maxValue;
    }

    public final void setMaxValue(long var1) {
        this.maxValue = var1;
    }

    public final int getLineColor() {
        return this.lineColor;
    }

    public final void setLineColor(int var1) {
        this.lineColor = var1;
    }

    public final int getBgColor() {
        return this.bgColor;
    }

    public final void setBgColor(int var1) {
        this.bgColor = var1;
    }

    public final Typeface getTypeFace() {
        return this.typeFace;
    }

    public final void setTypeFace(Typeface var1) {
        this.typeFace = var1;
    }

    //    public final void setList(ArrayList<ChartEntity> list) {
//        this.chartEntities.clear();
//        this.invalidate();
//        this.chartEntities.setValues(list);
//        this.initializePaint();
//    }
    public final void setList(List list) {

        this.invalidate();
        if (!list.isEmpty()) {
            this.chartEntities = list;

            ArrayList maxes = new ArrayList();
            Iterator var4 = this.chartEntities.iterator();

            while(var4.hasNext()) {
                ChartEntity lineGraph = (ChartEntity)var4.next();
                float[] var6 = lineGraph.getValues();
                int var7 = lineGraph.getValues().length;
                float[] var10000 = Arrays.copyOf(var6, var7);

                maxes.add(var10000[var10000.length - 1]);
            }

            this.initializePaint();
        }
    }
    private final void initialize(AttributeSet attrs) {


        this.invalidate();
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            if (this.chartEntities == null) {
                canvas.drawColor(this.bgColor);
            }

            this.initializePaint();
            canvas.drawColor(this.bgColor);

            GraphCanvasWrapper graphCanvasWrapper = new GraphCanvasWrapper(canvas, this.getWidth(), this.getHeight(), (int)this.mPaddingLeft, (int)this.mPaddingBottom);
            graphCanvasWrapper.drawLine(0.0F, 0.0F, (float)this.chartXLength, 0.0F, this.pBaseLine);
            float newX = 0.0F;
            int gap = this.chartXLength / (((ChartEntity)this.chartEntities.get(0)).getValues().length - 1);
            int i = 0;

            for(int var6 = ((ChartEntity)this.chartEntities.get(0)).getValues().length; i < var6; ++i) {
                newX = (float)(gap * i);
                graphCanvasWrapper.drawLine(newX, 0.0F, newX, (float)this.chartYLength, this.pBaseLine);
                this.drawGraph(graphCanvasWrapper);
                this.drawXText(graphCanvasWrapper);
            }

            canvas.save();
            canvas.restore();
        }
    }



    private final void drawXText(GraphCanvasWrapper graphCanvas) {
        float xGap = (float)(this.xLength / (((ChartEntity)this.chartEntities.get(0)).getValues().length - 1));
        int i = 0;

        for(int var13 = ((ChartEntity)this.chartEntities.get(0)).getValues().length; i < var13; ++i) {
            Rect rect = new Rect();
            String text = (String)this.legendArray[i];
            this.pMarkText.measureText(text);
            this.pMarkText.setTextSize(40.0F);
            this.pMarkText.setTypeface(this.typeFace);
            float x = xGap * (float)i;
            float y = (float)(-(rect.height() + 30));
            this.pMarkText.getTextBounds(text, 0, text.length(), rect);
            int degree = -45;
            float px = rect.exactCenterX() + x + (float)10;
            float py = y + rect.exactCenterY() - (float)10;
            graphCanvas.drawText(text, x - (float)(rect.width() / 2), y, this.pMarkText, (float)degree, px, py);
        }

    }

    private final void drawGraph(GraphCanvasWrapper graphCanvasWrapper) {
        this.pCircleBG.setColor(this.bgColor);
        int m = 0;

        for(int var3 = ((Collection)this.chartEntities).size(); m < var3; ++m) {
            GraphPath linePath = new GraphPath(this.getWidth(), this.getHeight(), (int)this.mPaddingLeft, (int)this.mPaddingBottom);
            boolean first = false;

            float x ;
            Float y ;
            this.p.setColor(-1);
            this.pCircle.setColor(-1);
            int xGap = this.xLength / (((ChartEntity)this.chartEntities.get(m)).getValues().length - 1);
            System.out.println("    xGap"+xGap);

            int t = 0;

            int var10;

            for(var10 = ((ChartEntity)this.chartEntities.get(m)).getValues().length; t < var10; ++t) {
                if (t < ((ChartEntity)this.chartEntities.get(m)).getValues().length) {
                    x = (float)(xGap * t);
                    y = (float)this.yLength * ((ChartEntity)this.chartEntities.get(m)).getValues()[t] / (float)this.maxValue;
                    if (first) {
                        linePath.lineTo(x, y);
                    } else {
                        linePath.moveTo(x, y);
                        first = true;
                    }
                }
            }

            Canvas var10000 = graphCanvasWrapper.getCanvas();
            if (var10000 != null) {
                var10000.drawPath((Path)linePath, this.p);
            }
        }

    }

    private final void initializePaint() {

        Paint var1 = new Paint();
        var1.setFlags(1);
        var1.setAntiAlias(true);
        var1.setFilterBitmap(true);
        var1.setColor(-16776961);
        var1.setStrokeWidth(10.0F);
        var1.setAntiAlias(true);
        var1.setStrokeCap(Paint.Cap.ROUND);
        var1.setStyle(Paint.Style.STROKE);
        this.p = var1;


        var1 = new Paint();
        var1.setFlags(1);
        var1.setAntiAlias(true);
        var1.setFilterBitmap(true);
        var1.setColor(-16776961);
        var1.setStrokeWidth(20.0f);
        var1.setStyle(Paint.Style.STROKE);
        this.pCircle = var1;

        var1 = new Paint();
        var1.setAntiAlias(true);
        var1.setFilterBitmap(true);
        var1.setColor(this.bgColor);
        var1.setStrokeWidth(10.0F);
        var1.setStyle(Paint.Style.FILL_AND_STROKE);
        this.pCircleBG = var1;

        var1 = new Paint();
        var1.setFlags(1);
        var1.setAntiAlias(true);
        var1.setFilterBitmap(true);
        var1.setShader((Shader)(new LinearGradient(0.0F, 300.0F, 0.0F, 0.0F, -16776961, -16776961, Shader.TileMode.MIRROR)));
        this.pLine = var1;

        var1 = new Paint();
        var1.setFlags(1);
        var1.setAntiAlias(true);
        var1.setFilterBitmap(true);
        var1.setColor(this.lineColor);
        var1.setStrokeWidth(10.0F);//lllll10

        this.pBaseLine = var1;
        var1 = new Paint();
        var1.setFlags(1);
        var1.setAntiAlias(true);
        var1.setFilterBitmap(true);
        var1.setColor(this.lineColor);
        var1.setStrokeWidth(5.0F);////5
        var1.setStyle(Paint.Style.STROKE);
        this.pBaseLineX = var1;

        var1 = new Paint();
        var1.setFlags(1);
        var1.setAntiAlias(true);
        var1.setColor(-16777216);
        this.pMarkText = var1;

    }


}




//===========================================================================


class ChartEntity {
    int color;
    float[] values;

    public ChartEntity(int color, float[] values) {
        this.color = color;
        this.values = values;
    }

    public int getColor() {
        return color;
    }

    public float[] getValues() {
        return values;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

}

//===========================================================================
class GraphCanvasWrapper {
    MatrixTranslator mMt;
    Canvas canvas;

    public GraphCanvasWrapper(Canvas canvas, int width, int height, int paddingLeft, int paddingBottom) {
        this.canvas = canvas;
        this.mMt = new MatrixTranslator(width, height, paddingLeft, paddingBottom);
    }

    //    public final void drawCircle(float cx, float cy, float radius,Paint paint) {
//        Canvas var = this.canvas;
//        var.drawCircle(this.mMt.calcX(cx), this.mMt.calcY(cy), radius, paint);
//    }
//    public final void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint) {
//        Canvas var = this.canvas;
//        if (var != null) {
//            var.drawArc(oval, startAngle, sweepAngle, true, paint);
//        }
//    }
    public final void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        Canvas var10000 = this.canvas;
        var10000.drawLine(this.mMt.calcX(startX), this.mMt.calcY(startY), this.mMt.calcX(stopX), this.mMt.calcY(stopY), paint);
    }
//    public final void drawRect(float startX, float startY, float stopX, float stopY,Paint paint) {
//        Canvas var = this.canvas;
//        var.drawRect(this.mMt.calcX(startX), this.mMt.calcY(startY), this.mMt.calcX(stopX), this.mMt.calcY(stopY), paint);
//    }
//    public final void drawText(String text, float x, float y,  Paint paint) {
//        Canvas var10000 = this.canvas;
//        var10000.drawText(text, this.mMt.calcX(x), this.mMt.calcY(y), paint);
//    }

    public final void drawText(String text, float x, float y, Paint paint, float degree, float px, float py) {
        Canvas var10000 = this.canvas;
        var10000.save();
        this.canvas.rotate(degree, this.mMt.calcX(px), this.mMt.calcY(py));
        this.canvas.drawText(text, this.mMt.calcX(x), this.mMt.calcY(y), paint);
        this.canvas.restore();
    }

    public final void drawBitmapIcon(Bitmap bitmap, float left, float top, Paint paint) {

        Canvas var10000 = this.canvas;
        var10000.drawBitmap(bitmap, this.mMt.calcBitmapCenterX(bitmap, left), this.mMt.calcBitmapCenterY(bitmap, top), paint);
    }


    public MatrixTranslator getmMt() {
        return mMt;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}

//===========================================================================
class MatrixTranslator {
    private int mWidth;
    private int mHeight;
    private int mPaddingLeft;
    private int mPaddingBottom;

    public MatrixTranslator(int mWidth, int mHeight, int mPaddingLeft, int mPaddingBottom) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.mPaddingLeft = mPaddingLeft;
        this.mPaddingBottom = mPaddingBottom;
    }


    public Float calcX(float x) {
        return x + (float)this.mPaddingLeft;
    }
    public Float calcY(float y) {
        return (float)this.mHeight - (y + (float)this.mPaddingBottom);
    }
    public Float calcBitmapCenterX(Bitmap bitmap, float x) {
        return x + (float)this.mPaddingLeft - (float)(bitmap.getWidth() / 2);
    }

    public Float calcBitmapCenterY(Bitmap bitmap, float y) {
        return (float)this.mHeight - (y + (float)this.mPaddingBottom) - (float)(bitmap.getHeight() / 2);
    }


}
//===========================================================================
class GraphPath extends Path {
    private final MatrixTranslator mMt;

    public void moveTo(float x, float y) {
        super.moveTo(this.mMt.calcX(x), this.mMt.calcY(y));
    }

    public final void moveTo(PointF point) {
        super.moveTo(this.mMt.calcX(point.x), this.mMt.calcY(point.y));
    }

    public void lineTo(float x, float y) {
        super.lineTo(this.mMt.calcX(x), this.mMt.calcY(y));
    }

    public final void lineTo(PointF point) {
        super.lineTo(this.mMt.calcX(point.x), this.mMt.calcY(point.y));
    }

    public GraphPath(int width, int height, int paddingLeft, int paddingBottom) {
        this.mMt = new MatrixTranslator(width, height, paddingLeft, paddingBottom);
    }


}

//===========================================================================
