package com.example.maartenvandenhof.studentmenu.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.R;

public class HomeScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        /*Button btn = findViewById(R.id.searchMenuButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this,MenuListScreen.class));
                Toast toast = Toast.makeText(getApplicationContext(), "Dit is een test", Toast.LENGTH_LONG);
                                toast.show();
            }
        });*/

        mDrawerLayout = findViewById(R.id.homeScreen);

        NavigationView navigationView = findViewById(R.id.navigationSlider);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        switch (menuItem.getItemId()){
                            case R.id.ml:
                                Toast toast = Toast.makeText(getApplicationContext(), "Dit is een test", Toast.LENGTH_LONG);
                                toast.show();
                                startActivity(new Intent(HomeScreen.this,MenuListScreen.class));
                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        startActivity(new Intent(HomeScreen.this,MenuListScreen.class));

                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchMenu(View view){

    }


}
