package com.example.it19022802_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txtID,txtName,txtAddress,txtnumber;
    Button btn1,btn2,btn3,btn4;

    DatabaseReference dbRef;
    Student std;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text
        txtID=findViewById(R.id.txtID);
        txtName=findViewById(R.id.txtName);
        txtAddress=findViewById(R.id.txtAddress);
        txtnumber=findViewById(R.id.txtnumber);
        //button
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);

        std=new Student();

    }

    @Override
    protected void onResume() {
        super.onResume();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef= FirebaseDatabase.getInstance().getReference().child("Student");
                try{
                    if(TextUtils.isEmpty(txtID.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter an Id",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the Name",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtAddress.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the Address",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtnumber.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the Contact Number",Toast.LENGTH_SHORT).show();
                    else{
                        //Take input from the user and assigning thenm to this insatance (std) of the Student
                        std.setID(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setAddress(txtAddress.getText().toString().trim());
                        std.setConNo(Integer.parseInt(txtnumber.getText().toString().trim()));
                        //insert in to the databse
                        //dbRef.push().setValue(std);
                        //Feedback in to user via a Toast
                        dbRef.child("std1").setValue(std);
                        Toast.makeText(getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_SHORT).show();
                        clearControls();
                    }

                }

                catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Enter Id ",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference readRef=FirebaseDatabase.getInstance().getRefefernce().child("Student").child("std1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            txtID.setText(dataSnapshot)

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public void clearControls(){
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtnumber.setText("");
    }









}
