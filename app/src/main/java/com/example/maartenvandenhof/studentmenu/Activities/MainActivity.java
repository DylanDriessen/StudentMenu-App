package com.example.maartenvandenhof.studentmenu.Activities;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.Fragments.HomeScreenFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.IngredientListFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuDisplayFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuListFragment;
import com.example.maartenvandenhof.studentmenu.*;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuPriceSearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public ArrayList<Menu> menuList;
    public ArrayList<Ingredient> ingredientList;
    private Menu selectedMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationSlider);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeScreenFragment()).commit();


        menuList = new ArrayList<>();
        ingredientList = new ArrayList<>();

        //Dummy menu's aanmaken
        Ingredient wortel = new Ingredient("Wortel", 5, "Komt van onder de grond, is ne plant.");
        Ingredient selder = new Ingredient("Selder", 1, "Kunt ge soep van maken.");
        Ingredient patat = new Ingredient("Patat", 6, "Perfect voor puree.");
        Ingredient sla = new Ingredient("Sla", 3, "Alleen voor konijnen.");

        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        ArrayList<Ingredient> ingredients2 = new ArrayList<>();
        ingredientList.add(wortel);
        ingredientList.add(selder);
        ingredientList.add(patat);
        ingredientList.add(sla);

        ingredients1.add(wortel);
        ingredients1.add(patat);
        ingredients2.add(sla);
        ingredients2.add(selder);

        Menu menu1 = new Menu("Patat me wortelen", ingredients1);
        Menu menu2 = new Menu("Selder me sla", ingredients2);
        menu1.setDescription("Wa wortelen me een paar goeie patatten, kan ni slecht zijn");
        menu2.setDescription("Selder en sla zijn fucking gezond, gewoon eten");

        menuList.add(menu1);
        menuList.add(menu2);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return false;
    }

    //Menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Toast notImplemented = Toast.makeText(this, "Not yet implemented", Toast.LENGTH_LONG);
        switch (menuItem.getItemId()) {
            case R.id.ml:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).commit();
                break;
            case R.id.db:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeScreenFragment()).commit();
                break;
            case R.id.il:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IngredientListFragment()).commit();
                break;
            case R.id.sMenu:
                notImplemented.show();
                break;
            case R.id.sIngredient:
                notImplemented.show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Search Menu Button
    public void searchMenuPrice(View v){
        SearchView priceSearch = findViewById(R.id.searchPriceField);
        String text = "Please fill in a number";

        if (priceSearch.getQuery() != null){
            CharSequence query = priceSearch.getQuery();
            String temp = query.toString();
            try{
                double price = Double.parseDouble(temp);
                MenuPriceSearchFragment fragment = new MenuPriceSearchFragment();

                Bundle args = new Bundle();
                args.putDouble("price", price);
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            } catch (NumberFormatException e){
                Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    public void menuDescription(String menuTitle, String menuPrice, String menuRecipe, ArrayList<String> ingredientList){
        MenuDisplayFragment fragment = new MenuDisplayFragment();
        Bundle args = new Bundle();
        args.putString("MenuTitle", menuTitle);
        args.putString("MenuPrice", menuPrice);
        args.putString("MenuRecipe", menuRecipe);
        args.putStringArrayList("IngredientList", ingredientList);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
