package com.unisoc.fw_selftest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TimeChangeReceivere timeChangeReceivere;
    private BroadcastReceiver testReceiver1;
    private BroadcastReceiver testReceiver2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 接收系统广播 -- 通过动态方式注册 // 没实现
        IntentFilter intentFilter = new IntentFilter();
        // intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        timeChangeReceivere = new TimeChangeReceivere();
        registerReceiver(timeChangeReceivere, intentFilter);

        /* 发送一条 自定义的 标准广播 -- 通过静态方式注册 -- 8.0前后有差*/
        findViewById(R.id.btn_send_broadcast_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.unisoc.fw_selftest.MY_BROADCAST_RECEIVER");
                // 8.0以后的版本需要指明该广播是发送到哪一个地方的
                intent.setComponent(new ComponentName( "com.unisoc.fw_selftest", "com.unisoc.fw_selftest.MyBroadcastReceiver") );
                sendBroadcast(intent);
            }
        });

        testReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "有序广播1", Toast.LENGTH_SHORT).show();
            }
        };

        testReceiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "有序广播2", Toast.LENGTH_SHORT).show();
            }
        };

        /* 发送有序广播 -- 会接收到2个Toas提示 */
        IntentFilter i1 = new IntentFilter();
        i1.addAction("com.unisoc.fw.orderBroadcastReceiver_test");
        i1.setPriority(100);
        registerReceiver(testReceiver1, i1);

        IntentFilter i2 = new IntentFilter();
        i2.addAction("com.unisoc.fw.orderBroadcastReceiver_test");
        i2.setPriority(95);
        registerReceiver(testReceiver2, i2);

        findViewById(R.id.btn_send_broadcast_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.unisoc.fw.orderBroadcastReceiver_test");
                //发送一个有序广播
                sendOrderedBroadcast(intent, null);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeChangeReceivere);
        unregisterReceiver(testReceiver1);
        unregisterReceiver(testReceiver2);

    }
}

/**
 * 定义时间变化的类，继承自BroadcastReceiver
 */
class TimeChangeReceivere extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"接收到时间变化的系统广播", Toast.LENGTH_SHORT).show();
    }
}




