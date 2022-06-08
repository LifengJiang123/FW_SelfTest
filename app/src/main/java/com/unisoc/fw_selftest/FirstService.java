package com.unisoc.fw_selftest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class FirstService extends Service {

    private TestBinder mbinder = new TestBinder();

    public FirstService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService","onCreat");

        /* 5 使用前台service */
        // 5.1 获取NotificationManger实例对象（通知管理对象）
        NotificationManager manger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 5.2 构建通知渠道
        String channelId = "1";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "111", NotificationManager.IMPORTANCE_DEFAULT);
            manger.createNotificationChannel(channel);
        }
        // 5.3 通过Builder构造器创建Notification对象，这是8.0以后的写法
        /*  // 加了一个版本判断，以适应低版本
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, channelId);
        }
        */
        // 忽略低版本的情况，如果运行在低版本上，抛出异常
        Notification.Builder builder = new Notification.Builder(this, channelId);
        // 5.4 中间通过set...可丰富通知
        Intent intent = new Intent(this, ThirdActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notify = builder
                .setContentText("qi qi")
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        // 5.5 通知显示
        manger.notify(1, notify);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService","onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService","onDestroy");
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mbinder;
    }

    class TestBinder extends Binder{
        public void f1(){
            System.out.println("绑定成功");
        }
    }
}

