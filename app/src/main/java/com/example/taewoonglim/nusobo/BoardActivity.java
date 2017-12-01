package com.example.taewoonglim.nusobo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<ImageDTO> imageDTOs = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private ImageView homeImageViewBtn;
    private ImageView plusImageViewBtn;
    private ImageView receiptImageViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        database = FirebaseDatabase.getInstance(); //singleton 패턴
        auth = FirebaseAuth.getInstance();


        recyclerView = (RecyclerView)findViewById(R.id.writeBoard_recycleView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);





        //홈 메뉴버튼
        homeImageViewBtn = (ImageView)findViewById(R.id.home_imageView);
        homeImageViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(BoardActivity.this, BoardActivity.class);
                BoardActivity.this.startActivity(registerIntent);
            }
        });



        //게시글작성 메뉴버튼
        plusImageViewBtn = (ImageView)findViewById(R.id.plus_imageView);
        plusImageViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(BoardActivity.this, PostingImagePreview.class);
                BoardActivity.this.startActivity(registerIntent);
            }
        });


        //영수증 관리 메뉴버튼
        receiptImageViewBtn = (ImageView)findViewById(R.id.receipt_imageView);
        receiptImageViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this, accountActivity.class);
                BoardActivity.this.startActivity(i);
                Toast.makeText(BoardActivity.this, "영수증 관리 페이지 이동", Toast.LENGTH_SHORT).show();
            }
        });


        //database 읽어오기, 옵저버 패턴 : 관찰 대상이 변하는 순간 이벤트를 처리함
                database.getReference().child("images").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        imageDTOs.clear(); // 수정될 때 데이터가 날라오기 때문에 clear()를 안해주면 쌓인다.
                        uidLists.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                            String uidKey = snapshot.getKey(); //images 데이터베이스에 있는 key값을 받아온다
                            imageDTOs.add(imageDTO);
                            uidLists.add(uidKey);
                        }
                        boardRecyclerViewAdapter.notifyDataSetChanged(); //갱신 후 새로고침이 필요

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    //inner class
    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            ((CustomViewHolder)holder).textView.setText(imageDTOs.get(position).title);
            ((CustomViewHolder)holder).textView2.setText(imageDTOs.get(position).description);

            Glide.with(holder.itemView.getContext()).load(imageDTOs.get(position).imageUrl).into(((CustomViewHolder)holder).imageVIew);
            ((CustomViewHolder)holder).starButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    onStarClicked(database.getReference().child("images").child(uidLists.get(position)));
                }
            });


            /*
            ((CustomViewHolder)holder).writeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String childPosition = uidLists.get(position).toString();
                    startActivity(new Intent(getApplicationContext(), WriteBoardActivity.class).putExtra("childPosition", childPosition) );

                }
            });
*/

            //imageDTOs안에 있는 것 중에 stars변수를 접근하여 내가 눌렀는지 안눌렀는지에 따른 상태를 표시해준다.
            if(imageDTOs.get(position).stars.containsKey(auth.getCurrentUser().getUid())) {
                //내가 눌렀으면 하트가 칠해진 것으로 표시
                ((CustomViewHolder) holder).starButton.setImageResource(R.drawable.ic_favorite_black_24dp);
            }else{
                //안눌렀으면 비어있는 상태로 표시
                ((CustomViewHolder) holder).starButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }


        }

        @Override
        public int getItemCount() {
            return imageDTOs.size();
        }


        //트랜잭션 : 여러사람이 같이 좋아요 같은 버튼을 눌렀을 시 동기화 에러를 방지하기 위함
        private void onStarClicked(DatabaseReference postRef) {
            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    ImageDTO imageDTO = mutableData.getValue(ImageDTO.class);
                    if (imageDTO == null) {
                        return Transaction.success(mutableData);
                    }
                    //좋아요 버튼을 누른 사람중에 내 아이디가 있느냐
                    if (imageDTO.stars.containsKey(auth.getCurrentUser().getUid())) {
                        // Unstar the post and remove self from stars
                        imageDTO.starCount = imageDTO.starCount - 1;
                        imageDTO.stars.remove(auth.getCurrentUser().getUid());
                    } else {
                        // Star the post and add self to stars
                        //게시물에 내 아이디가 없으면 좋아요를 누를 수 있도록 해준다.
                        imageDTO.starCount = imageDTO.starCount + 1;
                        imageDTO.stars.put(auth.getCurrentUser().getUid(), true);
                    }

                    // Set value and report transaction success
                    mutableData.setValue(imageDTO);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b,
                                       DataSnapshot dataSnapshot) {
                    // Transaction completed
                   // Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                }
            });
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageVIew;
            TextView textView;
            TextView textView2;
            ImageView starButton; //좋아요 버튼
            ImageView writeButton; //게시글 작성 버튼


            public CustomViewHolder(View view) {
                super(view);
                imageVIew = (ImageView)view.findViewById(R.id.item_imageView);
                textView = (TextView)view.findViewById(R.id.item_textView);
                textView2 = (TextView)view.findViewById(R.id.item_textView2);
                starButton = (ImageView)view.findViewById(R.id.item_starButton_imageView);
                writeButton = (ImageView)view.findViewById(R.id.writeBoard_writeBtn_imageView);
            }
        }
    }
}
