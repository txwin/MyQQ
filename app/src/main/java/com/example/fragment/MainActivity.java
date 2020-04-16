package com.example.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private FragmentTransaction fragmentTransaction;
    private int[] imageid = {R.drawable.find, R.drawable.my};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentFragment contentFragment=new ContentFragment();
        FriendFragment friendFragment=new FriendFragment();
        MenuFragment menuFragment=new MenuFragment();
        fragmentTransaction=getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content, friendFragment);
        fragmentTransaction.replace(R.id.menu,menuFragment);
        fragmentTransaction.commit();
    }

    public int[] getImageid() {
        return imageid;
    }
}
