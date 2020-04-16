package com.example.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Reg_style_Dialog extends AlertDialog {
    private Button button[]=new Button[2];
    private CheckBox checkBox[]=new CheckBox[4];
    private static String style[]=new String[]{"","","",""};   //个性签名数据变量
    private Reg_style_Dialog_listener regStyleDialogListener;   //3定义内部监听器对象变量

    public Reg_style_Dialog(Context context, Reg_style_Dialog_listener regStyleDialogListener) { //4定义带监听器参数的构造方法
        super(context);
        this.regStyleDialogListener=regStyleDialogListener;     //5传递外部监听器对象给内部监听器对象变量
    }

    public interface Reg_style_Dialog_listener{     //1自定义内部类接口
        void getstyle(String style[]);      //2声明监听方法，用来获取个性签名的数据
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);      //删除窗口标题
        setContentView(R.layout.layout_regeister_mydialog);

        button[0]=(Button)findViewById(R.id.button4);   //退出按钮
        button[1]=(Button)findViewById(R.id.button5);   //确定按钮
        checkBox[0]=(CheckBox)findViewById(R.id.checkBox); //选项
        checkBox[1]=(CheckBox)findViewById(R.id.checkBox2);
        checkBox[2]=(CheckBox)findViewById(R.id.checkBox3);
        checkBox[3]=(CheckBox)findViewById(R.id.checkBox4);

        //判断checkbox是否在上一次运行时被选择上，如有则将checkbox设为true
        for(int i=0;i<checkBox.length;i++){
            if(!style[i].equals("")){
                checkBox[i].setChecked(true);
            }
        }
        //给checkbox设置监听器
        for(int i=0;i<checkBox.length;i++){
            checkBox[i].setOnCheckedChangeListener(new Style_Dialog());
        }

        button[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();      //退出对话框
            }
        });

        button[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regStyleDialogListener.getstyle(style);  //6在按钮监听方法中调用内部类接口方法实现获取style数据
                dismiss();
            }
        });
    }
    //通过内部类定义监听器
    public class Style_Dialog implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.checkBox:
                    if(isChecked)
                        style[0]=checkBox[0].getText().toString();
                    else
                        style[0]="";
                    break;
                case R.id.checkBox2:
                    if(isChecked)
                        style[1]=checkBox[1].getText().toString();
                    else
                        style[1]="";
                    break;
                case R.id.checkBox3:
                    if(isChecked)
                        style[2]=checkBox[2].getText().toString();
                    else
                        style[2]="";
                    break;
                case R.id.checkBox4:
                    if(isChecked)
                        style[3]=checkBox[3].getText().toString();
                    else
                        style[3]="";
                    break;
            }
        }
    }
}
