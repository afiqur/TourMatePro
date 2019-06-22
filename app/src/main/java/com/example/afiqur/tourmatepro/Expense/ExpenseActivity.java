package com.example.afiqur.tourmatepro.Expense;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afiqur.tourmatepro.Data;
import com.example.afiqur.tourmatepro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseActivity extends AppCompatActivity {

    private int eventId;
    private String eventName;
    private String eventFrom;
    private String eventTo;
    private double eventBudget;
    private TextView tvEventName;
    private TextView tvEventFrom;
    private TextView tvEventTo;
    private TextView tvEventBudgetAmount;
    private ListView lvExpenseList;
    String EventId, EventName, StartDate, EndDate, Budget;
    ArrayList<Expense> dataModelMoments;
    ListView listView;
    private static ExpenseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        tvEventName = (TextView) findViewById(R.id.tvExpenseEventName);
        tvEventFrom = (TextView) findViewById(R.id.tvExpenseEventFrom);
        tvEventTo = (TextView) findViewById(R.id.tvExpenseEventTo);
        tvEventBudgetAmount = (TextView) findViewById(R.id.tvExpenseEventBudgetAmount);
        lvExpenseList = (ListView) findViewById(R.id.lvExpenseList);

        listView=(ListView)findViewById(R.id.lvExpenseList);


        dataModelMoments = new ArrayList<>();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                EventId = null;
            } else {
                EventId = extras.getString("mainEventID");
                EventName = extras.getString("eventName");
                StartDate = extras.getString("fromDate");
                EndDate = extras.getString("toDate");
                Budget = extras.getString("budget");

            }
        } else {
            EventId = (String) savedInstanceState.getSerializable("mainEventID");
            EventName = (String) savedInstanceState.getSerializable("eventName");
            StartDate = (String) savedInstanceState.getSerializable("fromDate");
            EndDate = (String) savedInstanceState.getSerializable("toDate");
            Budget = (String) savedInstanceState.getSerializable("budget");

        }






    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllExpense();
    }

    public void loadAllExpense() {

        dataModelMoments.clear();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.ExpenseListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.trim().toString().equals("no")) {


                            Toast.makeText(ExpenseActivity.this, "No History", Toast.LENGTH_SHORT).show();


                        } else {


                            try {

                                JSONArray notiJson = new JSONArray(response.toString());


                                for(int i = 0; i<notiJson.length(); i++){
                                    JSONObject every_noti = notiJson.getJSONObject(i);
                                    String expenseid = every_noti.getString("expense_id");
                                    String name = every_noti.getString("expense_title");
                                    String cost = every_noti.getString("expense_cost");

                                    dataModelMoments.add(new Expense(expenseid,name,cost));

                                }


                                intializeAdapter();




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ExpenseActivity.this,"error"+error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID",userid.trim());
                params.put("EVentID",EventId.trim());


                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest);

    }


    public void intializeAdapter(){

        adapter= new ExpenseAdapter(dataModelMoments,this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.expense_menu, menu);
        return true;
    }

    public void back(MenuItem item) {
        finish();
    }
}
