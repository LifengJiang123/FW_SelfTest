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
    private Test1Receiver t1;
    private Test2Receiver t2;
    private Test3Receiver t3;

    private T1 t11;
    private T2 t22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        timeChangeReceivere = new TimeChangeReceivere();
        registerReceiver(timeChangeReceivere, intentFilter);


        findViewById(R.id.btn_send_broadcast_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.unisoc.fw_selftest.MY_BROADCAST_RECEIVER");
                // android 8.0 以后的问题
                intent.setComponent( new ComponentName( "com.unisoc.fw_selftest", "com.unisoc.fw_selftest.MyBroadcastReceiver") );
                sendBroadcast(intent);
            }
        });


        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("x.xx.xxx.有序");
        filter2.setPriority(999);
        registerReceiver(new Test2Receiver(), filter2);

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("x.xx.xxx.有序");
        filter1.setPriority(1000);
        registerReceiver(new Test1Receiver(), filter1);

        IntentFilter filter3 = new IntentFilter();
        filter3.addAction("x.xx.xxx.有序");
        filter3.setPriority(998);
        registerReceiver(new Test3Receiver(), filter3);

        IntentFilter i1 = new IntentFilter();
        IntentFilter i2 = new IntentFilter();
        i1.addAction("aaaaa.bbbd");
        i2.addAction("aaaaa.bbbd");
        registerReceiver(new T1(), i1);
        registerReceiver(new T2(), i2);


        findViewById(R.id.btn_send_broadcast_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.setAction("x.xx.xxx.有序");
                intent.setAction("aaaaa.bbbd");
                //发送一个有序广播
                sendOrderedBroadcast(intent, null);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeChangeReceivere);

    }

    class TimeChangeReceivere extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"接收到时间变化的系统广播", Toast.LENGTH_SHORT).show();
        }
    }
}

class T1 extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "111", Toast.LENGTH_SHORT).show();
    }
}

class T2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "222", Toast.LENGTH_SHORT).show();
    }
}


//广播接收者：有序广播-1
class Test1Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        //有序广播里终止广播
        //abortBroadcast();
        setResultData("国家发放补贴800");
        Toast.makeText(context, "省接收："+resultData, Toast.LENGTH_SHORT).show();
    }
}

//广播接收者：有序广播-2
class Test2Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        setResultData("国家发放补贴600");
        Toast.makeText(context, "市接收："+resultData, Toast.LENGTH_SHORT).show();
    }
}

//广播接收者：有序广播-3
class Test3Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Toast.makeText(context, "县接收："+resultData, Toast.LENGTH_SHORT).show();
    }
}
