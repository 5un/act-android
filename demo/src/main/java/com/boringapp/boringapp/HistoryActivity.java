package com.boringapp.boringapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.boringapp.boringapp.adapters.ActivityLogAdapter;
import com.boringapp.boringapp.adapters.PollingTopicAdapter;
import com.boringapp.boringapp.data.ActivityLog;
import com.boringapp.boringapp.data.PollingTopic;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.historyListView)
    ListView historyListView;

    private ActivityLogAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        ArrayList<ActivityLog> activities = new ArrayList<ActivityLog>();
        activities.add(new ActivityLog("08 JUN 17", "Namthip 500 ml.", 2));
        activities.add(new ActivityLog("07 JUN 17", "Voted Bill 0525", 1));
        activities.add(new ActivityLog("06 JUN 17", "Shared Bill 3467", 3));
        activities.add(new ActivityLog("05 JUN 17", "Kleenex Bio", 1));
        activities.add(new ActivityLog("04 JUN 17", "Recycled Paper", 2));
        activities.add(new ActivityLog("03 JUN 17", "Reusable bag", 2));
        activities.add(new ActivityLog("02 JUN 17", "Voted Bill 1542", 1));

        mAdapter = new ActivityLogAdapter(this, activities);
        historyListView.setAdapter(mAdapter);

    }
}
