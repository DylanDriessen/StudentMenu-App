package com.example.maartenvandenhof.studentmenu.Screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.maartenvandenhof.studentmenu.*;

import java.util.ArrayList;

public class MenuListScreen extends AppCompatActivity {
    protected ArrayList<Menu> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public MenuListScreen(){
        this.menuList = new ArrayList<>();

    }
}
