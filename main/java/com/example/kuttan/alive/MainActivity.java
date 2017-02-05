package com.example.kuttan.alive;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class MainActivity extends Activity {

    EditText name,pass,email;
    Button login,register;
    TextView forget;
    String url="http://sudheesh.16mb.com/php/sudheesh.php";
    String name_db,pass_db,email_db;
    String TAG="MainActivity";
    boolean empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Init");
        name=(EditText)findViewById(R.id.name);
        pass=(EditText)findViewById(R.id.pass);
        email=(EditText)findViewById(R.id.mail);
        login=(Button)findViewById(R.id.log);
        register=(Button)findViewById(R.id.reg);
        forget= (TextView) findViewById(R.id.forget);

        forget.setText(
                Html.fromHtml("<a href=\"http://www.google.com\">Forget Password?</a>")
        );
        forget.setMovementMethod(LinkMovementMethod.getInstance());
        Log.d(TAG, "button listener");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"get value");
                name_db=name.getText().toString();
                pass_db=pass.getText().toString();
                email_db=email.getText().toString();
                Log.d(TAG, "pass to db");
                checkempty(name_db, pass_db, email_db);
                if(empty == true) {
                    Toast.makeText(MainActivity.this,"Fields Cannot be Empty",Toast.LENGTH_SHORT).show();
                }else{
                    onlinedb(name_db, pass_db, email_db);
                }
                Log.d(TAG, "end of pass db");
            }
        });

    }

    private void checkempty(String name_db, String pass_db, String email_db) {

        if(TextUtils.isEmpty(email_db) || TextUtils.isEmpty(pass_db) || TextUtils.isEmpty(email_db)){
            empty=true;
        }else {
            empty=false;
        }
    }

    private void onlinedb(final String name_db, final String pass_db, final String email_db) {
        Log.d(TAG,"inside onlne db");
        class senddbtask extends AsyncTask<String , Void ,String>{

            @Override
            protected String doInBackground(String... params) {
                Log.d(TAG,"inside do in bg");
                String d_name=name_db;
                String p_name=pass_db;
                String m_name=email_db;
                Log.d(TAG,"test 1");
                List<NameValuePair> valuePairs=new ArrayList<NameValuePair>();
                Log.d(TAG,"test 2");
                valuePairs.add(new BasicNameValuePair("name", d_name));
                valuePairs.add(new BasicNameValuePair("pass",p_name));
                valuePairs.add(new BasicNameValuePair("email",m_name));
                Log.d(TAG, "test 3");
                try{
                    Log.d(TAG,"inisdee try");
                    HttpClient httpClient=new DefaultHttpClient();
                    Log.d(TAG,"11");
                    HttpPost httpPost =new HttpPost(url);
                    Log.d(TAG,"12");
                    httpPost.setEntity(new UrlEncodedFormEntity(valuePairs));
                    Log.d(TAG, "13");
                    HttpResponse response =httpClient.execute(httpPost);
                    Log.d(TAG,"14");
                    HttpEntity entity =response.getEntity();
                    Log.d(TAG,"15");
                }
                catch (ClientProtocolException e){

                }catch (IOException e){

                }

                Log.d(TAG,"sucess");
                return "Data Submitted Successfully";
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "on post doneeeee");
                Toast.makeText(MainActivity.this,"Inserted",Toast.LENGTH_SHORT).show();
            }
        }
        Log.d(TAG,"before call");
        senddbtask send =new senddbtask();
        Log.d(TAG,"instance Created");
        send.execute(name_db, pass_db, email_db);
        Log.d(TAG, "called excecute");
    }


}

