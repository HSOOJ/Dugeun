package com.example.kmucs.dugeun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class we_are extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_are);
    }

    public void info(View v){
        Intent intent = new Intent(getApplicationContext(), info2.class);
        startActivity(intent);
    }
}
