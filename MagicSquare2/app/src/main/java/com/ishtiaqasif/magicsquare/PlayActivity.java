package com.ishtiaqasif.magicsquare;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class ThreeGridActivity extends AppCompatActivity {
    int[][] grid;
    LinearLayout[] layouts;
    Button[][] buttons;
    LinearLayout mainLayout;
    TextView cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threegrid);

        grid = new MagicGenerator().generateGrid(3);

        layouts = new LinearLayout[4];
        buttons = new Button[5][5];

        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);

        cancelButton = (TextView) findViewById(R.id.cancelTextView);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cancel();
            }
        });

        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++) {
                if (i == 0) {
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    param.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.bottomMargin = 5;
                    param.topMargin = 5;
                    param.leftMargin = 5;
                    param.rightMargin = 5;
                    param.weight = 1f;
                    buttons[i][j] = new Button(this);
                    buttons[i][j].setBackgroundResource(0);
                    buttons[i][j].setText("0");
                    buttons[i][j].setTextColor(Color.WHITE);
                    buttons[i][j].setLayoutParams(param);

                } else if (j == 0) {
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    param.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.bottomMargin = 5;
                    param.topMargin = 5;
                    param.leftMargin = 5;
                    param.rightMargin = 5;
                    param.weight = 1f;
                    buttons[i][j] = new Button(this);
                    buttons[i][j].setBackgroundResource(0);
                    buttons[i][j].setText("0");
                    buttons[i][j].setTextColor(Color.WHITE);
                    buttons[i][j].setLayoutParams(param);
                } else if (j == 4) {
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    param.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.bottomMargin = 5;
                    param.topMargin = 5;
                    param.leftMargin = 5;
                    param.rightMargin = 5;
                    param.weight = 1f;
                    buttons[i][j] = new Button(this);
                    buttons[i][j].setBackgroundResource(0);
                    buttons[i][j].setLayoutParams(param);
                } else {
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    param.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.bottomMargin = 5;
                    param.topMargin = 5;
                    param.leftMargin = 5;
                    param.rightMargin = 5;
                    param.weight = 1f;
                    buttons[i][j] = new Button(this);
                    buttons[i][j].setBackgroundResource(R.drawable.gridbutton);
                    buttons[i][j].setText("0");
                    buttons[i][j].setTextColor(Color.WHITE);
                    buttons[i][j].setLayoutParams(param);

                }
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        increaseValue(v);
                    }
                });

            }
        }


        for(int i=0;i<4;i++)
        {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            layouts[i] = new LinearLayout(this);
            layouts[i].setOrientation(LinearLayout.HORIZONTAL);
            layouts[i].setLayoutParams(param);

            for(int j=0;j<5;j++)
            {
                layouts[i].addView(buttons[i][j]);
            }

            mainLayout.addView(layouts[i]);
        }

        SetPlaceholder();


    }

    private void SetPlaceholder() {
        Random random = new Random();
        int count = random.nextInt(6) + 1;
        while(count > 0) {
            int i = random.nextInt(3) + 1;
            int j = random.nextInt(3) + 1;

            if (buttons[i][j].getText().toString().equals("0")) {
                buttons[i][j].setText(grid[i-1][j-1] + "");
                buttons[i][j].setEnabled(false);
                buttons[i][j].setTextColor(getResources().getColor(R.color.darkGray));
            }

            count--;
        }
        updateRowCol();
    }

    private void increaseValue(View e) {

        Button temp  = (Button) e;
        int num = Integer.parseInt(temp.getText().toString());
        num++;
        if(num==10){num=1;}

        temp.setText(num+"");

        updateRowCol();
        if(hasWon()) {
            SharedPreferences settings = getSharedPreferences("MAGIC_SQUARES_SETTINGS", MODE_PRIVATE);
            long score = settings.getLong("PLAYER_SCORE", 0);
            score += 300;
            SharedPreferences.Editor settingsEditor = getSharedPreferences("MAGIC_SQUARES_SETTINGS", MODE_PRIVATE).edit();
            settingsEditor.putLong("PLAYER_SCORE", score);
            settingsEditor.commit();
            AlertWinner();
        }

    }
    private void AlertWinner() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.won));
        builder.setMessage(getResources().getString(R.string.congratulations))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.again), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Reset();
                    }
                })
                .setNeutralButton(getResources().getString(R.string.main_menu), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainMenu();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void Reset() {
        Intent parent = new Intent(this, MainActivity.class);
        parent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent child = new Intent(this, SizeActivity.class);
        startActivities(new Intent[] {parent, child});
    }

    private void MainMenu() {
        Intent parent = new Intent(this, MainActivity.class);
        parent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(parent);
    }

    private void Cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.quit));
        builder.setMessage(getResources().getString(R.string.are_you_sure))
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean hasWon() {
        String ld = buttons[0][0].getText().toString();
        for(int i = 1; i <5 ; i++) {
            if(!buttons[0][i].getText().toString().equals(ld))
            {
                return false;
            }
        }
        for(int i=1;i<4;i++)
        {
            if(!buttons[i][0].getText().toString().equals(ld))
            {
                return false;
            }
        }

        return true;
    }

    private void updateRowCol() {
        int sum;
        for(int i =1;i<4;i++)
        {
            sum=0;
            for(int j=1;j<4;j++)
            {
                sum+=(Integer.parseInt(buttons[i][j].getText().toString()));
            }
            buttons[i][0].setText(sum+"");
        }

        for(int j =1;j<4;j++)
        {
            sum=0;
            for(int i=1;i<4;i++)
            {
                sum+=(Integer.parseInt(buttons[i][j].getText().toString()));
            }
            buttons[0][j].setText(sum+"");
        }
        sum=0;
        for(int i=1;i<4;i++)
        {
            sum+=(Integer.parseInt(buttons[i][i].getText().toString()));
        }
        buttons[0][0].setText(sum+"");
        sum=0;
        for(int i=1,j=3;i<4&&j>0;i++,j--)
        {
            sum+=(Integer.parseInt(buttons[i][j].getText().toString()));
        }
        buttons[0][4].setText(sum+"");



    }
}