/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.boringapp.boringapp.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boringapp.boringapp.R;
import com.boringapp.boringapp.data.ActUser;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Sun on 6/11/2017 AD.
 */

public class ActUserAdapter extends ArrayAdapter<ActUser> {

    public ActUserAdapter(Context context, ArrayList<ActUser> users){
        super(context, 0, users);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ActUser user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_act_user, parent, false);
        }
        // Lookup view for data population
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtPoint = (TextView) convertView.findViewById(R.id.txtPoint);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.imageView);
        // Populate the data into the template view using the data object

        txtName.setText(user.name);
        txtPoint.setText("" + user.points);
        Glide.with(getContext()).load(user.imageUrl).into(imgView);

        // Return the completed view to render on screen
        return convertView;
    }



}
