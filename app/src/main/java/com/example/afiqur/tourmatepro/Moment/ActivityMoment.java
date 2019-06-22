package com.example.afiqur.tourmatepro.Moment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afiqur.tourmatepro.AboutActivity;
import com.example.afiqur.tourmatepro.Data;
import com.example.afiqur.tourmatepro.Expense.AddMomentExpenseActivity;
import com.example.afiqur.tourmatepro.Expense.ExpenseActivity;
import com.example.afiqur.tourmatepro.LoginActivity;
import com.example.afiqur.tourmatepro.NearBy.MapsActivity;
import com.example.afiqur.tourmatepro.PhotoMoment.AddPhotoMoment;
import com.example.afiqur.tourmatepro.R;
import com.example.afiqur.tourmatepro.TravelEvent.CustomListAdapterDialog;
import com.example.afiqur.tourmatepro.TravelEvent.UpdateTravelEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.afiqur.tourmatepro.Data.MomentDetails;
import static com.example.afiqur.tourmatepro.Data.MomentListDelete;
import static com.example.afiqur.tourmatepro.Data.MomentListUrl;
import static com.example.afiqur.tourmatepro.LoginActivity.LOGINPREF;

public class ActivityMoment extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String eventName;
    private String eventFrom;
    private String eventTo;
    private double eventBudget;
    private TextView tvEventName,tvUserName;
    private TextView tvEventFrom;
    private TextView tvEventTo;
    private TextView tvEventBudgetAmount;
    private ListView lvMomentList;
    ImageButton ibEmergency,imageButtonDelete,imageButtonUpdate;
    String EventId, EventName, StartDate, EndDate, Budget, Contact;
    ArrayList<DataModelMoment> dataModelMoments;
    ListView listView;
    private static MomentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvEventName = (TextView) findViewById(R.id.tvExpenseEventName);
        tvEventFrom = (TextView) findViewById(R.id.tvExpenseEventFrom);
        tvEventTo = (TextView) findViewById(R.id.tvExpenseEventTo);
        tvEventBudgetAmount = (TextView) findViewById(R.id.tvExpenseEventBudgetAmount);
        lvMomentList = (ListView) findViewById(R.id.lvMomentList);
        ibEmergency = (ImageButton) findViewById(R.id.ibEmergency);
        imageButtonDelete = (ImageButton) findViewById(R.id.imageButtonUpdate);///swaped
        imageButtonUpdate = (ImageButton) findViewById(R.id.imageButtonDelete);///swaped


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
                Contact = extras.getString("contact");
            }
        } else {
            EventId = (String) savedInstanceState.getSerializable("mainEventID");
            EventName = (String) savedInstanceState.getSerializable("eventName");
            StartDate = (String) savedInstanceState.getSerializable("fromDate");
            EndDate = (String) savedInstanceState.getSerializable("toDate");
            Budget = (String) savedInstanceState.getSerializable("budget");
            Contact = (String) savedInstanceState.getSerializable("contact");
        }


        tvEventName.setText(EventName);
        tvEventFrom.setText(StartDate);
        tvEventTo.setText(EndDate);
        tvEventBudgetAmount.setText(Budget);
        //Toast.makeText(this, EventId, Toast.LENGTH_SHORT).show();
        ibEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emergencyNumber = Contact;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + emergencyNumber));
                if (ActivityCompat.checkSelfPermission(ActivityMoment.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);

            }
        });

        imageButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityMoment.this, UpdateTravelEvent.class);
                intent.putExtra("mainEventID", EventId);
                intent.putExtra("eventName", EventName);
                intent.putExtra("fromDate", StartDate);
                intent.putExtra("toDate", EndDate);
                intent.putExtra("budget", Budget);
                intent.putExtra("contact", Contact);
                //Toast.makeText(ActivityMoment.this, EventId, Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityMoment.this);
                alertDialogBuilder.setMessage("Are you sure,You wanted to Delete");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                DeleteItemMoment();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> myListOfItems = new ArrayList<>();
                myListOfItems.add("Add Photo Moment");
                myListOfItems.add("Add Expense Moment");

                final Dialog dialog = new Dialog(ActivityMoment.this);

                View view1 = getLayoutInflater().inflate(R.layout.dialog_main, null);

                ListView lv = (ListView) view1.findViewById(R.id.custom_list);

                // Change MyActivity.this and myListOfItems to your own values
                CustomListAdapterDialog clad = new CustomListAdapterDialog(ActivityMoment.this,
                        myListOfItems);

                lv.setAdapter(clad);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            Intent intent = new Intent(ActivityMoment.this, AddPhotoMoment.class);
                            intent.putExtra("EventID", EventId);
                            //Toast.makeText(ActivityMoment.this, EventId, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            dialog.dismiss();
                        } else {
                            Intent intent = new Intent(ActivityMoment.this, AddMomentExpenseActivity.class);
                            intent.putExtra("EventID", EventId);
                            //Toast.makeText(ActivityMoment.this, EventId, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                });

                dialog.setContentView(view1);
                dialog.setTitle("Select an option");

                dialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tvUserName = (TextView) header.findViewById(R.id.tvEvnetUserName);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userame = preferences.getString("username", "User");
        tvUserName.setText(userame);


        listView = (ListView) findViewById(R.id.lvMomentList);


        dataModelMoments = new ArrayList<>();


    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadMomentDetails();
        loadAllMoment();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void   DeleteItemMoment(){

       // dataModelMoments.clear();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MomentListDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.trim().toString().equals("deleted")) {


                           finish();


                        } else {


                            Toast.makeText(ActivityMoment.this, "Try again", Toast.LENGTH_SHORT).show();


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ActivityMoment.this, "error" + error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID", userid.trim());
                params.put("EVentID", EventId.trim());


                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest);




    }


    private void LoadMomentDetails(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MomentDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.trim().toString().equals("no")) {


                            Toast.makeText(ActivityMoment.this, "No Details", Toast.LENGTH_SHORT).show();


                        } else {


                            try {

                                JSONArray notiJson = new JSONArray(response.toString());


                                for (int i = 0; i < notiJson.length(); i++) {
                                    JSONObject every_noti = notiJson.getJSONObject(i);
                                    String name = every_noti.getString("name");
                                    String startdate = every_noti.getString("startdate");
                                    String enddate = every_noti.getString("enddate");
                                    String budget = every_noti.getString("budget");
                                    String econtact = every_noti.getString("econtact");

                                    tvEventName.setText(name);
                                    tvEventFrom.setText(startdate);
                                    tvEventTo.setText(enddate);
                                    tvEventBudgetAmount.setText(budget);
                                    Contact = econtact;

                                }





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ActivityMoment.this, "error" + error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID", userid.trim());
                params.put("EVentID", EventId.trim());


                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest);

    }


    private void loadAllMoment() {

        dataModelMoments.clear();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MomentListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.trim().toString().equals("no")) {


                            Toast.makeText(ActivityMoment.this, "No History", Toast.LENGTH_SHORT).show();


                        } else {


                            try {

                                JSONArray notiJson = new JSONArray(response.toString());


                                for (int i = 0; i < notiJson.length(); i++) {
                                    JSONObject every_noti = notiJson.getJSONObject(i);
                                    String photo_caption = every_noti.getString("photo_caption");
                                    String location = every_noti.getString("photo_name");

                                    String photoUrl = Data.ip2.concat(location);
                                    //Log.v("url", photoUrl);
                                    //Toast.makeText(ActivityMoment.this, photoUrl, Toast.LENGTH_SHORT).show();
                                    dataModelMoments.add(new DataModelMoment(photo_caption, photoUrl));

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

                        Toast.makeText(ActivityMoment.this, "error" + error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID", userid.trim());
                params.put("EVentID", EventId.trim());


                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest);

    }


    public void intializeAdapter() {

        adapter = new MomentAdapter(dataModelMoments, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModelMoment dataModelMoment = dataModelMoments.get(position);


            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_moment, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_photo_moment) {
            Intent intent = new Intent(this, AddPhotoMoment.class);
            intent.putExtra("EventID", EventId);
            //Toast.makeText(ActivityMoment.this, EventId, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else if (id == R.id.nav_expense_moment) {
            Intent intent = new Intent(this, AddMomentExpenseActivity.class);
            intent.putExtra("EventID", EventId);
            //Toast.makeText(ActivityMoment.this, EventId, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else if (id == R.id.nav_weather_moment) {
            Toast.makeText(this, "Incomplete", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_nearby) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_all_expense) {
            Intent i = new Intent(ActivityMoment.this, ExpenseActivity.class);
            // String strName = null;
            i.putExtra("mainEventID", EventId);
            i.putExtra("eventName", eventName);
            i.putExtra("fromDate", StartDate);
            i.putExtra("toDate", EndDate);
            i.putExtra("budget", Budget);
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(i);
        } else if (id == R.id.nav_logout_moment) {

            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            SharedPreferences.Editor all_data = sharedPreferences.edit();



            all_data.clear().commit();
            Intent intent = new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
           finish();

        } else if (id == R.id.nav_about_moment) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void exp(View view) {

        Intent i = new Intent(ActivityMoment.this, ExpenseActivity.class);
        // String strName = null;
        i.putExtra("mainEventID", EventId);
        i.putExtra("eventName", eventName);
        i.putExtra("fromDate", StartDate);
        i.putExtra("toDate", EndDate);
        i.putExtra("budget", Budget);
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(i);
    }
}
