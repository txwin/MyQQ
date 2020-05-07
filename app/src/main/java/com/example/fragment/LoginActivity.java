package com.example.fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "---";
    private Button button, button2;
    private EditText editText_account, editText_password;
    private CheckBox checkBox, checkBox2;
    private SPStorage spStorage = new SPStorage();
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase database;
    private Cursor cursor;
    private Map<String, ?> map;
    private FileStorage fileStorage = new FileStorage();
    private boolean check = false;
    private boolean remember_password, auto_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = findViewById(R.id.button2);
        button2 = findViewById(R.id.button);
        editText_account = findViewById(R.id.editText);
        editText_password = findViewById(R.id.editText2);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);

        //检查应用程序配置信息并更新状态
        map = spStorage.readSP(LoginActivity.this, "Configuration");
        if (!map.isEmpty()) {
            remember_password = (Boolean) map.get("remember_password");
            auto_login = (Boolean) map.get("auto_login");
            checkBox.setChecked(remember_password);
            checkBox2.setChecked(auto_login);
            //当自动登录选项被选择，则自动登陆
            if (remember_password && !auto_login) {
                map = spStorage.readSP(LoginActivity.this, "Current_password");
                editText_account.setText((String) map.get("account"));
                editText_password.setText((String) map.get("password"));
            } else if (auto_login) {
                map = spStorage.readSP(LoginActivity.this, "Current_password");
                editText_account.setText((String) map.get("account"));
                editText_password.setText((String) map.get("password"));
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当选上自动登录，记住密码将一起被选上
                if (checkBox2.isChecked()) {
                    checkBox.setChecked(true);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivityForResult(intent, 1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果记住密码为选上则保存最后一次账号密码信息
                if (checkBox.isChecked()) {
                    spStorage.write_user_SP(LoginActivity.this, "Current_password",
                            editText_account.getText().toString(),
                            editText_password.getText().toString());
                }
                //保存配置信息
                if (spStorage.writeSP(LoginActivity.this, "Configuration", checkBox.isChecked(),
                        checkBox2.isChecked())) {
                    /***********文件读取方式检查账号密码**********/
                    //判断输入的账号是否存在
//                    List<Map<String, Object>> list = fileStorage.readFile(LoginActivity.this,
//                            "data" +
//                                    ".txt", false);
//                    //判断文件是否存在，通过ReadFile()方法的返回值获取
//                    if (list != null) {
//                        for (Map<String, Object> map : list) {
//                            if (map.get("account").equals(editText_account.getText().toString()
//                            ) && map.get("password").equals(editText_password.getText()
//                            .toString())) {
//                                Intent intent = new Intent(LoginActivity.this, MainActivity
//                                .class);
//                                startActivity(intent);
//                                finish();
//                                check = true;
//                                break;
//                            } else {
//                                check = false;
//                            }
//                        }
//                        //判断账号密码是否与文件中的数据匹配
//                        if (!check) {
//                            Toast.makeText(LoginActivity.this, "账号和密码错误", Toast.LENGTH_LONG)
//                            .show();
//                            //当密码账号错误时，如果选上了自动登录则取消，并重写Configuration配置文件
////                            checkBox2.setChecked(false);
//                            spStorage.writeSP(LoginActivity.this, "Configuration",
//                                    checkBox.isChecked(), false);
//                        }
//                    } else {
//                        Toast.makeText(LoginActivity.this, "不存在账号和密码", Toast.LENGTH_LONG).show();
//                    }

                    /***********数据库读取方式检查账号密码**********/
                    mySQLiteHelper = new MySQLiteHelper(LoginActivity.this, "data.db", null, 1);  //获取数据库
                    database = mySQLiteHelper.getReadableDatabase();
                    cursor = database.rawQuery("select * from Message", null);      //查询数据库中所有数据
                    cursor.moveToFirst();       //将Cursor对象的内部指针指向第一行数据
                    //从Cursor对象中取出查询到的数据
                    do {
                        if (cursor.getString(0).equals(editText_account.getText().toString()) && cursor.getString(1).equals(editText_password.getText().toString())) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            check = true;
                            break;
                        } else {
                            check = false;
                        }
                    } while (cursor.moveToNext());
                    cursor.close();

                    if (!check) {
                        Toast.makeText(LoginActivity.this, "账号和密码错误", Toast.LENGTH_LONG).show();
                        //当密码账号错误时，如果选上了自动登录则取消，并重写Configuration配置文件
//                            checkBox2.setChecked(false);
                        spStorage.writeSP(LoginActivity.this, "Configuration",
                                checkBox.isChecked(), false);
                    }
//                    cursor=database.rawQuery("select * from Message where sex=? and _id=?", new
//                    String[]{"女","12"});
                } else {
                    Toast.makeText(LoginActivity.this, "配置信息保存失败", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            editText_account.setText(data.getBundleExtra("info").getString("account"));
            editText_password.setText(data.getBundleExtra("info").getString("password"));
        }
    }
}
