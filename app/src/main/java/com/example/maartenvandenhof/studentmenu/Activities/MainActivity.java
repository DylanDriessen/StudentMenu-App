package com.example.maartenvandenhof.studentmenu.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.Fragments.HomeScreenFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuListFragment;
import com.example.maartenvandenhof.studentmenu.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public ArrayList<Menu> menuList;

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

        //Dummy menu's aanmaken
        Ingredient wortel = new Ingredient("wortel", 5);
        Ingredient selder = new Ingredient("selder", 5);
        Ingredient patat = new Ingredient("patat", 5);
        Ingredient sla = new Ingredient("sla", 5);

        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        ArrayList<Ingredient> ingredients2 = new ArrayList<>();

        ingredients1.add(wortel);
        ingredients1.add(patat);
        ingredients2.add(sla);
        ingredients2.add(selder);

        Menu menu1 = new Menu("Patat me wortelen", ingredients1);
        Menu menu2 = new Menu("Selder me sla", ingredients2);

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
                notImplemented.show();
                break;
            case R.id.share:
                notImplemented.show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<String> getMenuNamesList(){
        ArrayList<String> menuNames = new ArrayList<>();
        for(Menu m : menuList){
            menuNames.add(m.getName());
        }
        return menuNames;
    }
}