package com.sir.quartex.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sir.quartex.Login.Activity_RegisterPage;
import com.sir.quartex.R;

public class Activity_SplashScreen extends AppCompatActivity {
    ImageView img;
    Animation top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = (ImageView) findViewById( R.id.Logo);
        top = AnimationUtils.loadAnimation( getApplicationContext( ), R.anim.splash_logo );
        img.setAnimation( top );

        new Handler( ).postDelayed(new Runnable( ) {
            @Override
            public void run() {
                startActivity( new Intent( getApplicationContext( ), Activity_RegisterPage.class ) );
                finish( );
            }
        }, 5000 );

    }
}