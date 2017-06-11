package com.boringapp.boringapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAndConditionsActivity extends AppCompatActivity {

    @BindView(R.id.btnAcceptTerms) Button btnAcceptTerms;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        ButterKnife.bind(this);

        sharedPref = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);

        if (!sharedPref.getBoolean("isFirstTime", true))
        {
            Intent intent = new Intent(TermsAndConditionsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnAcceptTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("isFirstTime", false);
                editor.apply();

                Intent intent = new Intent(TermsAndConditionsActivity.this, InviteActivity.class);
                startActivity(intent);
            }
        });


    }


}
