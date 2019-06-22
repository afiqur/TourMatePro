package com.example.afiqur.tourmatepro.TravelEvent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
import com.example.afiqur.tourmatepro.AboutActivity;
import com.example.afiqur.tourmatepro.Data;
import com.example.afiqur.tourmatepro.LoginActivity;
import com.example.afiqur.tourmatepro.NearBy.MapsActivity;
import com.example.afiqur.tourmatepro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.afiqur.tourmatepro.LoginActivity.LOGINPREF;

public class TravelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Travel> dataModels;
    ListView listView;
    private static TravelAdapter adapter;
    Context mContext;
    TextView tvUserName;


    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    //public static final String LOGINPREF = "Login";
    //public static final String USER_EMAIL = "userEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this.getApplicationContext();

        sharedPreferences = getSharedPreferences(LOGINPREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TravelActivity.this, AddTravelEvent.class));

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

        listView=(ListView)findViewById(R.id.lvEventList);




        dataModels= new ArrayList<>();

        // getTravelList();

    }

    @Override
    protected void onStart() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(mContext, "dsfhsdfghgv", Toast.LENGTH_SHORT).show();
            }
        });

        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getTravelList();

    }

    @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }


    private void getTravelList() {

        dataModels.clear();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = preferences.getString("userid", "0");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.TravelList,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        if (response.trim().toString().equals("no")) {

                        } else {

                            try {

                                JSONArray notiJson = new JSONArray(response.toString());

                                for(int i = 0; i<notiJson.length(); i++){
                                    JSONObject every_noti = notiJson.getJSONObject(i);
                                    String eventid = every_noti.getString("eventid");
                                    String eventname = every_noti.getString("eventname");
                                    String  startdate = every_noti.getString("startdate");
                                    String  enddate = every_noti.getString("enddate");
                                    String  budget = every_noti.getString("budget");
                                    String  econtact = every_noti.getString("econtact");

                                    dataModels.add(new Travel(eventid,eventname,startdate,enddate,budget,econtact));

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



                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid.trim());


                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(TravelActivity.this);
        requestQueue2.add(stringRequest);

    }


    public void intializeAdapter(){

        adapter= new TravelAdapter(dataModels,mContext);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Travel dataModel= dataModels.get(position);


            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.travel, menu);
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

        if (id == R.id.nav_travel_event) {
            Intent intent = new Intent(this, AddTravelEvent.class);
            //intent.putExtra(Constants.USER_EMAIL, email);
            startActivity(intent);
        } else if (id == R.id.nav_weather) {
            //Intent intent = new Intent(this, WeatherActivity.class);
            //startActivity(intent);
            Toast.makeText(mContext, "Incomplete", Toast.LENGTH_SHORT).show();
        }   else if (id == R.id.nav_nearby) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            SharedPreferences.Editor all_data = sharedPreferences.edit();

            all_data.clear().commit();
            Intent intent = new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
