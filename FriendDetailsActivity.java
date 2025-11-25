package com.example.friendapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

public class FriendDetailsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frienddetail);

        TextView txtName = findViewById(R.id.txtName);
        EditText txtPhone = findViewById(R.id.txtPhone);
        EditText txtAddress = findViewById(R.id.txtAddress);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        DBHelper dbHelper;
        int friendId;

        dbHelper = new DBHelper(this);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.friendDetail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Display the back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Back icon effect
        }


        //get the passed friend's id
        friendId = getIntent().getIntExtra("FRIEND_ID", -1);

        if (friendId != -1) {
            //get friend details frm db
            Friend friend = dbHelper.getFriendById(friendId);
            if (friend != null) {
                txtName.setText(friend.getName());
                txtPhone.setText(friend.getPhone());
                txtAddress.setText(friend.getAddress());
            }
        }

        btnUpdate.setOnClickListener(v -> {
            String newPhone = txtPhone.getText().toString().trim();
            String newAddress = txtAddress.getText().toString().trim();

            boolean updated = dbHelper.updateFriend(friendId, newPhone, newAddress);
            if (updated) {
                Toast.makeText(FriendDetailsActivity.this, "Info update successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FriendDetailsActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });


        btnDelete.setOnClickListener(v -> {
            boolean deleted = dbHelper.deleteFriend(friendId);
            if (deleted) {
                Toast.makeText(FriendDetailsActivity.this, "Friend deleted", Toast.LENGTH_SHORT).show();
                finish(); // close detail page
            } else {
                Toast.makeText(FriendDetailsActivity.this, "delete failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_backhome, menu);
        return true;
    }

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