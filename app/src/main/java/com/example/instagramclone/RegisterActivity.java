package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    /*Kullanicilarin girdigi degerleri alacak*/
    EditText kullaniciIsmi, isim, emailadres, sifre;
    /*Kayit ol butonu*/
    Button registerbtn;
    TextView loginGit;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        kullaniciIsmi = findViewById(R.id.username);
        isim = findViewById(R.id.fullname);
        emailadres = findViewById(R.id.email);
        sifre = findViewById(R.id.password);
        loginGit = findViewById(R.id.logInGit);
        registerbtn = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();
        loginGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*In translation the startActivity() will start an activity
                * newIntent(RegisterActivity.this, StartActivity.class)
                * with the intent to go opening StartActivity.class because we want to open the class
                * we want to go from RegisterActivity to StartActivity*/
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                String str_username = kullaniciIsmi.getText().toString();
                String str_fullName = isim.getText().toString();
                String str_email = emailadres.getText().toString();
                String str_password = sifre.getText().toString();
                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullName) || TextUtils.isEmpty(str_email)|| TextUtils.isEmpty(str_password)){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if(str_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerFunction(str_username, str_fullName, str_email, str_password);
                }
            }
        });

    }
    public void registerFunction(final String username, String fullname, String email, String password ){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userID);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("fullname", fullname);
                            hashMap.put("bio", "");
                            hashMap.put("imageURL", "https://firebasestorage.googleapis.com/v0/b/threadclone-35e1f.appspot.com/o/user.png?alt=media&token=53161e32-278d-4290-a9eb-b5485c27e95b");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "You can't access with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}