package com.boringapp.boringapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAndConditionsActivity extends AppCompatActivity {

    @BindView(R.id.btnAcceptTerms) Button btnAcceptTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        ButterKnife.bind(this);

        btnAcceptTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsAndConditionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}
