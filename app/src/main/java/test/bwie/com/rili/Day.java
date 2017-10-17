package test.bwie.com.rili;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * name:周振辉
 * 时间：2017/10/12 13:54
 * 类描述：
 */

public class Day {
    /**
     * 单个日期格子的宽
     */
    public int width;
    /**
     * 单个日期格子的高
     */
    public int height;
    /**
     * 日期的文本
     */
    public String text;
    /**
     * 文本字体的颜色
     */
    public int textClor;
    /**
     * 日期背景的类型 0代表无任何背景，1代表正常打卡，2代表所选日期，3代表当前日期 4,代表即是当前日期，也被选中
     */
    public int backgroundStyle;
    /**
     * 字体的大小
     */
    public float textSize;
    /**
     * 背景的半径
     */
    public int backgroundR;
    /**
     * 出勤的类型 0为不画，1为正常考勤，2为异常，3为出差外出灯
     */
    public int workState;
    public int workState2;
    /**
     * 出勤状态的半径
     */
    public int workStateR = 5;
    /**
     * 字体在第几行
     */
    public int location_x;
    /**
     * 字体在第几列
     */
    public int location_y;

    /**
     * 日期是否已经过去，包含今天
     */
    public boolean hadPassed;

    public boolean isToday;

    /**
     * 创建日期对象
     * @param width 每个日期格子的宽度
     * @param height 每个日期格子的高度
     */
    public Day(int width, int height) {
        this.width = width;
        this.height = height;
    }


    /**
     * 画天数
     *
     * @param canvas  要画的画布
     * @param paint   画笔
     * @param context 画布的上下文对象
     */
    public void drawDays(Canvas canvas, Context context, Paint paint) {
        //取窄的边框为圆的半径

        backgroundR = width > height ? height : width;

        //画背景
        drawBackground(canvas, paint);

        //画数字
        drawTaxt(canvas, paint);



        //画对号
        drawMark(canvas, paint);

    }

    /**
     * 画对号
     *
     * @param canvas
     * @param paint
     */
    private void drawMark(Canvas canvas, Paint paint) {
        //确定圆心位置
        float cx = location_x * width + width / 2;
        float xy = location_y * height + height * 44 / 60;

        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        //根据工作状态设置画笔颜色
        if (workState2 == 0) {
            return;
        }
        switch (workState2) {
            case 2:

                break;
            case 1:
//                paint.setColor(Color.RED);
//                Path path = new Path();
//                path.moveTo(cx-5, xy-5);
//                path.lineTo(cx,xy);
//                path.lineTo(cx+10, xy-10);
//                canvas.drawPath(path,paint);
                break;
        }

    }
    /**
     * 花数字
     *
     * @param canvas
     * @param paint
     */
    private void drawTaxt(Canvas canvas, Paint paint) {
        //根据圆的半径设置字体的大小
        textSize = backgroundR / 3;
        paint.setTextSize(textSize);
        paint.setColor(textClor);
        paint.setStyle(Paint.Style.FILL);
        //计算文字的宽度
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width();
        //计算画文字的位置
        float x = location_x * width + (width - w) / 2;
        float y = location_y * height + (height + textSize/2) / 2;
        canvas.drawText(text, x, y, paint);
    }

    /**
     * 画背景
     *
     * @param canvas
     * @param paint
     */
    private void drawBackground(Canvas canvas, Paint paint) {
        //计算圆心的位置
        float cx = location_x * width + width / 2;
        float cy = location_y * height + height / 2;
        //画背景 根据背景状态设置画笔类型
        if (backgroundStyle == 0) {
            return;
        }
        if (hadPassed){
            drawRoundRect(canvas,paint);
        }
        if (!hadPassed || isToday){
            switch (backgroundStyle) {
                case 1:
                    paint.setColor(0xFFFAFAFA);
                    paint.setStyle(Paint.Style.FILL);
                    break;
                case 2:
                    // paint.setStyle(Paint.Style.FILL);
                    // paint.setColor(0xFFE52615);
                    break;
                case 3:
                    paint.setColor(0xFFEE6F79);
                    paint.setStyle(Paint.Style.FILL);

                    break;
            }
            canvas.drawCircle(cx, cy, backgroundR * 9 / 20, paint);
        }
    }

    private void drawRoundRect(Canvas canvas, Paint paint){
        float cx = location_x * width + width / 2;
        float cy = location_y * height + height / 2;
        float radius = backgroundR * 9 / 20;
        paint.setColor(0xFFFDE8EA);
        paint.setStyle(Paint.Style.FILL);
        if (("01".equals(text) || location_x == 0) && backgroundStyle !=3){
            canvas.drawCircle(cx , cy, radius , paint);
            RectF rect = new RectF();
            rect.left = cx;
            rect.top = cy - radius;
            rect.right = location_x * width + width;
            rect.bottom = cy + radius;
            canvas.drawRect(rect, paint);
        }

        else if (location_x == 6||(location_x == 2&&location_y==3)){
            canvas.drawCircle(cx , cy, radius , paint);
            RectF rect = new RectF();
            rect.left = location_x * width;
            rect.top = cy - radius;
            rect.right = cx;
            rect.bottom = cy + radius;
            canvas.drawRect(rect,paint);
        }else {
            RectF rect = new RectF();
            rect.left = location_x * width;
            rect.top = cy - radius;
            rect.right = location_x * width + width;
            rect.bottom = cy + radius;
            canvas.drawRect(rect,paint);
        }


    }

}
