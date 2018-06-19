package com.apprentirec.apprentirec.CV;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apprentirec.apprentirec.AccountActivity.CandidateProfileActivity;

import com.apprentirec.apprentirec.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ConsultCV extends AppCompatActivity {

    private static final String TAG = "Consult_Class_java";
    private FirebaseFirestore store;
    private TextView t_Name, t_FirstName, t_Mail, t_Phone, t_Formation, t_Experience, t_Skill, t_Language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_cv);

        //auth = FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();

        String Mail = CandidateProfileActivity.EmailUser;

        t_Name = (TextView) findViewById(R.id.text_nomCandidat);
        t_FirstName = (TextView) findViewById(R.id.text_prenomCandidat);
        t_Mail = (TextView) findViewById(R.id.text_adrMailCandidat);
        t_Phone = (TextView) findViewById(R.id.text_numCandidat);

        t_Formation = (TextView) findViewById(R.id.edit_Formation);
        t_Experience = (TextView) findViewById(R.id.edit_Experience);
        t_Skill = (TextView) findViewById(R.id.edit_Competence);
        t_Language = (TextView) findViewById(R.id.edit_Langue);

        final DocumentReference docRef = store.collection("Candidat").document(Mail).collection("CV").document("CV");

        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        t_Name.setText(documentSnapshot.get("Nom").toString());
                        t_FirstName.setText(documentSnapshot.get("Prenom").toString());
                        t_Mail.setText(documentSnapshot.get("Email").toString());
                        t_Phone.setText(documentSnapshot.get("Tel").toString());

                        t_Formation.setText(documentSnapshot.get("Formation").toString());
                        t_Experience.setText(documentSnapshot.get("Experience").toString());
                        t_Skill.setText(documentSnapshot.get("Competence").toString());
                        t_Language.setText(documentSnapshot.get("Langue").toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("Nom", t_Name);
                        data.put("Prenom",t_FirstName);
                        data.put("Email",t_Mail);
                        data.put("Tel",t_Phone);

                        data.put("Formation", t_Formation);
                        data.put("Experience", t_Experience);
                        data.put("Competence", t_Skill);
                        data.put("Langue", t_Language);

                        docRef.set(data);
                        Log.w(TAG, "CV's Data Created", e);
                    }
                });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.ModifyCV);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intModify = new Intent(ConsultCV.this, CreerCV.class);
                    startActivity(intModify);
                    onPause();
                } catch (Exception e){
                    Log.v(TAG, e.getMessage());
                }
            }
        });
    }
}
