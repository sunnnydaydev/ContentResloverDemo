package com.example.contentresloverdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermision();
    }

    /**
     * 权限检测
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void checkPermision() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
    }

    /**
     * 读取联系人
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void readContacts() {
        StringBuffer sb = new StringBuffer(); //sb
        String name = null;  //联系人姓名
        String number = null;// 手机号


        Cursor cursor = null;
        cursor = getContentResolver().query( //简单模拟 实际放线程中操作（查询也是耗时）
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                sb.append("联系人：")
                        .append(name)
                        .append("手机号:")
                        .append(number)
                        .append("\n");
            }
        }
        ((TextView) findViewById(R.id.tv_text)).setText(sb.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "权限否定", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
