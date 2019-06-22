package com.example.afiqur.tourmatepro.TravelEvent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.afiqur.tourmatepro.Moment.ActivityMoment;
import com.example.afiqur.tourmatepro.R;

import java.util.ArrayList;


/**
 * Created by Istiyak on 07/05/17.
 */


public class TravelAdapter extends ArrayAdapter<Travel> implements View.OnClickListener{

    private ArrayList<Travel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView tvPlaceText;
        TextView tvFromDate;
        TextView tvToDate;
        TextView tvBudgetText;
        RelativeLayout customRowEvent;
    }



    public TravelAdapter(ArrayList<Travel> data, Context context) {
        super(context, R.layout.custom_event_row, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {


        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Travel travel =(Travel)object;


        switch (v.getId())
        {

            case R.id.lvEventList:



                break;


        }



    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Get the data item for this position
        final Travel travel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_event_row, parent, false);
            viewHolder.tvPlaceText = (TextView) convertView.findViewById(R.id.tvPlaceText);
            viewHolder.tvFromDate = (TextView) convertView.findViewById(R.id.tvFromDate);
            viewHolder.tvToDate = (TextView) convertView.findViewById(R.id.tvToDate);
            viewHolder.tvBudgetText = (TextView) convertView.findViewById(R.id.tvBudgetText);
            viewHolder.customRowEvent = (RelativeLayout) convertView.findViewById(R.id.customRowEvent);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        viewHolder.tvPlaceText.setText(travel.getEventname());
        viewHolder.tvFromDate.setText(travel.getStartdate());
        viewHolder.tvToDate.setText(travel.getEnddate());
        viewHolder.tvBudgetText.setText("Budget:"+travel.getBudget());


        final View finalConvertView = convertView;
        viewHolder.customRowEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(v.getContext(), ActivityMoment.class);
               // String strName = null;
                i.putExtra("mainEventID", travel.getEventid());
                i.putExtra("eventName", travel.getEventname());
                i.putExtra("fromDate", travel.getStartdate());
                i.putExtra("toDate", travel.getEnddate());
                i.putExtra("budget", travel.getBudget());
                i.putExtra("contact", travel.getEcontact());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                v.getContext().startActivity(i);

               // Intent i = new Intent().setClass(.getApplication(), TestUserProfileScreenActivity.class);


// Launch the new activity and add the additional flags to the intent
             //   mActivity.getApplication().startActivity(i);

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }


}
