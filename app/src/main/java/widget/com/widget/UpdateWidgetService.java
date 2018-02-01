package widget.com.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangqiang on 2018/1/31.
 */

public class UpdateWidgetService extends Service {
    private Timer timer;
    private TimerTask task;

    public UpdateWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        startSchedule();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startSchedule();
        return START_REDELIVER_INTENT;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        task.cancel();
        timer = null;
        task = null;
    }

    void startSchedule(){
        //timer存在不需要添加task定时器
        if(timer != null && task != null){
            return;
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //int runningTaskCount = SystemInfoUtils.getRunningTaskCount(UpdateWidgetService.this);
                //long avaliMem = SystemInfoUtils.getAvaliMem(UpdateWidgetService.this);
                CharSequence text;
                java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
                text = "http://blog.csdn.net/imyang2007" + "    Time:" + df.format(new Date());
                ComponentName componentName = new ComponentName(UpdateWidgetService.this, HelloAppWidgetProvider.class);
                RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_main);
                views.setTextViewText(R.id.appwidget_text, text);
                views.setImageViewResource(R.id.image,getApplicationContext().getResources().getColor(R.color.colorPrimary));

//                //设置Widget中Textview的显示内容
//                remoteViews.setTextViewText(R.id.tv_runprocessnumber, "正在运行软件:" + runningTaskCount);
//                remoteViews.setTextViewText(R.id.tv_avalimem, "可用内存:" + Formatter.formatFileSize(UpdateWidgetService.this, avaliMem));
//
//                //点击widget的一键清理按钮，发送广播，在AutoKillTaskReceiver广播中杀掉所有的进程。
//                Intent intent = new Intent(UpdateWidgetService.this, AutoKillTaskReceiver.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(UpdateWidgetService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                remoteViews.setOnClickPendingIntent(R.id.btn_killall, pendingIntent);
//
//                //点击widget显示信息部分，跳到程序管理页面
//                Intent startActivityIntent = new Intent(UpdateWidgetService.this, TaskManagerActivity.class);
//                startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                PendingIntent processInfoIntent = PendingIntent.getActivity(UpdateWidgetService.this, 0, startActivityIntent, PendingIntent.FLAG_ONE_SHOT);
//                remoteViews.setOnClickPendingIntent(R.id.ll_processinfo, processInfoIntent);

                //由AppWidgetManager处理Wiget。
                AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
                awm.updateAppWidget(componentName, views);

            }
        };
        timer.schedule(task, 1000, 1000);
        super.onCreate();
    }
}
