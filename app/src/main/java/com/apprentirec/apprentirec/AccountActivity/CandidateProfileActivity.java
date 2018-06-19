package com.apprentirec.apprentirec.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apprentirec.apprentirec.CV.ConsultCV;
import com.apprentirec.apprentirec.R;

public class CandidateProfileActivity extends AppCompatActivity {

    public static String EmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        Intent i = getIntent();
        EmailUser = i.getStringExtra(LoginActivity.CLE);

        Button ButCV = (Button) findViewById(R.id.cvCandidate);

        ButCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();
                startActivity(new Intent(CandidateProfileActivity.this, ConsultCV.class));
            }
        });

    }
}
