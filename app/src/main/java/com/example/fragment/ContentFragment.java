package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContentFragment extends Fragment {
    private ImageView imageView;
    private TextView textView;
    private View view;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_message,container,false);
        if(view!=null){
            imageView=view.findViewById(R.id.imageView);
            textView = view.findViewById(R.id.textView4);
        }

        //激活ContentFragment后判断内容
        switch (MenuFragment.getCLICK_TAG()){
            case "PIC1":
            default:
                setImage(((MainActivity) getActivity()).getImageid()[0]);
                textView.setText(getResources().getStringArray(R.array.data)[0]);
                break;
            case "PIC2":
                setImage(((MainActivity) getActivity()).getImageid()[1]);
                textView.setText(getResources().getStringArray(R.array.data)[1]);
                break;
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (MenuFragment.getCLICK_TAG()){
                    case "PIC1":
                    default:
                        Toast.makeText((MainActivity) getActivity(),
                                getResources().getStringArray(R.array.menu)[1],
                                Toast.LENGTH_SHORT).show();
                        break;
                    case "PIC2":
                        Toast.makeText((MainActivity) getActivity(), getResources().getStringArray(R.array.menu)[2], Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        return view;
    }

    public void setImage(int id){
        imageView.setImageResource(id);
    }
}
