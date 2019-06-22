package com.example.afiqur.tourmatepro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afiqur.tourmatepro.TravelEvent.TravelActivity;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText name, username, password, repassword;
    private Button SignUP, Back;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        SignUP = (Button) findViewById(R.id.SignUP);
        Back = (Button) findViewById(R.id.Back);


        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (password.getText().toString().equals(repassword.getText().toString())) {
                    doSignup();
                }

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, TravelActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void doSignup() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.signupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String userNameText = name.getText().toString();
                        String emailText = username.getText().toString();
                        String passwordText = password.getText().toString();
                        String rePasswordText = repassword.getText().toString();

                        if (!userNameText.equals("")) {

                           if (!emailText.equals("")) {

                                if (!passwordText.equals("")) {

                                    if ((passwordText.length() > 3 && rePasswordText.length() > 3)) {

                                        if (passwordText != rePasswordText) {

                                            if (response.trim().equals("success")) {
                                                Toast.makeText(SignupActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignupActivity.this, TravelActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(SignupActivity.this, "Try Again" + response, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(SignupActivity.this, "Password Didn't Match", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Enter at least 4 digit password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SignupActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "Username/Email is Empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "Name Field is Emply", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name", name.getText().toString().trim());
                params.put("username", username.getText().toString().trim());
                params.put("password", password.getText().toString().trim());

                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest);

    }
}
