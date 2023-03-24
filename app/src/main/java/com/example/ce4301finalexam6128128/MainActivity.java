package com.example.ce4301finalexam6128128;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    Button buttonON;
    Button buttonOFF;

    SeekBar seekBar;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonON = findViewById(R.id.on);
        buttonOFF = findViewById(R.id.off);

        seekBar = (SeekBar)findViewById(R.id.seekBar1);

        textView1 = findViewById(R.id.current_view);
        textView2 = findViewById(R.id.errortemp);
        textView3 = findViewById(R.id.mvouttemp);

        dref = FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = String.valueOf(snapshot.child("Status/CurrentTemp").getValue());
                textView1.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = String.valueOf(snapshot.child("Status/ErrorTemp").getValue());
                textView2.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = String.valueOf(snapshot.child("Status/MVOutput").getValue());
                textView3.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        buttonOFF.setOnClickListener((view) -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Status/Control");
            myRef.setValue( "OFF");
        });

        buttonON.setOnClickListener((view) -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Status/Control");
            myRef.setValue("ON");
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Settings/Temperature");
                myRef.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}