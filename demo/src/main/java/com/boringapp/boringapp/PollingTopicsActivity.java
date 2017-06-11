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

package com.boringapp.boringapp;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;

import com.boringapp.boringapp.adapters.PollingTopicAdapter;
import com.boringapp.boringapp.data.PollingTopic;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sun on 6/11/2017 AD.
 */

public class PollingTopicsActivity extends Activity {

    @BindView(R.id.listView)
    ListView mListView;

    private PollingTopicAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling_topics);
        ButterKnife.bind(this);

        ArrayList<PollingTopic> topics = new ArrayList<PollingTopic>();
        topics.add(new PollingTopic("House Bill 20 Law to prevent deforestation", 1, 1, 370825));
        topics.add(new PollingTopic("Senate Bill 434 Minimum energy efficiency bill", 2, 2, 290324));
        topics.add(new PollingTopic("Unilever Petition 32 Prevent waste water dumping", 2, 1, 19568));
        topics.add(new PollingTopic("House Bill 20 Law to prevent deforestation", 1, 1, 370825));
        topics.add(new PollingTopic("Senate Bill 434 Minimum energy efficiency bill", 2, 2, 290324));
        topics.add(new PollingTopic("Unilever Petition 32 Prevent waste water dumping", 2, 1, 19568));
        topics.add(new PollingTopic("House Bill 20 Law to prevent deforestation", 2, 1, 370825));

        mAdapter = new PollingTopicAdapter(this, topics);
        mListView.setAdapter(mAdapter);

    }

}
