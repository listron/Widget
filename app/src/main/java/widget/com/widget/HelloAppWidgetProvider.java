package widget.com.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;

/**
 * Created by zhangqiang on 2018/1/31.
 */

public class HelloAppWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence text;
        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
        text = "http://blog.csdn.net/imyang2007" + "    Time:" + df.format(new Date());

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);
        views.setTextViewText(R.id.appwidget_text, text);
        views.setImageViewResource(R.id.image,context.getResources().getColor(R.color.colorPrimary));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null ){
            return;
        }
        super.onReceive(context, intent);

        //当系统收到广播，会执行自己的service
        Intent startUpdateIntent = new Intent(context, UpdateWidgetService.class);
        context.startService(startUpdateIntent);

        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {//如果广播是每分钟发送一次的时间广播
            Log.e("timeBroad", "时间变化了");

        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.d("timeBroad", "屏幕解锁了");

        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d("timeBroad", "屏幕关闭了");
        }
        if(intent.getAction().equals(WidgetApplication.REMINDER_ACTION)){
            Log.d("timeBroad", "时间发生变化了");
        }
    }
}