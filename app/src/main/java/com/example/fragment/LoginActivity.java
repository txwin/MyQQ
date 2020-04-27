package com.example.fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "---";
    private Button button,button2;
    private EditText editText_account,editText_password;
    private CheckBox checkBox, checkBox2;
    private SPStorage spStorage = new SPStorage();
    private Map<String, ?> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=findViewById(R.id.button2);
        button2=findViewById(R.id.button);
        editText_account=findViewById(R.id.editText);
        editText_password=findViewById(R.id.editText2);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);

        //检查应用程序配置信息并更新状态
        map = spStorage.readSP(LoginActivity.this, "Configuration");
        if (!map.isEmpty()) {
            checkBox.setChecked((Boolean) map.get("remember_password"));
            checkBox2.setChecked((Boolean) map.get("auto_login"));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Register.class);
                startActivityForResult(intent,1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                //保存配置信息
                if (spStorage.writeSP(LoginActivity.this, "Configuration", checkBox.isChecked(),
                        checkBox2.isChecked())) {
                    Log.e(TAG, "配置信息保存成功");
                } else {
                    Log.e(TAG, "配置信息保存失败");
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            editText_account.setText(data.getBundleExtra("info").getString("account"));
            editText_password.setText(data.getBundleExtra("info").getString("password"));
        }
    }
}
