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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.boringapp.boringapp.adapters.RewardAdapter;
import com.boringapp.boringapp.data.Reward;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sun on 6/11/2017 AD.
 */

public class RewardsListActivity extends Activity {

    @BindView(R.id.listView)
    ListView mListView;

    private RewardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_list);
        ButterKnife.bind(this);

        ArrayList<Reward> rewards = new ArrayList<Reward>();
        rewards.add(new Reward(
                "FLASH! Tesco Lotus: 30THB Discount with no minimum purchase",
                "Tesco Lotus", 10,
                "https://storage.googleapis.com/rabbit-rewards-v3/items/758/image_1_en-1496806597.png"));
        rewards.add(new Reward(
                "FLASH! topvalue.com: get 150 Baht Discount when purchase over 500 Baht from www"
                        + ".topvalue.com",
                "topvalue.com", 20,
                "https://storage.googleapis.com/rabbit-rewards-v3/items/717/image_1_en-1494819261.png"));
        rewards.add(new Reward(
                "Coffee World: Free Hot Americano (Presto size) Coffee World",
                "Coffee World", 60,
                "https://storage.googleapis.com/rabbit-rewards-v3/items/342/image_1_en-1487834453.png"));
        rewards.add(new Reward(
                "Lazgam: Free Laser Game (value 350 Baht)",
                "LaZgam", 30,
                "https://storage.googleapis.com/rabbit-rewards-v3/items/716/image_1_en-1494302979.jpg"));
        rewards.add(new Reward(
                "Carl's Jr: 60 Baht Discount on Super Star with Cheese Burger Combo",
                "Carl's Jr", 30,
                "https://storage.googleapis.com/rabbit-rewards-v3/items/756/image_1_en-1496636743.jpg"));
        rewards.add(new Reward(
                "Shop at 24: 100 Baht Discount when purchase over 500 Baht at www.shopat24.com",
                "ShopAt24", 30,
                "https://storage.googleapis.com/rabbit-rewards-v3/items/757/image_1_en-1496721991.jpg"));
        rewards.add(new Reward(
                "Chester's: Only 1.- for Hot Dog",
                "Chester's", 99,
                "https://storage.googleapis.com/rabbit-rewards-v3/items/713/image_1_en-1494216965.png"));

        mAdapter = new RewardAdapter(this, rewards);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RewardsListActivity.this, RedeemQRCodeActivity.class);
                startActivity(intent);
            }
        });

    }
}
