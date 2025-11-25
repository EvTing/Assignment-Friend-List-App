package com.example.friendapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class
FriendListActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ListView listView;
    SearchView searchView;
    ArrayList<Friend> allFriends; // All friend data
    ArrayList<Friend> displayedFriends; // Currently displayed (can be search results)
    ArrayAdapter<String> adapter; // Adapter that only displays names


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlists);

        listView = findViewById(R.id.listViewFriends);
        searchView = findViewById(R.id.searchView);
        dbHelper = new DBHelper(this); // database

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.friendList);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Display the back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Back icon effect
        }

        //get all friend and initialize the list
        allFriends = new ArrayList<>(dbHelper.getAllFriendsList());
        displayedFriends = new ArrayList<>(allFriends);

        updateListView(); // Update ListView to display the name list

        //searching function
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                displayedFriends.clear(); // Real-time filtering of displayed friends (not case sensitive)
                for (Friend f : allFriends) {
                    if (f.getName().toLowerCase().contains(newText.toLowerCase())) {
                        displayedFriends.add(f);
                    }
                }
                updateListView(); // Update the list after searching
                return true;
            }
        });

        //click and jump to detail page
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Friend selectedFriend = displayedFriends.get(position);
            Intent intent = new Intent(FriendListActivity.this, FriendDetailsActivity.class);
            intent.putExtra("FRIEND_ID", selectedFriend.getId());
            startActivity(intent);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_backhome, menu);
        return true;
    }


    // Update the ListView display according to displayedFriends
    private void updateListView() {
        ArrayList<String> names = new ArrayList<>();
        for (Friend f : displayedFriends) {
            names.add(f.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
    }


    // Handle the back button behavior
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            finish(); // return to last page
            return true;
        } else if (id == R.id.action_home) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

        return super.onOptionsItemSelected(item);
    }
}