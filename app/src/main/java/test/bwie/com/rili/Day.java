package test.bwie.com.rili;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    public void drawDays( Canvas canvas, Context context, Paint paint,String[] str,String[] str1,String wrong,String isday,String[] girl,String[] str1_g) {
        //取窄的边框为圆的半径

        backgroundR = width > height ? height : width;

        //画背景
        drawBackground(context,canvas, paint,str,str1,str1_g);
        //画对号
        drawMark(canvas, paint,girl,context);
        //画数字
        drawTaxt(context,canvas, paint,wrong,isday);





    }

    /**
     * 画对号
     *
     * @param canvas
     * @param paint
     */
    private void drawMark(Canvas canvas, Paint paint,String[] girl,Context context) {
        //确定圆心位置
        float cx = location_x * width + width / 2;
        float xy = location_y * height + height ;

        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        //女生特权

        for(int i = 0;i<girl.length;i++)
        {

            if(girl[i].equals(text)){
                //画点
                paint.setColor(Color.RED);
                Path path = new Path();
                path.moveTo(cx-5, xy-5);
                path.lineTo(cx,xy);
                path.lineTo(cx+10, xy-10);
                canvas.drawPath(path,paint);
                //化红心
                Bitmap rawBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.hongxin);
                canvas.drawBitmap(rawBitmap, location_x * width+width/5-3, location_y * height+height/5-2, paint);
            }
        }




    }
    /**
     * 花数字
     *
     * @param canvas
     * @param paint
     */
    private void drawTaxt(Context context,Canvas canvas, Paint paint,String wrong,String isday) {
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
        //大卡中断
        if(wrong != null){
            if(wrong.equals(text)){
                Bitmap rawBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.huang);
                canvas.drawBitmap(rawBitmap, location_x * width+width/5-3, location_y * height+height/5-2, paint);

            }
        }
        //打卡成功
        if(isday != null){
            if(isday.equals(text)&&isToday){
                Bitmap rawBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.duihao);
                canvas.drawBitmap(rawBitmap, location_x * width+width/5-3, location_y * height+height/5-2, paint);
            }
        }


    }

    /**
     * 画背景
     *
     * @param canvas
     * @param paint
     */
    private void drawBackground(Context context,Canvas canvas, Paint paint,String[] str ,String[] str1,String[] str1_g) {
        //计算圆心的位置
        float cx = location_x * width + width / 2;
        float cy = location_y * height + height / 2;
        int radius = backgroundR * 9 / 20;
        //画背景 根据背景状态设置画笔类型
        if (backgroundStyle == 0) {
            return;
        }
            //打卡中断
            drawRoundRect(canvas,paint,str);
            //打卡
            drawRoundRect2(canvas,paint,str1);
        //女生特权之后的打卡
        drawRoundRect2(canvas,paint,str1_g);

        //当天还没有打卡，就是刚进入的状态
        switch (backgroundStyle) {
            case 1:
                paint.setColor(0xFFFAFAFA);
                paint.setStyle(Paint.Style.FILL);
                break;
            case 2:
                 paint.setStyle(Paint.Style.FILL);
                 paint.setColor(0xFFEE6F7A);
                break;
            case 3:


                break;
        }
        canvas.drawCircle(cx, cy, backgroundR * 9 / 20-7, paint);

    }

    private void drawRoundRect(Canvas canvas, Paint paint,String[] str){
        float cx = location_x * width + width / 2;
        float cy = location_y * height + height / 2;
        float radius = backgroundR * 9 / 20;
        paint.setColor(0xFFF3F4F6);
        paint.setStyle(Paint.Style.FILL);

        for(int i = 0;i<str.length;i++){
            int a = 1;
            String s = str[i];
            if(s.equals(text)){
                a = location_x;
            }

            int b = 1;
            if(str[0].equals(text)){
                b = location_x;
            }
            int c = 1;
            if(str[str.length-1].equals(text)){
                c = location_x;
            }

        if (((s.equals(text)&&i==0)||a==0) && backgroundStyle !=3  && b!=6 && c!=0){
            canvas.drawCircle(cx , cy, radius , paint);
            RectF rect = new RectF();
            rect.left = cx;
            rect.top = cy - radius;
            rect.right = location_x * width + width;
            rect.bottom = cy + radius;
            canvas.drawRect(rect, paint);
        }else if(a!=0 && a!= 6 && i!= 0 && i!=str.length-1 && s.equals(text) && backgroundStyle !=3)
        {
            RectF rect = new RectF();
            rect.left = location_x * width;
            rect.top = cy - radius;
            rect.right = location_x * width + width;
            rect.bottom = cy + radius;
            canvas.drawRect(rect,paint);
        }else if(((s.equals(text)&&i==str.length-1)||a==6) && backgroundStyle !=3  && b!=6 && c!=0){
                        canvas.drawCircle(cx , cy, radius , paint);
            RectF rect = new RectF();
            rect.left = location_x * width;
            rect.top = cy - radius;
            rect.right = cx;
            rect.bottom = cy + radius;
            canvas.drawRect(rect,paint);
        }else if(c==0||b==6){
            canvas.drawCircle(cx, cy, backgroundR * 9 / 20, paint);
        }
        }


    }
    private void drawRoundRect2(Canvas canvas, Paint paint,String[] str){

        float cx = location_x * width + width / 2;
        float cy = location_y * height + height / 2;
        float radius = backgroundR * 9 / 20;
        paint.setColor(0xFFFDE8EA);
        paint.setStyle(Paint.Style.FILL);

        for(int i = 0;i<str.length;i++){
            int a = 1;
            String s = str[i];
            if(s.equals(text)){
                a = location_x;
            }
            int b = 1;
            if(str[0].equals(text)){
                b = location_x;
            }
            int c = 1;
            if(str[str.length-1].equals(text)){
                c = location_x;
            }


            if (((s.equals(text)&&i==0)||a==0) && backgroundStyle !=3 && b!=6 && c!=0){
                canvas.drawCircle(cx , cy, radius , paint);
                RectF rect = new RectF();
                rect.left = cx;
                rect.top = cy - radius;
                rect.right = location_x * width + width;
                rect.bottom = cy + radius;
                canvas.drawRect(rect, paint);
            }else if(a!=0 && a!= 6 && i!= 0 && i!=str.length-1 && s.equals(text) && backgroundStyle !=3)
            {
                RectF rect = new RectF();
                rect.left = location_x * width;
                rect.top = cy - radius;
                rect.right = location_x * width + width;
                rect.bottom = cy + radius;
                canvas.drawRect(rect,paint);
            }else if(((s.equals(text)&&i==str.length-1)||a==6) && backgroundStyle !=3 && b!=6 && c!=0){
                canvas.drawCircle(cx , cy, radius , paint);
                RectF rect = new RectF();
                rect.left = location_x * width;
                rect.top = cy - radius;
                rect.right = cx;
                rect.bottom = cy + radius;
                canvas.drawRect(rect,paint);
            }
            else if(c==0||b==6){
                canvas.drawCircle(cx, cy, backgroundR * 9 / 20, paint);
            }
        }


    }

}
