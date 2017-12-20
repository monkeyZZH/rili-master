package test.bwie.com.rili.view;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import test.bwie.com.rili.Day;

/**
 * 日期的管理类
 * Created by xiaozhu on 2016/8/7.
 */
public class DayManager  {



    /**
     * 记录当前的时间
     */
    public static  String currentTime;

    /**
     * 当前的日期
     */
    private static int current = -1;
    /**
     * 储存当前的日期
     */
    private static int tempcurrent=-1;
    /**
     *
     */
    static String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
    static String[] dayArray = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};


    /**
     * 设置当前的时间
     * @param currentTime
     */
    public static void setCurrentTime(String currentTime) {
        DayManager.currentTime = currentTime;
    }

    /**
     * 获取当前的时间
     * @return
     */
    public static String getCurrentTime() {
        return currentTime;
    }


    /**
     * 储存正常天数
     */
    static Set<Integer> normalDays = new HashSet<>();
    static List<Day> days = new ArrayList<>();
    static int count;

    /**
     * 添加正常天数
     *
     * @param i
     */
    public static void addNomalDays(int i) {
        normalDays.add(i);
    }

    /**
     * 移除正常的天数
     *
     * @param i
     */
    public static void removeNomalDays(int i) {
        if (normalDays.contains(i)) {
            normalDays.remove(i);
        }
    }

    /**
     * 储存异常天数
     */
    static Set<Integer> abnormalDays = new HashSet<>();

    /**
     * 添加异常天数
     *
     * @param i
     */
    public static void addAbnormalDays(int i) {
        abnormalDays.add(i);
    }

    /**
     * 移除异常的天数
     *
     * @param i
     */
    public static void removeAbnormalDays(int i) {
        if (abnormalDays.contains(i)) {
            abnormalDays.remove(i);
        }
    }
    /**
     * 储存对号天数
     */
    /**
     * 储存外出天数
     */
    static Set<Integer> outDays = new HashSet<>();

    /**
     * 添加外出天数天数
     *
     * @param i
     */
    public static void addOutDays(int i) {
        outDays.add(i);
    }

    /**
     * 移除外出天数的天数
     *
     * @param i
     */
    public static void removeOutDays(int i) {
        if (outDays.contains(i)) {
            outDays.remove(i);
        }
    }


    public static void removeAllDays() {
        abnormalDays.clear();

    }

    public static void setCurrent(int current) {
        DayManager.current = current;
    }

    private static int select = -1;

    public static void setSelect(int select) {
        DayManager.select = select;
    }

    /**
     * 根据日历对象创建日期集合
     *
     * @param calendar 日历
     * @param width 控件的宽度
     * @param heigh 控件的高度
     * @return 返回的天数的集合
     */

    public static List<Day> createDayByCalendar(Calendar calendar, int width, int heigh) {


        days.clear();
        Day day = null;

        int dayWidth = width / 7;
        int dayHeight = heigh / (calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) + 1);
        //添加星期标识，
        for (int i = 0; i < 7; i++) {

            day = new Day(dayWidth, dayHeight);
            //为星期设置位置，为第0行，
            day.location_x = i;
            day.location_y = 0;
            day.text = weeks[i];
            //设置日期颜色
            day.textClor = 0xFFCCCCCC;
            days.add(day);
        }
        count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstWeekCount = calendar.getActualMaximum(Calendar.DAY_OF_WEEK);
        //生成每一天的对象，其中第i次创建的是第i+1天
        for (int i = 0; i < count; i++) {
            day = new Day(dayWidth, dayHeight);
            day.text = dayArray[i];

            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
            //设置每个天数的位置
            day.location_y = calendar.get(Calendar.WEEK_OF_MONTH);
            day.location_x = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            //设置日期选择状态
            if (i == current - 1) {
//                day.backgroundStyle = 3;
//                day.textClor = 0xFFffffff;
                day.backgroundStyle = 2;
                day.textClor = 0xFF000000;

            } else if (i == select - 1) {
                day.backgroundStyle = 2;
                day.textClor = 0xFF000000;
            } else {
                day.backgroundStyle = 1;
                day.textClor = 0xFF000000;
            }

            day.hadPassed = i < current;
            day.isToday = i == current - 1;

            days.add(day);
        }

        return days;
    }


}
