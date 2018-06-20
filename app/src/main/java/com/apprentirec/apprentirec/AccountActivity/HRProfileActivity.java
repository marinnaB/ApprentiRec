package com.apprentirec.apprentirec.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apprentirec.apprentirec.CV.ConsultCV;
import com.apprentirec.apprentirec.ListOfferHR;
import com.apprentirec.apprentirec.R;

public class HRProfileActivity extends AppCompatActivity {

    public static String EmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrprofile);

        Intent i = getIntent();
        EmailUser = i.getStringExtra(LoginActivity.CLE);

        Button seeJobsHR = (Button) findViewById(R.id.seeJobsHR);

        seeJobsHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HRProfileActivity.this, ListOfferHR.class));
                onPause();
            }
        });
    }
}
