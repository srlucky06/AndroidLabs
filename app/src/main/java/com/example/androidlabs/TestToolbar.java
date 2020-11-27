package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.core.view.GravityCompat;


public class TestToolbar extends AppCompatActivity {

    private Object Menu;
    DrawerLayout drawer;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        //For navigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (item -> {

            switch (item.getItemId())
            {
                case R.id.chatPage:
                    Intent goToChat = new Intent(this, ChatRoomActivity.class);
                    startActivity(goToChat);
                    break;
                case R.id.weatherPage:
                    Intent goToWeather = new Intent(this, WeatherForecast.class);
                    startActivity(goToWeather);
                    break;
                case R.id.goBackPage:
                    setResult(500);
                    finish();
                    break;

            }

            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
        public boolean onCreateOptionsMenu (Menu menu) {
                // Inflate the menu items for use in the action bar
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menueitem, menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        String message =null;
            // Handle presses on the action bar items
            switch (item.getItemId()) {
                case R.id.searchItem:
                    //show a Toast
                    message = "You clicked on item 1";
                    break;

                case R.id.emailItem:
                    //launch another Activity
                    message = "You clicked on item 2";
                    break;

                case R.id.globeItem:
                    //launch another Activity
                    message="You clicked on item 3";
                    break;

                case R.id.downloadItem:
                    message = "you clicked on the overflow menu";
                    break;

            }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;

        }

    }


