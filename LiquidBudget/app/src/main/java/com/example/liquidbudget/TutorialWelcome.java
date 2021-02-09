package com.example.liquidbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class TutorialWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());

        setContentView(R.layout.activity_tutorial_welcome);

        TextView textView = findViewById(R.id.welcomeText);
    }
}