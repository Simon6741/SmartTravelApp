package com.simonsmarttravel.www.smarttravelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.simonsmarttravel.www.smarttravelapp.Model.Places;
import com.simonsmarttravel.www.smarttravelapp.Model.UserProfile;

public class NewUserActivity extends AppCompatActivity {
    EditText txtName;
    EditText txtUsername;
    EditText txtPassword;

    EditText txtTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        txtName = (EditText) findViewById(R.id.txtName);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        txtTest = (EditText) findViewById(R.id.txtTest);
    }

    public void onClickSubmit(View view) {
        String name = txtName.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if (!name.equals("") && !username.equals("") && !password.equals("")) {
            DBHandler db = new DBHandler(this, null, null, 1);

         //   UserProfile newValue = new UserProfile(name, username, password);
           // db.insertUserProfile(newValue);

           Places newValue = new Places(name, username, txtTest.getText().toString(), password);
            db.insertPlaces(newValue);

            Toast.makeText(this, "You have become a member!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
