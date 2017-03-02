package com.example.adnan.upload;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuizActivity extends AppCompatActivity {
    int count =0;
    TextView challengeQuestion;
    ProgressDialog progressDialog;
    Button but1,but2,but3,but4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        challengeQuestion = (TextView) findViewById(R.id.challengeQuestion);
        but1 = (Button) findViewById(R.id.bt1);
        but2 = (Button) findViewById(R.id.bt2);
        but3 = (Button) findViewById(R.id.bt3);
        but4 = (Button) findViewById(R.id.bt4);
        load_qst();
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_qst();
            }
        });


        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_qst();
            }
        });
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_qst();
            }
        });
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_qst();
            }
        });
        but1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                but1.setBackgroundResource(R.drawable.multichoice_bg_checked);
                return false;
            }
        });
        but2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                but2.setBackgroundResource(R.drawable.multichoice_bg_checked);
                return false;
            }
        });
        but3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                but3.setBackgroundResource(R.drawable.multichoice_bg_checked);
                return false;
            }
        });
        but4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                but4.setBackgroundResource(R.drawable.multichoice_bg_checked);
                return false;
            }
        });

    }

    private void load_qst() {
        if(count <= 20) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            but1.setBackgroundResource(R.drawable.multichoice_bg_unchecked);
            but2.setBackgroundResource(R.drawable.multichoice_bg_unchecked);
            but3.setBackgroundResource(R.drawable.multichoice_bg_unchecked);
            but4.setBackgroundResource(R.drawable.multichoice_bg_unchecked);
            count++;
            Toast.makeText(getApplicationContext(),""+count,Toast.LENGTH_SHORT).show();
            Array_parser_update array_parser_update = new Array_parser_update();
            array_parser_update.execute("");

        }
    }

    public class Array_parser_update extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            InputStream is = null;
            String result = "";
            BufferedReader bufferedReader = null;

            String surl = "http://35.154.119.139:90/extractor/stub.php";
            Log.v("checkpoints:",surl);
            try{
                //String surl = "http://www.djitplacement.esy.es/app_files/company.php";
                URL url = new URL(surl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String json;
                while((json = bufferedReader.readLine())!= null){
                    sb.append(json+"\n");
                }
                return sb.toString().trim();
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                challengeQuestion.setText( jsonObject.getString("question"));
                but1.setText( jsonObject.getString("a"));
                but2.setText( jsonObject.getString("b"));
                but3.setText( jsonObject.getString("c"));
                but4.setText( jsonObject.getString("d"));
                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                load_qst();
            }
        }
    }
}
