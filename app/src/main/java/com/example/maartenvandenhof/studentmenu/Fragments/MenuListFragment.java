package com.example.maartenvandenhof.studentmenu.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.maartenvandenhof.studentmenu.Activities.MainActivity;
import com.example.maartenvandenhof.studentmenu.R;

import java.util.ArrayList;

public class MenuListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ArrayList<String> menuNames = ((MainActivity)getActivity()).getMenuNamesList();
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        ListView lv = (ListView)view.findViewById(R.id.menuList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.menu_list_view,R.id.menuListViewText, menuNames);
        lv.setAdapter(arrayAdapter);
        return view;
    }
}
