package com.example.maartenvandenhof.studentmenu.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddIngredientFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuFragmentRecipeFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.GoToAddMenuPictureFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.HomeScreenFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.IngredientListFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuDisplayFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuListFragment;
import com.example.maartenvandenhof.studentmenu.*;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuOrdendListFragment;
import com.example.maartenvandenhof.studentmenu.Fragments.MenuPriceSearchFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import android.widget.AdapterView.OnItemSelectedListener;


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
    private ArrayList<String> allergiesList;


    /*private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };*/

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
        allergiesList = new ArrayList<>();
        /*allergiesList.add("Gluten");
        allergiesList.add("Fish");
        allergiesList.add("Milk");
        allergiesList.add("Mushrooms");
        allergiesList.add("Mustard");
        allergiesList.add("Eggs");
        allergiesList.add("Celery");
        allergiesList.add("Shellfish");
        allergiesList.add("Nuts");
        allergiesList.add("Peanuts");
        allergiesList.add("Lupine");
        allergiesList.add("Mollusc's");
        allergiesList.add("Cheese");*/


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




        //imageToUpLoad.setOnClickListener(this);
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

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

            } catch (NumberFormatException e){
                Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }


    public void sortPrice(View v){

        sortedPriceList = new ArrayList<>();
        sortedPriceList = menuList;
        Collections.sort(sortedPriceList,PriceOrde);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuOrdendListFragment()).addToBackStack(null).commit();



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

    //
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
        LinearLayout ingredients = findViewById(R.id.addMenuIngredientColunm);
        LinearLayout ingredientsPrices = findViewById(R.id.addMenuPriceColunm);

        if (!name.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()){

                ArrayList<Ingredient> ingredientMenuList = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                ArrayList<Integer> priceList = new ArrayList<>();

                for( int i = 0; i < ingredients.getChildCount(); i++) {
                    if (ingredients.getChildAt(i) instanceof TextView) {
                            priceList.add(Integer.parseInt(((TextView) ingredientsPrices.getChildAt(i)).getText().toString()));
                            names.add(((TextView) ingredients.getChildAt(i)).getText().toString());
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
                        ingredientList.add(newIng);
                    } else {
                        ingredientMenuList.add(existing);
                    }
                }

               if (ingredientMenuList.isEmpty()){
                    Toast.makeText(this, "Please add ingredients", Toast.LENGTH_LONG).show();
               } else {
                   Menu m = new Menu(name.getText().toString(), ingredientMenuList, desc.getText().toString());
                   menuList.add(m);
                   Bundle args = new Bundle();
                   args.putString("menuTitle", m.getName());
                   GoToAddMenuFragmentRecipeFragment fragmentRecipe = new GoToAddMenuFragmentRecipeFragment();
                   fragmentRecipe.setArguments(args);
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentRecipe).addToBackStack(null).commit();
               }
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentPicture).addToBackStack(null).commit();
        }
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).addToBackStack(null).commit();
    }

    //Add Picture to menu
    public void endPictureMenu(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuListFragment()).addToBackStack(null).commit();
    }

    /*public void onPickImage(View view) {
        imageToUpLoad = (ImageView) findViewById(R.id.imageView);
       bUploadImage = (Button) findViewById(R.id.bUploadImage);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_ID);
        TextView menuTitle = findViewById(R.id.addMenuTitle);
        Log.d(TAG, "test");
        for (Menu m:menuList){
            if (m.getName().equals(menuTitle.getText().toString())){
                Log.d(TAG, "test2");
                m.setImageToUpload(imageToUpLoad);
                Log.d(TAG, "test3");
            }
        }
    }*/

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
            }
            else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        //if(requestCode == PICK_IMAGE_ID && resultCode == RESULT_OK && data != null){
        //    Uri selectedImage = data.getData();
        //    if(selectedImage != null) {
        //        Log.d(TAG, "test4");
        //        imageToUpLoad.setImageURI(selectedImage);
        //        Log.d(TAG, "test5" + selectedImage.toString());
        //    }
        //}
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

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_gluten:
                if (checked){
                   
                }
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
            case R.id.checkbox_shellfish:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_eggs:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_fish:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_peanut:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_soy:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_milk:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_nuts:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_celery:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_mustard:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_lupine:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.checkbox_molluscs:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;

            // TODO: Veggie sandwich
        }
    }


    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }*/
}
