package com.project.timecapsule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {
    private Button add,update,display,info,signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        add = (Button)findViewById(R.id.button);
        update = (Button)findViewById(R.id.button2);
        display = (Button)findViewById(R.id.button3);
        info = (Button)findViewById(R.id.button4);
        signout = (Button)findViewById(R.id.logout);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info();
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });
    }

    public void add(){
        Intent create = new Intent(this,Create.class);
        startActivity(create);
        finish();
    }

    public void update(){
        Intent update = new Intent(this,Update.class);
        startActivity(update);
        finish();
    }

    public void display(){
        Intent disp = new Intent(this,Display.class);
        startActivity(disp);
        finish();
    }

    public void info(){
        Intent info = new Intent(this,Info.class);
        startActivity(info);
        finish();
    }

    public void signout(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        Toast.makeText(getApplicationContext(), "Logged out successfully!", Toast.LENGTH_LONG).show();
        Intent back = new Intent(this,Login.class);
        startActivity(back);
        finish();
    }
}
