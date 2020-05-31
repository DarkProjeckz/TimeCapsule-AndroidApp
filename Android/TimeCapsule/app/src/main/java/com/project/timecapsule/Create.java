package com.project.timecapsule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Create extends AppCompatActivity {
    public Button b1;
    public EditText e1,e2,e3;
    public TextView t1;
    public ImageView i1;
    String id;
    public DatePickerDialog.OnDateSetListener mDateSetListener;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        b1 = (Button)findViewById(R.id.click);
        e1 = (EditText)findViewById(R.id.name);
        e2 = (EditText)findViewById(R.id.date);
        e3 = (EditText)findViewById(R.id.desc);
        t1 = (TextView)findViewById(R.id.pgd);
        i1 = (ImageView)findViewById(R.id.imageView2);

        id = getApplicationContext().getSharedPreferences("MyPref", 0).getString("cont","0");
        e2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Create.this,
                            android.R.style.Theme_Holo_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Create.this,
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
                e2.setText(date);
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    public boolean validate_title(){
        String title = e1.getText().toString().trim();
        if(title.isEmpty())
        {
            e1.setError("Enter Title");
            return false;
        }
        else    return true;
    }

    public boolean validate_date(){
        String date = e2.getText().toString().trim();
        if(date.isEmpty())
        {
            e2.setError("Pick a Date");
            return false;
        }
        else    return true;
    }

    public boolean validate_memos(){
        String memos = e3.getText().toString();
        if(memos.isEmpty())
        {
            e3.setError("Enter your memories");
            return false;
        }
        else    return true;
    }

    public void add(){
        if(validate_title() && validate_date() && validate_memos())
        {
            final String title=e1.getText().toString();
            final String date=e2.getText().toString();
            final String mem=e3.getText().toString().replaceAll("\n"," ");
            pd = new ProgressDialog(Create.this);
            pd.setMessage("Loading...");
            pd.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CREATE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressBar.setVisibility(View.GONE);
                            pd.dismiss();
                            response = response.trim();
                            if(response.equals("SUCCESS")){
                                Toast.makeText(getApplicationContext(), "Successfully Added!", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Something went wrong. Try again later!", Toast.LENGTH_LONG).show();
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
                    params.put("mem", mem);
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
    }

    @Override
    public void onBackPressed(){
        Intent main = new Intent(this,Dashboard.class);
        startActivity(main);
        finish();
    }

}
