package com.example.maartenvandenhof.studentmenu.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddIngredientFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuIngredientFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuRecipeFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuPictureFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoogleMapsFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.HomeScreenFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.IngredientListFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuDisplayFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuListFragment;
import com.example.maartenvandenhof.studentmenu.*;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuOrdendListFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuPriceSearchFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.PriceOrdendListFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.WeekMenuListFragment;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Main Activity";
    private static final int PICK_IMAGE_ID = 234 ;
    private DrawerLayout drawer;
    public ArrayList<Menu> menuList;
    public ArrayList<Ingredient> ingredientList;
    public RatingBar ratingBar;
    public ImageView imageToUpLoad;
    public Button bUploadImage;
    public String mFilePath;
    public String imgpath,storedpath;
    public SharedPreferences sp;
    public ArrayList<Menu> sortedList;
    public ArrayList<Menu> sortedPriceList;
    public ArrayList<String> allergiesList;
    public ArrayList<String> allergiesListWeek;
    public ArrayList<Menu> weekMenus;


    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationSlider);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeScreenFragment()).commit();


        menuList = new ArrayList<>();
        ingredientList = new ArrayList<>();
        allergiesList = new ArrayList<>();
        weekMenus = new ArrayList<>();

        //Dummy menu's aanmaken
        Ingredient wortel = new Ingredient("Wortel", 5, "Komt van onder de grond, is ne plant.");
        Ingredient selder = new Ingredient("Selder", 1, "Kunt ge soep van maken.");
        Ingredient patat = new Ingredient("Patat", 6, "Perfect voor puree.");
        Ingredient sla = new Ingredient("Sla", 3, "Alleen voor konijnen.");

        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        ArrayList<Ingredient> ingredients2 = new ArrayList<>();
        patat.addAllergy("Gluten");
        selder.addAllergy("Celery");
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

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getSupportFragmentManager().popBackStack();
        }
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).addToBackStack(null).commit();
                break;
            case R.id.db:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeScreenFragment()).addToBackStack(null).commit();
                break;
            case R.id.il:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IngredientListFragment()).addToBackStack(null).commit();
                break;
            case R.id.gm:
                //showMap();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GoogleMapsFragment()).addToBackStack(null).commit();
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
                price = round(price, 2);
                MenuPriceSearchFragment fragment = new MenuPriceSearchFragment();

                Bundle args = new Bundle();
                args.putDouble("price", price);
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

            } catch (NumberFormatException e){
                Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    //Search Week Menu Button
    public void searchWeekMenu(View v){
        SearchView priceSearch = findViewById(R.id.searchPriceField);
        String text = "Please fill in a number";
        weekMenus = new ArrayList<>();

        if (priceSearch.getQuery() != null){
            CharSequence query = priceSearch.getQuery();
            String temp = query.toString();
            try{
                double price = Double.parseDouble(temp);
                price = round(price, 2);
                WeekMenuListFragment fragment = new WeekMenuListFragment();

                Bundle args = new Bundle();
                args.putDouble("price", price);
                fragment.setArguments(args);
                allergiesListWeek = new ArrayList<>();
                allergiesListWeek = allergiesList;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

            } catch (NumberFormatException e){
                Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    //Search Day Menu Button
    public void searchDayMenu(View v){
        SearchView priceSearch = findViewById(R.id.searchPriceField);
        String text = "Please fill in a number";
        ArrayList<Menu> menus = new ArrayList<>();

        if (priceSearch.getQuery() != null){
            CharSequence query = priceSearch.getQuery();
            String temp = query.toString();
            try{
                double price = Double.parseDouble(temp);
                price = round(price, 2);

                for (Menu mn:menuList){
                    if (mn.getPrice() <= price && Collections.disjoint(mn.getAllergies(), allergiesList)){
                        menus.add(mn);
                    }
                }
                allergiesList = new ArrayList<>();

                int random = (int)(Math.random() * menus.size() + 0);
                Menu m = new Menu();
                m = menus.get(random);

                menuDescription(m.getName(), m.getDescription(), m.getRecipe(), m.getIngredientsString());
            } catch (NumberFormatException e){
                Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    //Sort Menus
    public void sortPrice(View v){
        sortedPriceList = new ArrayList<>();
        sortedPriceList = menuList;
        Collections.sort(sortedPriceList,PriceOrde);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PriceOrdendListFragment()).addToBackStack(null).commit();
    }

    public static Comparator<Menu> PriceOrde = new Comparator<Menu>() {
        @Override
        public int compare(Menu o1, Menu o2) {
            int i = (int) Math.round(o1.getPrice()*100);
            int j = (int) Math.round(o2.getPrice()*100);

            int menu1 = i;
            int menu2 = j;
            return menu2 - menu1;
        }
    };

    public void sortRating(View v)  {
        sortedList = new ArrayList<>();
        sortedList = menuList;
        Collections.sort(sortedList,MenuOrde);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuOrdendListFragment()).addToBackStack(null).commit();
    }

    public static Comparator<Menu> MenuOrde = new Comparator<Menu>() {
        @Override
        public int compare(Menu o1, Menu o2) {
            int menu1 = o1.getRating();
            int menu2 = o2.getRating();
            return menu2 - menu1;
        }
    };



    //Show Menu description
    public void menuDescription(String menuTitle, String menuPrice, String menuRecipe, ArrayList<String> ingredientList){
        MenuDisplayFragment fragment = new MenuDisplayFragment();
        Bundle args = new Bundle();
        args.putString("MenuTitle", menuTitle);
        args.putString("MenuPrice", menuPrice);
        args.putString("MenuRecipe", menuRecipe);
        args.putStringArrayList("IngredientList", ingredientList);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    //Add Ingredient
    public void goToAddIngredient(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GoToAddIngredientFragment()).addToBackStack(null).commit();
    }

    public void addIngredient(View v){
        EditText name = findViewById(R.id.addIngredientName);
        EditText price = findViewById(R.id.addIngredientPrice);
        EditText desc = findViewById(R.id.addIngredientDescription);

        if (!name.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()){

            try{
                double priceDouble = Double.parseDouble(price.getText().toString());
                priceDouble = round(priceDouble, 2);

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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IngredientListFragment()).addToBackStack(null).commit();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    public void addMenu(View v){
        EditText name = findViewById(R.id.addMenuName);
        EditText desc = findViewById(R.id.addMenuDescription);


        if (!name.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()){

                   boolean menuExists = false;
                   for (Menu mn:menuList){
                       if (mn.getName().equals(name.getText().toString().trim())){
                           menuExists = true;
                       }
                   }

                   if (menuExists){
                       Toast.makeText(this, "Menu already exists", Toast.LENGTH_LONG).show();
                   } else {
                       Menu m = new Menu();
                       m.setName(name.getText().toString());
                       m.setDescription(desc.getText().toString());
                       menuList.add(m);
                       Bundle args = new Bundle();
                       args.putString("menuTitle", m.getName());
                       GoToAddMenuIngredientFragment fragment = new GoToAddMenuIngredientFragment();
                       fragment.setArguments(args);
                       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                   }
        }
    }

    public void addMenuIngredients(View v){
        LinearLayout ingredients = findViewById(R.id.addMenuIngredientColunm);
        LinearLayout ingredientsPrices = findViewById(R.id.addMenuPriceColunm);
        LinearLayout allergies = findViewById(R.id.invisibleAllergies);
        TextView menuTitel = findViewById(R.id.menuTitle);

        ArrayList<Ingredient> ingredientMenuList = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Double> priceList = new ArrayList<>();
        ArrayList<ArrayList> allergyList = new ArrayList<>();

        if (ingredients.getChildAt(0) != null){
            for( int i = 0; i < ingredients.getChildCount(); i++) {
                if (ingredients.getChildAt(i) instanceof TextView) {
                    double price = Double.parseDouble(((TextView) ingredientsPrices.getChildAt(i)).getText().toString());
                    price = round(price, 2);
                    priceList.add(price);

                    names.add(((TextView) ingredients.getChildAt(i)).getText().toString());

                    LinearLayout al = (LinearLayout) allergies.getChildAt(i);
                    ArrayList<String> stringAl = new ArrayList<>();
                    for (int j = 0; j < al.getChildCount(); j++){
                        stringAl.add(((TextView)al.getChildAt(j)).getText().toString().trim());
                    }
                    allergyList.add(stringAl);
                }
            }


            for (int i = 0; i<names.size(); i++){
                boolean exists = false;
                Ingredient existing = null;
                Ingredient newIng = new Ingredient(names.get(i), priceList.get(i));
                newIng.setAllergies(allergyList.get(i));
                for (Ingredient ing:ingredientList){
                    if (ing.getName().equals(newIng.getName())){
                        exists = true;
                        existing = ing;
                    }
                }
                if (!exists){
                    ingredientMenuList.add(newIng);
                    ingredientList.add(newIng);
                } else {
                    ingredientMenuList.add(existing);
                }

                if (ingredientMenuList.isEmpty()){
                    Toast.makeText(this, "Please add ingredients", Toast.LENGTH_LONG).show();
                } else {
                    for (Menu m:menuList){
                        if (m.getName().equals(menuTitel.getText().toString().trim())){
                            m.setIngredient(ingredientMenuList);
                        }
                    }
                }

            }
            Bundle args = new Bundle();
            args.putString("menuTitle", menuTitel.getText().toString().trim());
            GoToAddMenuRecipeFragment fragment = new GoToAddMenuRecipeFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            Toast.makeText(this, "Please add an ingredient", Toast.LENGTH_LONG).show();
        }
    }

    public void addMenuWithDescription(View v){
        EditText recipe = findViewById(R.id.addMenuRecipe);
        TextView menuTitle = findViewById(R.id.addMenuTitle);
        Menu m1 = new Menu();

        for (Menu m:menuList){
            if (m.getName().equals(menuTitle.getText().toString())){
                m.setRecipe(recipe.getText().toString());
                m1 = m;
            }
        }
        if (recipe.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please fill in a Recipy", Toast.LENGTH_LONG).show();
        } else {
            Bundle args = new Bundle();
            args.putString("menuTitle", m1.getName());
            Log.d(TAG, m1.getName());
            GoToAddMenuPictureFragment fragmentPicture = new GoToAddMenuPictureFragment();
            fragmentPicture.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentPicture).commit();
        }
    }


    //Add Picture to menu
    public void endPictureMenu(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).commit();
    }

    public void loadImagefromGallery(View view) {
        imageToUpLoad = (ImageView) findViewById(R.id.imageView);
        sp=getSharedPreferences("setback", MODE_PRIVATE);
        if(sp.contains("imagepath")) {
            storedpath=sp.getString("imagepath", "");
            imageToUpLoad.setImageBitmap(BitmapFactory.decodeFile(storedpath));
        }
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_ID && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.MediaColumns.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgpath = cursor.getString(columnIndex);
                Log.d("path", imgpath);
                cursor.close();

                SharedPreferences.Editor edit=sp.edit();
                edit.putString("imagepath",imgpath);
                edit.commit();


                Bitmap myBitmap = BitmapFactory.decodeFile(imgpath);

                imageToUpLoad.setImageBitmap(myBitmap);
                Log.d(TAG, "lijst " + myBitmap.toString());
            }
            else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void rateMe(View v){
        ratingBar = findViewById(R.id.ratingBar);
        TextView name = findViewById(R.id.menuDisplayTitle);
        for (Menu m:menuList){
            if (m.getName().equals(name.getText().toString())){
                m.setRating(Math.round(ratingBar.getRating()));
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).addToBackStack(null).commit();
    }


    //CheckBoxMethods
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_gluten:
                if (checked){
                   allergiesList.add("Gluten");
                }
                // Put some meat on the sandwich
            else
                allergiesList.remove("Gluten");
                break;
            case R.id.checkbox_shellfish:
                if (checked){
                    allergiesList.add("Shellfish");
                }
                // Cheese me
            else
                    allergiesList.remove("Shellfish");
                break;
            case R.id.checkbox_eggs:
                if (checked){
                    allergiesList.add("Eggs");
                }
                // Cheese me
            else
                    allergiesList.remove("Eggs");
                break;
            case R.id.checkbox_fish:
                if (checked){
                    allergiesList.add("Fish");
                }
                // Cheese me
            else
                    allergiesList.remove("Fish");
                break;
            case R.id.checkbox_peanut:
                if (checked){
                    allergiesList.add("Peanut");
                }
                // Cheese me
            else
                    allergiesList.remove("Peanut");
                break;
            case R.id.checkbox_soy:
                if (checked){
                    allergiesList.add("Cheese");
                }
                // Cheese me
            else
                    allergiesList.remove("Cheese");
                break;
            case R.id.checkbox_milk:
                if (checked){
                    allergiesList.add("Milk");
                }
                // Cheese me
            else
                    allergiesList.remove("Milk");
                break;
            case R.id.checkbox_nuts:
                if (checked){
                    allergiesList.add("Nuts");
                }
                // Cheese me
            else
                    allergiesList.remove("Nuts");
                break;
            case R.id.checkbox_celery:
                if (checked){
                    allergiesList.add("Celery");
                }
                // Cheese me
            else
                    allergiesList.remove("Celery");
                break;
            case R.id.checkbox_mustard:
                if (checked){
                    allergiesList.add("Mustard");
                }
                // Cheese me
            else
                    allergiesList.remove("Mustard");
                break;
            case R.id.checkbox_lupine:
                if (checked){
                    allergiesList.add("Lupine");
                }
                // Cheese me
            else
                    allergiesList.remove("Lupine");
                break;
            case R.id.checkbox_molluscs:
                if (checked){
                    allergiesList.add("Mollusc's");
                }
                // Cheese me
            else
                    allergiesList.remove("Mollusc's");
                break;

        }
    }

    //Share Menu
    public void shareButton(View v){
        TextView title = findViewById(R.id.menuDisplayTitle);
        TextView price = findViewById(R.id.menuDisplayPrice);
        TextView recipe = findViewById(R.id.recipeText);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hi! I found this cool recipe on the StudentMenu App \n\n" + title.getText().toString() + " \n\nFor only " + price.getText().toString() +"! \n\nHere is the recipe if u would like to try it. \n\n" + recipe.getText().toString();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    //Rounding method
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
