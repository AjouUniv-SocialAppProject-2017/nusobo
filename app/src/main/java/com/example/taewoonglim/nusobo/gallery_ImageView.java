package com.example.taewoonglim.nusobo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/
public class gallery_ImageView extends AppCompatActivity {

    //test
    private ImageView GalleryImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image_view);

       // Intent i = getIntent();
        //File f = (File)i.getExtras().getParcelable("img");

        //HomeActivity에 있는 customgridview에서 넘어온다.
        String f = getIntent().getStringExtra("img");

        GalleryImageView = (ImageView)findViewById(R.id.gallery_ImageView);
        GalleryImageView.setImageURI(Uri.parse(f));



    }
}
