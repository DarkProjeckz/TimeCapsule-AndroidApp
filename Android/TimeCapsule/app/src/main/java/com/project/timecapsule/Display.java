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
import android.widget.TextView;
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

public class Display extends AppCompatActivity {

    private Button disp;
    private EditText e1,e2;
    private TextView t1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ProgressDialog pd;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        disp = (Button)findViewById(R.id.disp_button);
        e1 = (EditText)findViewById(R.id.disp_date);
        e2 = (EditText)findViewById(R.id.disp_title);
        t1 = (TextView)findViewById(R.id.disp_memos);

        id = getApplicationContext().getSharedPreferences("MyPref", 0).getString("cont","0");
        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Display.this,
                            android.R.style.Theme_Holo_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });
        disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate_title() && validate_date() ){
                    t1.setText("");
                    exc();
                }
            }
        });

        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Display.this,
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
                e1.setText(date);
            }
        };
    }

    public boolean validate_title(){
        String title = e2.getText().toString().trim();
        if(title.isEmpty())
        {
            e2.setError("Enter Title");
            return false;
        }
        else    return true;
    }

    public boolean validate_date(){
        String date = e1.getText().toString().trim();
        if(date.isEmpty())
        {
            e1.setError("Pick a Date");
            return false;
        }
        else    return true;
    }

    public void exc()
    {

        final String date=e1.getText().toString();
        final String title=e2.getText().toString();
        pd = new ProgressDialog(Display.this);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DISPLAY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        pd.dismiss();
                        if(response.equals("ERROR"))
                        {
                            Toast.makeText(getApplicationContext(), "Data not found!", Toast.LENGTH_LONG).show();
                        }
                        else{
                           t1.setText(response);
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
    public void onBackPressed(){
        Intent main = new Intent(this,Dashboard.class);
        startActivity(main);
        finish();
    }
}
