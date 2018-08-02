package com.ishtiaqasif.magicsquare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_main);
        OnClickButtonListener();
    }
        private  static Button butplay,buthelp,butabout,buthscore,butexit;

    public void OnClickButtonListener() {
        butplay = (Button) findViewById(R.id.button);
        buthscore = (Button) findViewById(R.id.button2);
        buthelp = (Button) findViewById(R.id.button3);
        butabout = (Button) findViewById(R.id.button4);
        butexit = (Button) findViewById(R.id.button5);

        butplay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,com.ishtiaqasif.magicsquare.PlayActivity.class);
                        startActivity(intent);
                    }

                }
        );
        buthscore.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,com.ishtiaqasif.magicsquare.HighScoreActivity.class);
                        startActivity(intent);
                    }

                }
        );
        buthelp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,com.ishtiaqasif.magicsquare.HelpActivity.class);
                        startActivity(intent);
                    }

                }
        );
        butabout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,com.ishtiaqasif.magicsquare.AboutActivity.class);
                        startActivity(intent);
                    }

                }
        );


    }




    }
