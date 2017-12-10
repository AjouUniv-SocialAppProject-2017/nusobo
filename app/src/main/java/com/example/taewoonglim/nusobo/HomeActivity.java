package com.example.taewoonglim.nusobo;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity{
       // implements NavigationView.OnNavigationItemSelectedListener {

    private static final int GALLERY_CODE = 10;
    private TextView nameTextView;
    private TextView emailTextView;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private ImageView imageView;
    private EditText title;
    private EditText description;
    private String imagePath;

    private ImageView contentBackImageView;
    private ImageView contentUploadImageView;



    //타임스탬프에 적용하기 위해
    //시간을 받아올 때는 유니스 시간이므로 사람이 알아볼 수 있도록 변환해주어야한다.
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd. hh:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        //이미지뷰 intent로 받아오기
        String f = getIntent().getStringExtra("img");
        imagePath = f;
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageURI(Uri.parse(imagePath));

        Toast.makeText(HomeActivity.this, " "+ f, Toast.LENGTH_LONG).show();

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();


        title = (EditText)findViewById(R.id.title);
        description  = (EditText)findViewById(R.id.description);


        //뒤로가기 버튼
        contentBackImageView = (ImageView)findViewById(R.id.contentBack_ImgeView);
        contentBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(HomeActivity.this, PostingImagePreview.class);
                HomeActivity.this.startActivity(registerIntent);
            }
        });

        //업로드
        contentUploadImageView = (ImageView)findViewById(R.id.contentUpload_ImageView);
        contentUploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(imagePath);
                Toast.makeText(HomeActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
                Intent registerIntent = new Intent(HomeActivity.this, BoardActivity.class);
                HomeActivity.this.startActivity(registerIntent);

            }
        });


        //권한부여
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }



    }


    public String getPath(Uri uri){
        String []proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    private void upload(String uri){

        //파일 업로드 후 서버로 이동할 서버 주소
        StorageReference storageRef = storage.getReferenceFromUrl("gs://socialapp-nuboso.appspot.com");

        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

    // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.imageUrl = downloadUrl.toString();
                imageDTO.title = title.getText().toString();
                imageDTO.description = description.getText().toString();
                imageDTO.uid = mAuth.getCurrentUser().getUid();
                imageDTO.userId = mAuth.getCurrentUser().getEmail();

                //타임스탬프
                long nowUnixtime = System.currentTimeMillis();
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                Date date = new Date(nowUnixtime);
                imageDTO.timeStamp = simpleDateFormat.format(date);


                Toast.makeText(HomeActivity.this, "홓몸", Toast.LENGTH_LONG).show();
                database.getReference().child("images").push().setValue(imageDTO);

            }
        });
    }



}
