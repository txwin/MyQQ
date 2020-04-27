package com.example.fragment;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPStorage {
    SharedPreferences sp;

    //写配置信息
    public boolean writeSP(Context context, String filename, boolean remember_password,
                           boolean auto_login) {
        sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
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
