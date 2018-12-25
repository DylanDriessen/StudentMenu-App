package com.example.maartenvandenhof.studentmenu.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddIngredientFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuFragmentRecipeFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.HomeScreenFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.IngredientListFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuDisplayFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuListFragment;
import com.example.maartenvandenhof.studentmenu.*;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuPriceSearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Main Activity";
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
        menu1.setRecipe("Kook u patatten, schil u wortelen en smijt ze dan bij elkaar." +
                "Laat nog effe koken en eet dan op.");
        menu2.setRecipe("Heel eenvoudig recept, kuis u selder, kuis u sla en gooi het in ne schone pot, dan is u eten klaar");

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

    //Show Menu description
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

    //Add Ingredient
    public void goToAddIngredient(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GoToAddIngredientFragment()).commit();
    }

    public void addIngredient(View v){
        EditText name = findViewById(R.id.addIngredientName);
        EditText price = findViewById(R.id.addIngredientPrice);
        EditText desc = findViewById(R.id.addIngredientDescription);

        if (!name.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()){
            try{
                double priceDouble = Double.parseDouble(price.getText().toString());

                //Make ingredient
                Ingredient i = new Ingredient(name.getText().toString(), priceDouble, desc.getText().toString());

                //Check if it already exists
                boolean exists = false;
                for (Ingredient in:ingredientList){
                    if (in.getName().equals(name.getText().toString().trim())){
                        exists = true;
                    }
                }

                //Add Ingredient
                if (!exists){
                    ingredientList.add(i);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IngredientListFragment()).commit();
                } else {
                    Toast t = Toast.makeText(this, "Ingredient already exists", Toast.LENGTH_SHORT);
                    t.show();
                }
            } catch (NumberFormatException e){
                Toast t = Toast.makeText(this, "Please fill in a number for price", Toast.LENGTH_SHORT);
                t.show();
            }
        } else {
            Toast t = Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    //Add Menu
    public void goToAddMenu(View v){
        Bundle args = new Bundle();
        ArrayList<String> ingredientNames = new ArrayList<>();
        for (Ingredient i:ingredientList){
            ingredientNames.add(i.getName());
        }
        args.putStringArrayList("IngredientList", ingredientNames);
        GoToAddMenuFragment fragment = new GoToAddMenuFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void addMenu(View v){
        EditText name = findViewById(R.id.addMenuName);
        EditText desc = findViewById(R.id.addMenuDescription);
        LinearLayout ingredients = findViewById(R.id.addIngredientToMenu);

        if (!name.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()){

                ArrayList<Ingredient> ingredientMenuList = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                ArrayList<Integer> priceList = new ArrayList<>();

                for( int i = 0; i < ingredients.getChildCount(); i++) {
                    if (ingredients.getChildAt(i) instanceof TextView) {
                        if (i%2==0){
                            priceList.add(Integer.parseInt(((TextView) ingredients.getChildAt(i)).getText().toString()));
                        } else {
                            names.add(((TextView) ingredients.getChildAt(i)).getText().toString());
                        }
                    }
                }


                for (int i = 0; i<names.size(); i++){
                    boolean exists = false;
                    Ingredient existing = null;
                    Ingredient newIng = new Ingredient(names.get(i), priceList.get(i));
                    for (Ingredient ing:ingredientList){
                        if (ing.getName().equals(newIng.getName())){
                            exists = true;
                            existing = ing;
                        }
                    }
                    if (!exists){
                        ingredientMenuList.add(newIng);
                    } else {
                        ingredientMenuList.add(existing);
                    }
                }

                Menu m = new Menu(name.getText().toString(), ingredientMenuList, desc.getText().toString());
                menuList.add(m);
                Bundle args = new Bundle();
                args.putString("menuTitle", m.getName());
                GoToAddMenuFragmentRecipeFragment fragmentRecipe = new GoToAddMenuFragmentRecipeFragment();
                fragmentRecipe.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentRecipe).commit();
        }
    }

    public void addMenuWithDescription(View v){
        EditText recipe = findViewById(R.id.addMenuRecipe);
        TextView menuTitle = findViewById(R.id.addMenuTitle);

        for (Menu m:menuList){
            if (m.getName().equals(menuTitle.getText().toString())){
                m.setRecipe(recipe.getText().toString());
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).commit();
    }
}
