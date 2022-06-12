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
        System.out.println(1);
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
        System.out.println(2);
        btn_addData1 = findViewById(R.id.btn_addData);
        btn_addData1.setOnClickListener(this);

        btn_queryData1 = findViewById(R.id.btn_queryData);
        btn_queryData1.setOnClickListener(this);
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

                Uri newuri = getContentResolver().insert(uri1, contentValues);  // 会返回新增数据的id  // 返回为空
                // getContentResolver不为空，但是insert为空

                System.out.println(newuri);  // null
//                String id = newuri.getPathSegments().get(1);  // 存在问题
                Log.d("btn_addData","success");
                System.out.println("add success");
                break;

            case R.id.btn_queryData:
                Uri uri2 = Uri.parse("content://com.unisoc.fw_database.provider/Book");
                Cursor cursor = getContentResolver().query(uri2, null, null, null, null);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String title = getString(cursor.getColumnIndex("book_title"));
                        String author = getString(cursor.getColumnIndex("book_author"));
                        String pages = getString(cursor.getColumnIndex("book_pages"));

                        System.out.println(title);
                        System.out.println(author);
                        System.out.println(pages);

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
                System.out.println(d); // 0
                Log.d("btn_deleteData","success");
                break;

            case R.id.btn_updateData:
                Uri uri4 = Uri.parse("content://com.unisoc.fw_database.provider/Book");
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("book_title", "A Store");
                contentValues1.put("book_pages", "24");
                int c = getContentResolver().update(uri4, contentValues1, "book_title = ?", new String[]{"Old Man1"});
                System.out.println(c);  // 0
                Log.d("btn_updateData","success");
                break;
            default:
                break;

        }
    }
}