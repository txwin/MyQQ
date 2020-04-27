package com.example.fragment;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileStorage {
    public boolean writeFile(Context context, String filename, String data) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filename,
                    Context.MODE_APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(data);
            writer.flush();
            writer.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Map<String, Object>> readFile(Context context, String filename) {
        String[] data = new String[2];
        String message;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((message = reader.readLine()) != null) {
                data = message.split("&");
                Map<String, Object> map_data = new HashMap<String, Object>();
                map_data.put("account", data[0]);
                map_data.put("password", data[1]);
                list.add(map_data);
            }
            reader.close();
            fileInputStream.close();
        } catch (IOException e) {
            return null;    //文件不存在时或读出失败时返回null
        }

        return list;
    }
}
