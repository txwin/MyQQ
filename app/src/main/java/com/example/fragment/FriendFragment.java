package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendFragment extends Fragment {
    private ListView listView;
    private View view;
    final int[] imageid = {R.drawable.sun, R.drawable.zhu, R.drawable.tang, R.drawable.sha, R.drawable.niu, R.drawable.zhi,
            R.drawable.zi};

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_listview,container,false);
        if(view!=null){
            listView = (ListView) view.findViewById(R.id.listview);
        }

        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return imageid.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                MyHolder myHolder = null;
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.layout_listview_style, null);
                    myHolder = new MyHolder();
                    myHolder.textView = (TextView) convertView.findViewById(R.id.textView);       //获取转换后的布局文件中的组件
                    myHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
                    myHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                    convertView.setTag(myHolder);
                } else {
                    myHolder = (MyHolder) convertView.getTag();
                }

                //向组件装载数据
                myHolder.imageView.setImageResource(imageid[position]);
                myHolder.textView.setText(getResources().getTextArray(R.array.name)[position]);
                myHolder.textView2.setText(getResources().getTextArray(R.array.work)[position]);
                return convertView;
            }
        };
        listView.setAdapter(baseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText((MainActivity)getActivity(),getResources().getTextArray(R.array.name)[position],Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public class MyHolder {
        TextView textView, textView2;
        ImageView imageView;
    }
}
