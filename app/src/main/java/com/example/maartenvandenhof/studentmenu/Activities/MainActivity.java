package com.example.maartenvandenhof.studentmenu.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Main Activity";
    private static final int PICK_IMAGE_ID = 234 ;
    private DrawerLayout drawer;
    public ArrayList<Menu> menuList;
    public ArrayList<Ingredient> ingredientList;
    public RatingBar ratingBar;
    public ImageView imageToUpLoad;
    public Button bUploadImage;
    public ArrayList<Menu> sortedList;
    public ArrayList<String> allergiesList;

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


    public void sortRating(View v)  {
        double rating = 5;
        sortedList = new ArrayList<>();
        for(int x = 0; x < menuList.size(); x++){
            if( menuList.get(x).getRating() == rating){
                sortedList.add(menuList.get(x));
            }
            rating = rating - 0.5;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuOrdendListFragment()).addToBackStack(null).commit();
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
                       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
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
        ArrayList<Integer> priceList = new ArrayList<>();
        ArrayList<ArrayList> allergyList = new ArrayList<>();

        for( int i = 0; i < ingredients.getChildCount(); i++) {
            if (ingredients.getChildAt(i) instanceof TextView) {
                priceList.add(Integer.parseInt(((TextView) ingredientsPrices.getChildAt(i)).getText().toString()));
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
            Bundle args = new Bundle();
            args.putString("menuTitle", menuTitel.getText().toString().trim());
            GoToAddMenuRecipeFragment fragment = new GoToAddMenuRecipeFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
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

    public void onPickImage(View view) {
        //Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        //startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
        imageToUpLoad = (ImageView) findViewById(R.id.imageView);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_ID);
        TextView menuTitle = findViewById(R.id.addMenuTitle);
        for (Menu m:menuList){
            if (m.getName().equals(menuTitle.getText().toString())){
                m.setImageToUpload(imageToUpLoad);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //switch(requestCode) {
        //    case PICK_IMAGE_ID:
                //Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
        //        Uri selectedImage = data.getData();
        //        imageToUpLoad.setImageURI(selectedImage);
        //        break;
        //    default:
        //        super.onActivityResult(requestCode, resultCode, data);
         //       break;
        //}

        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PICK_IMAGE_ID && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            if(selectedImage != null) {
                imageToUpLoad.setImageURI(selectedImage);
            }
        }
    }



    public void rateMe(View v){
        ratingBar = findViewById(R.id.ratingBar);
        TextView name = findViewById(R.id.menuDisplayTitle);
        for (Menu m:menuList){
            if (m.getName().equals(name.getText().toString())){
                m.setRating(ratingBar.getRating());
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
}
