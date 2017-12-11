package com.example.taewoonglim.nusobo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class RegisterActivity extends AppCompatActivity {


   // private ArrayAdapter adapter;
   // private Spinner spinner;

    private EditText emailText;
    private EditText emailPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

    //    spinner = (Spinner)findViewById(R.id.majorSpinner);
     //   adapter = ArrayAdapter.createFromResource(this, R.array.major, R.layout.support_simple_spinner_dropdown_item);
     //   spinner.setAdapter(adapter);


        emailText = (EditText)findViewById(R.id.emailText);
        emailPassword = (EditText)findViewById(R.id.passwordText);

        Button emailLogin = (Button)findViewById(R.id.registerButton);


        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser(emailText.getText().toString(), emailPassword.getText().toString());
            }
        });
    }


    private void createUser(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                           // updateUI(user);

                            Toast.makeText(RegisterActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();

                            //회원가입 성공 시 메인 화면으로 이동
                            Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(registerIntent);

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                          //  Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            //        Toast.LENGTH_SHORT).show();
                            //updateUI(null);

                            //회원가입 실패시

                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}



