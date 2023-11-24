package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText user;
    EditText pass;
    Button login;
    public  static int User_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        DBHelper db= new DBHelper(this);
        user= findViewById(R.id.User);
        pass = findViewById(R.id.Pass);
        login =findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ch = db.checkLogin(user.getText().toString(),pass.getText().toString());
                if (ch==true){
                    Toast.makeText(LoginActivity.this,"Sign in success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"Incorrect username or password",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}