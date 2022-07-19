package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.moviecollection.R;

public class MainActivity extends AppCompatActivity {

    Animation animation;
    ImageView startImage;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Fade());

        setContentView(R.layout.activity_main);

       startButton = findViewById(R.id.start_button);
       startImage = findViewById(R.id.start_image);
       animation = AnimationUtils.loadAnimation(this, R.anim.zoom_tv);

       startButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, MenuActivity.class);

               startImage.startAnimation(animation);
               animation.setAnimationListener(new Animation.AnimationListener() {
                   @Override
                   public void onAnimationStart(Animation animation) {
                       startButton.setVisibility(View.INVISIBLE);
                   }

                   @Override
                   public void onAnimationEnd(Animation animation) {
                       startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                   }

                   @Override
                   public void onAnimationRepeat(Animation animation) {

                   }
               });
           }
       });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}