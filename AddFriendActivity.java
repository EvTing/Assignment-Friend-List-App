package com.example.friendapplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddFriendActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);

        EditText editName = findViewById(R.id.editName);
        EditText editPhone = findViewById(R.id.editPhone);
        EditText editAddress = findViewById(R.id.editAddress);
        Button btnAddFriend = findViewById(R.id.btnAddFriend);
        DBHelper dbHelper;

        TextView textBeMyFriend = findViewById(R.id.idBeMyFriends);

        dbHelper = new DBHelper(this);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.addFriend);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Display the back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Back icon effect
        }

        btnAddFriend.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String address = editAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(AddFriendActivity.this, "Name and phone cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertFriend(name, phone, address);
            if (inserted) {
                Toast.makeText(AddFriendActivity.this, "added successfully!", Toast.LENGTH_SHORT).show();
                editName.setText("");
                editPhone.setText("");
                editAddress.setText("");
            } else {
                Toast.makeText(AddFriendActivity.this, "add failed", Toast.LENGTH_SHORT).show();
            }
        });

        //Start the fade-in and fade-out animation
        Animation fadeAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);
        textBeMyFriend.startAnimation(fadeAnim);

        //Set the green gradient text
        Shader textShader = new LinearGradient(
                0, 0, 0, textBeMyFriend.getLineHeight(),
                new int[]{
                        Color.parseColor("#A8E6CF"),
                        Color.parseColor("#56AB2F")
                },
                null,
                Shader.TileMode.CLAMP);
        textBeMyFriend.getPaint().setShader(textShader);

        // Fade-in animation (from transparent to fully visible)
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textBeMyFriend, "alpha", 0f, 1f);
        fadeIn.setDuration(1500);  // 1.5 seconds fade in
        fadeIn.setStartDelay(500); // Delay 0.5 seconds to start
        fadeIn.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_backhome, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish(); // back to last page
            return true;
        } else if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

