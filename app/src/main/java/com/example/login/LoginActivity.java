package com.example.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser,edtPassword;
    Button btnRegister, btnLogin, btnCancel;
    private FirebaseAuth mAuth;
    String strUser,strPassword;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser=findViewById(R.id.edtUser);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnCancel=findViewById(R.id.btnCancel);
        mAuth = FirebaseAuth.getInstance();

        //passPushTokenToServer();
        //사용자 등록
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                strEmail=edtEmail.getText().toString();
//                strPassword=edtPassword.getText().toString();
//                //registerUser(strEmail,strPassword);
//                edtEmail.setText("");
//                edtPassword.setText("");
//            }
//        });

        //사용자 로그인
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUser=edtUser.getText().toString();
                strPassword=edtPassword.getText().toString();
                loginUser(strUser);

            }
        });

    }
    //firebase에 user값 넣기
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser=mAuth.getCurrentUser();
//    }
    //사용자 등록
//    public void registerUser(String strEmail,String strPassword){
//        mAuth.createUserWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    FirebaseUser user=mAuth.getCurrentUser();
//                    Toast.makeText(LoginActivity.this, "createUserWithEmail.success", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
    //사용자 로그인
    private void loginUser(String user){
        Toast.makeText(this, "user="+strUser, Toast.LENGTH_SHORT).show();
        updateProfile();
        Intent intent=new Intent(LoginActivity.this,ChatActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
//        mAuth.signInWithCustomToken(user).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(LoginActivity.this, "signInWithEmail:success", Toast.LENGTH_SHORT).show();
//                    //FirebaseUser user=mAuth.getCurrentUser();
//                    //updateProfile();
//                    //Intent intent=new Intent(LoginActivity.this,ChatActivity.class);
//                    //startActivity(intent);
//                    finish();
//                }else {
//                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        mAuth.signInWithEmailAndPassword(user).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(LoginActivity.this, "signInWithEmail:success", Toast.LENGTH_SHORT).show();
//                    FirebaseUser user=mAuth.getCurrentUser();
//                    updateProfile();
//                    Intent intent=new Intent(LoginActivity.this,ChatActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else {
//                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
    //토큰 값 받기
    private void updateProfile(){
        if(strUser != null){
            UserVO vo = new UserVO();
            //vo.setFromUser(strEmail.substring(0, strEmail.indexOf("@")));
            //vo.setFromUser(FirebaseInstanceId.getInstance().getId());
            vo.setFromUser(strUser);
            vo.setToken(FirebaseInstanceId.getInstance().getToken());
            System.out.println(vo.toString());
            db = FirebaseDatabase.getInstance();
            db.getReference("users").child(vo.getFromUser()).setValue(vo);
        }
    }

}
