package com.codesnroses.foodo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codesnroses.foodo.Etc.CurrentFragment;
import com.codesnroses.foodo.Model.Achievement;
import com.codesnroses.foodo.Model.Answer;
import com.codesnroses.foodo.Model.Question;
import com.codesnroses.foodo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game extends ActionBarActivity {

    private final String SERVER = "http://frodo.karlworks.com/api/questions/";
    private final String SERVER_POST = SERVER+ "results/";
    private Question question;

    //If false, that option is not selected,
    //If true, that option is selected
    private Boolean choice1 = false;
    private Boolean choice2 = false;
    private Boolean choice3 = false;
    private Boolean choice4 = false;

    //If true, that option is correct,
    //If false, that option is incorrect
    private Boolean answer1 = false;
    private Boolean answer2 = false;
    private Boolean answer3 = false;
    private Boolean answer4 = false;

    //Boolean to indicate if this question was answered correctly or not
    private int isQuestionCorrect;


    private ToggleButton toggle1;
    private ToggleButton toggle2;
    private ToggleButton toggle3;
    private ToggleButton toggle4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //TextView g= (TextView)findViewById(R.id.game_title);
        //g.setText(CurrentFragment.getInstance().getFragmentIndex()+" is the previous fragment");

        //Fetch a question from the server
        fetchQuestion();

        handleSubmitButton();
    }

    private void handleSubmitButton(){
        Button submitButton = (Button)findViewById(R.id.button_submit_answer);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSolution();
                postQuestionResultToServer();
            }
        });
    }


    private void checkSolution(){
        if(toggle1.isChecked()){
            choice1 = true;
        }
        if(toggle2.isChecked()){
            choice2 = true;
        }
        if(toggle3.isChecked()){
            choice3 = true;
        }
        if(toggle4.isChecked()){
            choice4 = true;
        }


        if(choice1 == question.getChoices().get(0).getIsAnswer()){
            answer1 = true;
        }
        if(choice2 == question.getChoices().get(1).getIsAnswer()){
            answer2 = true;
        }
        if(choice3 == question.getChoices().get(2).getIsAnswer()){
            answer3 = true;
        }
        if(choice4 == question.getChoices().get(3).getIsAnswer()){
            answer4 = true;
        }


        //If all answers are correct
        if((answer1 == true) && (answer2 == true) && (answer3 == true) && (answer4 == true) ){
            isQuestionCorrect = 1;
        }else{//If one of them is wrong
            isQuestionCorrect = 2;
        }

    }

    private void postQuestionResultToServer(){
        //Get device unique id
        final String uuid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, SERVER_POST,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        showResultOnScreen();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error Response", error.getMessage());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<String, String>();
                params.put("question",question.getId()+"");
                params.put("device", ""+uuid);
                params.put("status", isQuestionCorrect+"");

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void showResultOnScreen(){
        String m ="";
        if(isQuestionCorrect==1){//Its correct
            m = "Excellent, this is correct!";
        }else if(isQuestionCorrect == 2){//its wrong
            m = "Incorrect answer!";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(m)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void fetchQuestion(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.GET, SERVER,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        setValues(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error Response", error.getMessage());
                    }
                }
        );
        queue.add(postRequest);
    }

    private void setValues(String res){
        try {

            JSONObject jsonObject = new JSONObject(res);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("choices"));

            question = new Question();
            question.setId(jsonObject.getInt("pk"));
            question.setName(jsonObject.getString("name"));
            question.setQuestion(jsonObject.getString("question"));
            question.setGameType(jsonObject.getInt("game_type"));
            question.setLevel(jsonObject.getInt("level"));


            ArrayList<Answer> answerArrayList = new ArrayList<Answer>();
            for (int i =0;i<jsonArray.length();i++) {
                Answer a = new Answer();

                a.setId(((JSONObject) jsonArray.get(i)).getInt("pk"));
                a.setChoice(((JSONObject) jsonArray.get(i)).getString("choice"));
                a.setImage(((JSONObject) jsonArray.get(i)).getString("image"));
                a.setIsAnswer(((JSONObject) jsonArray.get(i)).getBoolean("answer"));

                answerArrayList.add(a);
            }

            question.setChoices(answerArrayList);


            setUpValues();



            Log.d("QUESTION","question name is "+question.getName());

            for(int i = 0;i<question.getChoices().size();i++){
                Log.d("CHOICES",question.getChoices().get(i).getChoice());
            }


        } catch (JSONException x) {
            x.printStackTrace();
        }
    }


    private void setUpValues(){
        TextView questionName = (TextView)findViewById(R.id.question_name);
        questionName.setText(question.getName());

        TextView question2 = (TextView)findViewById(R.id.question);
        question2.setText(question.getQuestion());

        //First choice
        toggle1 = (ToggleButton)findViewById(R.id.toggleAnswer1);
        toggle1.setText(question.getChoices().get(0).getChoice());
        toggle1.setTextOn(question.getChoices().get(0).getChoice());
        toggle1.setTextOff(question.getChoices().get(0).getChoice());

        //Second choice
        toggle2 = (ToggleButton)findViewById(R.id.toggleAnswer2);
        toggle2.setText(question.getChoices().get(1).getChoice());
        toggle2.setTextOn(question.getChoices().get(1).getChoice());
        toggle2.setTextOff(question.getChoices().get(1).getChoice());

        //Third choice
        toggle3 = (ToggleButton)findViewById(R.id.toggleAnswer3);
        toggle3.setText(question.getChoices().get(2).getChoice());
        toggle3.setTextOn(question.getChoices().get(2).getChoice());
        toggle3.setTextOff(question.getChoices().get(2).getChoice());

        //Fourth choice
        toggle4 = (ToggleButton)findViewById(R.id.toggleAnswer4);
        toggle4.setText(question.getChoices().get(3).getChoice());
        toggle4.setTextOn(question.getChoices().get(3).getChoice());
        toggle4.setTextOff(question.getChoices().get(3).getChoice());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
