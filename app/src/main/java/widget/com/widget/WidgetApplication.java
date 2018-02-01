package widget.com.widget;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Calendar;

/**
 * Created by zhangqiang on 2018/1/31.
 */

public class WidgetApplication extends Application {

    final static String REMINDER_ACTION = "reminder_action";
    @Override
    public void onCreate() {
        super.onCreate();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_TIME_TICK);//系统时间，每分钟发送一次
//        intentFilter.addAction(Intent.ACTION_SCREEN_ON);//屏幕打开（解锁），
//        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);//屏幕关闭
//        intentFilter.addAction(WidgetApplication.REMINDER_ACTION);
//        HelloAppWidgetProvider myBroadcast = new HelloAppWidgetProvider();
//        registerReceiver(myBroadcast, intentFilter);
        //setReminder(true);


    }


    /**
     * Set the alarm
     *
     * @param b whether enable the Alarm clock or not
     */
    private void setReminder(boolean b) {

        // get the AlarmManager instance
        AlarmManager am= (AlarmManager) getSystemService(ALARM_SERVICE);
        // create a PendingIntent that will perform a broadcast
        Intent intent = new Intent();
        intent.setAction(REMINDER_ACTION);
        PendingIntent pi= PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(this,HelloAppWidgetProvider.class), 0);

        if(b){
            // just use current time as the Alarm time.
            Calendar c=Calendar.getInstance();
            // schedule an alarm
            am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 5000,pi);
        }
        else{
            // cancel current alarm
            am.cancel(pi);
        }

    }
}
