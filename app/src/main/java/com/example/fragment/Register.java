package com.example.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private Button button;
    private Button button_dialog;
    private EditText editText_style,editText_account,editText_password,editText_confirm;
    private String style;
    private Intent intent;
    private Bundle bundle;

    private int progress=0;
    private boolean log[]=new boolean[]{false,false,false,false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button=(Button)findViewById(R.id.button3);
        button_dialog=(Button)findViewById(R.id.button_dialog);
        editText_style=(EditText)findViewById(R.id.editText6);
        editText_account=findViewById(R.id.editText3);
        editText_confirm=findViewById(R.id.editText5);
        editText_password=findViewById(R.id.editText4);
        intent=getIntent();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText_style.getText().toString().equals("")&&!editText_account.getText().toString().equals("")&&!editText_password.getText().toString().equals("")
                        &&!editText_confirm.getText().toString().equals("")&&editText_password.getText().toString().equals(editText_confirm.getText().toString())){
                    final ProgressDialog progressDialog=new ProgressDialog(Register.this);
                    progressDialog.setTitle("QQ会员");
                    progressDialog.setIcon(R.drawable.vip);
                    progressDialog.setMessage("正在注册QQ会员，请等待...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.show();
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            while(progress<100){
                                progress++;
                                try {
                                    sleep(50);
                                    progressDialog.setProgress(progress);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            progress=0;     //进度值置位
                            progressDialog.dismiss();    //关闭进度对话框
                            bundle=new Bundle();
                            bundle.putString("account",editText_account.getText().toString());
                            bundle.putString("password",editText_password.getText().toString());
                            intent.putExtra("info",bundle);
                            setResult(1,intent);
                            finish();
                            Looper.prepare();   //子线程不会创建Looper，要手动加入消息队列（makeText方法中会创建Handle）
                            Toast.makeText(getApplicationContext(),"注册成功！", Toast.LENGTH_SHORT).show();
                            Looper.loop();      //开始工作，之后的代码不会执行
                        }
                    }.start();
                }else if(!editText_password.getText().toString().equals(editText_confirm.getText().toString())){
                    Toast.makeText(getApplicationContext(),"密码不一致", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"注册内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*****单选对话框*******/
//                new AlertDialog.Builder(register.this).setTitle("个性签名")
//                        .setIcon(getResources().getDrawable(R.drawable.myicon)).setSingleChoiceItems(getResources().getStringArray(R.array.style), -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        style=getResources().getStringArray(R.array.style)[i];
//                    }
//                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        editText.setText(style);
//                    }
//                }).setNegativeButton("退出",null).create().show();
                /*****多选对话框*******/
//                new AlertDialog.Builder(register.this).setTitle("个性签名")
//                        .setIcon(R.drawable.myicon).setMultiChoiceItems(getResources().getStringArray(R.array.style), log, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                        log[i]=b;
//                    }
//                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        editText.setText("");
//                        for(int x=0;x<getResources().getStringArray(R.array.style).length;x++){
//                            if(log[x]){
//                                editText.append(getResources().getStringArray(R.array.style)[x]+",");
//                            }
//                        }
//                    }
//                }).setNegativeButton("退出",null).create().show();
                /*****自定义对话框*******/
                Reg_style_Dialog regStyleDialog=new Reg_style_Dialog(Register.this, new Reg_style_Dialog.Reg_style_Dialog_listener() {
                    @Override
                    public void getstyle(String[] style) {  //7重写getstyle（）方法
                        editText_style.setText("");
                        String Style="";
                        for(int i=0;i<style.length;i++){
                            Style+=style[i]+" ";
                        }
                        editText_style.setText(Style);
                    }
                });
                regStyleDialog.show();
            }
        });
    }
}
