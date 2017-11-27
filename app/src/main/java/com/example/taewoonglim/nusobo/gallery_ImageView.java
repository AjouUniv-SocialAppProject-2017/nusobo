package com.example.taewoonglim.nusobo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

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
