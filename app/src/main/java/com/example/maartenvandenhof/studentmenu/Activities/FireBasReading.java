package com.example.maartenvandenhof.studentmenu.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.maartenvandenhof.studentmenu.Ingredient;
import com.example.maartenvandenhof.studentmenu.Menu;
import com.example.maartenvandenhof.studentmenu.R;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBasReading extends AppCompatActivity {

    private static final String TAG = "ViewDataBase";
    private FirebaseDatabase database;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            showData(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }

    private void showData(DataSnapshot snapshot){
        for(DataSnapshot ds: snapshot.getChildren()) {
            Menu menu = new Menu();
            menu.setName(ds.getValue(Menu.class).getName());
            menu.setIngredient(ds.getValue(Menu.class).getIngredientsString());
            menu.setDescription(ds.getValue(Menu.class).getDescription());
            menu.setRecipe(ds.getValue(Menu.class).getRecipe());
            menu.setPrice(ds.getValue(Menu.class).getPrice());
            menu.setRating(ds.getValue(Menu.class).getRating());


            Log.d(TAG, menu.getName());

        }
    }






}


