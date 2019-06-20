package pdx.quizapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * Author: Ryan Bornhorst
 *
 * Class: JSONAssetLoader
 *
 * Description: Reads in a JSON file, creates a JSON Object, and parses the file into
 *              an array.
 *
 * Date: 5-7-2019
 *
 */
public class JSONAssetLoader {
    // class instance variables
    private InputStream jsonIS;                 // file InputStream
    private JSONObject jsonObject;              // JSONObject used for handling the file
    private ArrayList <String> listChoices;     // list of choices used in the quiz

    // default constructor
    public JSONAssetLoader() {
        setInputStream(null);
    }

    // constructor that uses InputStream to create a JSONObject
    public JSONAssetLoader(InputStream is) {
        setInputStream(is);
        this.listChoices = new ArrayList<>();
        this.jsonObject = loadJsonObjectFromAsset(this.jsonIS);
    }

    // method for setting up the InputStream manually
    public void setInputStream(InputStream is) {
        this.jsonIS = is;
    }

    // method used for getting quiz questions from the JSON file
    public String setupJSONString(String choice) {
        String jsonString = new String();
        try {
            // grab the string that corresponds to the 'choice'
            // within the JSON file
            jsonString = this.jsonObject.getString(choice);
        } catch (JSONException e) {
            // send exceptions to the Debug Log
            Log.d("setupJSONString", e.toString());
        }

        // return the requested 'choice' value
        return jsonString;
    }

    // method used for getting quiz choices from the JSON file
    public ArrayList<String> setupJSONArray(String choice, String choices) {
        try {
            // use a JSONArray to store all the choices named 'choice'
            JSONArray jsonArray = this.jsonObject.getJSONArray(choice);
            // add any choices found to the listChoices ArrayList
            for (int i = 0; i < jsonArray.length(); i++) {
                String ref = jsonArray.getJSONObject(i).getString(choices);
                this.listChoices.add(ref);
                // print choices to Debug Log for reference
                Log.d("Quiz Choices", this.listChoices.get(i));
            }
        } catch (JSONException e) {
            // send exceptions to the Debug Log
            Log.d("setupJSONArray", e.toString());
        }

        // return the choices so they can be added to Radio Buttons
        return this.listChoices;
    }

    // method that reads in the JSON file and creates a JSONObject
    private JSONObject loadJsonObjectFromAsset(InputStream is) {
        try {
            // read the JSON file as a string and return it as
            // a JSONObject
            String json = loadStringFromAsset(is);
            if (json != null)
                return new JSONObject(json);
        } catch (Exception e) {
            // send exceptions to the Debug Log
            Log.d("loadJsonObject-->", e.toString());
        }

        // return empty if the string read in was null
        return null;
    }

    // method that reads the JSON file as buffered chunks
    // and returns it as a string
    private String loadStringFromAsset(InputStream is) throws IOException {
        // check the InputStream size
        int size = is.available();
        // set the size of the buffer depending on the InputStream
        byte[] buffer = new byte[size];
        // read the InputStream and store as a buffer
        is.read(buffer);
        is.close();
        // convert the byte buffer to a string
        String out = new String(buffer, StandardCharsets.UTF_8);
        // print buffer to Debug Log
        Log.d("loadStringFromAsset", out);
        // return the string buffer
        return new String(buffer, StandardCharsets.UTF_8);
    }
}
