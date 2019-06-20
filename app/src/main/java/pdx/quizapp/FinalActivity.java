package pdx.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 * Author: Ryan Bornhorst
 *
 * Class: FinalActivity
 *
 * Description: Launches the final screen showing the user their quiz results.
 *
 * Date: 5-7-2019
 *
 */
public class FinalActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // set up the widgets for the final display
        TextView finalResults = findViewById(R.id.final_results);
        TextView cheatResults = findViewById(R.id.cheat_results);
        Button finalButton = findViewById(R.id.final_button);

        // grab all the quiz results from the bundle into a Boolean array
        ArrayList <Boolean> quizAnswerList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        quizAnswerList.add(bundle.getBoolean("wasCorrect1"));
        quizAnswerList.add(bundle.getBoolean("wasCorrect2"));
        quizAnswerList.add(bundle.getBoolean("wasCorrect3"));
        quizAnswerList.add(bundle.getBoolean("wasCorrect4"));
        quizAnswerList.add(bundle.getBoolean("wasCorrect5"));
        quizAnswerList.add(bundle.getBoolean("wasCorrect6"));
        quizAnswerList.add(bundle.getBoolean("wasCorrect7"));
        quizAnswerList.add(bundle.getBoolean("wasCorrect8"));

        // grab all the cheat results into a Boolean array
        ArrayList <Boolean> cheatList = new ArrayList<>();
        cheatList.add(bundle.getBoolean("Cheater1"));
        cheatList.add(bundle.getBoolean("Cheater2"));
        cheatList.add(bundle.getBoolean("Cheater3"));
        cheatList.add(bundle.getBoolean("Cheater4"));
        cheatList.add(bundle.getBoolean("Cheater5"));
        cheatList.add(bundle.getBoolean("Cheater6"));
        cheatList.add(bundle.getBoolean("Cheater7"));
        cheatList.add(bundle.getBoolean("Cheater8"));

        int numberCorrect = 0;
        int numberCheated = 0;
        int numberQuestions = 8;

        // determine how many questions the user got correct
        // and how many times they decided to cheat
        for(int i = 0; i < 8; i++) {
            if(quizAnswerList.get(i))
                numberCorrect += 1;
            if(cheatList.get(i))
                numberCheated += 1;
            Log.d("ANSWERS", quizAnswerList.get(i).toString());
            Log.d("CHEATS", cheatList.get(i).toString());
        }

        // output their results onto the screen
        String results = "You answered " + numberCorrect + " out of " + numberQuestions + " questions correctly.";
        String cheats = "You cheated " + numberCheated + " times.";
        finalResults.setText(results);
        cheatResults.setText(cheats);

        // setup the listener for the return home button
        finalButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // create an intent to open the quiz activity when the start button is clicked
        final Intent intent = new Intent(this, MainActivity.class);

        // reopen the MainActivity to choose another quiz
        if(v.getId() == R.id.final_button) {
            startActivity(intent);
        }
    }
}
