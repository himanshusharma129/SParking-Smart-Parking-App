package com.hs.sparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerHome extends AppCompatActivity {

    Button new_book_button,ExitSlotBut;
    private FirebaseUser mcurrentuser;
    private DatabaseReference mData,bookedRef,DBref2;
    String avail2;

    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        new_book_button = (Button)findViewById(R.id.new_booking);
        ExitSlotBut = (Button)findViewById(R.id.exit_slot);

        mcurrentuser= FirebaseAuth.getInstance().getCurrentUser();



        newBookingListner();
        exitSlotListner();
    }

    private void exitSlotListner() {
        ExitSlotBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uid = mcurrentuser.getUid();
                mData = FirebaseDatabase.getInstance().getReference().child("Customers").child(uid);
                mData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s = dataSnapshot.child("Booked").getValue().toString();


                        unBookSlot(s);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void unBookSlot(final String s) {

        DBref2 = FirebaseDatabase.getInstance().getReference().child("ParkingSlots").child(s);
        DBref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Toast.makeText(CustomerHome.this,s,Toast.LENGTH_SHORT).show();
                if (dataSnapshot.child("AvailSlots").getValue() == null)
                    return;
                String avail = dataSnapshot.child("AvailSlots").getValue().toString();
                //Toast.makeText(CustomerHome.this,s,Toast.LENGTH_SHORT).show();

                int curr = Integer.parseInt(avail);
                curr++;
                avail = Integer.toString(curr);
                avail2 = avail;

            fund2(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return;
    }

    private void fund2(final String s)
    {

        bookedRef = FirebaseDatabase.getInstance().getReference().child("ParkingSlots").child(s).child("AvailSlots");
        bookedRef.setValue(avail2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CustomerHome.this,"Parking slot Exit Successfull",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(CustomerHome.this,"Parking slot Exit Error",Toast.LENGTH_SHORT).show();

                }



            }
        });



    }

    private void newBookingListner() {
        new_book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }


}
