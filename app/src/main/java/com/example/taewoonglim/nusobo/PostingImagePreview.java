package com.example.taewoonglim.nusobo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Stack;


public class PostingImagePreview extends AppCompatActivity {




    private GridView gallery_gridview;
    private ArrayList<File> gv_list;

    private ImageView backBtn;
    private ImageView nextBtn;
    private ImageView preView;
    private String preViewPosition;


    //커스텀 그리드 갤러리뷰
    public String basePath = null;
    public GridView mGridView;
    public CustomGalleryAdapter mCustomImageAdapter;


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


        /*
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
            }
        }

        basePath = mediaStorageDir.getPath();

        mGridView = (GridView)findViewById(R.id.gallery_gridview); // .xml의 GridView와 연결
        mCustomImageAdapter = new CustomGalleryAdapter(this, basePath); // 앞에서 정의한 Custom Image Adapter와 연결
        mGridView.setAdapter(mCustomImageAdapter); // GridView가 Custom Image Adapter에서 받은 값을 뿌릴 수 있도록 연결
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), mCustomImageAdapter.getItemPath(position), Toast.LENGTH_LONG).show();
            }
        });
    }

*/







        String myCapture = Environment.getExternalStorageDirectory() + "/DCIM/Screenshots";
        File temp_myCaptures = new File(myCapture);


        gv_list = imageReader(temp_myCaptures);

        Log.i("sdfjalskdjflkasjdf", Environment.getExternalStorageDirectory()+ " : " + temp_myCaptures);




        //갤러리 커스텀 그리드뷰뷰
        gallery_gridview = (GridView) findViewById(R.id.gallery_gridview);
        gallery_gridview.setAdapter(new PostingImagePreview.GridAdapter());

        gallery_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                preViewPosition = gv_list.get(position).toString();

                Uri _uri = Uri.parse(preViewPosition);
                preView.setImageURI(_uri);

             //   startActivity(new Intent(getApplicationContext(), gallery_ImageView.class).putExtra("img", gv_list.get(position).toString()) );


                  // Intent i = getIntent();
        //File f = (File)i.getExtras().getParcelable("img");

        //HomeActivity에 있는 customgridview에서 넘어온다.
        String f = getIntent().getStringExtra("img");

    //    GalleryImageView = (ImageView)findViewById(R.id.gallery_ImageView);
      //  GalleryImageView.setImageURI(Uri.parse(f));



            }

        });


    }


    /*
    public View getView(int position, View convertView, ViewGroup parent){

        ImageView _imageview;

        if(convertView == null){

            _imageview = new ImageView(getApplicationContext());
        }else{

            _imageview = (ImageView)convertView;

        }


    }
*/



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

            iv.setImageURI(Uri.parse(getItem(position).toString()));

            return convertView;
        }
    }

    ArrayList<File> imageReader(File root){
        Stack<File> s = new Stack<>();

        File [] files = root.listFiles();
        for(int i = 0; i < files.length; i++){
            if(files[i].isDirectory()){
                s.addAll(imageReader(files[i]));
            }
            else{
                if(files[i].getName().endsWith(".png")){
                    s.add(files[i]);
                }
            }
        }
        /*
        for(File f : files){
            f
         }
        */

        ArrayList<File> a = new ArrayList<>();

        while(!s.empty()){
            a.add(s.pop());
        }


        Log.i("sdfjalskdjflkasjdf2", s.size() + " : " );

        return a;
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }



    // 파일명 찾기
    private String getName(Uri uri)
    {
        String[] projection = { MediaStore.Images.ImageColumns.DISPLAY_NAME };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = 	cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
