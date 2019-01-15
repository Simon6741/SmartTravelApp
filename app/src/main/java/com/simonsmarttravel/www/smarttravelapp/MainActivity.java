package com.simonsmarttravel.www.smarttravelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    public int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        userId = -1;

    }

    public void onClickLogin(View view) {
        if (validateLogin(txtUsername.getText().toString(), txtPassword.getText().toString())) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("UserId", userId);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(this, getString(R.string.login_page_failed_toast), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickNewUser(View view){
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }

    private boolean validateLogin(String username, String password) {
        boolean isMatch = false;

        DBHandler db = new DBHandler(this, null, null, 1);
        userId = db.getIdByUsernamePassword(txtUsername.getText().toString(), txtPassword.getText().toString());

        if (userId != -1) {
            isMatch = true;
        }

        return isMatch;
    }
}
