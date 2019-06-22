package com.example.afiqur.tourmatepro.TravelEvent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTravelEvent extends AppCompatActivity {

    private static TextView FromDate;
    private static TextView ToDate;
    private EditText Emergency;
    private EditText Destination;
    private EditText Budget;
    private Button Save,Cancel;
    public static boolean date = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        FromDate = (TextView) findViewById(R.id.FDate);
        Emergency = (EditText) findViewById(R.id.editTextNote);
        ToDate = (TextView) findViewById(R.id.TDate);
        Destination = (EditText) findViewById(R.id.editTextDestination);
        Budget = (EditText) findViewById(R.id.editTextBudget);
        Save = (Button) findViewById(R.id.buttonAdd);
        Cancel = (Button) findViewById(R.id.back);

        ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        FromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTravelEvent();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTravelEvent.this, TravelActivity.class));
            }
        });


    }

    public void AddTravelEvent() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.AddTravelEvent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String destinationET = Destination.getText().toString();
                        String BudgetET = Budget.getText().toString();
                        String StartDate = FromDate.getText().toString();
                        String EndDate = ToDate.getText().toString();
                        String EmergencyCon = Emergency.getText().toString();
                        if (!destinationET.equals("")) {
                            if (!BudgetET.equals("")) {
                                if (!StartDate.equals("")) {
                                    if (!EndDate.equals("")) {
                                        if (!EmergencyCon.equals(""))
                                            if (response.trim().equals("success")) {
                                                Toast.makeText(AddTravelEvent.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                                                finish();
                                                Destination.setText("");
                                                Budget.setText("");
                                                FromDate.setText("");
                                                ToDate.setText("");
                                                Emergency.setText("");
                                            } else {
                                                Toast.makeText(AddTravelEvent.this, "Try Again", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        else {
                                            Toast.makeText(AddTravelEvent.this, "Enter Emergency Contact to Confinue", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(AddTravelEvent.this, "Ending Date is not Selected", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AddTravelEvent.this, "Starting Date is not Selected", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddTravelEvent.this, "Enter Budget", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddTravelEvent.this, "Enter Destination", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(AddTravelEvent.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", userid.trim());
                params.put("fromDate", FromDate.getText().toString().trim());
                params.put("emergency", Emergency.getText().toString().trim());
                params.put("todate", ToDate.getText().toString().trim());
                params.put("destination", Destination.getText().toString().trim());
                params.put("budget", Budget.getText().toString().trim());


                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest);

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            if (!date) {
                FromDate.setText(day + "/" + month + "/" + year);
                date = !date;
            } else {
                ToDate.setText(day + "/" + month + "/" + year);
                date = !date;
            }
        }
    }
}
