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

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.boringapp.boringapp.adapters.ActUserAdapter;
import com.boringapp.boringapp.data.ActUser;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

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

    @BindView(R.id.button3)
    Button btnInviteFriend;

    @BindView(R.id.listView)
    ListView listView;

    ActUserAdapter mAdapter;

    CallbackManager callbackManager;

    private ArrayList<ActUser> leaderboard;
    private ArrayList<ActUser> activeUsers;

    public static FriendsFragment newInstance() {
        FriendsFragment f = new FriendsFragment();
        return f;
    }

    ViewPager pager;

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");

        //TODO Mock Friends
        leaderboard = new ArrayList<ActUser>();
        leaderboard.add(new ActUser(
                "John Doe",
                500000,
                "https://randomuser.me/api/portraits/men/1.jpg"));
        leaderboard.add(new ActUser(
                "Mary Jane",
                498000,
                "https://randomuser.me/api/portraits/women/2.jpg"));
        leaderboard.add(new ActUser(
                "Clark Kent",
                375000,
                "https://randomuser.me/api/portraits/men/3.jpg"));
        leaderboard.add(new ActUser(
                "Lois Lane",
                282000,
                "https://randomuser.me/api/portraits/women/4.jpg"));
        leaderboard.add(new ActUser(
                "Peter Parker",
                100023,
                "https://randomuser.me/api/portraits/men/5.jpg"));
        leaderboard.add(new ActUser(
                "Logan",
                100023,
                "https://randomuser.me/api/portraits/men/6.jpg"));
        leaderboard.add(new ActUser(
                "Natasha Romanov",
                100023,
                "https://randomuser.me/api/portraits/women/7.jpg"));

        activeUsers = new ArrayList<ActUser>();
        activeUsers.add(new ActUser(
                "James Smith",
                345,
                "https://randomuser.me/api/portraits/men/8.jpg"));
        activeUsers.add(new ActUser(
                "Jane Pickering",
                2523,
                "https://randomuser.me/api/portraits/women/9.jpg"));
        activeUsers.add(new ActUser(
                "Dan Kumamoto",
                343,
                "https://randomuser.me/api/portraits/men/10.jpg"));
        activeUsers.add(new ActUser(
                "Naomi Ray",
                35983,
                "https://randomuser.me/api/portraits/women/11.jpg"));
        activeUsers.add(new ActUser(
                "Shaun Bonham",
                244,
                "https://randomuser.me/api/portraits/men/12.jpg"));
        activeUsers.add(new ActUser(
                "Andy Lee",
                7688,
                "https://randomuser.me/api/portraits/men/13.jpg"));
        activeUsers.add(new ActUser(
                "Zoe Zimmerman",
                1123,
                "https://randomuser.me/api/portraits/women/14.jpg"));

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);
        //TODO Initialize Friend Data

        pager = (ViewPager) container;

        mAdapter = new ActUserAdapter(getContext(), new ArrayList<ActUser>());
        mAdapter.addAll(leaderboard);
        listView.setAdapter(mAdapter);

        btnFriendsTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.clear();
                mAdapter.addAll(leaderboard);
            }
        });

        btnFriendsActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.clear();
                mAdapter.addAll(activeUsers);
            }
        });

        callbackManager = CallbackManager.Factory.create();

        final FriendsFragment _self = this;
        btnInviteFriend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                String appLinkUrl, previewImageUrl;

                appLinkUrl = "https://fb.me/1957657991151520";
                previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";

                if (AppInviteDialog.canShow()) {
                    AppInviteContent content = new AppInviteContent.Builder()
                            .setApplinkUrl(appLinkUrl)
                            .setPreviewImageUrl(previewImageUrl)
                            .build();
                    AppInviteDialog appInviteDialog = new AppInviteDialog(_self);
                    appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>(){
                        @Override
                        public void onSuccess(AppInviteDialog.Result result) {
                            Toast.makeText(getContext(), "Sent Invite your friends", Toast.LENGTH_SHORT).show();
                            pager.setCurrentItem(0);
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {

                        }
                    });
                    appInviteDialog.show(content);
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
