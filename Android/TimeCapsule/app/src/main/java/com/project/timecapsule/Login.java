package com.project.timecapsule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText e1,e2;
    private Button login,reg;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
        login = (Button)findViewById(R.id.logbutton);
        reg = (Button)findViewById(R.id.regbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginfunc();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regfunc();
            }
        });
    }

    public boolean validate_uname(){
        String uname = e1.getText().toString().trim();
        if(uname.isEmpty())
        {
            e1.setError("Enter Username");
            return false;
        }
        else
            return true;
    }

    public boolean validate_password(){
        String pwd = e2.getText().toString().trim();
        if(pwd.isEmpty())
        {
            e2.setError("Enter Password");
            return false;
        }
        else
            return true;
    }

    public void loginfunc(){
        if(validate_uname() && validate_password()) {
            final String UNAME=e1.getText().toString();
            final String PASS=e2.getText().toString();

            pd = new ProgressDialog(Login.this);
            pd.setMessage("Loading...");
            pd.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressBar.setVisibility(View.GONE);
                            pd.dismiss();
                            if(response.equals("INVALID"))
                            {
                                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                            }
                            else{
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("cont", response);
                                editor.commit();

                                Toast.makeText(getApplicationContext(), "Login successful! ", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                finish();
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
                    params.put("user", UNAME);
                    params.put("pass", PASS);
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

    public void regfunc(){
        Intent reg = new Intent(this,Signout.class);
        startActivity(reg);
        finish();

    }




}
