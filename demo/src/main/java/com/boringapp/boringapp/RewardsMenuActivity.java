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
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sun on 6/11/2017 AD.
 */

public class RewardsMenuActivity extends Activity {

    @BindView(R.id.btnRedeemFood)
    ImageButton btnRedeemFood;

    @BindView(R.id.btnRedeemShopping)
    ImageButton btnRedeemShopping;

    @BindView(R.id.btnRedeemFlight)
    ImageButton btnRedeemFlight;

    @BindView(R.id.btnRedeemOther)
    ImageButton btnRedeemOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_menu);
        ButterKnife.bind(this);

        View.OnClickListener oc = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RewardsMenuActivity.this, RewardsListActivity.class);
                startActivity(intent);
            }
        };

        btnRedeemFood.setOnClickListener(oc);
        btnRedeemShopping.setOnClickListener(oc);
        btnRedeemFlight.setOnClickListener(oc);
        btnRedeemOther.setOnClickListener(oc);

    }

}
