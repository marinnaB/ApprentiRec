package com.apprentirec.apprentirec.CV;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.apprentirec.apprentirec.AccountActivity.CandidateProfileActivity;
import com.apprentirec.apprentirec.AccountActivity.LoginActivity;
import com.apprentirec.apprentirec.AccountActivity.SignupActivity;
import com.apprentirec.apprentirec.MainActivity;
import com.apprentirec.apprentirec.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ConsultCV extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore store;
    private TextView t_Nom, t_Prenom, t_Adr, t_Phone, t_Formation, t_Experience, t_Competence, t_Langue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_cv);

        /*if (auth.getCurrentUser() != null) {
        // si rh ou apprenti, à completer
            startActivity(new Intent(ConsultCV.this, MainActivity.class));
            finish();
        }*/

        auth = FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String Msg = intent.getStringExtra(LoginActivity.CLE);
        String M = CandidateProfileActivity.EmailUser;

        t_Nom = (TextView) findViewById(R.id.text_nomCandidat);
        t_Prenom = (TextView) findViewById(R.id.text_prenomCandidat);
        t_Adr = (TextView) findViewById(R.id.text_adrMailCandidat);
        t_Phone = (TextView) findViewById(R.id.text_numCandidat);

        t_Formation = (TextView) findViewById(R.id.edit_Formation);
        t_Experience = (TextView) findViewById(R.id.edit_Experience);
        t_Competence = (TextView) findViewById(R.id.edit_Competence);
        t_Langue = (TextView) findViewById(R.id.edit_Langue);

        DocumentReference docRef = store.collection("Candidat").document(M).collection("CV").document("CV");

        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        t_Nom.setText(documentSnapshot.get("Nom").toString());
                        t_Prenom.setText(documentSnapshot.get("Prenom").toString());
                        t_Adr.setText(documentSnapshot.get("Email").toString());
                        t_Phone.setText(documentSnapshot.get("Tel").toString());

                        t_Formation.setText(documentSnapshot.get("Formation").toString());
                        t_Experience.setText(documentSnapshot.get("Experience").toString());
                        t_Competence.setText(documentSnapshot.get("Competence").toString());
                        t_Langue.setText(documentSnapshot.get("Langue").toString());

                        Toast.makeText(ConsultCV.this, "ADDED", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
