package com.unisoc.fw_selftest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    }
}