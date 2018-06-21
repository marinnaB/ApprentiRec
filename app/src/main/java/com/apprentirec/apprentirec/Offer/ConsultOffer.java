package com.apprentirec.apprentirec.Offer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.apprentirec.apprentirec.AccountActivity.CandidateProfileActivity;
import com.apprentirec.apprentirec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ConsultOffer extends AppCompatActivity {
    private static final String TAG = "Consult_Class_java";
    private FirebaseFirestore store;
    private Boolean error;
    private TextView t_title, t_entreprise, t_statut, t_type, t_date, t_duration, t_location, t_profile, t_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_cv);
        store=FirebaseFirestore.getInstance();

        error = true;

        String Mail = CandidateProfileActivity.EmailUser;

        t_title = (TextView) findViewById(R.id.offerTitle);
        t_entreprise = (TextView) findViewById(R.id.offerEntreprise);
        t_statut = (TextView) findViewById(R.id.offerStatut);
        t_type = (TextView) findViewById(R.id.offerType);
        t_date = (TextView) findViewById(R.id.offerDate);
        t_duration = (TextView) findViewById(R.id.offerDuration);
        t_location = (TextView) findViewById(R.id.offerLocation);
        t_profile = (TextView) findViewById(R.id.offerProfile);
        t_description = (TextView) findViewById(R.id.offerDescription);

        final DocumentReference docRef = store.collection("Offre").document(Mail);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    t_title.setText(document.get("Titre").toString());
                    t_entreprise.setText(document.get("Entreprise").toString());
                    t_statut.setText(document.get("Statut").toString());
                    t_type.setText(document.get("Type").toString());
                    t_date.setText(document.get("Date").toString());
                    t_duration.setText(document.get("Durée").toString());
                    t_location.setText(document.get("Lieu").toString());
                    t_profile.setText(document.get("Profile").toString());
                    t_description.setText(document.get("Description").toString());
                }
                else{
                    Toast.makeText(ConsultOffer.this, "add DB", Toast.LENGTH_SHORT).show();

                    Map<String, Object> data = new HashMap<>();
                    data.put("Titre", "");
                    data.put("Entreprise","");
                    data.put("Statut","");
                    data.put("Type","");
                    data.put("Date", "");
                    data.put("Durée", "");
                    data.put("Lieu", "");
                    data.put("Profile", "");
                    data.put("Description", "");
                    docRef.set(data, SetOptions.merge());
                }
            }
        });
    }
}
