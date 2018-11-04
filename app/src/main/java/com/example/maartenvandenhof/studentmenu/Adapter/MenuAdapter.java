package com.example.maartenvandenhof.studentmenu.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maartenvandenhof.studentmenu.Menu;
import com.example.maartenvandenhof.studentmenu.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {

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

    }

    @Override
    public int getItemCount() {
        return menuData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView menuTitle, menuDescription, menuPrice;


        public myViewHolder(View itemView){
            super(itemView);
            menuTitle = itemView.findViewById(R.id.menuListViewText);
            menuDescription = itemView.findViewById(R.id.menuDescription);
            menuPrice = itemView.findViewById(R.id.menuPrice);
        }
    }
}
