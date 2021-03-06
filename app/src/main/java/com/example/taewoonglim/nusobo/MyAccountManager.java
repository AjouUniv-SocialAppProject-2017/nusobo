package com.example.taewoonglim.nusobo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class MyAccountManager extends AppCompatActivity {


    private static final int PICK_FROM_ALBUM = 10;

    private ImageView preImageViewBtn;
    private ImageView newMyImage;
    private EditText myNewNick;
    private EditText myNewPassword;
    private Button modifyMyPorfileBtn;


    private FirebaseAuth mAuth;
    private FirebaseStorage mStroage;
    private DatabaseReference mDatabase;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_manager);

        mAuth = FirebaseAuth.getInstance();
        mStroage = FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        preImageViewBtn = (ImageView)findViewById(R.id.my_account_manager_contentBack_ImgeView);
        newMyImage = (ImageView)findViewById(R.id.my_account_manager_myImage_ImageView);
        myNewNick = (EditText)findViewById(R.id.my_account_manager_userName);
        myNewPassword = (EditText)findViewById(R.id.my_account_manager_passwordText);
        modifyMyPorfileBtn = (Button)findViewById(R.id.my_account_manager_registerButton);



        FirebaseDatabase.getInstance().getReference().child("authusers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String myUid = snapshot.getKey();
                    UserModelAuth tempUser = snapshot.getValue(UserModelAuth.class);

                    if(myUid.equals(mAuth.getCurrentUser().getUid())){

                        newMyImage.setBackgroundColor(Color.parseColor("#00000000"));
                        Glide.with(getApplicationContext()).load(tempUser.profileImageUrl).apply(new RequestOptions().circleCrop()).into(newMyImage);
                        myNewNick.setHint(tempUser.userName);
                        break;

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Post post = dataSnapshot.getValue(Post.class);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);
*/

        preImageViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MyAccountManager.this, BoardActivity.class);
                MyAccountManager.this.startActivity(i);
               // Toast.makeText(MyAccountManager.this, "BoardActivity로 이동", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        newMyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });


        modifyMyPorfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyUserProfile(myNewNick.getText().toString(), myNewPassword.getText().toString());
                modifyPassword(myNewPassword.getText().toString());



            }
        });

    }

    private void modifyUserProfile(final String _nick, String _password){
      //  mAuth.


        mStroage.getReference().child("authusers").child(mAuth.getCurrentUser().getUid())
                .putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                @SuppressWarnings("VisibleForTests")
                String imageUrl = task.getResult().getDownloadUrl().toString();

                UserModelAuth usermodelauth = new UserModelAuth();
                usermodelauth.userName = _nick;
                usermodelauth.profileImageUrl = imageUrl;

                FirebaseDatabase.getInstance().getReference().child("authusers").child(mAuth.getCurrentUser().getUid()).setValue(usermodelauth);


                Toast.makeText(MyAccountManager.this, "수정완료", Toast.LENGTH_SHORT).show();
                //수정 후 로그아웃
               // FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MyAccountManager.this, BoardActivity.class);
                startActivity(intent);
                finish();

            }
        });

        /*
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
*/



    }

    private void delete_content(){

//        mStroage.getReference().child("")
    }

    private void modifyPassword(String _newPassword){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = _newPassword;

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           // Toast.makeText(MyAccountManager.this, "비밀번호 변경 성공", Toast.LENGTH_SHORT).show();
                          //  Log.d(TAG, "User password updated.");
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//   Glide.with(getApplicationContext()).load(tempUser.profileImageUrl).apply(new RequestOptions().circleCrop()).into(newMyImage);

        if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK){

         //   newMyImage.setImageURI(data.getData()); // 가운데 뷰를 바꿈
            imageUri = data.getData();// 이미지 경로 원본
            Glide.with(getApplicationContext()).load(imageUri).apply(new RequestOptions().circleCrop()).into(newMyImage);

        }
    }
}
