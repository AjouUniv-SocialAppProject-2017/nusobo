package com.example.taewoonglim.nusobo;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 10;
    private GoogleApiClient mGoogleApiClient;

    private EditText emailText;
    private EditText passworkdText;
    private Button loginButton;

    private FirebaseAuth.AuthStateListener mAuthListener; //로그인이 되었는지 안되었는지 확인해준다.
    private FirebaseAuth mAuth; //파이어베이스 관리

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestSmsPermission();
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        emailText = (EditText)findViewById(R.id.login_email_editText);
        passworkdText = (EditText)findViewById(R.id.login_passwordText_editText);

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton googleLoginBtn = (SignInButton)findViewById(R.id.googleLogin);
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });




        //로그인 언터페이스 리스너
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    //로그인이 되어 있으면 여기부터 바로시작
                    Intent intent = new Intent(LoginActivity.this, BoardActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //로그아웃

                }
            }
        };

        loginButton = (Button)findViewById(R.id.login_Button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(emailText.getText().toString(), passworkdText.getText().toString());
            }
        });



    }



    private void requestSmsPermission() {
        String permission = android.Manifest.permission.READ_SMS;
        String permission2 =android.Manifest.permission.INTERNET;
        String permission3= android.Manifest.permission.READ_EXTERNAL_STORAGE;
        String permission4=android.Manifest.permission.GET_ACCOUNTS;

        //String permission5=android.Manifest.permission.
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[4];
            permission_list[0] = permission;
            permission_list[1]=permission2;
            permission_list[2]=permission3;
            permission_list[3]=permission4;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"permission granted@@@@", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(this,"permission not granted@@@@", Toast.LENGTH_SHORT).show();
            }
        }

    }





    private void loginUser(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //로그인 실패
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "로그인 실패 : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
       // Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          //  Log.d(TAG, "signInWithCredential:success");
                       //     FirebaseUser user = mAuth.getCurrentUser();
                       //    updateUI(user);

                            Toast.makeText(LoginActivity.this, "아이디 생성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
                                   // Toast.LENGTH_SHORT).show();
                           // updateUI(null);


                            Toast.makeText(LoginActivity.this, "실패.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //로그인이 실패했을 때
    }

    @Override
    public void onStart(){
        super.onStart();

        Log.i("asjdfkl;ajsldf", "짜잔");
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
