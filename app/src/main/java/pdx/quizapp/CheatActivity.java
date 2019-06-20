package pdx.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 * Author: Ryan Bornhorst
 *
 * Class: CheatActivity
 *
 * Description: Launches the activity that allows a user to reveal a quiz answer.
 *
 * Date: 5-7-2019
 *
 */
public class CheatActivity extends AppCompatActivity {

    // set up the instance variables
    private Boolean wasCheated = false;
    private String answerShown;

    // set up the widgets
    private TextView cheatText;
    private Button cheatButton;

    // set up variables for saving state of our instance variables
    public static final String CHEAT_KEY = "cheated_key";
    public static final String ANSWER_KEY = "answer_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // check to see if the state changed, if yes, then reset instance variables to last
        // known state
        if(savedInstanceState != null) {
            wasCheated = savedInstanceState.getBoolean(CHEAT_KEY, false);
            answerShown = savedInstanceState.getString(ANSWER_KEY, "");
        }

        // find our widgets by their view id
        cheatText = findViewById(R.id.cheat_text);
        cheatButton = findViewById(R.id.cheat_button);

        // setup the listener for the cheat button
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // grab the quiz answer from the bundle
                // and display it on the screen
                // update that the user has cheated on this question
                Bundle bundle = getIntent().getExtras();
                answerShown = bundle.getString("answer");
                String textToDisplay = "The answer is " + answerShown;
                cheatText.setText(textToDisplay);
                wasCheated = true;
                cheatButton.setEnabled(false);
            }
        });
    }

    // make sure onDestroy gets handled
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // save our instance variables when the state changes
    // using their keys, so they can be retrieved
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(CHEAT_KEY, wasCheated);
        savedInstanceState.putString(ANSWER_KEY, answerShown);
    }

    // when the back button is pressed, set the cheated results, and return
    // to the previous activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("wasCheated", wasCheated);
        setResult(RESULT_OK, intent);
        finish();
    }
}
