package com.example.maartenvandenhof.studentmenu.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddIngredientAllergiesFragment;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.widget.AdapterView.OnItemSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TEST10";
    private static final int PICK_IMAGE_ID = 234 ;
    private DrawerLayout drawer;
    public ArrayList<Menu> menuList;
    public ArrayList<Ingredient> ingredientList;
    public RatingBar ratingBar;
    public ImageView imageToUpLoad;
    public Button bUploadImage;
    public String mFilePath;
    public String imgpath, storedpath;
    public SharedPreferences sp;
    public ArrayList<Menu> sortedList;
    public ArrayList<Menu> sortedPriceList;
    public ArrayList<String> allergiesList;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public ArrayList<String> allergiesListWeek;
    public ArrayList<Menu> weekMenus;
    public FusedLocationProviderClient mFusedLocationClient;
    public double lat;
    public double lon;
    public GoogleMap mGoogleMap;
    public LatLngBounds mMapBoundary;
    public EditText mSearchText;



    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET
    };



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
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

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Log.d(TAG,myRef.toString());
        Log.d(TAG,database.toString());



        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,database.toString());

                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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






        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastKnowLocation();

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        //imageToUpLoad.setOnClickListener(this);


    }



    private void showData(DataSnapshot snapshot){




        menuList = new ArrayList<>();
        for(DataSnapshot ds: snapshot.getChildren()) {
            for (DataSnapshot d : ds.getChildren()) {
                if(d != null){
                    Menu menu = new Menu();

                    String name = (String) d.child("name").getValue();
                    String description = (String) d.child("description").getValue();
                    String recipe = (String) d.child("recipe").getValue();
                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                    for(DataSnapshot dk: d.child("ingredient").getChildren()){

                        String price =  dk.child("price").getValue().toString();
                        String nameIngredient = (String) dk.child("name").getValue();
                        Ingredient ing = new Ingredient(nameIngredient,Double.parseDouble(price));
                        ingredients.add(ing);
                    }

                    try {
                        String price = d.child("price").getValue().toString();
                        menu.setPrice(Double.parseDouble(price));

                    }
                    catch(Exception e){
                        Toast.makeText(this, "Prijs mag niet 0 zijn",Toast.LENGTH_LONG).show();
                        }
                        try{
                            String rating = d.child("rating").getValue().toString();
                            menu.setRating(Integer.parseInt(rating));

                        }
                        catch(Exception e){
                            menu.setRating(0);
                        }


                    menu.setName(name);
                    menu.setRecipe(recipe);
                    menu.setDescription(description);
                    menu.setIngredients(ingredients);

                    menuList.add(menu);



                }

            }
        }
    }





    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
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
    public void searchMenuPrice(View v) {
        SearchView priceSearch = findViewById(R.id.searchPriceField);
        String text = "Please fill in a number";

        if (priceSearch.getQuery() != null) {
            CharSequence query = priceSearch.getQuery();
            String temp = query.toString();
            try {
                double price = Double.parseDouble(temp);
                price = round(price, 2);
                MenuPriceSearchFragment fragment = new MenuPriceSearchFragment();

                Bundle args = new Bundle();
                args.putDouble("price", price);
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

            } catch (NumberFormatException e) {
                Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    //Search Week Menu Button
    public void searchWeekMenu(View v) {
        SearchView priceSearch = findViewById(R.id.searchPriceField);
        String text = "Please fill in a number";
        weekMenus = new ArrayList<>();

        if (priceSearch.getQuery() != null) {
            CharSequence query = priceSearch.getQuery();
            String temp = query.toString();
            try {
                double price = Double.parseDouble(temp);
                price = round(price, 2);
                WeekMenuListFragment fragment = new WeekMenuListFragment();

                Bundle args = new Bundle();
                args.putDouble("price", price);
                fragment.setArguments(args);
                allergiesListWeek = new ArrayList<>();
                allergiesListWeek = allergiesList;

                double sum = 0;
                Menu smallest = menuList.get(0);
                for (Menu m: menuList){
                    for (Menu mn: menuList){
                        if (mn.getPrice() < m.getPrice() && mn.getPrice() < smallest.getPrice()){
                            smallest = mn;
                        }
                    }
                }

                for (int i = 0; i < 5; i++){
                    sum = sum + smallest.getPrice();
                }

                if (sum > price){
                    Toast.makeText(this, "Your budget is too small", Toast.LENGTH_SHORT).show();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            } catch (NumberFormatException e) {
                Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    //Search Day Menu Button
    public void searchDayMenu(View v) {
        SearchView priceSearch = findViewById(R.id.searchPriceField);
        String text = "Please fill in a number";
        ArrayList<Menu> menus = new ArrayList<>();

        if (priceSearch.getQuery() != null) {
            CharSequence query = priceSearch.getQuery();
            String temp = query.toString();
            try {
                double price = Double.parseDouble(temp);
                price = round(price, 2);

                for (Menu mn : menuList) {
                    if (mn.getPrice() <= price && Collections.disjoint(mn.getAllergies(), allergiesList)) {
                        menus.add(mn);
                    }
                }
                allergiesList = new ArrayList<>();

                int random = (int) (Math.random() * menus.size() + 0);
                Menu m = new Menu();
                m = menus.get(random);

                menuDescription(m.getName(), m.getDescription(), m.getRecipe(), m.getIngredientsString());
            } catch (NumberFormatException e) {
                Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    //Sort Menus
    public void sortPrice(View v) {
        sortedPriceList = new ArrayList<>();
        sortedPriceList = menuList;
        Collections.sort(sortedPriceList, PriceOrde);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PriceOrdendListFragment()).addToBackStack(null).commit();


    }

    public static Comparator<Menu> PriceOrde = new Comparator<Menu>() {
        @Override
        public int compare(Menu o1, Menu o2) {
            int i = (int) Math.round(o1.getPrice() * 100);
            int j = (int) Math.round(o2.getPrice() * 100);

            int menu1 = i;
            int menu2 = j;
            return menu2 - menu1;
        }
    };

    public void sortRating(View v) {
        sortedList = new ArrayList<>();
        sortedList = menuList;
        Collections.sort(sortedList, MenuOrde);

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
    public void menuDescription(String menuTitle, String menuPrice, String menuRecipe, ArrayList<String> ingredientList) {
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
    public void goToAddIngredient(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GoToAddIngredientFragment()).addToBackStack(null).commit();
    }

    public void goToAllergiesTab(View v){
        EditText name = findViewById(R.id.addIngredientName);
        EditText price = findViewById(R.id.addIngredientPrice);
        EditText desc = findViewById(R.id.addIngredientDescription);

        if (!name.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()) {
            try {
                double priceDouble = Double.parseDouble(price.getText().toString());
                priceDouble = round(priceDouble, 2);

                //Make ingredient
                Ingredient i = new Ingredient(name.getText().toString(), priceDouble, desc.getText().toString());

                //Check if it already exists
                boolean exists = false;
                for (Ingredient in : ingredientList) {
                    if (in.getName().equals(name.getText().toString().trim())) {
                        exists = true;
                    }
                }

                //Add Ingredient
                if (!exists) {
                    ingredientList.add(i);
                    Bundle args = new Bundle();
                    args.putString("ingredientTitle", i.getName());
                    GoToAddIngredientAllergiesFragment fragment = new GoToAddIngredientAllergiesFragment();
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    Toast t = Toast.makeText(this, "Ingredient already exists", Toast.LENGTH_SHORT);
                    t.show();
                }
            } catch (NumberFormatException e) {
                Toast t = Toast.makeText(this, "Please fill in a number for price", Toast.LENGTH_SHORT);
                t.show();
            }
        } else {
            Toast t = Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public void addIngredientWithAllergies(View v) {
        TextView title = findViewById(R.id.ingredientTitle);
        for (Ingredient i:ingredientList){
            if (i.getName().equals(title.getText().toString().trim())){
                i.setAllergies(allergiesList);
            }
        }
        allergiesList = new ArrayList<>();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IngredientListFragment()).commit();
    }

    //Add Menu
    public void goToAddMenu(View v) {
        Bundle args = new Bundle();
        ArrayList<String> ingredientNames = new ArrayList<>();
        for (Ingredient i : ingredientList) {
            ingredientNames.add(i.getName());
        }
        args.putStringArrayList("IngredientList", ingredientNames);
        GoToAddMenuFragment fragment = new GoToAddMenuFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    public void addMenu(View v) {
        EditText name = findViewById(R.id.addMenuName);
        EditText desc = findViewById(R.id.addMenuDescription);


        if (!name.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()) {

            boolean menuExists = false;
            for (Menu mn : menuList) {
                if (mn.getName().equals(name.getText().toString().trim())) {
                    menuExists = true;
                }
            }

            if (menuExists) {
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

    public void addMenuIngredients(View v) {
        LinearLayout ingredients = findViewById(R.id.addMenuIngredientColunm);
        LinearLayout ingredientsPrices = findViewById(R.id.addMenuPriceColunm);
        LinearLayout allergies = findViewById(R.id.invisibleAllergies);
        TextView menuTitel = findViewById(R.id.menuTitle);

        ArrayList<Ingredient> ingredientMenuList = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Double> priceList = new ArrayList<>();
        ArrayList<ArrayList> allergyList = new ArrayList<>();

        if (ingredients.getChildAt(0) != null) {
            for (int i = 0; i < ingredients.getChildCount(); i++) {
                if (ingredients.getChildAt(i) instanceof TextView) {
                    double price = Double.parseDouble(((TextView) ingredientsPrices.getChildAt(i)).getText().toString());
                    price = round(price, 2);
                    priceList.add(price);

                    names.add(((TextView) ingredients.getChildAt(i)).getText().toString());

                    LinearLayout al = (LinearLayout) allergies.getChildAt(i);
                    ArrayList<String> stringAl = new ArrayList<>();
                    for (int j = 0; j < al.getChildCount(); j++) {
                        stringAl.add(((TextView) al.getChildAt(j)).getText().toString().trim());
                    }
                    allergyList.add(stringAl);
                }
            }


            for (int i = 0; i < names.size(); i++) {
                boolean exists = false;
                Ingredient existing = null;
                Ingredient newIng = new Ingredient(names.get(i), priceList.get(i));
                newIng.setAllergies(allergyList.get(i));
                for (Ingredient ing : ingredientList) {
                    if (ing.getName().equals(newIng.getName())) {
                        exists = true;
                        existing = ing;
                    }
                }
                if (!exists) {
                    ingredientMenuList.add(newIng);
                    ingredientList.add(newIng);
                } else {
                    ingredientMenuList.add(existing);
                }

                if (ingredientMenuList.isEmpty()) {
                    Toast.makeText(this, "Please add ingredients", Toast.LENGTH_LONG).show();
                } else {
                    for (Menu m : menuList) {
                        if (m.getName().equals(menuTitel.getText().toString().trim())) {
                            m.setIngredients(ingredientMenuList);
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

    public void addMenuWithDescription(View v) {
        EditText recipe = findViewById(R.id.addMenuRecipe);
        TextView menuTitle = findViewById(R.id.addMenuTitle);
        Menu m1 = new Menu();

        for (Menu m : menuList) {
            if (m.getName().equals(menuTitle.getText().toString())) {
                m.setRecipe(recipe.getText().toString());

                myRef.child("menu").child(m.getName()).child("name").setValue(m.getName());
                myRef.child("menu").child(m.getName()).child("ingredient").setValue(m.getIngredient());
                myRef.child("menu").child(m.getName()).child("price").setValue(m.getPrice());
                myRef.child("menu").child(m.getName()).child("rating").setValue(m.getRating());
                myRef.child("menu").child(m.getName()).child("description").setValue(m.getDescription());
                myRef.child("menu").child(m.getName()).child("recipe").setValue(m.getRecipe());


                m1 = m;
            }
        }
        if (recipe.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill in a Recipy", Toast.LENGTH_LONG).show();
        } else {
            Bundle args = new Bundle();
            args.putString("menuTitle", m1.getName());
            GoToAddMenuPictureFragment fragmentPicture = new GoToAddMenuPictureFragment();
            fragmentPicture.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentPicture).commit();
        }
    }


    //Add Picture to menu
    public void endPictureMenu(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).commit();
    }

    public void loadImagefromGallery(View view) {
        imageToUpLoad = (ImageView) findViewById(R.id.imageView);
        sp = getSharedPreferences("setback", MODE_PRIVATE);
        if (sp.contains("imagepath")) {
            storedpath = sp.getString("imagepath", "");
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
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_ID && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.MediaColumns.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgpath = cursor.getString(columnIndex);
                Log.d("path", imgpath);
                cursor.close();

                SharedPreferences.Editor edit = sp.edit();
                edit.putString("imagepath", imgpath);
                edit.commit();


                Bitmap myBitmap = BitmapFactory.decodeFile(imgpath);

                imageToUpLoad.setImageBitmap(myBitmap);
                Log.d(TAG, "lijst " + myBitmap.toString());
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void rateMe(View v) {
        ratingBar = findViewById(R.id.ratingBar);
        TextView name = findViewById(R.id.menuDisplayTitle);
        for (Menu m : menuList) {
            if (m.getName().equals(name.getText().toString())) {
                m.setRating(Math.round(ratingBar.getRating()));
                myRef.child("menu").child(m.getName()).child("rating").setValue(m.getRating());
            }
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).addToBackStack(null).commit();
    }


    //CheckBoxMethods
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_gluten:
                if (checked) {
                    allergiesList.add("Gluten");
                }
                // Put some meat on the sandwich
                else
                    allergiesList.remove("Gluten");
                break;
            case R.id.checkbox_shellfish:
                if (checked) {
                    allergiesList.add("Shellfish");
                }
                // Cheese me
                else
                    allergiesList.remove("Shellfish");
                break;
            case R.id.checkbox_eggs:
                if (checked) {
                    allergiesList.add("Eggs");
                }
                // Cheese me
                else
                    allergiesList.remove("Eggs");
                break;
            case R.id.checkbox_fish:
                if (checked) {
                    allergiesList.add("Fish");
                }
                // Cheese me
                else
                    allergiesList.remove("Fish");
                break;
            case R.id.checkbox_peanut:
                if (checked) {
                    allergiesList.add("Peanut");
                }
                // Cheese me
                else
                    allergiesList.remove("Peanut");
                break;
            case R.id.checkbox_soy:
                if (checked) {
                    allergiesList.add("Cheese");
                }
                // Cheese me
                else
                    allergiesList.remove("Cheese");
                break;
            case R.id.checkbox_milk:
                if (checked) {
                    allergiesList.add("Milk");
                }
                // Cheese me
                else
                    allergiesList.remove("Milk");
                break;
            case R.id.checkbox_nuts:
                if (checked) {
                    allergiesList.add("Nuts");
                }
                // Cheese me
                else
                    allergiesList.remove("Nuts");
                break;
            case R.id.checkbox_celery:
                if (checked) {
                    allergiesList.add("Celery");
                }
                // Cheese me
                else
                    allergiesList.remove("Celery");
                break;
            case R.id.checkbox_mustard:
                if (checked) {
                    allergiesList.add("Mustard");
                }
                // Cheese me
                else
                    allergiesList.remove("Mustard");
                break;
            case R.id.checkbox_lupine:
                if (checked) {
                    allergiesList.add("Lupine");
                }
                // Cheese me
                else
                    allergiesList.remove("Lupine");
                break;
            case R.id.checkbox_molluscs:
                if (checked) {
                    allergiesList.add("Mollusc's");
                }
                // Cheese me
                else
                    allergiesList.remove("Mollusc's");
                break;

        }
    }

    //Share Menu
    public void shareButton(View v) {
        TextView title = findViewById(R.id.menuDisplayTitle);
        TextView price = findViewById(R.id.menuDisplayPrice);
        TextView recipe = findViewById(R.id.recipeText);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hi! I found this cool recipe on the StudentMenu App \n\n" + title.getText().toString() + " \n\nFor only " + price.getText().toString() + "! \n\nHere is the recipe if u would like to try it. \n\n" + recipe.getText().toString();
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

    //Google Maps
    public void getLastKnowLocation() {
        Log.d(TAG, "getLastKnowLocation: called.");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                    Log.d(TAG, "onComplete: latitude: " + geoPoint.getLat());
                    Log.d(TAG, "onComplete: longtitude: " + geoPoint.getLon());
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
            }
        });
    }

    /*public void init(){
        Log.d(TAG, "init: initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    //execute our method for searching
                    geoLocate();
                }
                return false;
            }
        });
    }

    public void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(MainActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.d(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if(list.size() > 0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate found location" + address.toString());
        }
    }*/
}

