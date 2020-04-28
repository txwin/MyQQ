package com.example.fragment;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPStorage {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    //写最后一次用户登录信息
    public boolean write_user_SP(Context context, String filename, String account,
                                 String password) {
        sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("account", account);
        editor.putString("password", password);

        return editor.commit();
    }

    //写配置信息
    public boolean writeSP(Context context, String filename, boolean remember_password,
                           boolean auto_login) {
        sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("remember_password", remember_password);
        editor.putBoolean("auto_login", auto_login);

        return editor.commit();
    }

    //读配置信息
    public Map<String, ?> readSP(Context context, String filename) {
        sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
