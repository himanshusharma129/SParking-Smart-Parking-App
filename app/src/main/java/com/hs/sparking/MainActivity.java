package com.hs.sparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button Cus_Reg_But,Cus_Login_But,Admin_Reg_But,Admin_log_But;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCusLoginListner();
        onAdminLoginListner();
        onCusRegListner();
        onAdminRegListner();


    }


    public void onCusRegListner(){
        Cus_Reg_But = (Button)findViewById(R.id.cus_reg);
        Cus_Reg_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cusintetn = new Intent(MainActivity.this,Customer_Registration.class);
                startActivity(cusintetn);
            }
        });
    }

    public void onCusLoginListner(){
        Cus_Login_But = (Button)findViewById(R.id.cs_login);
        Cus_Login_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Customer_login.class);
                startActivity(intent);
            }
        });
    }
    public void onAdminLoginListner(){

    }
    public void onAdminRegListner(){

    }


}
