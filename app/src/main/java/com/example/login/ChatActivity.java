package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {
    RecyclerView list;
    EditText edtContent;
    Button btnAdd;
    FirebaseDatabase db;
    DatabaseReference ref;
    ArrayList<ChatVO> array;
    ChatAdapter adapter;
    String strUser;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String toUserToken,fromUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        list=findViewById(R.id.list);
        edtContent=findViewById(R.id.edtContent);
        btnAdd=findViewById(R.id.btnAdd);



        //사용자 받아오기
        Intent intent=getIntent();

        //FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        strUser=intent.getStringExtra("user");

        getSupportActionBar().setTitle(strUser);
        list.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        list.setLayoutManager(manager);

        array=new ArrayList<ChatVO>();
        adapter=new ChatAdapter(this,array,strUser);
        list.setAdapter(adapter);

        db=FirebaseDatabase.getInstance();
        ref=db.getReference("Test");

        //입력한 대화내용 출력
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String strDate=dataSnapshot.getKey();
                ChatVO vo=dataSnapshot.getValue(ChatVO.class);
                vo.setWdate(strDate);
                array.add(vo);

                list.scrollToPosition(array.size()-1);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //대화 내용 입력
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strContent=edtContent.getText().toString().trim();
                if(strContent.equals("")){
                    Toast.makeText(ChatActivity.this, "내용을 입력하세요!", Toast.LENGTH_SHORT).show();
                }else {
                    String strDate=sdf.format(new Date());
                    ref=db.getReference("Test").child(strDate);
                    ChatVO vo=new ChatVO();
                    vo.setContent(strContent);
                    vo.setEmail(strUser);
                    ref.setValue(vo);
                }
                getToken();

                edtContent.setText("");
            }
        });

    }


    //토큰 받기
    void getToken(){
        //알림을 보내기위해 받을 유저의 토큰값 받아오기
        ref = db.getReference("users").child("/TestUser2");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserVO vo=dataSnapshot.getValue(UserVO.class);
                System.out.println("................" + vo.getToken());
                toUserToken=vo.getToken();
                fromUser=vo.getFromUser();
                sendGcm();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    //푸시알람
    void sendGcm(){
        Gson gson=new Gson();
        NotificationVO notification=new NotificationVO();
        System.out.println("touser....................."+toUserToken);
        notification.setTo(toUserToken);
        notification.notification.title=fromUser;
        notification.notification.text="님으로 부터 메세지가 도착했습니다.";
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf8"),gson.toJson(notification));

        Request request=new Request.Builder()
                .header("Content-Type","application/json")
                .addHeader("Authorization","key=AIzaSyCOGljuFf47ROFYUougsZqozUOwt0NMeYM")
                .url("https://gcm-http.googleapis.com/gcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        //String userid=strUser.substring(0, strUser.indexOf("@"));

        //FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        db=FirebaseDatabase.getInstance();
//        ref=db.getReference("users/").child(userid);
//        String fromUser=ref.getKey();
//        String token=db.getReference("users/"+fromUser+"/"+ref.getKey()).toString();
//        //String token="eZS3HmvDGvU:APA91bG8FZWLi3KMzWiJwpn0ogCZ9CVwE4RgCeCB0e3ZU2DyS4mvUWeLlwLo0389G1ujTK5o0Novy0UetwkdI4CzWkp5fo_U--OPwD83Qm0m3qSaR0_ZPjLGEOyRc8_jH28KvCHGfUEU";
//        System.out.println("...............token??"+token);
//        Gson gson=new Gson();
//        NotificationVO vo=new NotificationVO();
//        vo.setTo(fromUser);
//        vo.notification.title=fromUser;
//        vo.notification.text="님으로 부터 메세지가 도착했습니다.";
//        System.out.println("................................."+vo.toString());
//        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf8"),gson.toJson(vo));
//
//        Request request=new Request.Builder()
//                .header("Content-Type","application/json")
//                .addHeader("Authorization","key=AIzaSyCOGljuFf47ROFYUougsZqozUOwt0NMeYM")
//                .url("https://gcm-http.googleapis.com/gcm/send")
//                .post(requestBody)
//                .build();
//        OkHttpClient okHttpClient=new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
    }


    //액션메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
