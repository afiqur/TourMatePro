package com.example.afiqur.tourmatepro.Moment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afiqur.tourmatepro.Data;
import com.example.afiqur.tourmatepro.Expense.ExpenseActivity;
import com.example.afiqur.tourmatepro.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Istiyak on 07/05/17.
 */


public class MomentAdapter extends ArrayAdapter<DataModelMoment> implements View.OnClickListener{

    private ArrayList<DataModelMoment> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView tvMomentDetails;
        ImageView ivMomentImage;
        ProgressBar progressBar2;
        RelativeLayout moment_photo_row;
    }



    public MomentAdapter(ArrayList<DataModelMoment> data, Context context) {
        super(context, R.layout.custom_moment_row, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {


        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModelMoment dataModelMoment =(DataModelMoment)object;

        switch (v.getId())
        {

            case R.id.item_info:

                Snackbar.make(v, dataModelMoment.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;


        }


    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final DataModelMoment dataModelMoment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_moment_row, parent, false);
            viewHolder.tvMomentDetails = (TextView) convertView.findViewById(R.id.tvMomentDetails);
            viewHolder.ivMomentImage = (ImageView) convertView.findViewById(R.id.ivMomentImage);
            viewHolder.progressBar2 = (ProgressBar) convertView.findViewById(R.id.progressBar2);
            viewHolder.moment_photo_row = (RelativeLayout) convertView.findViewById(R.id.moment_photo_row);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        viewHolder.tvMomentDetails.setText(dataModelMoment.getName());
      //  viewHolder.progressBar2.setVisibility(View.GONE);

        Picasso.with(mContext)
                .load(dataModelMoment.getLocation())
                .into(viewHolder.ivMomentImage,  new ImageLoadedCallback(viewHolder.progressBar2) {
                    @Override
                    public void onSuccess() {
                        if (viewHolder.progressBar2 != null) {
                            viewHolder.progressBar2.setVisibility(View.GONE);
                        }
                    }
                });


        viewHolder.moment_photo_row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

              //  deletePhoto(dataModelMoment.get);
                return false;
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }


    public void deletePhoto(final String expenseID){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getContext(),"You clicked yes "+expenseID,Toast.LENGTH_LONG).show();
                       // deletPhotioDataFromServer(expenseID);
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


    public void deletPhotioDataFromServer(final String expenseID){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String userid = preferences.getString("userid", "0");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.ExpenseDeleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.trim().toString().equals("success")) {

                           // ((ActivityMoment)mContext).loadAllExpense();

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

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public  ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }

}
