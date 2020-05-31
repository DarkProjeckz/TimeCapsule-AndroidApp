package com.project.timecapsule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Info extends AppCompatActivity {
    private TextView e1,e2,e3,e4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        e1 = (TextView) findViewById(R.id.info_name);
        e2 = (TextView) findViewById(R.id.name1);
        e3 = (TextView) findViewById(R.id.name2);
        e4 = (TextView) findViewById(R.id.name3);
    }

    @Override
    public void onBackPressed(){
        Intent dash = new Intent(this,Dashboard.class);
        startActivity(dash);
        finish();
    }
}
