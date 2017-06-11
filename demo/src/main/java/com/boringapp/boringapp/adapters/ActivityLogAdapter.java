package com.boringapp.boringapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.boringapp.boringapp.R;
import com.boringapp.boringapp.data.ActivityLog;

import java.util.ArrayList;

/**
 * Created by Pakapon on 6/11/2017 AD.
 */

public class ActivityLogAdapter extends ArrayAdapter<ActivityLog> {

    public ActivityLogAdapter(Context context, ArrayList<ActivityLog> activities){
        super(context, 0, activities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ActivityLog activity = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_activity, parent, false);
        }

        TextView activityDateTextView = (TextView) convertView.findViewById(R.id.activityDateTextView);
        TextView activityDescriptionTextView = (TextView) convertView.findViewById(R.id.activityDescriptionTextView);
        TextView activityPointTextView = (TextView) convertView.findViewById(R.id.activityPointTextView);

        activityDateTextView.setText(activity.dateString);
        activityDescriptionTextView.setText(activity.description);
        activityPointTextView.setText(activity.point + " " + (activity.point > 1 ? "pts" : "pt"));

        // Return the completed view to render on screen
        return convertView;
    }
}
