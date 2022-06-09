package com.unisoc.fw_selftest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is designed for testing ContentProvider and ContentResolver
 * */
public class FirstActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // "author text, " + "price real, " + "pages integer, " + "name text)";
        // 一 访问自己的ContentProvider

        findViewById(R.id.btn_addData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.unisoc.fw_database.provider/Book");

                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "A Clash of Kings");
                contentValues.put("author", "Martin");
                contentValues.put("pages", "1040");
                contentValues.put("price", "22.85");

                Uri newuri = getContentResolver().insert(uri, contentValues);  // 会返回新增数据的id

                String id = newuri.getPathSegments().get(1);
                Log.d("btn_addData","success");
                System.out.println("add success");
            }
        });

        findViewById(R.id.btn_queryData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.unisoc.fw_database.provider/Book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                while (cursor.moveToNext()){
                    String name = getString(cursor.getColumnIndex("name"));
                    System.out.println(name);
                    Log.d("btn_queryData","success");
                }
                cursor.close();
            }
        });

        findViewById(R.id.btn_updateData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.unisoc.fw_database.provider/Book");  // 未完成,未说明哪一行
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "A Store");
                contentValues.put("price", "24.05");
                getContentResolver().update(uri, contentValues, null, null);
                Log.d("btn_updateData","success");
            }
        });

        findViewById(R.id.btn_deleteData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.unisoc.fw_database.provider/Book");  // 未完成
                getContentResolver().delete(uri, null, null);
                Log.d("btn_deleteData","success");
            }
        });

        // 跳转到联系人界面
        findViewById(R.id.btn_creat_database).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });



    }
}