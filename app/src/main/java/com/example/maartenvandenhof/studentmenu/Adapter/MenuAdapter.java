package com.example.maartenvandenhof.studentmenu.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.maartenvandenhof.studentmenu.Activities.MainActivity;
import com.example.maartenvandenhof.studentmenu.Menu;
import com.example.maartenvandenhof.studentmenu.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    Context myContext;
    List<Menu> menuData;




    public MenuAdapter(Context myContext, List<Menu> menuData){
        this.myContext = myContext;
        this.menuData = menuData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(myContext);
        View v = inflater.inflate(R.layout.menu_list_view, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((myViewHolder) viewHolder).menuTitle.setText(menuData.get(i).getName());
        ((myViewHolder) viewHolder).menuDescription.setText(menuData.get(i).getDescription());
        ((myViewHolder) viewHolder).menuPrice.setText("€" +  menuData.get(i).getPrice());
        ((myViewHolder) viewHolder).itemMenu = menuData.get(i);

        if (menuData.get(i).getRating() == 0.0){
            ((myViewHolder) viewHolder).ratingBar.setVisibility(View.INVISIBLE);
        } else {
            ((myViewHolder) viewHolder).ratingBar.setVisibility(View.VISIBLE);
            ((myViewHolder) viewHolder).ratingBar.setRating(menuData.get(i).getRating());
        }
    }

    @Override
    public int getItemCount() {

        return menuData.size();
    }

    @Override
    public void onClick(View view) {

    }


    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView menuTitle, menuDescription, menuPrice;
        Menu itemMenu = new Menu();
        RatingBar ratingBar;

        public myViewHolder(View itemView){
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            menuTitle = itemView.findViewById(R.id.menuListViewText);
            menuDescription = itemView.findViewById(R.id.menuDescription);
            menuPrice = itemView.findViewById(R.id.menuPrice);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v){
                    ((MainActivity)myContext).menuDescription(menuTitle.getText().toString(), menuPrice.getText().toString(), itemMenu.getRecipe(), itemMenu.getIngredientsString());
                }
            });
        }
    }
}
