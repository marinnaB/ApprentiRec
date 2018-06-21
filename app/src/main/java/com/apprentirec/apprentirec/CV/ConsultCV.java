package com.apprentirec.apprentirec.CV;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apprentirec.apprentirec.AccountActivity.CandidateProfileActivity;

import com.apprentirec.apprentirec.AccountActivity.SignupActivity;
import com.apprentirec.apprentirec.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ConsultCV extends AppCompatActivity {

    private static final String TAG = "Consult_Class_java";
    private FirebaseFirestore store;
    private Boolean error;
    private TextView t_Name, t_FirstName, t_Mail, t_Phone, t_Formation, t_Experience, t_Skill, t_Language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_cv);
        store=FirebaseFirestore.getInstance();

        error = true;

        String Mail = CandidateProfileActivity.EmailUser;

        t_Name = (TextView) findViewById(R.id.text_nomCandidat);
        t_FirstName = (TextView) findViewById(R.id.text_prenomCandidat);
        t_Mail = (TextView) findViewById(R.id.text_adrMailCandidat);
        t_Phone = (TextView) findViewById(R.id.text_numCandidat);

        t_Formation = (TextView) findViewById(R.id.edit_Formation);
        t_Experience = (TextView) findViewById(R.id.edit_Experience);
        t_Skill = (TextView) findViewById(R.id.edit_Competence);
        t_Language = (TextView) findViewById(R.id.edit_Langue);

        final DocumentReference docRef = store.collection("CV").document(Mail);


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    t_Name.setText(document.get("Nom").toString());
                    t_FirstName.setText(document.get("Prenom").toString());
                    t_Mail.setText(document.get("Email").toString());
                    t_Phone.setText(document.get("Tel").toString());

                    t_Formation.setText(document.get("Formation").toString());
                    t_Experience.setText(document.get("Experience").toString());
                    t_Skill.setText(document.get("Competence").toString());
                    t_Language.setText(document.get("Langue").toString());
                }
                else{
                    Toast.makeText(ConsultCV.this, "add DB", Toast.LENGTH_SHORT).show();

                    Map<String, Object> data = new HashMap<>();
                    data.put("Nom", "");
                    data.put("Prenom","");
                    data.put("Email","");
                    data.put("Tel","");

                    data.put("Formation", "");
                    data.put("Experience", "");
                    data.put("Competence", "");
                    data.put("Langue", "");
                    docRef.set(data, SetOptions.merge());
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.ModifyCV);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intModify = new Intent(ConsultCV.this, CreerCV.class);
                    startActivity(intModify);
                    finish();
                } catch (Exception e){
                    Log.v(TAG, e.getMessage());
                }
            }
        });
    }
}
