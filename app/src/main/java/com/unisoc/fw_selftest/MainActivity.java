package com.unisoc.fw_selftest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 跳转到content界面
        Intent i1 = new Intent(this, FirstActivity.class);
        findViewById(R.id.btn_2_FirstActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });

        // 跳转到Broadcast界面
        Intent i2 = new Intent(this, SecondActivity.class);
        findViewById(R.id.btn_2_SecondActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });

        // 跳转到service界面
        Intent i3 = new Intent(this, ThirdActivity.class);
        findViewById(R.id.btn_2_ThirdActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i3);
            }
        });


        // 测试anr
        // 1 主线程
        // 2 超时时间，根据前后文的不同响应时间不同
        // 3 输入事件：按键、触摸  or  特定事件：广播（10s）service（20s）
        // 按钮的延时
        Intent i5 = new Intent(MainActivity.this, FifthActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(i5);
            }
        };
        // 不会抛出ANR
        findViewById(R.id.btn_2_FifthAcivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.schedule(task, 10000);
            }
        });

        // 发送广播的方式  // 最好加个按钮
        Intent intentFilter = new Intent("aaaaaa");
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentFilter.setComponent(new ComponentName( "com.unisoc.fw_selftest", "com.unisoc.fw_selftest.MyBroadcastReceiver") );
                sendBroadcast(intentFilter);
                System.out.println(11);
            }
        });


    }
}