package com.unisoc.fw_selftest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private FirstService.TestBinder testBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            testBinder = (FirstService.TestBinder) service;
            testBinder.f1();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(ThirdActivity.this, "service被异常销毁", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent_third = new Intent(this,FirstService.class);
        findViewById(R.id.btn_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent_third);
            }
        });

        /* 2 停止service*/
        findViewById(R.id.btn_stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent_third);
            }
        });

        /* activity service之间的通信*/
        /* 3 绑定service */
        findViewById(R.id.btn_bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent_third, connection, Context.BIND_AUTO_CREATE);
            }
        });

        /* 4 解除绑定 */
        findViewById(R.id.btn_unbind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });


    }
}