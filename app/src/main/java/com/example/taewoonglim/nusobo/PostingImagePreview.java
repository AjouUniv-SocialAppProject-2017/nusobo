package com.example.taewoonglim.nusobo;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/
public class PostingImagePreview extends AppCompatActivity {




    private GridView gallery_gridview;
    private ArrayList<File> gv_list;

    private ImageView backBtn;
    private ImageView nextBtn;
    private ImageView preView;
    private String preViewPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_image_preview);


        preView = (ImageView)findViewById(R.id.posting_preView_ImageView);

        //왼쪽 상단 백버튼
        backBtn = (ImageView)findViewById(R.id.posting_backBtn_imageView);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(PostingImagePreview.this, BoardActivity.class);
                PostingImagePreview.this.startActivity(registerIntent);
            }
        });

        //오른쪽 상단 다음(게스글 작성)으로 넘어가는 버튼
        nextBtn = (ImageView)findViewById(R.id.posting_nextBtn_imageView);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           //     Intent registerIntent = new Intent(PostingImagePreview.this, HomeActivity.class);
         //       PostingImagePreview.this.startActivity(registerIntent);
               // Toast.makeText(PostingImagePreview.this, "아직 다음 페이지를 만들지 않음", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("img", preViewPosition));
            }
        });


        //갤러리 커스텀 그리드뷰뷰
        gv_list = imageReader(Environment.getExternalStorageDirectory());

        gallery_gridview = (GridView) findViewById(R.id.gallery_gridview);
        gallery_gridview.setAdapter(new PostingImagePreview.GridAdapter());

        gallery_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                preViewPosition = gv_list.get(position).toString();
                Uri _uri = Uri.parse(preViewPosition);
                preView.setImageURI(_uri);

             //   startActivity(new Intent(getApplicationContext(), gallery_ImageView.class).putExtra("img", gv_list.get(position).toString()) );

                /*
                  // Intent i = getIntent();
        //File f = (File)i.getExtras().getParcelable("img");

        //HomeActivity에 있는 customgridview에서 넘어온다.
        String f = getIntent().getStringExtra("img");

        GalleryImageView = (ImageView)findViewById(R.id.gallery_ImageView);
        GalleryImageView.setImageURI(Uri.parse(f));


                 */
            }

        });



    }


    //갤러리 커스텀뷰 이너클래스
    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return gv_list.size();
        }

        @Override
        public Object getItem(int position) {
            return gv_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //layout 선택
            convertView = getLayoutInflater().inflate(R.layout.single_grid, parent,false);
            ImageView iv = convertView.findViewById(R.id.single_grid_imageView);

            iv.setImageURI(Uri.parse( getItem(position).toString() ));

            return convertView;
        }
    }

    ArrayList<File> imageReader(File root){
        ArrayList<File> a = new ArrayList<>();

        File [] files = root.listFiles();
        for(int i = 0; i < files.length; i++){
            if(files[i].isDirectory()){
                a.addAll(imageReader(files[i]));
            }
            else{
                if(files[i].getName().endsWith(".jpg")){
                    a.add(files[i]);
                }
            }
        }
        /*
        for(File f : files){
            f
         }
         */
        return a;
    }
}
