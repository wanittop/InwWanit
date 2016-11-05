package com.example.os.week8su1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText eddisplayRE;
    private EditText edusernameRE;
    private EditText edpasswordRE;
    private EditText edconpass;
    private Button BtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindWidget();
        setListener();

    }

    private void setListener() {
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                new Register(edusernameRE.getText().toString(),edpasswordRE.getText().toString(),eddisplayRE.getText().toString(),edconpass.getText().toString());
                } else {
                    Toast.makeText(RegisterActivity.this, "กรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private boolean validate() {
        String username = edusernameRE.getText().toString();
        String password = edpasswordRE.getText().toString();
        String passwordConfirm = edconpass.getText().toString();
        String displayName = eddisplayRE.getText().toString();

        if (username.isEmpty())
            return false;

        if (password.isEmpty())
            return false;

        if (passwordConfirm.equals(password))
            return false;

        if (displayName.isEmpty())
            return false;

        return true;
    }

    private void bindWidget() {
        eddisplayRE = (EditText) findViewById(R.id.tvdisplayRE);
        edusernameRE = (EditText) findViewById(R.id.tvusernameRE);
        edpasswordRE = (EditText) findViewById(R.id.tvpasswordRE);
        edconpass = (EditText) findViewById(R.id.tvconpasswordRE);
        BtnRegister = (Button) findViewById(R.id.btnregister);
    }

    private class Register extends AsyncTask<Void, Void, String> {
        private String username;
        private String password;
        private String passwordcon;
        private String display;

        public Register(String username, String display, String passwordcon, String password) {
            this.username = username;
            this.display = display;
            this.passwordcon = passwordcon;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add("password_con", passwordcon)
                    .add("display_name", display)
                    .build();
            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/signup.php")
                    .post(requestBody)
                    .build();

            try {
                response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    return response.body().string();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }

}
