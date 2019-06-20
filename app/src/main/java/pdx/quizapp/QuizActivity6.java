package pdx.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * Author: Ryan Bornhorst
 *
 * Class: QuizActivity6
 *
 * Description: Launches the sixth question for a selected quiz.
 *
 * Date: 5-7-2019
 *
 */
public class QuizActivity6 extends AppCompatActivity implements View.OnClickListener {

    // instance variables that need to persist between activities
    private String quizAnswer = "";
    private Boolean correctAnswer = false;
    private Boolean wasCheated = false;

    // application widgets
    private RadioButton choiceOne;
    private RadioButton choiceTwo;
    private RadioButton choiceThree;
    private RadioButton choiceFour;
    private Button startQuiz;
    private TextView quizQuestion;
    private Button cheatButton;

    // keys used to save the instance variables on a state change
    public static final String ANSWER_KEY = "answer_key";
    public static final String CORRECT_KEY = "correct_key";
    public static final String CHEAT_KEY = "cheat_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz6);

        // if the state changed, we need to restore these values to their previous state
        if(savedInstanceState != null) {
            quizAnswer = savedInstanceState.getString(ANSWER_KEY, "");
            correctAnswer = savedInstanceState.getBoolean(CORRECT_KEY, false);
            wasCheated = savedInstanceState.getBoolean(CHEAT_KEY, false);
        }

        // find widgets by their View Id
        this.choiceOne = findViewById(R.id.quiz6_choiceOne);
        this.choiceTwo = findViewById(R.id.quiz6_choiceTwo);
        this.choiceThree = findViewById(R.id.quiz6_choiceThree);
        this.choiceFour = findViewById(R.id.quiz6_choiceFour);
        this.startQuiz = findViewById(R.id.quiz6_button);
        this.quizQuestion = findViewById(R.id.quiz6_question);
        this.cheatButton = findViewById(R.id.cheat_button);

        // check the bundle to see which quiz was launched
        Bundle bundle = getIntent().getExtras();
        String quizSelected = bundle.getString("quiz");

        // load the first quiz question from the json file
        // also grab the possible choices and answer from the file
        try {
            InputStream jsonInputStream = getAssets().open(quizSelected);
            JSONAssetLoader jsonLoader = new JSONAssetLoader(jsonInputStream);
            String question = jsonLoader.setupJSONString("question6");
            this.quizAnswer = jsonLoader.setupJSONString("answer6");
            ArrayList<String> choices = jsonLoader.setupJSONArray("choices6", "choice");
            Log.d("QuizAnswer", this.quizAnswer);

            // after parsing the json file, store the choices as Radio buttons
            this.quizQuestion.setText(question);
            this.choiceOne.setText(choices.get(0));
            this.choiceTwo.setText(choices.get(1));
            this.choiceThree.setText(choices.get(2));
            this.choiceFour.setText(choices.get(3));

        } catch (IOException e) {
            Log.d("IO Exception", e.toString());
        }

        // setup listeners for Radio, Next, and Cheat buttons
        this.choiceOne.setOnClickListener(this);
        this.choiceTwo.setOnClickListener(this);
        this.choiceThree.setOnClickListener(this);
        this.choiceFour.setOnClickListener(this);
        this.startQuiz.setOnClickListener(this);
        this.cheatButton.setOnClickListener(this);

    }

    // make sure that onDestroy is being handled
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // save our instance variables when the state changes
    // using their keys, so they can be retrieved
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(ANSWER_KEY, quizAnswer);
        savedInstanceState.putBoolean(CORRECT_KEY, correctAnswer);
        savedInstanceState.putBoolean(CHEAT_KEY, wasCheated);
    }

    // we need to catch a value that tells us if the cheat activity was used
    // this can be done by catching the result of the cheat activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null)
            return;
        this.wasCheated = data.getBooleanExtra("wasCheated", false);
        cheatButton.setEnabled(false);
        Log.d("Cheated?", this.wasCheated.toString());
    }

    // setup the onClick event to handle button clicks
    @Override
    public void onClick(View v) {

        // create an intent to open the quiz activity when the start button is clicked
        final Intent intent = new Intent(this, QuizActivity7.class);
        final Intent cheat = new Intent(this, CheatActivity.class);

        // match the correct answer with the value held on the selected radio button
        switch(v.getId()) {
            case R.id.quiz6_choiceOne:
                this.correctAnswer = this.choiceOne.getText().toString().equalsIgnoreCase(this.quizAnswer);
                break;
            case R.id.quiz6_choiceTwo:
                this.correctAnswer = this.choiceTwo.getText().toString().equalsIgnoreCase(this.quizAnswer);
                break;
            case R.id.quiz6_choiceThree:
                this.correctAnswer = this.choiceThree.getText().toString().equalsIgnoreCase(this.quizAnswer);
                break;
            case R.id.quiz6_choiceFour:
                this.correctAnswer = this.choiceFour.getText().toString().equalsIgnoreCase(this.quizAnswer);
                break;
            case R.id.cheat_button:
                // send the answer to the cheat activity
                // and launch it
                cheat.putExtra("answer", this.quizAnswer);
                startActivityForResult(cheat, 0);
                break;
            case R.id.quiz6_button:
                // send the boolean result/cheat value to the next activity
                // along with any previous activities results
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("Cheater6", this.wasCheated);
                intent.putExtra("wasCorrect6", this.correctAnswer);
                startActivity(intent);
                break;
        }
    }
}
