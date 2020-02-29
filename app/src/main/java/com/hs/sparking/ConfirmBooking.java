package com.hs.sparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

public class ConfirmBooking extends AppCompatActivity {

    Button Confirm_Button;
    private FirebaseUser mcurrentuser;
    EditText hrs;

    DatabaseReference databaseReference,mData;
    public int numhrs;
    public int bill,pricei;
    public String charges;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        final String slot_location = getIntent().getStringExtra("location");
        final String updated_slots = getIntent().getStringExtra("AvailSlots");
        final String Chargesp = getIntent().getStringExtra("Charges");
        charges = Chargesp;
        pricei = Integer.parseInt(charges);
        mcurrentuser= FirebaseAuth.getInstance().getCurrentUser();
        final String uid = mcurrentuser.getUid();
        userid = uid;

        String bookingId = slot_location+updated_slots;
        final int as = Integer.parseInt(updated_slots) -1;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ParkingSlots").child(slot_location).child("AvailSlots");

        hrs=(EditText)findViewById(R.id.hours);


        Confirm_Button = (Button)findViewById(R.id.confirm_book_button);
        Confirm_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numhrs = Integer.parseInt(hrs.getText().toString());
                bill = numhrs*pricei;
                String  st = Integer.toString(bill);
                Toast.makeText(ConfirmBooking.this,"Your Bill Amounts to :"+st,Toast.LENGTH_SHORT).show();
                databaseReference.setValue(as).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ConfirmBooking.this, "Booking Confirmed",
                                    Toast.LENGTH_SHORT).show();

                            //uploading
                            mData = FirebaseDatabase.getInstance().getReference().child("Customers").child(uid).child("Booked");
                            mData.setValue(slot_location).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                            Intent intent = new Intent(ConfirmBooking.this,CustomerHome.class);
                           startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }


}
