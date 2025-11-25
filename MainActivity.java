package com.example.friendapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAddFriend = findViewById(R.id.btnAddFriend);
        Button btnViewList = findViewById(R.id.btnViewList);
        ImageView imgInfo = findViewById(R.id.imgInfo);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnAddFriend.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddFriendActivity.class);
            startActivity(intent);
        });

        btnViewList.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FriendListActivity.class));
        });


        imgInfo.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("How to Update/Delete")
                    .setMessage("To update or delete a friend, go to View Friend List and tap a friend.")
                    .setPositiveButton("OK", null)
                    .show();
        });
    }
}