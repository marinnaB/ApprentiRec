package com.apprentirec.apprentirec.CV;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.apprentirec.apprentirec.AccountActivity.CandidateProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import com.apprentirec.apprentirec.R;

import com.apprentirec.apprentirec.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreerCV extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private static final String TAG = "CreerCv_class";
    private FirebaseFirestore store;
    private ImageButton pdpCand;
    private String MailUser;
    private EditText nameC, surnameC, mailC, phoneC, FormationC, ExperienceC, SkillC, LanguageC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = FirebaseFirestore.getInstance();
        MailUser = CandidateProfileActivity.EmailUser;
        setContentView(R.layout.activity_creer_cv);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.SaveCV);

        final DocumentReference docRef = store.collection("CV").document(MailUser);

        nameC = (EditText) findViewById(R.id.text_nomCandidat);
        surnameC = (EditText) findViewById(R.id.text_prenomCandidat);
        mailC = (EditText) findViewById(R.id.text_adrMailCandidat);
        phoneC = (EditText) findViewById(R.id.text_numCandidat);

        FormationC = (EditText) findViewById(R.id.edit_Formation);
        ExperienceC = (EditText) findViewById(R.id.edit_Experience);
        SkillC = (EditText) findViewById(R.id.edit_Competence);
        LanguageC = (EditText) findViewById(R.id.edit_Langue);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    nameC.setText(document.get("Nom").toString());
                    surnameC.setText(document.get("Prenom").toString());
                    mailC.setText(document.get("Email").toString());
                    phoneC.setText(document.get("Tel").toString());

                    FormationC.setText(document.get("Formation").toString());
                    ExperienceC.setText(document.get("Experience").toString());
                    SkillC.setText(document.get("Competence").toString());
                    LanguageC.setText(document.get("Langue").toString());
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom = nameC.getText().toString();
                String prenom = surnameC.getText().toString();
                String mail = mailC.getText().toString();
                String phone = phoneC.getText().toString();

                String Formation = FormationC.getText().toString();
                String Experience = ExperienceC.getText().toString();
                String Skill = SkillC.getText().toString();
                String Language = LanguageC.getText().toString();

                if(nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || phone.isEmpty()
                        || Formation.isEmpty()||Experience.isEmpty()||Skill.isEmpty()||Language.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Some Text fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Phone number's format is Wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> dataSave = new HashMap<>();

                dataSave.put("Nom", nom);
                dataSave.put("Prenom", prenom);
                dataSave.put("Tel", phone);
                dataSave.put("Email", mail);

                dataSave.put("Experience", Experience);
                dataSave.put("Formation", Formation);
                dataSave.put("Competence", Skill);
                dataSave.put("Langue", Language);

                store.collection("CV").document(MailUser).update(dataSave)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Information Successfully Updated!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreerCV.this, ConsultCV.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error Update!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        pdpCand = (ImageButton) findViewById(R.id.btnImg_ajoutPdpCandidat);

        pdpCand.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent chooserIntent = null;
                try{
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");

                    chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                    startActivityForResult(chooserIntent, PICK_IMAGE);
                }catch (Exception e) {
                    Log.w(TAG, "Select Image Error", e);
                    return;
                }
            }
        } );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Uri targetUri;
            try{
                targetUri = data.getData();
            }catch(Exception e){ return; }

            Bitmap bitmap;

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                pdpCand.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
