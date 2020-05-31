package com.project.timecapsule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {


    private EditText et1;
    private EditText et2;
    private EditText et3;
    private Button im;
    String id;
    public DatePickerDialog.OnDateSetListener mDateSetListener;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        et1=findViewById(R.id.ET1);
        et2=findViewById(R.id.editText);
        et3=findViewById(R.id.ET2);
        im=findViewById(R.id.button);

        id = getApplicationContext().getSharedPreferences("MyPref", 0).getString("cont","0");
        et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Update.this,
                            android.R.style.Theme_Holo_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Update.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;

                String monthh = "0"+month;
                String dayy = "0"+dayOfMonth;

                monthh = monthh.substring(monthh.length()-2);
                dayy = dayy.substring(dayy.length()-2);

                String date = year + "-" + monthh + "-" + dayy;
                et2.setText(date);
                if(validate_title() && validate_date()) {
                    displayData();
                }

            }
        };

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate_title() && validate_date() && validate_mem()) {
                    exc();
                }
            }
        });

    }

    public void displayData(){
        final String date=et2.getText().toString();
        final String title=et1.getText().toString();
        pd = new ProgressDialog(Update.this);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DISPLAY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        //progressBar.setVisibility(View.GONE);

                        if(!response.equals("ERROR"))
                        {
                            et3.setText(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("date", date);
                params.put("id", id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", URLs.getApiKey());
                return params;
            }
        };

        Singleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public boolean validate_title(){
        String title = et1.getText().toString().trim();
        if(title.isEmpty())
        {
            et1.setError("Enter Title");
            return false;
        }
        else    return true;
    }

    public boolean validate_date(){
        String date = et2.getText().toString().trim();
        if(date.isEmpty())
        {
            et2.setError("Pick a Date");
            return false;
        }
        else    return true;
    }

    public boolean validate_mem(){
        String mem = et3.getText().toString().trim();
        if(mem.isEmpty())
        {
            et3.setError("Event should not be empty");
            return false;
        }
        else    return true;
    }

    public void exc()
    {
        final String date=et2.getText().toString();
        final String title=et1.getText().toString();
        final String mem=et3.getText().toString().replaceAll("\n"," ");
        pd = new ProgressDialog(Update.this);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        //progressBar.setVisibility(View.GONE);
                        if(response.equals("SUCCESS")){
                            Toast.makeText(getApplicationContext(), "Successfully Updated!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Data does not exist for modification!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("date", date);
                params.put("id", id);
                params.put("cont", mem);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", URLs.getApiKey());
                return params;
            }
        };
        Singleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    @Override
    public void onBackPressed(){
        Intent main = new Intent(this,Dashboard.class);
        startActivity(main);
        finish();
    }



}
