package com.example.maartenvandenhof.studentmenu.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maartenvandenhof.studentmenu.Activities.MainActivity;
import com.example.maartenvandenhof.studentmenu.R;

public class GoToAddIngredientFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add Ingredient");
        return inflater.inflate(R.layout.fragment_add_ingredient, container, false);
    }
}
