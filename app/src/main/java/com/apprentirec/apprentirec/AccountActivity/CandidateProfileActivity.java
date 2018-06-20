package com.apprentirec.apprentirec.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apprentirec.apprentirec.CV.ConsultCV;
import com.apprentirec.apprentirec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CandidateProfileActivity extends AppCompatActivity {

    public static String EmailUser;
    private FirebaseFirestore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        store = FirebaseFirestore.getInstance();

        final TextView nameCandidate = (TextView)findViewById(R.id.nameCandidate);

        Intent i = getIntent();
        EmailUser = i.getStringExtra(LoginActivity.CLE);

        final DocumentReference docRef = store.collection("Candidat").document(EmailUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()) {
                    String firstNameC = document.get("firstNameC").toString();
                    String nameC = document.get("nameC").toString();
                    nameCandidate.setText(firstNameC + " " + nameC);
                }
        Button ButCV = (Button) findViewById(R.id.cvCandidate);

        ButCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CandidateProfileActivity.this, ConsultCV.class));
                onPause();
            }
        });

        Button ButLogout = (Button) findViewById(R.id.logoutCandidate);

                ButLogout.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        finish();
                    }
                });

    }
});
    }
}
