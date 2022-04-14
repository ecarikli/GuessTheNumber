package com.ekremcarikli.guessthenumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GamePage extends AppCompatActivity {

    private TextView textViewLast,textViewRight,textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;

    Random r = new Random();
    int random;
    int remainingRight = 10;
    ArrayList<Integer> guessList = new ArrayList<>();

    int attempts = 0;

    boolean radio2,radio3,radio4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        editTextGuess = findViewById(R.id.editTextGuess);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        textViewLast = findViewById(R.id.textViewLastGuess);
        textViewRight = findViewById(R.id.textViewRights);
        textViewHint = findViewById(R.id.textViewHint);


        radio2 = getIntent().getBooleanExtra("two",false);
        radio3 = getIntent().getBooleanExtra("three",false);
        radio4 = getIntent().getBooleanExtra("four",false);

        if (radio2){
            random = r.nextInt(90) + 10;
        }

        if (radio3){
            random = r.nextInt(900) + 100;
        }

        if (radio4){
            random = r.nextInt(9000) + 1000;
        }





        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String guess = editTextGuess.getText().toString().trim();

                if (guess.equals("")){
                    Toast.makeText(GamePage.this, "Please Enter a Guess", Toast.LENGTH_SHORT).show();
                }
                else{

                    textViewLast.setVisibility(View.VISIBLE);
                    textViewRight.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);




                    remainingRight--;
                    attempts++;



                    int userGuess = Integer.parseInt(guess);
                    guessList.add(userGuess);

                    textViewRight.setText("Your Remaining Right: " + remainingRight);
                    textViewLast.setText("Your Last Guess is: " + userGuess);

                    if (userGuess == random){

                        AlertDialog.Builder builder = new AlertDialog.Builder(GamePage.this);
                        builder.setTitle("Guess The Number");
                        builder.setCancelable(false);
                        builder.setMessage("Congrulations, My guess was " + random
                                +"\n\n You guessed my number in " + attempts +" attempts"
                                +"\n\n Your guesses " + guessList
                                + "\n\n Would You Like To Play Again?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GamePage.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.exit(1);
                            }
                        });

                        builder.create().show();

                    }

                    if (userGuess<random){
                        textViewHint.setText("Increase Your Guess");
                    }

                    if(userGuess>random){
                        textViewHint.setText("Decrease Your Guess");
                    }

                    if (remainingRight == 0){

                        AlertDialog.Builder builder = new AlertDialog.Builder(GamePage.this);
                        builder.setTitle("Guess The Number");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry, Your remaining rights are finished"
                                + "\n\n My Guess was: " + random
                                +"\n\n Your guesses " + guessList
                                + "\n\n Would You Like To Play Again?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GamePage.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        builder.create().show();


                    }
                    editTextGuess.setText("");



                }










            }
        });

    }
}