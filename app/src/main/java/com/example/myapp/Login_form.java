package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_form extends AppCompatActivity
{

    EditText txtEmail, txtPassword;
    Button btn_login;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login Form");

        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.buttonLogin);

        setUpDisplayFields();

        firebaseAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login_form.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login_form.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(Login_form.this, "Password Too Short", Toast.LENGTH_SHORT).show();
                }


                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login_form.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    Toast.makeText(Login_form.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(Login_form.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    //showErrorDialog("There was a problem signing in");
                                }
                            }
                        });
            }
        });
    }

    public void setUpDisplayFields(){
        SharedPreferences prefs = getSharedPreferences(Signup_form.CHAT_PREFS, MODE_PRIVATE);
        String emailId = prefs.getString(Signup_form.DISPLAY_EMAIL_KEY, null);
        String password = prefs.getString(Signup_form.DISPLAY_PASSWORD_KEY, null);

        txtEmail.setText(emailId);
        txtPassword.setText(password);
    }

    public void btn_signupForm(View view){

        Intent intent = new Intent(getApplicationContext(),Signup_form.class);
        startActivity(intent);
    }
}
