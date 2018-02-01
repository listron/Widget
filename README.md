# Widget
这个demo主要是实现android的app widget。
实现原理：
1：首先按照官方指导，创建app工程。
2：之后注册HelloAppWidgetProvider广播。
3：在广播接收器的基础上，启动service
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
    
    4:service的目的就是启动循环定时器。
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


用于更新widget上的ui或者执行自己的业务。
