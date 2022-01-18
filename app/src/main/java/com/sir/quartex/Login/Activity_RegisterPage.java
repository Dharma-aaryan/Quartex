package com.sir.quartex.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sir.quartex.R;

import java.util.HashMap;
import java.util.Map;

public class Activity_RegisterPage extends AppCompatActivity {
    EditText mfullname,memail,mpassword,mphone;
    Button mregisterbtn ;
    FirebaseAuth fauth;
    FirebaseFirestore flogindetails;
    String userid_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mfullname=findViewById(R.id.name);
        memail=findViewById(R.id.register_email);
        mpassword=findViewById(R.id.register_pwd);
        mphone=findViewById(R.id.phone_login);

        mregisterbtn=findViewById(R.id.registerbtn);

        fauth = FirebaseAuth.getInstance();
        flogindetails = FirebaseFirestore.getInstance();

        mregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = mfullname.getText().toString().trim();
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                String phone2= mphone.getText().toString().trim();


                if (TextUtils.isEmpty(fname)){
                    mfullname.setError("Required Name");
                }

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is required");
                    return;
                }
                if(mphone.length()>10 && mphone.length()<10){
                    mphone.setError("Number should be exact 10 digits");
                    return;
                }
                if(password.length()<6){
                    mpassword.setError("Password is weak");
                    return;
                }
                //register user in firebase
                fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"user created",Toast.LENGTH_SHORT).show();
                            userid_register = fauth.getCurrentUser().getUid();
                            DocumentReference drregister = flogindetails.collection("Users").document(userid_register);
                            Map<String,Object> item_register = new HashMap<>();
                            item_register.put("name",fname);
                            item_register.put("email",email);
                            item_register.put("phone",phone2);

                            drregister.set(item_register).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG", "onSuccess:user Profile is created" + userid_register);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","onFailure:"+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext() , Activity_LoginPage.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error"+task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        TextView btn2 =findViewById(R.id.already);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Activity_LoginPage.class));
            }
        });
    }
}