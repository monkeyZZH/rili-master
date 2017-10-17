package test.bwie.com.rili.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.List;

import test.bwie.com.rili.Day;

/**
 * 自定义的日历控件
 * Created by xiaozhu on 2016/8/1.
 */
public class CalendarView extends View {

    private Context context;
    /**
     * 画笔
     */
    private Paint paint;
    /***
     * 当前的时间
     */
    private Calendar calendar;
    private  List<Day> days;

    /**
     * 改变日期，并更改当前状态，由于绘图是在calendar基础上进行绘制的，所以改变calendar就可以改变图片
     *
     * @param calendar
     */
    public void setCalendar(Calendar calendar) {

        this.calendar = calendar;
        if ((calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.YEAR)).equals(DayManager.getCurrentTime())) {

        } else {
            DayManager.setCurrent(-1);
        }
        invalidate();
    }

    public CalendarView(Context context) {
        super(context);
        this.context = context;
        //初始化控件
        initView();
    }


    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //初始化控件
        initView();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化控件
        initView();

    }

    public void initDate() {
       days = DayManager.createDayByCalendar(calendar, getMeasuredWidth(), getMeasuredHeight());
       Log.e("getMeasuredWidth","getMeasuredWidth()"+getMeasuredWidth());
}

    /***
     * 初始化控件
     */
    private void initView() {

        paint = new Paint();
        paint.setAntiAlias(true);
        calendar = Calendar.getInstance();
        DayManager.setCurrent(calendar.get((Calendar.DAY_OF_MONTH)));
        DayManager.setCurrentTime(calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.YEAR));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取day集合并绘制
        for (Day day : days) {
            Log.e("myMessage","fff= "+day.text+"==== "+day.workState);
            day.drawDays(canvas, context, paint);
        }

    }
}
