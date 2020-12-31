package com.chandan.orahi_assigment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int otp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler(Looper.getMainLooper());
        final Random random = new Random();
        final TextView mobilenum = findViewById(R.id.mobilenumtextview);
        final TextView otptext = findViewById(R.id.otptextview);
        Button getotpbtn = findViewById(R.id.otpbtn);
        Button signinbtn = findViewById(R.id.loginbtn);
        findViewById(R.id.constraintLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        getotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobilenum.getText().length() == 10 || (mobilenum.getText().toString().startsWith("+91") && mobilenum.getText().length() == 13)) {
                    otp = random.nextInt(9999 - 1000 + 1) + 1000;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Your OTP is:" + otp, Toast.LENGTH_LONG).show();
                        }
                    }, 5000);
                }else
                    Toast.makeText(MainActivity.this, "Please!! Enter valid mobile number", Toast.LENGTH_LONG).show();
            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! otptext.getText().toString().isEmpty()){
                    if(Integer.parseInt(otptext.getText().toString())==otp){
                        startActivity(new Intent(MainActivity.this,HomeScreen.class));
                    }else
                        Toast.makeText(MainActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(MainActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}