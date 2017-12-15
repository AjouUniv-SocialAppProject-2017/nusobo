package com.example.taewoonglim.nusobo;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/

//회원가입 register에 대한 코드입니다.
public class RegisterActivity extends AppCompatActivity {


    private static final int PICK_FROM_ALBUM = 10;


    private EditText emailText;
    private EditText emailPassword;
    private EditText userName;
    private FirebaseAuth mAuth;
    private ImageView profile;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

    //    spinner = (Spinner)findViewById(R.id.majorSpinner);
     //   adapter = ArrayAdapter.createFromResource(this, R.array.major, R.layout.support_simple_spinner_dropdown_item);
     //   spinner.setAdapter(adapter);


        profile = (ImageView)findViewById(R.id.register_myImage_ImageView);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });


        emailText = (EditText)findViewById(R.id.emailText);
        emailPassword = (EditText)findViewById(R.id.passwordText);
        userName = (EditText)findViewById(R.id.register_userName);

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

                            final String uid =task.getResult().getUser().getUid();
                            FirebaseStorage.getInstance().getReference().child("authusers").child(uid)
                                    .putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    @SuppressWarnings("VisibleForTests")
                                    String imageUrl = task.getResult().getDownloadUrl().toString();

                                    UserModelAuth usermodelauth = new UserModelAuth();
                                    usermodelauth.userName = userName.getText().toString();
                                    usermodelauth.profileImageUrl = imageUrl;


                                    FirebaseDatabase.getInstance().getReference().child("authusers").child(uid).setValue(usermodelauth);

                                }
                            });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK){

            profile.setImageURI(data.getData()); // 가운데 뷰를 바꿈
            imageUri = data.getData();// 이미지 경로 원본

        }
    }
}



