package com.hs.sparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    TextView Slot_Name,Max_slots,Avail_Slots,charges;
    DatabaseReference databaseReference;
    Button BookSlot;
    String avail_Slots,slot_price;
    String Slot_Loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

       final String slot_location = getIntent().getStringExtra("title");
       //Handling Your Location
       if(slot_location.equals("Your Location")){
           return;
       }



       Slot_Loc = slot_location;
//        title.setText(title_from_maps);
        Slot_Name = (TextView)findViewById(R.id.slot_name);
        Max_slots = (TextView)findViewById(R.id.max_slot);
        Avail_Slots = (TextView)findViewById(R.id.avail_slot);
        charges = (TextView)findViewById(R.id.price);
        BookSlot = (Button)findViewById(R.id.Book_Button);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("ParkingSlots").child(slot_location);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String max_Slots = dataSnapshot.child("MaxSlots").getValue().toString();
                 avail_Slots = dataSnapshot.child("AvailSlots").getValue().toString();
                 slot_price = dataSnapshot.child("Charges").getValue().toString();
                Slot_Name.setText(slot_location);
                Max_slots.setText(max_Slots);
                Avail_Slots.setText(avail_Slots);
                charges.setText(slot_price);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        BookButtonListner();
    }

    private void BookButtonListner() {
        BookSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int as= Integer.parseInt(avail_Slots);
                if(as == 0){
                    Toast.makeText(DetailsActivity.this,"No Slots Available , Select other Location",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(DetailsActivity.this,ConfirmBooking.class);
                    i.putExtra("location",Slot_Loc);
                    i.putExtra("AvailSlots",avail_Slots);
                    i.putExtra("Charges",slot_price);
                    startActivity(i);

                }
            }
        });
    }
}
