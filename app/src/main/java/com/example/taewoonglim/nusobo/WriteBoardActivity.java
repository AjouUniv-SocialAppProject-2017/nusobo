package com.example.taewoonglim.nusobo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WriteBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;


    private ImageView writeBoard_BackBtnImageView;
    private ImageView writeBoard_WriteBtnImageView;

    private EditText writeBoard_writeEditText;

    private String nowChildPostion;


    //타임스탬프에 적용하기 위해
    //시간을 받아올 때는 유니스 시간이므로 사람이 알아볼 수 있도록 변환해주어야한다.
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd. hh:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_board);
        database = FirebaseDatabase.getInstance(); //singleton 패턴
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        nowChildPostion = getIntent().getStringExtra("childPostion");

        recyclerView = (RecyclerView)findViewById(R.id.writeBoard_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final WriteBoardRecyclerViewAdapter writeBoardRecyclerViewAdapter = new WriteBoardRecyclerViewAdapter(nowChildPostion);
        recyclerView.setAdapter(writeBoardRecyclerViewAdapter);


        Toast.makeText(WriteBoardActivity.this, " "+ nowChildPostion, Toast.LENGTH_SHORT).show();

        //뒤로가기 버튼
        writeBoard_BackBtnImageView = (ImageView)findViewById(R.id.writeBoard_backBtn_imageView);
        writeBoard_BackBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(WriteBoardActivity.this, BoardActivity.class);
                WriteBoardActivity.this.startActivity(i);

            }
        });


        //댓글 작성 버튼
        writeBoard_WriteBtnImageView = (ImageView)findViewById(R.id.writeBoard_writeBtn_imageView);
        writeBoard_WriteBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload(nowChildPostion);
                Toast.makeText(WriteBoardActivity.this, "댓글작성완료", Toast.LENGTH_SHORT).show();
              //  upload(database.getReference().child("images").child(nowChildPostion).toString());
            }
        });


        writeBoard_writeEditText = (EditText)findViewById(R.id.writeBoard_reply_EditText);


    }


    //inner class
    class WriteBoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private List<MyWirteDTO> myWirteDTOs = new ArrayList<>();
        //private List<String> uidLists = new ArrayList<>();
        private String nowPos;

        public WriteBoardRecyclerViewAdapter(){
            //디폴트 생성자
        }

        public WriteBoardRecyclerViewAdapter(String _nowPos) {

            nowPos = _nowPos;
            FirebaseDatabase.getInstance().getReference().child("images").child(nowPos).child("reply").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myWirteDTOs.clear(); // 수정될 때 데이터가 날라오기 때문에 clear()를 안해주면 쌓인다.
             //       uidLists.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MyWirteDTO myWirteDTO = snapshot.getValue(MyWirteDTO.class);
                        //  String uidKey = snapshot.getKey(); //images 데이터베이스에 있는 key값을 받아온다
                        myWirteDTOs.add(myWirteDTO);
                        // uidLists.add(uidKey);
                    }

                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            ((CustomViewHolder)holder).userIdTextView.setText(myWirteDTOs.get(position).userId);
            ((CustomViewHolder)holder).dscriptionTextView.setText(myWirteDTOs.get(position).description);
            ((CustomViewHolder)holder).timestampTextView.setText(myWirteDTOs.get(position).timeStamp);
        }


        @Override
        public int getItemCount() {
            return myWirteDTOs.size();
        }


        private class CustomViewHolder extends RecyclerView.ViewHolder {

            public TextView userIdTextView;
            public TextView dscriptionTextView;
            public TextView timestampTextView;


            public CustomViewHolder(View view) {
                super(view);
                userIdTextView = (TextView)view.findViewById(R.id.itempReply_userID_Textview);
                dscriptionTextView = (TextView)view.findViewById(R.id.itemReply_Description_TextView);
                timestampTextView = (TextView)view.findViewById(R.id.itemReply_timeStamp_TextView);

            }
        }
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

                //타임스탬프
                long nowUnixtime = System.currentTimeMillis();
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                Date date = new Date(nowUnixtime);



                //게시글이 있어야지 (실패해야지) 댓글을 쓸 수 있다.
                MyWirteDTO myWirteDTO = new MyWirteDTO();
                myWirteDTO.userId = auth.getCurrentUser().getEmail();
                myWirteDTO.description = writeBoard_writeEditText.getText().toString();
                myWirteDTO.timeStamp = simpleDateFormat.format(date);




                database.getReference().child("images").child(nowChildPostion).child("reply").push().setValue(myWirteDTO);

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                Toast.makeText(WriteBoardActivity.this, "댓글작성 실패~", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
