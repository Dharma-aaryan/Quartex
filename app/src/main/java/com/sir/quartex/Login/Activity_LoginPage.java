package com.sir.quartex.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sir.quartex.Activity_HomePage;
import com.sir.quartex.R;

public class Activity_LoginPage extends AppCompatActivity {
    EditText memail, mpassword;
    Button mloginbtn, mgoogle;
    TextView mCreatebtn,fp;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        memail = findViewById( R.id.email);
        mpassword = findViewById( R.id.password);
        mloginbtn = findViewById( R.id.loginbtn);
        fp = findViewById( R.id.forgotpwd);

        fauth = FirebaseAuth.getInstance( );

        mloginbtn.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                String email = memail.getText( ).toString( ).trim( );
                String password = mpassword.getText( ).toString( ).trim( );

                if (TextUtils.isEmpty( email )
                        && TextUtils.isEmpty( password )
                        && (password.length( ) < 6)) {
                    memail.setError( "Email is required" );
                    mpassword.setError( "Password is required" );
                    mpassword.setError( "Password is weak" );
                    return;
                }
                //authentication
                fauth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>( ) {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful( )) {
                            Toast.makeText( getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT ).show( );
                            startActivity( new Intent( getApplicationContext( ), Activity_HomePage.class ) );
                        } else {
                            Toast.makeText( getApplicationContext(), "Error" + task.getException( ).getMessage( ), Toast.LENGTH_SHORT ).show( );
                        }
                    }
                } );
            }
        } );


        mCreatebtn=findViewById(R.id.toregister);
        mCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext( ), Activity_RegisterPage.class ) );
            }
        });

        fp = findViewById( R.id.forgotpwd );
        fp.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                String input = memail.getText( ).toString( ).trim( );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    fauth.getInstance( ).sendPasswordResetEmail( input )
                            .addOnCompleteListener( getMainExecutor(), new OnCompleteListener( ) {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful( )) {
                                        Toast.makeText( getApplicationContext(), "Reset Link Sent to Your Email", Toast.LENGTH_SHORT ).show( );
                                    } else {
                                        Toast.makeText( getApplicationContext(), "failed", Toast.LENGTH_SHORT ).show( );
                                    }
                                }
                            } );
                }
            }
        } );
    }
}