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

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.boringapp.boringapp.adapters.ActUserAdapter;
import com.boringapp.boringapp.data.ActUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sun on 6/10/2017 AD.
 */

public class FriendsFragment extends Fragment {

    @BindView(R.id.btnFriendsTop)
    Button btnFriendsTop;

    @BindView(R.id.btnFriendsActive)
    Button btnFriendsActive;

    @BindView(R.id.listView)
    ListView listView;

    ActUserAdapter mAdapter;

    private ArrayList<ActUser> leaderboard;

    public static FriendsFragment newInstance() {
        FriendsFragment f = new FriendsFragment();
        return f;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");

        //TODO Mock Friends
        leaderboard = new ArrayList<ActUser>();
        leaderboard.add(new ActUser(
                "John Doe 1",
                500000,
                "https://randomuser.me/api/portraits/men/1.jpg"));
        leaderboard.add(new ActUser(
                "John Doe 2",
                498000,
                "https://randomuser.me/api/portraits/men/2.jpg"));
        leaderboard.add(new ActUser(
                "John Doe 3",
                375000,
                "https://randomuser.me/api/portraits/men/3.jpg"));
        leaderboard.add(new ActUser(
                "John Doe 4",
                282000,
                "https://randomuser.me/api/portraits/men/4.jpg"));
        leaderboard.add(new ActUser(
                "John Doe 5",
                100023,
                "https://randomuser.me/api/portraits/men/5.jpg"));

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);
        //TODO Initialize Friend Data

        mAdapter = new ActUserAdapter(getContext(), leaderboard);
        listView.setAdapter(mAdapter);

        return view;
    }

}
