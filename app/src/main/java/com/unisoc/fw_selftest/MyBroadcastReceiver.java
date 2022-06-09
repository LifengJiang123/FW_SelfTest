package com.unisoc.fw_selftest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(context,"aaaaaaa",Toast.LENGTH_SHORT).show();
            }
        };


        // 可实现，但是ANR没有
        String action = intent.getAction();
        switch (action){
            case "aaaaaa":
//                timer.schedule(task, 3000);
                Toast.makeText(context,"aaaaaaa",Toast.LENGTH_SHORT).show();
                break;

            case "com.unisoc.fw_selftest.MY_BROADCAST_RECEIVER":
                Toast.makeText(context,"接收到的标准广播",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}