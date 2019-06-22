package com.example.afiqur.tourmatepro.Expense;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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


/**
 * Created by Istiyak on 07/05/17.
 */


public class ExpenseAdapter extends ArrayAdapter<Expense> implements View.OnClickListener{

    private ArrayList<Expense> dataSet;
    Context mContext;
    ListView lvExpenseList;

    // View lookup cache
    private static class ViewHolder {
        TextView tvExpenseTitle;
        TextView tvExpenseAmount;
        LinearLayout expense_row;
    }



    public ExpenseAdapter(ArrayList<Expense> data, Context context) {
        super(context, R.layout.custom_expense_row, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {


        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Expense dataModel=(Expense)object;



    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Expense dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_expense_row, parent, false);
            viewHolder.tvExpenseTitle = (TextView) convertView.findViewById(R.id.tvExpenseTitle);
            viewHolder.tvExpenseAmount = (TextView) convertView.findViewById(R.id.tvExpenseAmount);
            viewHolder.expense_row = (LinearLayout) convertView.findViewById(R.id.expense_row);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        viewHolder.tvExpenseTitle.setText(dataModel.getExpenseTitle());
        viewHolder.tvExpenseAmount.setText(dataModel.getExpenseCost());

        viewHolder.expense_row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                deleteexpense(dataModel.getExpenseId());

                return false;
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }


    public void deleteexpense(final String expenseID){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(getContext(),"You clicked yes "+expenseID,Toast.LENGTH_LONG).show();
                                deleteExpenseDataFromServer(expenseID);
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


    public void deleteExpenseDataFromServer(final String expenseID){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String userid = preferences.getString("userid", "0");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.ExpenseDeleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.trim().toString().equals("success")) {

                            ((ExpenseActivity)mContext).loadAllExpense();

                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(),"Error"+error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID",userid.trim());
                params.put("expenseID",expenseID.trim());

                return params;
            }

        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(stringRequest);
    }


}
