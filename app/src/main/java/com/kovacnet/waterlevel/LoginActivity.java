package com.kovacnet.waterlevel;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.activeandroid.ActiveAndroid;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private ProgressBar progressBar;
    private EditText usernameEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActiveAndroid.initialize(this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progressBar.setVisibility(View.VISIBLE);

                RestClient.getInstance().loginUser(
                    getApplicationContext(),
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    new RestClient.RequestListener() {
                        @Override
                        public void onSuccess() {
                            startActivity(new Intent(LoginActivity.this, MeasurementsActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure() {
                            Snackbar.make(v, "Login unsuccessful!",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                            progressBar.setVisibility(View.GONE);
                            usernameEditText.setText("");
                            passwordEditText.setText("");
                            usernameEditText.requestFocus();
                        }
                    });
            }
        });
    }

}
