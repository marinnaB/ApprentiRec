package com.apprentirec.apprentirec.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apprentirec.apprentirec.R;

public class HRProfileActivity extends AppCompatActivity {

    public static String EmailUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        Intent i = getIntent();
        EmailUser = i.getStringExtra(LoginActivity.CLE);

        Button seeJobsHr = (Button) findViewById(R.id.seeJobsHR);

        seeJobsHr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Button ButLogout = (Button) findViewById(R.id.logoutHR);
        ButLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
}