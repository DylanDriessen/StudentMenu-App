package com.example.maartenvandenhof.studentmenu.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.Activities.MainActivity;
import com.example.maartenvandenhof.studentmenu.Adapter.MenuAdapter;
import com.example.maartenvandenhof.studentmenu.Menu;
import com.example.maartenvandenhof.studentmenu.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.zip.CheckedOutputStream;


public class WeekMenuListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list_week, container, false);

        double price = getArguments().getDouble("price");
        ArrayList<RecyclerView> views = new ArrayList<>();
        ArrayList<Menu> menuList = new ArrayList<>();
        ArrayList<Menu> menuListActivity = ((MainActivity)getActivity()).menuList;

        views.add((RecyclerView)view.findViewById(R.id.day1));
        views.add((RecyclerView)view.findViewById(R.id.day2));
        views.add((RecyclerView)view.findViewById(R.id.day3));
        views.add((RecyclerView)view.findViewById(R.id.day4));
        views.add((RecyclerView)view.findViewById(R.id.day5));

        //Allergies
        ArrayList<String> allergies = ((MainActivity) getActivity()).allergiesListWeek;
        ArrayList<Menu> adjustedList = new ArrayList<>();

        for (Menu m : menuListActivity){
            if(m.getPrice() <= price && Collections.disjoint(m.getAllergies(), allergies)){
                adjustedList.add(m);
            }
        }
        ((MainActivity) getActivity()).allergiesList = new ArrayList<String>();
        menuListActivity = adjustedList;


        //Searching for menu's
        if (sum(menuListActivity) <= price){
            for (int i = 0; i <5; i++){
                int random = (int)(Math.random() * (menuListActivity.size()) + 0);
                menuList.add(menuListActivity.get(random));
            }
        } else {
            double CountPrice = 0;
            while (CountPrice < price) {
                int random = (int)(Math.random() * (menuListActivity.size()) + 0);
                Menu m = menuListActivity.get(random);
                menuList.add(m);
                CountPrice = CountPrice + m.getPrice();
            }
        }


        //Assigning a menu for each day
        if (!((MainActivity)getActivity()).weekMenus.isEmpty()){
            menuList = ((MainActivity)getActivity()).weekMenus;
        } else {
            ((MainActivity)getActivity()).weekMenus = menuList;
        }
        for (int i = 0; i <5; i++){
            ArrayList<Menu> singelList = new ArrayList<>();
            singelList.add(menuList.get(i));
            MenuAdapter adapter = new MenuAdapter(getContext(), singelList);
            views.get(i).setAdapter(adapter);
            views.get(i).setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;


    }

    private double sum(ArrayList<Menu> list) {
        double sum = 0;

        for (Menu i : list)
            sum = sum + i.getPrice();

        return sum;
    }

}
