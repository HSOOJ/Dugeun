package com.example.kmucs.dugeun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Duguen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duguen);
    }

    public void menuinfo(View v){
        Intent intent = new Intent(getApplicationContext(), we_are.class);
        startActivity(intent);
    }

    public void country(View v){
        Intent intent = new Intent(getApplicationContext(), countrymemo.class);
        startActivity(intent);
    }

    public void memo(View v){
        Intent intent = new Intent(getApplicationContext(), onememo.class);
        startActivity(intent);
    }

    public void exchange(View v){
        Intent intent = new Intent(getApplicationContext(), exchange.class);
        startActivity(intent);
    }

    public void cash(View v){
        Intent intent = new Intent(getApplicationContext(), cashbook.class);
        startActivity(intent);
    }

    public void map(View v){
        Intent intent = new Intent(getApplicationContext(), mappage.class);
        startActivity(intent);
    }
}
