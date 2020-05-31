package com.project.timecapsule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Signout extends AppCompatActivity {
    private Button reg;
    private EditText uname, pass, cpass, email, phone ,dob;
    private RadioGroup rg;
    private RadioButton male,female,other;
    private String val_pass,val_cpass;
    ProgressDialog pd;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);
        reg = (Button)findViewById(R.id.regbutton);
        uname = (EditText) findViewById(R.id.editText);
        pass = (EditText)findViewById(R.id.editText2);
        cpass = (EditText)findViewById(R.id.editText3);
        email = (EditText)findViewById(R.id.editText5);
        phone = (EditText)findViewById(R.id.editText7);
        rg = (RadioGroup)findViewById(R.id.radioGroup2);
        male = (RadioButton)findViewById(R.id.radioButton3);
        female = (RadioButton)findViewById(R.id.radioButton9);
        other = (RadioButton)findViewById(R.id.radioButton10);
        dob = (EditText)findViewById(R.id.date);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regpage();
            }
        });

        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Signout.this,
                            android.R.style.Theme_Holo_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Signout.this,
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
                dob.setText(date);
            }
        };
    }

    public boolean validate_name() {
        String u_name = uname.getText().toString().trim();
        if (u_name.isEmpty()) {
            uname.setError("Enter Username");
            return false;
        }
        else    return true;
    }

    public boolean validate_pwd() {
        String pwd = pass.getText().toString().trim();
        if (pwd.isEmpty()) {
            pass.setError("Enter Password");
            return false;
        }
        else    return true;
    }

    public boolean validate_cpwd() {
        String cpwd = cpass.getText().toString().trim();
        if (cpwd.isEmpty()) {
            cpass.setError("Enter Confirm Password");
            return false;
        }
        else            return true;
    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean validate_email() {
        String mail = email.getText().toString().trim();
        if (mail.isEmpty()) {
            email.setError("Enter Email");
            return false;
        }
        else
        {
            if(isValid(mail))
                return true;
            else
            {
                email.setError("Enter Valid Email");
                return false;
            }
        }
    }


    public boolean validate_phone(){
        String ph = phone.getText().toString().trim();
        if (ph.isEmpty()) {
            phone.setError("Enter Phone Number");
            return false;
        }
        else{
            if(ph.length() == 10)            return true;
            else   {
                phone.setError("Enter Valid Number");
                return false;
            }
        }
    }

    public boolean validate_gen(){
        if(rg.getCheckedRadioButtonId() == -1)
        {
            male.setError("Pick a Gender");
            return false;
        }
        else    return true;
    }

    public boolean passcheck(){
        val_pass = pass.getText().toString().trim();
        val_cpass = cpass.getText().toString().trim();
        if(val_cpass.equals(val_pass))
            return true;
        else{
            cpass.setError("Check Confirm Password");
            return false;
        }
    }

    public boolean validate_dob() {
        String val = dob.getText().toString().trim();
        if (val.isEmpty()) {
            dob.setError("Enter DOB");
            return false;
        }
        else    return true;
    }

    public void regpage(){

        if(validate_name() && validate_pwd() && validate_email() && validate_cpwd() && validate_phone() && validate_gen() && passcheck() && validate_dob())
        {
            final String gender1;
            String val="";
            final String uname1=uname.getText().toString();
            final String pass1=pass.getText().toString();
            int sradio = rg.getCheckedRadioButtonId();
            if(sradio != -1){
                RadioButton selected = (RadioButton)findViewById(sradio);
                val = selected.getText().toString();
            }
            gender1 = val;
            final String email1=email.getText().toString();
            final String phone1=phone.getText().toString();
            final String cpass1=cpass.getText().toString();
            final String dob1 = dob.getText().toString();

            pd = new ProgressDialog(Signout.this);
            pd.setMessage("Loading...");
            pd.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            //progressBar.setVisibility(View.GONE);
                            if(response.equals("SUCCESS")){
                                Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
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
                    params.put("user", uname1);
                    params.put("pass", pass1);
                    params.put("cpass", cpass1);
                    params.put("email", email1);
                    params.put("phone", phone1);
                    params.put("gender", gender1);
                    params.put("dob", dob1);
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
        Intent login = new Intent(this,Login.class);
        startActivity(login);
        finish();
    }
}
