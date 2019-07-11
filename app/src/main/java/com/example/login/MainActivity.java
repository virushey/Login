package com.example.login;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
BottomNavigationView bnaView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnaView=findViewById(R.id.bnaView);
        bnaView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bbs:
                        Toast.makeText(MainActivity.this, "bbs", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.chat:
                        Toast.makeText(MainActivity.this, "chat", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.heart:
                        Toast.makeText(MainActivity.this, "heart", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "account", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });


    }


}
