package com.unisoc.fw_selftest;

import androidx.annotation.RequiresApi;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is designed for testing ContentProvider and ContentResolver
 * */
public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_addData1;
    private Button btn_queryData1;
    private Button btn_deleteData1;
    private Button btn_updateData1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        // "author text, " + "price real, " + "pages integer, " + "name text)";
        // 一 访问自己的ContentProvider
        init();

        // 跳转到联系人界面
        findViewById(R.id.btn_creat_database).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        btn_addData1 = findViewById(R.id.btn_addData);
        btn_addData1.setOnClickListener(this);

        btn_queryData1 = findViewById(R.id.btn_queryData);
        btn_queryData1.setOnClickListener(this);

        btn_updateData1 = findViewById(R.id.btn_updateData);
        btn_updateData1.setOnClickListener(this);

        btn_deleteData1 = findViewById(R.id.btn_deleteData);
        btn_deleteData1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addData:
                Uri uri1 = Uri.parse("content://com.unisoc.fw_database.provider/Book");
                ContentValues contentValues = new ContentValues();
                contentValues.put("book_title", "A Clash of Kings");
                contentValues.put("book_author", "Martin");
                contentValues.put("book_pages", "1040");

//              可能的原因:测试数据写死在代码里面，每次执行的时候都会尝试插入相同的数据，这样就会导致记录不唯一而插入失败
//              调用的也是DataBase那边的insert方法（调用是provider的insert方法），返回值为null，Database那边返回的就是null
                Uri newuri = getContentResolver().insert(uri1, contentValues);  // 会返回新增数据的id  // 返回为空

                String id = newuri.getPathSegments().get(1);
                Log.d("btn_addData","success");
                break;

            case R.id.btn_queryData:
                Uri uri2 = Uri.parse("content://com.unisoc.fw_database.provider/Book");
                Cursor cursor = getContentResolver().query(uri2, null, null, null, null);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        //modify3 getString要用cursor的
                        int book_titleIndex = cursor.getColumnIndex("book_title");
                        String title = cursor.getString(book_titleIndex);
                        int book_authorIndex = cursor.getColumnIndex("book_author");
                        String author = cursor.getString(book_authorIndex);
//                        String pages = getString(cursor.getColumnIndex("book_pages"));

                        System.out.println(title);
                        System.out.println(author);
//                        System.out.println(pages);

                        Log.d("btn_queryData","success");
                    }
                    cursor.close();
                } else {
                    Toast.makeText(FirstActivity.this, "query Failed，cursor为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_deleteData:
                Uri uri3 = Uri.parse("content://com.unisoc.fw_database.provider/Book");
                int d = getContentResolver().delete(uri3, "_id = ?", new String[]{"2"});  // 第2个删掉后，会发现id = 1，3，4
                System.out.println(d);
                Log.d("btn_deleteData","success");
                break;

            case R.id.btn_updateData:
                Uri uri4 = Uri.parse("content://com.unisoc.fw_database.provider/Book");
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("book_title", "A Store");
                contentValues1.put("book_pages", "24");
                int c = getContentResolver().update(uri4, contentValues1, "book_title = ?", new String[]{"Old Man1"});
                System.out.println(c);
                Log.d("btn_updateData","success");
                break;
            default:
                break;

        }
    }
}