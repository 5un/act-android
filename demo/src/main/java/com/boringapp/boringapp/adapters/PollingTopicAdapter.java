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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.boringapp.boringapp.R;
import com.boringapp.boringapp.data.PollingTopic;

import java.util.ArrayList;

/**
 * Created by Sun on 6/11/2017 AD.
 */

public class PollingTopicAdapter extends ArrayAdapter<PollingTopic> {

    public PollingTopicAdapter(Context context, ArrayList<PollingTopic> users){
        super(context, 0, users);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final PollingTopic topic = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_vote, parent, false);
        }

        TextView txtTopic = (TextView) convertView.findViewById(R.id.txtTopic);
        TextView txtVotePoint = (TextView) convertView.findViewById(R.id.txtPointsFromTopicVote);
        TextView txtSharePoint = (TextView) convertView.findViewById(R.id.txtPointsFromSharing);
        TextView txtNumVotes = (TextView) convertView.findViewById(R.id.txtNumVotes);
        ImageButton btnVoteTopic = (ImageButton) convertView.findViewById(R.id.btnVoteTopic);

        btnVoteTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You got " + topic.votePoint + " " + (topic.votePoint > 1 ? "pts": "pt"), Toast.LENGTH_SHORT).show();
            }
        });

        txtTopic.setText("" + topic.topic);
        txtVotePoint.setText("" + topic.votePoint);
        txtSharePoint.setText("" + topic.sharePoint);
        txtNumVotes.setText("" + topic.numVotes);


        // Return the completed view to render on screen
        return convertView;
    }



}

