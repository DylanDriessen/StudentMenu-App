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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.maartenvandenhof.studentmenu.Activities.MainActivity;
import com.example.maartenvandenhof.studentmenu.Adapter.MenuAdapter;
import com.example.maartenvandenhof.studentmenu.R;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class MenuListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        RecyclerView lv = (RecyclerView)view.findViewById(R.id.menuList);




        MenuAdapter adapter = new MenuAdapter(getContext(), ((MainActivity)getActivity()).menuList);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;


    }



}
