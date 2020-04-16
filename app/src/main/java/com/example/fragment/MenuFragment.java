package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {
    private View view;
    private TextView textView1, textView2, textView3;
    private ContentFragment contentFragment;
    private FriendFragment friendFragment;
    private static String CLICK_TAG = "";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_menu, container, false);
        if (view != null) {
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            textView3 = view.findViewById(R.id.textView3);
        }

        textView1.setText(getResources().getStringArray(R.array.menu)[0]);
        textView2.setText(getResources().getStringArray(R.array.menu)[1]);
        textView3.setText(getResources().getStringArray(R.array.menu)[2]);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在触发监听时替换Fragment关联对象
                friendFragment = new FriendFragment();
                ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content, friendFragment).commit();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentFragment = new ContentFragment();
                ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content, contentFragment).commit();
                //设置标签用于ContentFragment判断内容选择（图片内容）
                CLICK_TAG="PIC1";
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentFragment = new ContentFragment();
                ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content, contentFragment).commit();
                CLICK_TAG="PIC2";
//                contentFragment = (ContentFragment) ((MainActivity) getActivity()).getSupportFragmentManager().findFragmentById(R.id.food);
//                contentFragment.setImage(((MainActivity) getActivity()).getImageid()[2]);
            }
        });

        return view;
    }

    public static String getCLICK_TAG() {
        return CLICK_TAG;
    }
}
