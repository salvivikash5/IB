package com.example.adnan.upload;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class Delete1Activity extends AppCompatActivity {
    int count =0;
    private TextView textView;
    private Button op1,op2,op3,op4;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete1);
        textView = (TextView) findViewById(R.id.textView1);
        op1 = (Button) findViewById(R.id.imageButton1);
        op2 = (Button) findViewById(R.id.imageButton2);
        op3 = (Button) findViewById(R.id.imageButton3);
        op4 = (Button) findViewById(R.id.imageButton4);

        load_qst();
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_qst();
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_qst();
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_qst();
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_qst();
            }
        });
    }

    private void load_qst() {
        if(count <= 20) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
            //return chaine.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                textView.setText( jsonObject.getString("question"));
                op1.setText( jsonObject.getString("a"));
                op2.setText( jsonObject.getString("b"));
                op3.setText( jsonObject.getString("c"));
                op4.setText( jsonObject.getString("d"));


                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                load_qst();
            }
        }
    }
}
