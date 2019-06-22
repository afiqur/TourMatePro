package com.example.afiqur.tourmatepro.Expense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afiqur.tourmatepro.Data;
import com.example.afiqur.tourmatepro.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by PIASH on 11-May-17.
 */

public class AddMomentExpenseActivity extends AppCompatActivity {


    private String eventId;
    private EditText etCostTitle;
    private EditText etCostAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment_cost);

       // expenseMomentOperation = new ExpenseMomentOperation(this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventId= null;
            } else {
                eventId= extras.getString("EventID");
            }
        } else {
            eventId= (String) savedInstanceState.getSerializable("EventID");
        }

        findView();
    }

    private void findView() {
        etCostTitle = (EditText) findViewById(R.id.etCostTitle);
        etCostAmount = (EditText) findViewById(R.id.etCostAmount);
    }

    public void save(View view) {
        String title = etCostTitle.getText().toString();
        String amount = etCostAmount.getText().toString();

        if (!title.equals("") && !amount.equals("")) {

            addExpense();


        } else {
            Toast.makeText(this, "Enter values properly", Toast.LENGTH_SHORT).show();
        }
    }


    public void addExpense(){


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");


        //Toast.makeText(this, userid+"---"+eventId+"--"+etCostTitle.getText().toString()+etCostAmount.getText().toString(), Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.addExpenseCost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if(response.trim().equals("success")){
                            Toast.makeText(AddMomentExpenseActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(AddMomentExpenseActivity.this, ExpenseActivity.class);
//                            intent.putExtra("EventID", eventId);
//                            intent.putExtra("mainEventID", eventId);
//                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(AddMomentExpenseActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(AddMomentExpenseActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid.toString().trim());
                params.put("EventID", eventId.toString().trim());
                params.put("etCostTitle", etCostTitle.getText().toString().trim());
                params.put("etCostAmount", etCostAmount.getText().toString().trim());

                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest);

    }

    public void cancel(View view) {
        finish();
    }
}
