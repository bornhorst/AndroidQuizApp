package pdx.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 *
 * Author: Ryan Bornhorst
 *
 * Class: MainActivity
 *
 * Description: Launches the Android application for taking multiple quizzes.
 *
 * Date: 5-7-2019
 *
 */
public class MainActivity extends AppCompatActivity {

    // text fields for user name storage
    private TextView userPrompt;
    private EditText userName;

    // hold on to users name as a shared pref
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // application widgets
        Button startQuiz;
        final Spinner quizSelect;

        // find widgets by their View Id
        startQuiz = findViewById(R.id.start_quiz_button);
        quizSelect = findViewById(R.id.quiz_spinner);
        userPrompt = findViewById(R.id.ask_user_name);
        userName = findViewById(R.id.user_name);

        // create an intent to open the quiz activity when the start button is clicked
        final Intent intent = new Intent(this, QuizActivity1.class);

        // create a shared preference for the users name and store it in the edit text field
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user = sharedPreferences.getString("username", "");
        userName.setText(user);

        // Start Quiz button opens up a new activity
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the user name field is not empty, save it in shared preferences
                if(!userName.getText().toString().equals("")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", userName.getText().toString());
                    editor.apply();
                }

                // change the intent of the next activity based on the Spinner selection
                // user chooses which quiz they want to take
                if (String.valueOf(quizSelect.getSelectedItem()).equalsIgnoreCase("game of thrones quiz")) {
                    intent.putExtra("quiz", "fullQuiz.json");
                    startActivity(intent);
                } else if (String.valueOf(quizSelect.getSelectedItem()).equalsIgnoreCase("landmark quiz")) {
                    intent.putExtra("quiz", "fullQuiz2.json");
                    startActivity(intent);
                }
            }
        });

    }






}
