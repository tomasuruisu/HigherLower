package com.thomas.higherlower;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThrowDice extends AppCompatActivity {

    private int lastThrow = 3;
    private int scoreInt = 0;
    private int highScoreInt = 0;
    private TextView score;
    private TextView highScore;
    private Button lower;
    private Button higher;
    private ListView throwList;
    private ArrayAdapter<String> throwListAdapter;
    private List<String> diceThrows;
    private ImageView diceView;
    private int[] diceImages;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw_dice);

        higher = findViewById(R.id.higher);
        lower = findViewById(R.id.lower);
        diceView = findViewById(R.id.diceView);
        score = findViewById(R.id.score);
        highScore = findViewById(R.id.high_score);

        throwList = findViewById(R.id.list_view);
        diceThrows = new ArrayList<>();

        diceImages = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

        // Define what happens when the user clicks the "higher" button
        higher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toast != null) {
                    toast.cancel();
                }
                int diceThrow = throwDice(lastThrow);
                updateList(diceThrow);
                if (diceThrow > lastThrow) {
                    scoreInt++;
                    score.setText("Score: " + scoreInt);
                    showCorrect(true);
                    if (scoreInt > highScoreInt) {
                        highScoreInt = scoreInt;
                        highScore.setText("High Score: " + highScoreInt);
                    }
                } else {
                    scoreInt = 0;
                    score.setText("Score: " + scoreInt);
                    showCorrect(false);
                }
                lastThrow = diceThrow;
                diceView.setImageResource(diceImages[diceThrow - 1]);
            }
        });

        // Define what happens when the user clicks the "lower" button
        lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toast != null) {
                    toast.cancel();
                }
                int diceThrow = throwDice(lastThrow);
                updateList(diceThrow);
                if (diceThrow < lastThrow) {
                    scoreInt++;
                    score.setText("Score: " + scoreInt);
                    showCorrect(true);
                    if (scoreInt > highScoreInt) {
                        highScoreInt = scoreInt;
                        highScore.setText("High Score: " + highScoreInt);
                    }
                } else {
                    scoreInt = 0;
                    score.setText("Score: " + scoreInt);
                    showCorrect(false);
                }
                lastThrow = diceThrow;
                diceView.setImageResource(diceImages[diceThrow - 1]);
            }
        });
    }

    private void updateUI() {
        if (throwListAdapter == null) {
            throwListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diceThrows);
            throwList.setAdapter(throwListAdapter);
        } else {
            throwListAdapter.notifyDataSetChanged();
        }
    }

    private void updateList(int diceThrow) {
        if (diceThrows.size() > 4) {
            diceThrows.remove(0);
            diceThrows.add("Throw was: " + diceThrow);
        } else {
            diceThrows.add("Throw was: " + diceThrow);
        }
        updateUI();
    }

    private int throwDice(int lastThrow) {
        Random random = new Random();
        int diceThrow = random.nextInt((6 - 1) + 1) + 1;
        if (diceThrow == lastThrow) {
            return throwDice(lastThrow);
        } else {
            return diceThrow;
        }
    }

    private void showCorrect(boolean correct) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        if (correct) {
            CharSequence text = "Correct! :)";
            toast = Toast.makeText(context, text, duration);
        } else {
            CharSequence text = "Wrong >:(";
            toast = Toast.makeText(context, text, duration);
        }
        toast.show();
    }
}
