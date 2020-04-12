package com.example.technopark;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.technopark.controller.LoginController;

public class MainActivity extends AppCompatActivity {
private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        loginController=new LoginController(this);
    }
}
