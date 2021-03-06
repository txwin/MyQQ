package com.example.fragment;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {
    private Button button;
    private Button button_dialog;
    private EditText editText_style, editText_account, editText_password, editText_confirm;
    private Spinner spinner_sex;
    private String style;
    private Intent intent;
    private Bundle bundle;
    private FileStorage fileStorage = new FileStorage();
    private boolean Filestate = false;
    private boolean registerstate = true;
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase database;
    private Cursor cursor;

    private int progress = 0;
    private boolean log[] = new boolean[]{false, false, false, false};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int x = 0; x < permissions.length; x++) {
                if (grantResults[x] == PackageManager.PERMISSION_GRANTED) {
                    //采用文件存储方式保存注册用户信息
                    if (fileStorage.writeFile(Register.this, "data.txt",
                            editText_account.getText().toString() + "&" + editText_password.getText().toString() + "\n", false)) {
                        Filestate = true;
                    } else {
                        Filestate = false;
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button = findViewById(R.id.button3);
        button_dialog = findViewById(R.id.button_dialog);
        editText_style = findViewById(R.id.editText6);
        editText_account = findViewById(R.id.editText3);
        editText_confirm = findViewById(R.id.editText5);
        editText_password = findViewById(R.id.editText4);
        spinner_sex = findViewById(R.id.spinner);
        intent = getIntent();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText_style.getText().toString().equals("") && !editText_account.getText().toString().equals("") && !editText_password.getText().toString().equals("")
                        && !editText_confirm.getText().toString().equals("") && editText_password.getText().toString().equals(editText_confirm.getText().toString())) {
                    /***********文件方式读取注册信息判断账号是否存在**********/
                    //判断输入的账号是否已经存在
//                    List<Map<String, Object>> list = fileStorage.readFile(Register.this, "data" +
//                            ".txt", false);
//                    //判断文件是否存在，通过ReadFile()方法的返回值获取
//                    if (list != null) {
//                        for (Map<String, Object> map : list) {
//                            if (map.get("account").equals(editText_account.getText().toString()
//                            )) {
//                                Toast.makeText(Register.this, "账号已存在", Toast.LENGTH_SHORT).show();
//                                registerstate = false;
//                                break;
//                            } else {
//                                registerstate = true;
//                            }
//                        }
//                    }
                    /***********数据库方式读取注册信息判断账号是否存在**********/
                    //创建数据库
                    mySQLiteHelper = new MySQLiteHelper(Register.this, "data.db", null, 1);
                    //以读写方式打开数据库，如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。如果该问题成功解决，则只读数据库对象就会关闭，然后返回一个可读写的数据库对象。
                    database = mySQLiteHelper.getReadableDatabase();
                    //查询数据库中所有数据
                    cursor = database.rawQuery("select * from Message", null);
                    //将Cursor对象的内部指针指向第一行数据
                    if (!cursor.isFirst()) {
                        cursor.moveToFirst();
                    }
                    //循环遍历数据库中的所有数据
                    if (cursor.getCount() > 0) {
                        do {
                            if (cursor.getString(1).equals(editText_account.getText().toString())) {
                                Toast.makeText(Register.this, "账号已存在", Toast.LENGTH_SHORT).show();
                                registerstate = false;
                                break;
                            } else {
                                registerstate = true;
                            }
                        } while (cursor.moveToNext());
                        cursor.close();
                    }

                    //不存在重复账号后注册账号
                    if (registerstate) {
                        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                        progressDialog.setTitle("QQ会员");
                        progressDialog.setIcon(R.drawable.vip);
                        progressDialog.setMessage("正在注册QQ会员，请等待...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();

                        /***********文件方式存储注册信息**********/
                        //动态请求写存储权限
//                        requestPermissions(new String[]{"android.permission" +
//                                ".WRITE_EXTERNAL_STORAGE"}, 1);
                        /***********数据库方式存储注册信息**********/
                        //以事务方式处理数据库
                        database.beginTransaction();    //开启事务
                        ContentValues contentValues = new ContentValues();    //打包数据库数据
                        contentValues.put("account", editText_account.getText().toString());
                        contentValues.put("password", editText_password.getText().toString());
                        contentValues.put("style", editText_style.getText().toString());
                        contentValues.put("sex", spinner_sex.getSelectedItem().toString());
                        //插入数据
                        try {
                            //execSQL方法插入数据
//                            database.execSQL("INSERT INTO Message (account,password,style,sex)
//                            VALUES (?,?,?,?)",
//                                    new Object[]{editText_account.getText().toString(),
//                                            editText_password.getText().toString(),
//                                            editText_style.getText().toString(),
//                                            spinner_sex.getSelectedItem().toString()});
                            //insert方法插入数据
                            if (database.insert("Message", null, contentValues) != -1) {
                                Filestate = true;
                            } else {
                                Filestate = false;
                            }
                            database.setTransactionSuccessful();    //提交实务
                        } catch (Exception e) {
                            Toast.makeText(Register.this, "数据库存储异常", Toast.LENGTH_SHORT).show();
                        } finally {
                            database.endTransaction();      //结束实务
                            database.close();
                            contentValues.clear();
                        }

                        //进度条对话框开启注册流程(模拟注册耗时)
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                while (progress < 100) {
                                    progress++;
                                    try {
                                        sleep(50);
                                        progressDialog.setProgress(progress);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                progress = 0;     //进度值置位
                                progressDialog.dismiss();    //关闭进度对话框
                                //打包注册信息回LoginActivity
                                bundle = new Bundle();
                                bundle.putString("account", editText_account.getText().toString());
                                bundle.putString("password",
                                        editText_password.getText().toString());
                                intent.putExtra("info", bundle);
                                setResult(1, intent);
                                finish();

                                Looper.prepare();   //子线程不会创建Looper，要手动加入消息队列（makeText方法中会创建Handle）
                                if (Filestate) {
                                    Toast.makeText(getApplicationContext(), "注册成功！",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "注册失败！",
                                            Toast.LENGTH_SHORT).show();
                                }
                                Looper.loop();      //开始工作，之后的代码不会执行
                            }
                        }.start();
                    }
                } else if (!editText_password.getText().toString().equals(editText_confirm.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "注册内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*****自定义对话框*******/
                Reg_style_Dialog regStyleDialog = new Reg_style_Dialog(Register.this,
                        new Reg_style_Dialog.Reg_style_Dialog_listener() {
                            @Override
                            public void getstyle(String[] style) {  //7重写getstyle（）方法
                                editText_style.setText("");
                                String Style = "";
                                for (int i = 0; i < style.length; i++) {
                                    Style += style[i] + " ";
                                }
                                editText_style.setText(Style);
                            }
                        });
                regStyleDialog.show();
            }
        });
    }
}
