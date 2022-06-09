package com.unisoc.fw_selftest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FourthActivity extends AppCompatActivity {

    private List<String> contactlist = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        // 二 ContentResolver使用例子，访问其他的ContentProvider 以访问通讯录为例
        // 1 xml文件上定义ListView
        // 2 定义列表，将要展示的数据存储进去
        // 3 构造适配器对象
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contactlist);
        // 4 获取LitView的实例，并将ListView绑定到适配器上
        ListView listView = (ListView) findViewById(R.id.listview_show_contact);
        listView.setAdapter(adapter);

        // 申请运行时权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS} ,1);
        } else {
            readContacts();
        }

    }

    /**
     * 读取联系人信息
     * */
    private void readContacts() {
        // 得到一个ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        // query方法返回一个cursor对象
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        // or
        // Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        // moveToNext方法返回的是一个boolean类型的数据
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                // 读取通讯录的姓名
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                // 读取通讯录的号码
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                System.out.println(name);
                System.out.println(number);

                contactlist.add(name);
                contactlist.add(number);
            }

            // 刷新listview
            adapter.notifyDataSetChanged();
            // 关闭cursor
            cursor.close();
            // AndroidManifest.xml中声明读取联系人的权限
            // <uses-permission android:name="android.permission.READ_CONTACTS"/>
        }
    }
}