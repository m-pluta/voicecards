package com.thundercandy.epq;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout passwordField = (TextInputLayout) findViewById(R.id.PasswordField);
        passwordField.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
                if (txtPassword.getTransformationMethod() == null) {
                    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    txtPassword.setTransformationMethod(null);
                }
            }
        });
    }
}
