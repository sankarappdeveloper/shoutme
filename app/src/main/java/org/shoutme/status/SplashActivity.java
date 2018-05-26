package org.shoutme.status;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
String ss="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        SharedPreferences mref = getApplicationContext().getSharedPreferences("Login", 0);
//        SharedPreferences.Editor editor = mref.edit();
//        ss=mref.getString("number",null);


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

//                    if(ss.isEmpty())
//                    {
//                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                    }
//                    else {
                        Intent intent = new Intent(SplashActivity.this, CategoryActivity.class);
                        startActivity(intent);
                    //}
                }
            }
        };
        timerThread.start();
    }
}
