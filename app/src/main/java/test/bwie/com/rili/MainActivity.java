package test.bwie.com.rili;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import test.bwie.com.rili.view.CalendarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private ImageView tv_pre;
    private TextView tv_month;
    private ImageView tv_next;
    /**日历控件*/
    private CalendarView calendar;
    /**日历对象*/
    private Calendar cal;
    /**格式化工具*/
    private SimpleDateFormat formatter;
    /**日期*/
    private Date curDate;


    private String[] str,str1,str1_g,girl;
    private String wrong,isday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_pre = (ImageView) findViewById(R.id.tv_pre);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_next = (ImageView) findViewById(R.id.tv_next);
        calendar = (CalendarView) findViewById(R.id.calendar);
        cal = Calendar.getInstance();
        //初始化界面
        init();


        //给左右月份设置点击监听事件
        tv_pre.setOnClickListener(this);
        tv_next.setOnClickListener(this);


    }

    /**
     * 初始化界面
     */
    private void init() {
        //打卡终端，也就是灰色部分
        str = new String[]{};
        //打卡中  粉红色部分
        str1 = new String[]{};
        //女生特权 之后的打卡部分
        str1_g= new String[]{};
        //女生特权
        girl = new String[]{};
        //在那一天中断打卡的
//        wrong = "10";
        //当天打卡成功 ，假如今天是18号   就写18
      //  isday = "18";

        //以上所有内容，没有的都可以不写，跟你获取到的值在进行赋值

        calendar.setCalendar(cal,str,str1,wrong,isday,girl,str1_g);

        formatter = new SimpleDateFormat("yyyy年MM月");
        //获取当前时间
        curDate = cal.getTime();
        String str = formatter.format(curDate);
        tv_month.setText(str);
        updateEveryDay();
    }

    private void updateEveryDay(){
        calendar.initDate();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        updateEveryDay();

    }
//切换月份
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_pre:
                cal.add(Calendar.MONTH,-1);
                init();
                calendar.setCalendar(cal,str,str1,wrong,isday,girl,str1_g);
                break;
            case R.id.tv_next:
                cal.add(Calendar.MONTH,+1);
                init();
                calendar.setCalendar(cal,str,str1,wrong,isday,girl,str1_g);
                break;
        }

    }
}
